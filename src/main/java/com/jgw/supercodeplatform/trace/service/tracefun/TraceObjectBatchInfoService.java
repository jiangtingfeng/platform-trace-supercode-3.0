package com.jgw.supercodeplatform.trace.service.tracefun;

import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.TraceObjectBatchInfoMapper;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceObjectBatchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraceObjectBatchInfoService extends CommonUtil {

    @Autowired
    private TraceObjectBatchInfoMapper traceObjectBatchInfoMapper;

    public void insertTraceObjectBatchInfo(TraceObjectBatchInfo traceObjectBatchInfo) throws Exception
    {
        AccountCache userAccount = getUserLoginCache();
        String traceBatchInfoId = getUUID();
        String organizationId = getOrganizationId();
        traceObjectBatchInfo.setTraceBatchInfoId(traceBatchInfoId);
        traceObjectBatchInfo.setOrganizationId(organizationId);//组织id
        traceObjectBatchInfo.setCreateId(userAccount.getUserId());
        traceObjectBatchInfo.setNodeDataCount(0);
        traceObjectBatchInfo.setCreateMan(userAccount.getUserName());
        String traceBatchName=traceObjectBatchInfo.getTraceBatchName();
        //checkBatchIdAndBatchName(traceBatchInfo.getTraceBatchId(), traceBatchName, organizationId, traceBatchInfoId);
        traceObjectBatchInfo.setTraceBatchName(traceBatchName.replaceAll(" ", ""));
        Integer record = traceObjectBatchInfoMapper.insertTraceObjectBatchInfo(traceObjectBatchInfo);

        if (record != 1) {
            throw new SuperCodeTraceException("新增溯源批次记录失败");
        }
    }

}
