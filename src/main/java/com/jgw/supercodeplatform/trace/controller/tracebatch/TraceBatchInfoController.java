package com.jgw.supercodeplatform.trace.controller.tracebatch;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dto.tracebatch.InsertTraceBatchInfo;
import com.jgw.supercodeplatform.trace.dto.tracebatch.UpdateTraceBatchInfo;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.ReturnTraceBatchInfo;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import com.jgw.supercodeplatform.trace.service.tracebatch.TraceBatchInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 溯源批次controller
 *
 * @author liujianqiang
 * @date 2018年12月12日
 */
@RestController
@RequestMapping("/trace/batch/info")
@Api(tags = "批次管理")
public class TraceBatchInfoController extends CommonUtil {

    @Autowired
    private TraceBatchInfoService traceBatchInfoService;

    /**
     * 新增溯源批次
     *
     * @param insertTraceBatchInfo
     * @return
     * @throws Exception
     * @author liujianqiang
     * @data 2018年12月12日
     */
    @PostMapping
    @ApiOperation(value = "新增溯源批次", notes = "返回溯源批次表唯一id")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult insertTraceBatchInfo(@RequestBody InsertTraceBatchInfo insertTraceBatchInfo) throws Exception {
        @SuppressWarnings("unchecked")
        Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(insertTraceBatchInfo), Map.class);
        validateRequestParamAndValueNotNull(map, "productId", "productName", "traceBatchName",
                "traceTemplateId", "traceTemplateName", "h5TrancePageId", "h5TempleteName");
        TraceBatchInfo traceBatchInfo = JSONObject.parseObject(JSONObject.toJSONString(map), TraceBatchInfo.class);
        traceBatchInfo.setTraceBatchId(insertTraceBatchInfo.getTraceBatchId());//批次id可以为空,转换会变为null,需要手动转换
        return new RestResult(200, "success", traceBatchInfoService.insertTraceBatchInfo(traceBatchInfo));
    }

    /**
     * 修改溯源批次
     *
     * @param insertTraceBatchInfo
     * @return
     * @throws Exception
     * @author liujianqiang
     * @data 2018年12月12日
     */
    @PutMapping
    @ApiOperation(value = "修改溯源批次", notes = "是否成功标志")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult updateTraceBatchInfo(@RequestBody UpdateTraceBatchInfo updateTraceBatchInfo) throws Exception {
        @SuppressWarnings("unchecked")
        Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(updateTraceBatchInfo), Map.class);
        validateRequestParamAndValueNotNull(map, "traceBatchInfoId");
        TraceBatchInfo traceBatchInfo = JSONObject.parseObject(JSONObject.toJSONString(map), TraceBatchInfo.class);
        traceBatchInfo.setTraceBatchId(updateTraceBatchInfo.getTraceBatchId());//批次id可以为空,转换会变为null,需要手动转换
        traceBatchInfoService.updateTraceBatchInfo(traceBatchInfo);
        return new RestResult(200, "success", null);
    }

    /**
     * 删除批次信息
     *
     * @param params
     * @return
     * @throws SuperCodeTraceException
     * @author liujianqiang
     * @data 2018年12月21日
     */
    @DeleteMapping
    @ApiOperation(value = "删除批次信息", notes = "删除成功失败标志位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "traceBatchInfoId", paramType = "query", defaultValue = "64b379cd47c843458378f479a115c322", value = "批次表唯一id,必需", required = true),
            @ApiImplicitParam(name = "traceTemplateId", paramType = "query", defaultValue = "64b379cd47c843458378f479a115c322", value = "模板表唯一id,必需", required = true),
    })
    public RestResult deleteTraceBatchInfo(@RequestParam Map<String, Object> params) throws Exception {
        validateRequestParamAndValueNotNull(params, "traceBatchInfoId", "traceTemplateId");
        traceBatchInfoService.deleteTraceBatch(params.get("traceBatchInfoId").toString(), params.get("traceTemplateId").toString());
        return new RestResult(200, "success", null);
    }

    /**
     * 获取分页溯源批次信息
     *
     * @param map
     * @return
     * @throws SuperCodeTraceException
     * @author liujianqiang
     * @data 2018年12月19日
     */
    @GetMapping("/page")
    @ApiOperation(value = "获取分页溯源批次", notes = "溯源批次列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", defaultValue = "30", value = "每页记录数,不传默认10条,非必需"),
            @ApiImplicitParam(name = "current", paramType = "query", defaultValue = "3", value = "当前页,不传默认第一页,非必需"),
            @ApiImplicitParam(name = "batchType", paramType = "query", defaultValue = "1", value = "批次类型,产品批次为1/地块批次为2,非必需")
    })
    public RestResult listTraceBatchInfoByOrgPage(@RequestParam @ApiIgnore Map<String, Object> map) throws Exception {
        Map<String, Object> result=traceBatchInfoService.listTraceBatchInfoByOrgPage(map);
       /* if (map.get("batchType")!=null && map.get("batchType").toString().equals("2")){
            result=traceBatchInfoService.listTraceBatchInfoByOrgPage(map);
        }else {
            result=traceBatchInfoService.listProductBatchInfo(map);
        }*/
        return new RestResult(200, "success", result);
    }

    /**
     * 获取溯源批次信息
     *
     * @return
     * @throws SuperCodeTraceException
     * @author liujianqiang
     * @throws SuperCodeException 
     * @data 2018年12月19日
     */
    @GetMapping
    @ApiOperation(value = "获取溯源批次", notes = "溯源批次列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult listTraceBatchInfoByOrg() throws SuperCodeTraceException, SuperCodeException {
        return new RestResult(200, "success", traceBatchInfoService.listTraceBatchInfo());
    }


    /**
     * @return
     * @Author corbett
     * @Description //TODO 获取溯源批次信息通过批次表属性
     * @Date 17:03 2018/12/29
     * @Param
     **/
    @GetMapping("/field")
    @ApiOperation(value = "获取溯源批次信息通过批次表属性", notes = "获取溯源批次信息通过批次表属性")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult listTraceBatchInfoByOrgAndField(@RequestParam Map<String, Object> map) throws Exception {
        validateRequestParamAndValueNotNull(map,"field");
        String field = map.get("field").toString();
        BatchInfo batchInfo = BatchInfo.valueOf(field);
        if (batchInfo == null) {
            return new RestResult(500, "field 有误：" + field, null);
        }

        Map result = traceBatchInfoService.listTraceBatchInfoByOrgPage(map);

        List<ReturnTraceBatchInfo> traceBatchInfos = (List<ReturnTraceBatchInfo>) result.get("list");

        Set<Object> re;
        if (traceBatchInfos != null && traceBatchInfos.size() > 0) {
            re = getBatchInfoValue(batchInfo,traceBatchInfos);
        } else {
            re = new HashSet<>();
        }

        result.put("list",re);
        return new RestResult(200, "success", result);
    }

    private Set<Object> getBatchInfoValue(BatchInfo batchInfo,List<ReturnTraceBatchInfo> traceBatchInfos) {
        Set<Object> re = new HashSet<>(traceBatchInfos.size());
        switch (batchInfo) {
            case TraceBatchInfoId:
                re = traceBatchInfos.stream().filter(p->p.getTraceBatchInfoId() != null).map(p -> new BatchInfoResult(p.getTraceBatchInfoId(),p.getTraceBatchInfoId())).collect(Collectors.toSet());
                break;
            case OrganizationId:
                re = traceBatchInfos.stream().filter(p->p.getOrganizationId() != null).map(p -> new BatchInfoResult(p.getTraceBatchInfoId(),p.getOrganizationId())).collect(Collectors.toSet());
                break;
            case ProductID:
                re = traceBatchInfos.stream().filter(p->p.getProductId() != null).map(p -> new BatchInfoResult(p.getTraceBatchInfoId(),p.getProductId())).collect(Collectors.toSet());
                break;
            case ProductName:
                re = traceBatchInfos.stream().filter(p->p.getProductName() != null).map(p -> new BatchInfoResult(p.getTraceBatchInfoId(),p.getProductName())).collect(Collectors.toSet());
                break;
            case TraceBatchName:
                re = traceBatchInfos.stream().filter(p->p.getTraceBatchName() != null).map(p ->new BatchInfoResult(p.getTraceBatchInfoId(),p.getTraceBatchName())).collect(Collectors.toSet());
                break;
            case TraceBatchId:
                re = traceBatchInfos.stream().filter(p->p.getTraceBatchId() != null).map(p -> new BatchInfoResult(p.getTraceBatchInfoId(),p.getTraceBatchId())).collect(Collectors.toSet());
                break;
            case ListedTime:
                re = traceBatchInfos.stream().filter(p->p.getListedTime() != null).map(p -> new BatchInfoResult(p.getTraceBatchInfoId(),p.getListedTime())).collect(Collectors.toSet());
                break;
            case TraceTemplateId:
                re = traceBatchInfos.stream().filter(p->p.getTraceTemplateId() != null).map(p -> new BatchInfoResult(p.getTraceBatchInfoId(),p.getTraceTemplateId())).collect(Collectors.toSet());
                break;
            case TraceTemplateName:
                re = traceBatchInfos.stream().filter(p->p.getTraceTemplateName() != null).map(p -> new BatchInfoResult(p.getTraceBatchInfoId(),p.getTraceTemplateName())).collect(Collectors.toSet());
                break;
            case H5TrancePageId:
                re = traceBatchInfos.stream().filter(p->p.getH5TrancePageId() != null).map(p -> new BatchInfoResult(p.getTraceBatchInfoId(),p.getH5TrancePageId())).collect(Collectors.toSet());
                break;
            case H5TempleteName:
                re = traceBatchInfos.stream().filter(p->p.getH5TempleteName() != null).map(p -> new BatchInfoResult(p.getTraceBatchInfoId(),p.getH5TempleteName())).collect(Collectors.toSet());
                break;
            case CreateId:
                re = traceBatchInfos.stream().filter(p->p.getCreateId() != null).map(p -> new BatchInfoResult(p.getTraceBatchInfoId(),p.getCreateId())).collect(Collectors.toSet());
                break;
            case CreateMan:
                re = traceBatchInfos.stream().filter(p->p.getCreateMan() != null).map(p -> new BatchInfoResult(p.getTraceBatchInfoId(),p.getCreateMan())).collect(Collectors.toSet());
                break;
            case CreateTime:
                re = traceBatchInfos.stream().filter(p->p.getCreateTime() != null).map(p -> new BatchInfoResult(p.getTraceBatchInfoId(),p.getCreateTime())).collect(Collectors.toSet());
                break;
            case UpdateTime:
                re = traceBatchInfos.stream().filter(p->p.getUpdateTime() != null).map(p -> new BatchInfoResult(p.getTraceBatchInfoId(),p.getUpdateTime())).collect(Collectors.toSet());
                break;
            default:

        }
        return re == null ? new HashSet<>() : re;
    }


    public static class BatchInfoResult{
        private String objectUniqueValue;
        private String field;

        public String getObjectUniqueValue() {
            return objectUniqueValue;
        }

        public void setObjectUniqueValue(String objectUniqueValue) {
            this.objectUniqueValue = objectUniqueValue;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public BatchInfoResult(String objectUniqueValue, String field) {
            this.objectUniqueValue = objectUniqueValue;
            this.field = field;
        }

        public BatchInfoResult() {
        }
    }

    private enum BatchInfo {
        TraceBatchInfoId, OrganizationId, ProductID, ProductName, TraceBatchName,
        TraceBatchId, ListedTime, TraceTemplateId, TraceTemplateName, H5TrancePageId,
        H5TempleteName, CreateId, CreateMan, CreateTime, UpdateTime;
    }
    

    /**
     *  获取溯源批次对应模板节点业务数据
     * @param traceBatchInfoId
     * @return
     * @throws Exception
     */
	@GetMapping("/nodeBusinessData")
	@ApiOperation(value = "根据批次号获取关联溯源模板下节点业务数据接口", notes = "节点业务数据",consumes="application/x-www-form-urlencoded;charset=UTF-8")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",value = "溯源批次号",name="traceBatchInfoId",required=true),
        @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
	public RestResult<List<Map<String, Object>>> listBusinessNodeData(@RequestParam String traceBatchInfoId) throws Exception{
		return traceBatchInfoService.listBusinessNodeData(traceBatchInfoId);
	}
	
    /**
     *  根据溯源批次扫码接口
     * @param traceBatchInfoId
     * @return
     * @throws Exception
     */
	@GetMapping("/h5PageData")
	@ApiOperation(value = "根据批次号获取h5溯源页数据接口", notes = "h5溯源页数据",consumes="application/x-www-form-urlencoded;charset=UTF-8")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",value = "溯源批次唯一id，注意不是批次号",name="traceBatchInfoId",required=true),
        @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
	public RestResult<HashMap<String, Object>> h5PageData(@RequestParam String traceBatchInfoId, @RequestParam(required = false) Integer traceBatchType) throws Exception{
		return traceBatchInfoService.h5PageData(traceBatchInfoId,traceBatchType);
	}
	
}
