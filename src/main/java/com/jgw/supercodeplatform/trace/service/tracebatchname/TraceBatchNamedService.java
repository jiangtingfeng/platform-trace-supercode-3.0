package com.jgw.supercodeplatform.trace.service.tracebatchname;

import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.CommonUtilComponent;
import com.jgw.supercodeplatform.trace.dao.mapper1.batchname.TraceBatchnamedMapper;
import com.jgw.supercodeplatform.trace.pojo.tracebatchname.TraceBatchnamed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraceBatchNamedService extends CommonUtil {
    @Autowired
    private TraceBatchnamedMapper traceBatchnamedMapper;

    @Autowired
    private CommonUtilComponent commonUtilComponent;

    public String queryOneTraceBatchnamed(String funId, String funName){
        String str="";
        TraceBatchnamed batchnamed = traceBatchnamedMapper.select(funId);
        try {
            long incr = commonUtilComponent.getIncr(funName);
            str+=batchnamed.getFieldName()+batchnamed.getCreateDate()+incr+batchnamed.getFieldCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
