package com.jgw.supercodeplatform.trace.common.util;

import com.alibaba.fastjson.JSON;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.config.redis.RedisUtil;
import com.jgw.supercodeplatform.trace.constants.RedisKey;
import com.jgw.supercodeplatform.trace.dao.mapper1.batchglobalseq.LogisticsGlobalseqMapper;
import com.jgw.supercodeplatform.trace.exception.LogisticsException;
import com.jgw.supercodeplatform.trace.pojo.globalseq.CodeGlobalseq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author liujianqiang
 * @Date 2019/3/13
 * @Description 注入配置类
 **/
@Component
@Slf4j
public class CommonUtilComponent extends CommonUtil {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private LogisticsGlobalseqMapper logisticsGlobalseqMapper;

    /**
     * 获取自增值,并算计间隔值和当前值相加是否大于预期最大值，大于的话更新redis和数据的数据
	 * @author liujianqiang
	 * @data 2018年12月26日
	 * @param key
	 * @return
             * @throws SuperCodeException
	 */
    public long getIncr(String key) throws Exception{
        long number = redisUtil.generate(key);
        CodeGlobalseq codeGlobalseq = toJavaBean(redisUtil.get(key+ RedisKey.GLOBAL_SYMBOL), CodeGlobalseq.class);
        long expectedMax = codeGlobalseq.getExpectedMax();//预期最大值
        long intervaData = codeGlobalseq.getIntervaData();//每次加的值
        long neardataDiffer = codeGlobalseq.getNeardataDiffer();//间隔值
        checkNull(expectedMax,key + " expectedMax null");
        checkNull(neardataDiffer,key + " neardataDiffer null");
        checkNull(intervaData,key + " intervaData null");
        if(codeGlobalseq != null && expectedMax !=0 && intervaData != 0 && neardataDiffer !=0){
            if((number + neardataDiffer) >= expectedMax){//假如当前值+间隔值大于等于预期最大值,需要修改数据库全局表和redis全局表
                updateCodeGlobalseq( key, number, expectedMax + intervaData);//修改数据库全局表
                updateRedisGlobal( key, number, expectedMax + intervaData, intervaData, neardataDiffer);//修改redis全局表
            }
        }else{
            throw new SuperCodeException("从redis获取全部序列号表数据有误,请检查参数,codeGlobalseq= "+ JSON.toJSON(codeGlobalseq));
        }
        return number;
    }

    /**
     * 将全局序列数据库数据存入redis
     * @author liujianqiang
     * @data 2018年1月12日
     * @throws NumberFormatException
     * @throws Exception
     */
    public void putUniqSeqToRedis() throws LogisticsException{//将全局序列编码信息放入redis
        List<CodeGlobalseq> list = logisticsGlobalseqMapper.getCodeGlobalseq();//将全局序列数据库数据存入redis
        for(CodeGlobalseq codeGlobalseq:list){
            String keysType = codeGlobalseq.getKeysType();
            long currentMax = codeGlobalseq.getCurrentMax();
            long expectMax = codeGlobalseq.getExpectedMax();
            if(keysType !=null && !"".equals(keysType)){
                updateRedisInc(keysType, currentMax,expectMax);//修改redis的当前最大值
                redisUtil.set(keysType+ RedisKey.GLOBAL_SYMBOL, JSON.toJSONString(codeGlobalseq));
                log.info("map=== " + redisUtil.get(keysType+ RedisKey.GLOBAL_SYMBOL));
            }else{
                throw new LogisticsException("从数据库获取全部序列号表数据有误,请检查参数,keysType= "+ keysType + ",currentMax= " + currentMax + ",expectMax= " + expectMax);
            }
        }
    }

    /**
     * 修改redis的当前最大值
     * @author liujianqiang
     * @data 2018年1月12日
     * @param mysqlCurrentMax
     * @throws Exception
     */
    private void updateRedisInc(String keysType,long mysqlCurrentMax,long mysqlExpectmax) throws LogisticsException{
        long redisCurrentMax =  redisUtil.generate(keysType, 0);//获取redis全局自增序列值,原来不存在直接返回0
        if(mysqlCurrentMax < redisCurrentMax){//假如mysql的当前最大值小于等于redis的前期最大值，不做任何操作
            log.info("mysql的当前最大值小于redis的预期最大值，不做任何操作,keysType= " + keysType);
        }else{//假如mysql的当前最大值大于redis的前期最大值。分两种情况,1.假如redis当前最大值为0，说明是redis的当前最大值丢失，将mysql的预期最大值加到redis中。2.否则抛出异常
            if(redisCurrentMax == 0){//假如redis的当前最大值是0，说明是redis的当前最大值丢失，将mysql的当前预期最大值加到redis中
                long maxNum = redisUtil.generate(keysType.toString(), mysqlExpectmax);
                log.info("redis当前最大值丢失，将mysql的预期最大值放入redis中, maxNum==== " + maxNum);
                updateCodeGlobalseq( keysType,mysqlExpectmax,mysqlExpectmax + 30000);//将数据库的当前最大值改为预期最大值,预期最大值改为原预期最大值+30000
            }else if(mysqlCurrentMax == redisCurrentMax){
                log.info("mysql的当前最大值等于redis的预期最大值，不做任何操作,keysType= " + keysType);
            }else{
                log.info("mysqlCurrentMax=== " + mysqlCurrentMax + "redisCurrentMax======" + redisCurrentMax + "mysqlExpectmax=== " + mysqlExpectmax);
                throw new LogisticsException("mysqlCurrentMax > redisCurrentMax");
            }
        }

    }

    /**
     * 修改当前最大值和预期最大值
     * @author liujianqiang
     * @data 2018年12月26日
     * @param keysType
     * @param currentMax
     * @param expectedMax
     * @throws Exception
     */
    private void updateCodeGlobalseq(String keysType,long currentMax,long expectedMax) throws LogisticsException {
        CodeGlobalseq codeGlobalseq = new CodeGlobalseq();
        codeGlobalseq.setKeysType(keysType);
        codeGlobalseq.setCurrentMax(currentMax);
        codeGlobalseq.setExpectedMax(expectedMax);
        Integer record = logisticsGlobalseqMapper.updateCodeGlobalseq(codeGlobalseq);
        if(record != 1){
            throw new LogisticsException("修改数据库当前最大值和预期最大值失败");
        }
    }

    /**
     * 修改redis的全局表数据
     * @author liujianqiang
     * @data 2018年12月26日
     * @param keysType
     * @param currentMax
     * @param expectedMax
     * @param intervaData
     * @param neardataDiffer
     */
    private void updateRedisGlobal(String keysType,long currentMax,long expectedMax,long intervaData,long neardataDiffer){
        CodeGlobalseq codeGlobalseq = new CodeGlobalseq();
        codeGlobalseq.setKeysType(keysType);
        codeGlobalseq.setIntervaData(intervaData);
        codeGlobalseq.setNeardataDiffer(neardataDiffer);
        codeGlobalseq.setCurrentMax(currentMax);
        codeGlobalseq.setExpectedMax(expectedMax);
        redisUtil.set(keysType + RedisKey.GLOBAL_SYMBOL, JSON.toJSONString(codeGlobalseq));
    }

}
