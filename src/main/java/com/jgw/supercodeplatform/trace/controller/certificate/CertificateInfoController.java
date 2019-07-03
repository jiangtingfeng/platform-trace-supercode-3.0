package com.jgw.supercodeplatform.trace.controller.certificate;

import com.jgw.supercodeplatform.common.AbstractPageService;
import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.common.pojo.common.JsonResult;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dto.certificate.*;

import com.jgw.supercodeplatform.trace.dto.common.DisableFlagDto;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateInfo;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificatePrintInfo;
import com.jgw.supercodeplatform.trace.service.certificate.CertificateInfoService;
import com.jgw.supercodeplatform.trace.service.certificate.CertificatePrintInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/trace/certificate/certificateinfo")
@Api(tags = "产品合格证")
public class CertificateInfoController {

    @Autowired
    private CertificateInfoService certificateInfoService;

    @Autowired
    private CertificatePrintInfoService certificatePrintInfoService;

    @Autowired
    private CommonUtil commonUtil;

    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header"),
            @ApiImplicitParam(name = "search", paramType = "query",value = "搜索值", defaultValue = "", required = false)
    })
    @ApiOperation(value = "获取产品合格证列表")
    public JsonResult<AbstractPageService.PageResults<CertificateInfoParam>> listSearchViewLike(DaoSearch daoSearch) throws Exception{
        daoSearch.setParams(null);
        daoSearch.getParams().put("organizationId",commonUtil.getOrganizationId());
        return new JsonResult(200, "success", certificateInfoService.listSearchViewLike(daoSearch));
    }

    @PostMapping
    @ApiOperation(value = "新增产品合格证", notes = "新增成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult insertTestingType(@RequestBody CertificateInfoParam record) throws Exception {
        return new RestResult(200, "success", certificateInfoService.insert(record));
    }

    @DeleteMapping
    @ApiOperation(value = "删除产品合格证", notes = "删除成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult deleteTemplateType(Long id ) throws Exception {
        certificateInfoService.delete(id);
        return new RestResult(200, "success", null);
    }

    @PutMapping
    @ApiOperation(value = "修改产品合格证", notes = "修改成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult updateTemplateType(@RequestBody CertificateInfoParam record) throws Exception {
        certificateInfoService.update(record);
        return new RestResult(200, "success", null);
    }



    @PutMapping("/copy")
    @ApiOperation(value = "复制合格证", notes = "复制合格证名称不需要传")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult updateTemplateType(@RequestBody CertificateInfoCopyParam record) throws Exception {
        certificateInfoService.copy(record);
        return new RestResult(200, "success", null);
    }




    @GetMapping("/getById")
    @ApiOperation(value = "1获取产品合格证详情2获取打印设备", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "id", paramType = "query", defaultValue = "1", value = "合格证id")
    })
    public RestResult<CertificateInfoAndPrintDriveParam> getById(Long id ) throws Exception {
        CertificateInfoParam byId = certificateInfoService.getById(id);
        CertificateInfoAndPrintDriveParam returnParam = new CertificateInfoAndPrintDriveParam();

        BeanUtils.copyProperties(byId,returnParam);
        returnParam.setPrintDrive(certificatePrintInfoService.getLastPrintDriveByUser());
        return new RestResult(200, "success",returnParam);
    }




    @GetMapping("/getPrintInfoById")
    @ApiOperation(value = "获取产品合格证打印详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "id", paramType = "query", defaultValue = "1", value = "合格证id")
    })
    public RestResult<CertificatePrintInfoDtoWithTemplateInfo> getPrintById(@NotNull Long id ) throws Exception {
        CertificatePrintInfoDtoWithTemplateInfo byId = certificatePrintInfoService.selectById(id);
        return new RestResult(200, "success",byId);
    }




    @GetMapping("/listPrintInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header"),
            @ApiImplicitParam(name = "search", paramType = "query",value = "搜索值", defaultValue = "", required = false)
    })
    @ApiOperation(value = "获取合格证打印记录列表")
    public JsonResult<AbstractPageService.PageResults<CertificatePrintInfoDto>> listPrintInfo(DaoSearch daoSearch) throws Exception{
        daoSearch.setParams(null);
        daoSearch.getParams().put("organizationId",commonUtil.getOrganizationId());
        return new JsonResult(200, "success", certificatePrintInfoService.listSearchViewLike(daoSearch));
    }

    @PostMapping("/printCertificate")
    @ApiOperation(value = "打印产品合格证", notes = "")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult insertCertificatePrintInfo(@RequestBody CertificatePrintParam record) throws Exception {
        return new RestResult(200, "success", certificatePrintInfoService.insert(record));
    }

    @PostMapping("updateDisableFlag")
    @ApiOperation(value = "设置产品合格证启用状态", notes = "修改成功失败标志位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    })
    public RestResult updateDisableFlag(@RequestBody DisableFlagDto disableFlagDto) throws Exception {
        certificateInfoService.updateDisableFlag(disableFlagDto.getDisableFlag(),disableFlagDto.getId());
        return new RestResult(200, "success", null);
    }
}
