package com.jgw.supercodeplatform.trace.service.tracebatchname;

import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.CommonUtilComponent;
import com.jgw.supercodeplatform.trace.dao.mapper1.dao.TraceBatchnamedMapper;
import com.jgw.supercodeplatform.trace.pojo.TraceBatchnamed;
import com.jgw.supercodeplatform.trace.pojo.TraceBatchnamedExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 流水号的生成service
 *
 * @author wangwenzhang
 * @date 2018年3月18日
 */
@Service
public class TraceBatchNamedService extends CommonUtil {

   @Autowired
   private TraceBatchnamedMapper traceBatchnamedMapper;

    @Autowired
    private CommonUtilComponent commonUtilComponent;

    public String queryOneTraceBatchnamed(String funId, String funName) throws Exception {
        String str="";
        TraceBatchnamedExample example=new TraceBatchnamedExample();
        TraceBatchnamedExample.Criteria criteria = example.createCriteria();
        criteria.andFunidEqualTo(funId);
        List<TraceBatchnamed> traceBatchnameds = traceBatchnamedMapper.selectByExample(example);
        commonUtilComponent.putUniqSeqToRedis();
        //得到一个流水号
        long incr = commonUtilComponent.getIncr(funName);
        //转化为具体的日期
        Date date= new Date();
        String pattern="yyyy年MM月dd";
        SimpleDateFormat sdf= new SimpleDateFormat(pattern);
        String datestr=sdf.format(date);// format  为格式化方法

        for (TraceBatchnamed traceBatchnamed : traceBatchnameds) {
            if(traceBatchnamed.getFieldcode().equals("ProductName")){
                str+=traceBatchnamed.getFieldname()+"_"+datestr+"_"+incr;
            }
            if(traceBatchnamed.getFieldcode().equals("FunName")){
                str+="_"+traceBatchnamed.getFieldname();
            }
        }



        return str;
    }
}
