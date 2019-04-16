package com.jgw.supercodeplatform.trace.controller.code;

import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.dto.code.CodeObjectRelationDto;
import com.jgw.supercodeplatform.trace.pojo.producttesting.TestingType;
import com.jgw.supercodeplatform.trace.service.tracefun.CodeRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/trace/code/relation")
@Api(tags = "码关联")
public class RelationController {

    @Autowired
    private CodeRelationService codeRelationService;

    @PostMapping
    @ApiOperation(value = "添加码对象关联", notes = "新增成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult insertCodeRelationInfo(@Valid @RequestBody CodeObjectRelationDto codeObjectRelationDto) throws Exception {

        return new RestResult(200, "success", codeRelationService.insertCodeRelationInfo(codeObjectRelationDto));
    }
}
