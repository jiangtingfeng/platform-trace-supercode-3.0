package com.jgw.supercodeplatform.trace.service.tracefun;

import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.model.Field;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.ReturnParamsMap;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.constants.ObjectTypeEnum;
import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.TraceObjectBatchInfoMapper;
import com.jgw.supercodeplatform.trace.dto.PlatformFun.MassifBatchInfo;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceObjectBatchInfo;
import com.jgw.supercodeplatform.trace.service.template.TraceFunTemplateconfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 地块批次管理
 *
 * @author wzq
 * @date: 2019-03-28
 */
@Service
public class TraceObjectBatchInfoService extends CommonUtil {

    @Autowired
    private TraceObjectBatchInfoMapper traceObjectBatchInfoMapper;

    @Autowired
    private TraceFunTemplateconfigService traceFunTemplateconfigService;

    /**
     * 新增地块批次数据
     * @param traceObjectBatchInfo
     * @throws Exception
     */
    public void insertTraceObjectBatchInfo(TraceObjectBatchInfo traceObjectBatchInfo) throws Exception
    {
        AccountCache userAccount = getUserLoginCache();
        String traceBatchInfoId = getUUID();
        String organizationId = getOrganizationId();
        traceObjectBatchInfo.setTraceBatchInfoId(traceBatchInfoId);
        traceObjectBatchInfo.setOrganizationId(organizationId);//组织id
        traceObjectBatchInfo.setSysId(getSysId());
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


    /**
     * 获取地块生产记录
     * @param map
     * @return
     * @throws Exception
     */
    public Map<String, Object> listMassifTraceBatchInfo(Map<String, Object> map) throws Exception
    {
        Integer total=null;
        ReturnParamsMap returnParamsMap=null;
        Map<String, Object> dataMap=null;
        String massifId=map.get("massifId").toString();
        map.put("objectid",massifId);

        total = traceObjectBatchInfoMapper.getCountByObjectId(massifId);
        returnParamsMap = getPageAndRetuanMap(map, total);

        List<TraceObjectBatchInfo> traceObjectBatchInfos= traceObjectBatchInfoMapper.getTraceBatchInfoByObjectId(returnParamsMap.getParamsMap());
        dataMap = returnParamsMap.getReturnMap();

        List<MassifBatchInfo> massifBatchInfos=new ArrayList<MassifBatchInfo>();
        if(traceObjectBatchInfos!=null && traceObjectBatchInfos.size()>0){
            int index=0;
            for (TraceObjectBatchInfo traceBatchInfo: traceObjectBatchInfos){
                MassifBatchInfo massifBatchInfo=new MassifBatchInfo(traceBatchInfo.getTraceBatchId(),traceBatchInfo.getTraceBatchName());
                List<Map<String, Object>> allNodeData = traceFunTemplateconfigService.queryNodeInfo(traceBatchInfo.getTraceBatchInfoId(), traceBatchInfo.getTraceTemplateId(),
                        true, null).getResults();
                if (allNodeData!=null && allNodeData.size()>0){
                    List<String> nodeInfos=new ArrayList<String>();
                    for (Map<String, Object> nodeData:allNodeData){
                        if(nodeData.get("businessType").toString().equals("1")){
                            String nodeInfo= nodeData.get("nodeFunctionName").toString();
                            List<Field> fields= (List<Field>)nodeData.get("lineData");
                            if(fields!=null){
                                fields= fields.stream().filter(e->String.valueOf( ObjectTypeEnum.FarmOperation.getCode()).equals(e.getObjectType())).collect(Collectors.toList());
                                if(fields!=null&& fields.size()>0){
                                    String fieldValue=String.valueOf( fields.get(0).getFieldValue());
                                    if(!StringUtils.isEmpty(fieldValue)){
                                        nodeInfo=fieldValue;
                                    }
                                }
                            }
                            nodeInfos.add(nodeInfo);
                        }
                    }
                    String allNodeInfo=StringUtils.join(nodeInfos,"、");
                    massifBatchInfo.setNodeInfo(allNodeInfo);
                    SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd" );
                    String time=sdf.format(traceBatchInfo.getCreateTime());
                    if(index==0){
                        time=time+" 至今";
                    }else {
                        time=time+" - "+sdf.format(traceObjectBatchInfos.get(index-1).getCreateTime());
                    }
                    index++;
                    massifBatchInfo.setTime(time);
                    massifBatchInfo.setTraceBatchInfoId(traceBatchInfo.getTraceBatchInfoId());
                    massifBatchInfos.add(massifBatchInfo);
                }
            }

        }
        getRetunMap(dataMap, massifBatchInfos);
        return dataMap;
    }
}
