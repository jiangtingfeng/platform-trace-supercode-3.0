package com.jgw.supercodeplatform.trace.controller.certificate;

import com.jgw.supercodeplatform.common.AbstractPageService;
import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.common.pojo.common.JsonResult;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dto.certificate.CertificateTemplateCopyParam;
import com.jgw.supercodeplatform.trace.dto.certificate.CertificateTemplateParam;
import com.jgw.supercodeplatform.trace.dto.standardtemplate.StandardTemplateParam;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.StandardTemplate;
import com.jgw.supercodeplatform.trace.service.certificate.CertificateTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trace/certificate/certificatetemplate")
@Api(tags = "合格证模板")
public class CertificateTemplateController {

    @Autowired
    private CertificateTemplateService certificateTemplateService;

    @Autowired
    private CommonUtil commonUtil;

    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header"),
            @ApiImplicitParam(name = "search", paramType = "query",value = "搜索值", defaultValue = "", required = false)
    })
    @ApiOperation(value = "获取合格证模板列表")
    public JsonResult<AbstractPageService.PageResults<CertificateTemplateParam>> listSearchViewLike(DaoSearch daoSearch) throws Exception{
        daoSearch.setParams(null);
        daoSearch.getParams().put("organizationId",commonUtil.getOrganizationId());
        return new JsonResult(200, "success", certificateTemplateService.listSearchViewLike(daoSearch));
    }

    @PostMapping
    @ApiOperation(value = "新增合格证模板", notes = "新增成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult insertTestingType(@RequestBody CertificateTemplateParam record) throws Exception {
        return new RestResult(200, "success", certificateTemplateService.insert(record));
    }

    @DeleteMapping
    @ApiOperation(value = "删除合格证模板", notes = "删除成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult deleteTemplateType(Long id ) throws Exception {
        certificateTemplateService.delete(id);
        return new RestResult(200, "success", null);
    }

    @PutMapping
    @ApiOperation(value = "修改合格证模板", notes = "修改成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult updateTemplateType(@RequestBody CertificateTemplateParam record) throws Exception {
        certificateTemplateService.update(record);
        return new RestResult(200, "success", null);
    }




    @PutMapping("/copy")
    @ApiOperation(value = "复制合格证模板", notes = "修改成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult copyTemplateType(@RequestBody CertificateTemplateCopyParam  record) throws Exception {
        certificateTemplateService.copy(record);
        return new RestResult(200, "success", null);
    }




    @GetMapping("/getById")
    @ApiOperation(value = "获取合格证模板详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "id", paramType = "query", defaultValue = "1", value = "模板id")
    })
    public RestResult<CertificateTemplateParam> getById(Long id ) throws Exception {
        return new RestResult(200, "success", certificateTemplateService.getById(id));
    }

}
