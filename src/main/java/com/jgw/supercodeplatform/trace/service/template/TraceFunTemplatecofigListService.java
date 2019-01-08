package com.jgw.supercodeplatform.trace.service.template;

import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.mapper1.template.TraceFunTemplateconfigMapper;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigListParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraceFunTemplatecofigListService extends AbstractPageService {
    private static Logger logger = LoggerFactory.getLogger(TraceFunTemplatecofigListService.class);

    @Autowired
    private TraceFunTemplateconfigMapper traceFunTemplateconfigDao;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 普通搜索只根据模板名称
     * @param searchParams
     * @return
     * @throws Exception
     */
    @Override
    protected List<TraceFunTemplateconfigListParam> searchResult(DaoSearch searchParams) throws Exception {
        String organizationId=commonUtil.getOrganizationId();
        int startNumber = (searchParams.getCurrent()-1)*searchParams.getPageSize();
        searchParams.setStartNumber(startNumber);
        return traceFunTemplateconfigDao.selectTemplateByTemplateName(searchParams,organizationId);
    }

    @Override
    protected int count(DaoSearch searchParams)throws Exception {
        String organizationId=commonUtil.getOrganizationId();
        return traceFunTemplateconfigDao.countByTemplateName(searchParams,organizationId);
    }
}
