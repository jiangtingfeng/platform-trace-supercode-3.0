package com.jgw.supercodeplatform.trace.service.tracefun;


import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.CommonUtilComponent;
import com.jgw.supercodeplatform.trace.constants.RedisKey;
import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.TraceBatchNamedMapper;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceBatchNamed;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceFunRegulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class TraceBatchNamedService extends CommonUtil {


    @Autowired
    private TraceBatchNamedMapper traceBatchNamedMapper;


    @Autowired
    private CommonUtilComponent commonUtilComponent;

    public void insertTraceBatchNamed(List<TraceBatchNamed> traceBatchNameds)
    {
        for(TraceBatchNamed traceBatchNamed:traceBatchNameds){
            traceBatchNamed.setFieldId(getUUID());
            traceBatchNamedMapper.insertTraceBatchNamed(traceBatchNamed);
        }
    }

    public String buildBatchName(TraceFunRegulation traceFunRegulation, LinkedHashMap<String, Object> identityMap) throws Exception
    {
        StringBuilder traceBatchName=new StringBuilder();
        String funId=traceFunRegulation.getFunId();
        List<TraceBatchNamed> batchNameds= traceBatchNamedMapper.selectByFunId(funId);
        for(int i=0;i<batchNameds.size();i++){
            TraceBatchNamed traceBatchNamed=batchNameds.get(i);
            String fieldCode= traceBatchNamed.getFieldCode();
            switch (fieldCode){
                case "ProductName":
                    traceBatchName.append(identityMap.get("ProductName").toString());
                    break;
                case  "CreateDate":
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                    traceBatchName.append(sdf.format(date));
                    break;
                case "SerialNumber":
                    String incrKey=getOrganizationId()+":"+traceFunRegulation.getCreateBatchType();
                    long incr = commonUtilComponent.getIncr(RedisKey.BatchId);
                    traceBatchName.append(incr);
                    break;
                case "FunName":
                    traceBatchName.append(traceFunRegulation.getFunctionName());
                    break;
            }
            if(i!=batchNameds.size()-1){
                traceBatchName.append(traceFunRegulation.getBatchNamingLinkCharacter());
            }
        }

        return traceBatchName.toString();
    }
}
