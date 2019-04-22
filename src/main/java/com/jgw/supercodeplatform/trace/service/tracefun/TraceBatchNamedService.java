package com.jgw.supercodeplatform.trace.service.tracefun;


import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.CommonUtilComponent;
import com.jgw.supercodeplatform.trace.constants.RedisKey;
import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.TraceBatchNamedMapper;
import com.jgw.supercodeplatform.trace.pojo.tracefun.BaseBatchInfo;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceBatchNamed;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceFunRegulation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 批次命名规则管理
 *
 * @author wzq
 * @date: 2019-03-28
 */
@Service
public class TraceBatchNamedService extends CommonUtil {


    @Autowired
    private TraceBatchNamedMapper traceBatchNamedMapper;


    @Autowired
    private CommonUtilComponent commonUtilComponent;

    /**
     * 新增批次命名规则
     * @param traceBatchNameds
     */
    public void insertTraceBatchNamed(List<TraceBatchNamed> traceBatchNameds)
    {
        for(TraceBatchNamed traceBatchNamed:traceBatchNameds){
            traceBatchNamed.setFieldId(getUUID());
            traceBatchNamedMapper.insertTraceBatchNamed(traceBatchNamed);
        }
    }

    /**
     * 根据批次命名规则自动生成批次名称
     * @param traceFunRegulation
     * @param baseBatchInfo
     * @return
     * @throws Exception
     */
    public String buildBatchName(TraceFunRegulation traceFunRegulation,BaseBatchInfo baseBatchInfo) throws Exception
    {
        StringBuilder traceBatchName=new StringBuilder();
        String funId=traceFunRegulation.getFunId();
        List<TraceBatchNamed> batchNameds= traceBatchNamedMapper.selectByFunId(funId);
        for(int i=0;i<batchNameds.size();i++){
            TraceBatchNamed traceBatchNamed=batchNameds.get(i);
            String fieldCode= traceBatchNamed.getFieldCode();
            String format= traceBatchNamed.getFieldFormat();
            //TODO fieldFormat
            switch (fieldCode){
                case "ProductName":
                    traceBatchName.append(baseBatchInfo.getProductName());
                    break;
                case  "CreateDate":
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                    traceBatchName.append(sdf.format(date));
                    break;
                case "SerialNumber":
                    //按组织、批次类型生成流水号
                    String incrKey=String.format("%s:%s:%s",RedisKey.BatchSerialNumber,getOrganizationId(),traceFunRegulation.getCreateBatchType());
                    long incr = redisUtil.generate(incrKey);
                    baseBatchInfo.setSerialNumber(incr);
                    String serial= StringUtils.leftPad(String.valueOf(incr),5,"0");
                    traceBatchName.append(serial);
                    break;
                case "FunName":
                    traceBatchName.append(traceFunRegulation.getFunctionName());
                    break;
                case "StaticText":
                    traceBatchName.append(traceBatchNamed.getFieldName());
                    break;
            }
            if(i!=batchNameds.size()-1){
                traceBatchName.append(traceFunRegulation.getBatchNamingLinkCharacter());
            }
        }

        return traceBatchName.toString();
    }
}
