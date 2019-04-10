package com.jgw.supercodeplatform.trace.service.tracefun;


import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.TraceBatchRelationMapper;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceBatchRelation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 批次关联关系数据管理
 *
 * @author wzq
 * @date: 2019-03-28
 */
@Service
public class TraceBatchRelationService extends CommonUtil {

    @Autowired
    private TraceBatchRelationMapper traceBatchRelationMapper;

    @Autowired
    private TraceBatchRelationEsService traceBatchRelationEsService;

    /**
     * 根据批次Id递归查询所有父级批次
     * @param batchId
     * @return
     */
    public List<TraceBatchRelation> selectByBatchId(String batchId)
    {
        boolean showAllOnSameClass=false;

        List<TraceBatchRelation> traceBatchRelations=traceBatchRelationEsService.selectByBatchId(batchId);
        if(traceBatchRelations==null || traceBatchRelations.size()==0){
            traceBatchRelations=traceBatchRelationMapper.selectByBatchId(batchId);
        }

        if(traceBatchRelations!=null && traceBatchRelations.size()>0){
            if(traceBatchRelations.size()==1
                    || ( traceBatchRelations.size()>1 && !showAllOnSameClass ))  //混批随机
            {
                 String parentBatchId= traceBatchRelations.get(0).getParentBatchId();
                 if(!StringUtils.isEmpty(parentBatchId)){
                     List<TraceBatchRelation> parentBatchRelations=selectByBatchId(parentBatchId);
                     if(parentBatchRelations!=null && parentBatchRelations.size()>0){
                         traceBatchRelations.addAll(parentBatchRelations);
                     }
                 }
            } else {
                //混批全显, do nothing
            }
        }
        return traceBatchRelations;
    }

}
