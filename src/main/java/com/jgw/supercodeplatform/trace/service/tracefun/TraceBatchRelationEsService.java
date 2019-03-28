package com.jgw.supercodeplatform.trace.service.tracefun;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.config.es.IndexAndType;
import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.TraceBatchRelationMapper;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceBatchRelation;
import org.apache.ibatis.annotations.Param;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * ElasticSearch中的批次关联关系数据管理
 *
 * @author wzq
 * @date: 2019-03-28
 */
@Service
public class TraceBatchRelationEsService extends CommonUtil {

    @Autowired
    private TransportClient eClient;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private TraceBatchRelationMapper traceBatchRelationMapper;

    private static Logger logger= LoggerFactory.getLogger(TraceBatchRelationEsService.class);

    /**
     * 新增批次关联数据，同时异步写入ElasticSearch
     * @param traceBatchRelation
     * @throws Exception
     */
    public void insertTraceBatchRelation(TraceBatchRelation traceBatchRelation)  throws Exception
    {
        traceBatchRelationMapper.insertTraceBatchRelation(traceBatchRelation);

        taskExecutor.execute(()->{
            try{
                insertTraceBatchRelationToEs(traceBatchRelation);
            }catch (Exception e){
                logger.error("批次关系写入es失败",e);
                e.printStackTrace();
            }
        });

    }

    /**
     * 批次关联数据写入ElasticSearch
     * @param traceBatchRelation
     * @throws Exception
     */
    private void insertTraceBatchRelationToEs(TraceBatchRelation traceBatchRelation) throws Exception{
        IndexResponse indexResponse=eClient.prepareIndex(IndexAndType.TRACE_INDEX,IndexAndType.TRACE_BATCHRELATION_TYPE).setSource(
                jsonBuilder().startObject()
                    .field("batchRelationId",traceBatchRelation.getBatchRelationId())
                    .field("currentBatchId",traceBatchRelation.getCurrentBatchId())
                    .field("parentBatchId",traceBatchRelation.getParentBatchId())
                    .field("createDate",traceBatchRelation.getCreateDate())
                    .field("updateDate",traceBatchRelation.getUpdateDate())
                    .field("currentBatchType",traceBatchRelation.getCurrentBatchType())
                    .field("parentBatchType",traceBatchRelation.getParentBatchType())
                .endObject()
        ).get();
        
        if(indexResponse.status().getStatus() != RestStatus.CREATED.getStatus()){
            throw new SuperCodeException("新增装箱流水es记录失败");
        }
    }

    /**
     * 根据批次Id从ElasticSearch中查询批次关联数据
     * @param batchId
     * @return
     */
    public List<TraceBatchRelation> selectByBatchId(String batchId){

        SearchResponse response=eClient.prepareSearch(IndexAndType.TRACE_INDEX)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setTypes(IndexAndType.TRACE_BATCHRELATION_TYPE)
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("currentBatchId.keyword",batchId))

                ).get();
        SearchHit[] hits=response.getHits().getHits();

        List<TraceBatchRelation> traceBatchRelations=new ArrayList<TraceBatchRelation>() ;
        for(SearchHit searchHit:hits){
            TraceBatchRelation traceBatchRelation= JSONObject.parseObject(JSONObject.toJSONString(searchHit.getSourceAsMap()),TraceBatchRelation.class);
            traceBatchRelations.add(traceBatchRelation);
        }
        return traceBatchRelations;
    }
}
