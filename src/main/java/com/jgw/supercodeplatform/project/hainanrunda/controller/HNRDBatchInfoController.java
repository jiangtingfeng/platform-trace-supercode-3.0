package com.jgw.supercodeplatform.project.hainanrunda.controller;

import com.jgw.supercodeplatform.common.AbstractPageService;
import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.common.pojo.common.JsonResult;
import com.jgw.supercodeplatform.project.hainanrunda.dto.batchinfo.BaseAreaDto;
import com.jgw.supercodeplatform.project.hainanrunda.dto.batchinfo.PlantingBatchDto;
import com.jgw.supercodeplatform.project.hainanrunda.service.EndBatchInfoService;
import com.jgw.supercodeplatform.project.hainanrunda.service.PlantingBatchInfoService;
import com.jgw.supercodeplatform.trace.common.model.ObjectUniqueValueResult;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.ReturnParamsMap;
import com.jgw.supercodeplatform.trace.common.model.page.Page;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.ExcelUtils;
import com.jgw.supercodeplatform.trace.controller.tracebatch.TraceBatchInfoController;
import com.jgw.supercodeplatform.trace.dto.standardtemplate.StandardTemplateParam;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.ReturnTraceBatchInfo;
import com.jgw.supercodeplatform.trace.service.tracebatch.TraceBatchInfoService;
import com.jgw.supercodeplatform.utils.BeanPropertyUtil;
import com.jgw.supercodeplatform.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trace/hainanrunda/batchinfo")
@Api(tags = "种植信息")
public class HNRDBatchInfoController {

    @Autowired
    private PlantingBatchInfoService plantingBatchInfoService;

    @Autowired
    private EndBatchInfoService endBatchInfoService;

    @Autowired
    private TraceBatchInfoService traceBatchInfoService;

    @Autowired
    private CommonUtil commonUtil;

    @GetMapping("/listPlantingBatch")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header"),
            @ApiImplicitParam(name = "search", paramType = "query",value = "搜索值", defaultValue = "", required = false)
    })
    @ApiOperation(value = "获取当前种植批次列表")
    public JsonResult<AbstractPageService.PageResults<PlantingBatchDto>> listPlantingBatch(DaoSearch daoSearch) throws Exception{
        plantingBatchInfoService.setSearchParam(daoSearch);
        return new JsonResult(200, "success", plantingBatchInfoService.listSearchViewLike(daoSearch));
    }

    @GetMapping("/listEndBatch")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header"),
            @ApiImplicitParam(name = "search", paramType = "query",value = "搜索值", defaultValue = "", required = false)
    })
    @ApiOperation(value = "获取采收批次列表")
    public JsonResult<AbstractPageService.PageResults<PlantingBatchDto>> listEndBatch(DaoSearch daoSearch) throws Exception{
        plantingBatchInfoService.setSearchParam(daoSearch);
        return new JsonResult(200, "success", endBatchInfoService.listSearchViewLike(daoSearch));
    }

    @GetMapping("/getPlantingBatchInfo")
    @ApiOperation(value = "获取种植批次生产档案详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header"),
            @ApiImplicitParam(paramType="query",value = "批次唯一id",name="traceBatchInfoId",required=true)
    })
    public RestResult<HashMap<String, Object>> getPlantingBatchInfo(@RequestParam String traceBatchInfoId) throws Exception{
        return plantingBatchInfoService.listBatchProductionInfo(traceBatchInfoId,false);
    }

    @GetMapping("/getEndBatchInfo")
    @ApiOperation(value = "获取采收批次生产档案详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header"),
            @ApiImplicitParam(paramType="query",value = "批次唯一id",name="traceBatchInfoId",required=true)
    })
    public RestResult<HashMap<String, Object>> getEndBatchInfo(@RequestParam String traceBatchInfoId) throws Exception{
        return plantingBatchInfoService.listBatchProductionInfo(traceBatchInfoId,true);
    }

    @GetMapping("/listMassIfInfo")
    @ApiOperation(value = "获取大棚种植产品信息接口", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header"),
            @ApiImplicitParam(paramType="query",value = "基地id",name="baseId",required=true)
    })
    public RestResult<List<BaseAreaDto>> listMassIfBatchInfo(String baseId) throws Exception{
        return new RestResult(200, "success", plantingBatchInfoService.listMassIfBatchInfo(baseId));
    }

    @RequestMapping(value = "/exportPlantingBatchInfo", method = RequestMethod.GET)
    @ApiOperation(value = "导出种植信息生产档案", notes = "返回文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header"),
            @ApiImplicitParam(paramType="query",value = "批次唯一id",name="traceBatchInfoId",required=true)
    })
    public void exportPlantingBatchInfo(@RequestParam String traceBatchInfoId, HttpServletResponse res) throws Exception{
        plantingBatchInfoService.exportProductionInfo(traceBatchInfoId,false,res);
    }

    @RequestMapping(value = "/exportEndBatchInfo", method = RequestMethod.GET)
    @ApiOperation(value = "导出采收信息生产档案", notes = "返回文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header"),
            @ApiImplicitParam(paramType="query",value = "批次唯一id",name="traceBatchInfoId",required=true)
    })
    public void exportEndBatchInfo(@RequestParam String traceBatchInfoId, HttpServletResponse res) throws Exception{
        plantingBatchInfoService.exportProductionInfo(traceBatchInfoId,true,res);
    }

    @GetMapping("/getPlantingBatchInfoList")
    @ApiOperation(value = "分页获取种植批次生产档案详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header"),
            @ApiImplicitParam(paramType="query",value = "批次唯一id",name="traceBatchInfoId",required=true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", defaultValue = "10", value = "每页记录数,不传默认10条,非必需"),
            @ApiImplicitParam(name = "current", paramType = "query", defaultValue = "1", value = "当前页,不传默认第一页,非必需")
    })
    public RestResult<Map<String, Object>> getPlantingBatchInfoList(@RequestParam String traceBatchInfoId
            ,@RequestParam Map<String, Object> map) throws Exception{
        HashMap<String, Object> resultMap= plantingBatchInfoService.listBatchProductionInfo(traceBatchInfoId,false).getResults();

        List<Map<String, Object>> nodeList= (List<Map<String, Object>>)resultMap.get("nodeInfo");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate=sdf.parse( nodeList.get(0).get("operateTime").toString());
        Date endDate= sdf.parse( nodeList.get(nodeList.size()-1).get("operateTime").toString());
        Date currDate=new Date();
        if(endDate.getTime()>currDate.getTime()) {
            currDate = endDate;
        }
        Integer endIndex =1+ (int)((currDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)); // 计算天

        nodeList = fillNodeList(nodeList,endIndex);

        Integer total=nodeList.size();
        ReturnParamsMap returnParamsMap= commonUtil.getPageAndRetuanMap(map, total);
        Page page = commonUtil.getPage(map, total);
        Integer toIndex=page.getStartNumber()+page.getPageSize();
        if(toIndex>=total){
            toIndex=total;
        }
        nodeList= nodeList.subList(page.getStartNumber(),toIndex);
        Map<String, Object> dataMap=returnParamsMap.getReturnMap();
        commonUtil.getRetunMap(dataMap,nodeList);
        dataMap.put("productInfo",resultMap.get("productInfo"));

        RestResult<Map<String, Object>> backResult = new RestResult<>();
        backResult.setState(200);
        backResult.setResults(dataMap);
        return backResult;
    }

    @GetMapping("/getEndBatchInfoList")
    @ApiOperation(value = "分页获取采收批次生产档案详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header"),
            @ApiImplicitParam(paramType="query",value = "批次唯一id",name="traceBatchInfoId",required=true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", defaultValue = "10", value = "每页记录数,不传默认10条,非必需"),
            @ApiImplicitParam(name = "current", paramType = "query", defaultValue = "1", value = "当前页,不传默认第一页,非必需")
    })
    public RestResult<Map<String, Object>> getEndBatchInfoList(@RequestParam String traceBatchInfoId
            ,@RequestParam Map<String, Object> map) throws Exception{
        HashMap<String, Object> resultMap= plantingBatchInfoService.listBatchProductionInfo(traceBatchInfoId,true).getResults();

        List<Map<String, Object>> nodeList= (List<Map<String, Object>>)resultMap.get("nodeInfo");
        Integer endIndex= Integer.parseInt( nodeList.get(nodeList.size()-1).get("index").toString());
        nodeList = fillNodeList(nodeList,endIndex);

        Integer total=nodeList.size();
        ReturnParamsMap returnParamsMap= commonUtil.getPageAndRetuanMap(map, total);
        Page page = commonUtil.getPage(map, total);
        Integer toIndex=page.getStartNumber()+page.getPageSize();
        if(toIndex>=total){
            toIndex=total;
        }
        nodeList= nodeList.subList(page.getStartNumber(),toIndex);
        Map<String, Object> dataMap=returnParamsMap.getReturnMap();
        commonUtil.getRetunMap(dataMap,nodeList);
        dataMap.put("productInfo",resultMap.get("productInfo"));

        RestResult<Map<String, Object>> backResult = new RestResult<>();
        backResult.setState(200);
        backResult.setResults(dataMap);
        return backResult;
    }

    List<Map<String, Object>> fillNodeList(List<Map<String, Object>> nodeList,Integer endIndex){
        List<Map<String, Object>> allNode=new ArrayList<>();
        int i=1;
        for(; i<=endIndex;i++){
            String indexStr=String.valueOf(i);
            List<Map<String, Object>> currNode= nodeList.stream().filter(e->e.get("index").toString().equals(indexStr)).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(currNode)){
                allNode.add(currNode.get(0));
            } else {
                HashMap<String, Object> emptyNode=new HashMap<>();
                emptyNode.put("index",i);
                allNode.add(emptyNode);
            }
        }
        return allNode;
    }

    @GetMapping("/field")
    @ApiOperation(value = "获取种植批次信息通过批次表属性", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", defaultValue = "10", value = "每页记录数,不传默认10条,非必需"),
            @ApiImplicitParam(name = "current", paramType = "query", defaultValue = "1", value = "当前页,不传默认第一页,非必需")
    })
    public JsonResult listTraceBatchInfoByOrgAndField(DaoSearch daoSearch) throws Exception{
        plantingBatchInfoService.setSearchParam(daoSearch);
        int total = plantingBatchInfoService.count(daoSearch);
        Map param = BeanPropertyUtil.toMap(daoSearch);
        param.put("current", daoSearch.getCurrent());
        com.jgw.supercodeplatform.common.pojo.common.Page page = PageUtil.getPage(param, total);
        daoSearch.setStartNumber(page.getStartNumber());
        daoSearch.setPageSize(page.getPageSize());
        List<ObjectUniqueValueResult> plantingBatchDtos=plantingBatchInfoService.selectProcessingBatch(daoSearch);

        return new JsonResult(200, "success", new AbstractPageService.PageResults(plantingBatchDtos, page));

    /*    Map result = plantingBatchInfoService.listTraceBatchInfoByOrgPage(map);
        List<ReturnTraceBatchInfo> traceBatchInfos = (List<ReturnTraceBatchInfo>) result.get("list");
        Set<Object> re;
        if (traceBatchInfos != null && traceBatchInfos.size() > 0) {
            re = traceBatchInfos.stream().map(p->new ObjectUniqueValueResult(p.getTraceBatchInfoId(),p.getTraceBatchName(),commonUtil.convert(p,Map.class)) ).collect(Collectors.toSet());
        } else {
            re = new HashSet<>();
        }
        result.put("list",re);
        return new RestResult(200, "success", result);*/
    }
}

