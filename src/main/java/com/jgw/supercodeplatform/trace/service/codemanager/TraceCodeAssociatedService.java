package com.jgw.supercodeplatform.trace.service.codemanager;

import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.dao.mapper1.codemanager.TraceCodeAssociatedMapper;
import com.jgw.supercodeplatform.trace.dto.codemanager.TraceCodeAssociatedModel;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.codemanager.TraceCodeAssociated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author corbett
 * @Description //TODO
 * @Date 17:47 2018/12/19
 * @Param
 * @return
 **/
@Service
public class TraceCodeAssociatedService extends AbstractPageService<DaoSearch> {
    @Autowired
    private TraceCodeAssociatedMapper traceCodeAssociatedMapper;
    
     /**
       * @Author corbett
       * @Description //TODO 插入码关联表
       * @Date 17:52 2018/12/19
       * @Param 
       * @return 
       **/
    @Transactional(rollbackFor = Exception.class)
    public void add(TraceCodeAssociated traceCodeAssociated) throws SuperCodeTraceException {
        int resu = traceCodeAssociatedMapper.insertSelective(traceCodeAssociated);
        if (resu <= 0){
            throw new SuperCodeTraceException("插入码关联表失败",500);
        }
    }

    /**
     * @Author corbett
     * @Description //TODO 更新码关联表
     * @Date 17:52 2018/12/19
     * @Param
     * @return
     **/
    @Transactional(rollbackFor = Exception.class)
    public void update(TraceCodeAssociated traceCodeAssociated) throws SuperCodeTraceException {
        int resu = traceCodeAssociatedMapper.updateByPrimaryKeySelective(traceCodeAssociated);
        if (resu <= 0){
            throw new SuperCodeTraceException("更新码关联表失败",500);
        }
    }

    /**
     * @Author corbett
     * @Description //TODO 获取码关联表
     * @Date 17:52 2018/12/19
     * @Param
     * @return
     **/
//    public void get(TraceCodeAssociated traceCodeAssociated) throws SuperCodeTraceException {
//        int resu = traceCodeAssociatedMapper.updateByPrimaryKeySelective(traceCodeAssociated);
//        if (resu <= 0){
//            throw new SuperCodeTraceException("更新码关联表失败",500);
//        }
//    }

    /**
     * @Author corbett
     * @Description //TODO 新增码关联
     * @Date 17:52 2018/12/19
     * @Param
     * @return
     **/
    @Transactional(rollbackFor = Exception.class)
    public void addCodeAssociated(TraceCodeAssociatedModel traceCodeAssociatedModel) throws SuperCodeTraceException {
        TraceCodeAssociated traceCodeAssociated = new TraceCodeAssociated(traceCodeAssociatedModel);
        add(traceCodeAssociated);
    }

     /**
       * @Author corbett
       * @Description //TODO 获取码关联列表
       * @Date 17:57 2018/12/19
       * @Param 
       * @return 
       **/
    @Override
    protected <T> T searchResult(DaoSearch searchParams) throws Exception {
        return super.searchResult(searchParams);
    }

     /**
       * @Author corbett
       * @Description //TODO 获取码关联列表总数
       * @Date 19:42 2018/12/19
       * @Param 
       * @return 
       **/
    @Override
    protected int count(DaoSearch searchParams) throws Exception {
        return super.count(searchParams);
    }
}
