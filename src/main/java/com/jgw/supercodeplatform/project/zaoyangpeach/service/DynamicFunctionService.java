package com.jgw.supercodeplatform.project.zaoyangpeach.service;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.aware.TraceApplicationContextAware;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.batchinfo.TraceBatchInfoMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.TraceFunRegulationMapper;
import com.jgw.supercodeplatform.trace.dto.dynamictable.DynamicTableRequestParam;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceFunRegulation;
import com.jgw.supercodeplatform.trace.service.dynamic.DynamicTableService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DynamicFunctionService extends AbstractPageService<DynamicTableRequestParam> {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private DynamicTableService dynamicTableService;

    @Autowired
    private TraceApplicationContextAware applicationAware;

    @Autowired
    private TraceFunRegulationMapper traceFunRegulationMapper;

    @Autowired
    private TraceBatchInfoMapper traceBatchInfoMapper;

    public RestResult<AbstractPageService.PageResults<List<Map<String, Object>>>> list(DynamicTableRequestParam param) throws Exception {
        RestResult<AbstractPageService.PageResults<List<Map<String, Object>>>> result=new RestResult<AbstractPageService.PageResults<List<Map<String, Object>>>>();
        String functionId=param.getFunctionId();
        if (StringUtils.isBlank(functionId)) {
            throw new SuperCodeTraceException("请求头里的functionId不能为空", 500);
        }
        AbstractPageService.PageResults<List<Map<String, Object>>> p = listSearchViewLike(param);
        result.setMsg("成功");
        result.setState(200);
        result.setResults(p);
        return result;
    }
    /**
     * 定制功能列表查询记录
     */
    @Override
    protected List<LinkedHashMap<String, Object>> searchResult(DynamicTableRequestParam param)
            throws SuperCodeTraceException, SuperCodeException {
        String orgnizationId=commonUtil.getOrganizationId();

        //orgnizationId="5d4010983d914fa7901b389d6ddcd39a";

        String sql = dynamicTableService.querySqlBuilder(null,null,param.getFunctionId(), null,null, false,false,orgnizationId,param);
        DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,param.getFunctionId());
        List<LinkedHashMap<String, Object>> list = dao.select(sql);

        if(CollectionUtils.isEmpty(list)){
            return list;
        }

        String funId=param.getFunctionId();
        dynamicTableService.getFunComponentData(funId,list);

        TraceFunRegulation traceFunRegulation= traceFunRegulationMapper.selectByFunId(param.getFunctionId());
        if (traceFunRegulation.getUseSceneType()==1){
            List<String> traceBatchInfoIds =list.stream().map(e->String.format("'%s'", e.get("TraceBatchInfoId").toString())).collect(Collectors.toList());
            List<TraceBatchInfo> traceBatchInfos= traceBatchInfoMapper.selectByTraceBatchInfoIds(StringUtils.join(traceBatchInfoIds,","));
            for (LinkedHashMap<String, Object> businessMap : list){
                String traceBatchInfoId= businessMap.get("TraceBatchInfoId").toString();
                List<TraceBatchInfo> matchBatchInfos= traceBatchInfos.stream().filter(e->e.getTraceBatchInfoId().equals(traceBatchInfoId)).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(matchBatchInfos)){
                    businessMap.put("TraceBatchName",  matchBatchInfos.get(0).getTraceBatchName());
                }
            }
        }

        return list;
    }

}
