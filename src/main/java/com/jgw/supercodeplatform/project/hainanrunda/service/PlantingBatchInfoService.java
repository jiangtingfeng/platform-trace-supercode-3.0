package com.jgw.supercodeplatform.project.hainanrunda.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jgw.supercodeplatform.common.AbstractPageService;
import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.common.pojo.common.JsonResult;
import com.jgw.supercodeplatform.project.hainanrunda.dto.batchinfo.MassIfBatchDto;
import com.jgw.supercodeplatform.project.hainanrunda.dto.batchinfo.PlantingBatchDto;
import com.jgw.supercodeplatform.trace.aware.TraceApplicationContextAware;
import com.jgw.supercodeplatform.trace.common.model.Field;
import com.jgw.supercodeplatform.trace.common.model.ObjectUniqueValueResult;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.ReturnParamsMap;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.ExcelUtils;
import com.jgw.supercodeplatform.trace.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.trace.common.util.WriteSheet;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.batchinfo.TraceBatchInfoMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.TraceBatchRelationMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.TraceObjectBatchInfoMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.BatchInfoMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.BatchStoragePlaceRelationMapper;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.ReturnTraceBatchInfo;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceBatchRelation;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceObjectBatchInfo;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.BatchStoragePlaceRelation;
import com.jgw.supercodeplatform.trace.service.template.TraceFunFieldConfigService;
import com.jgw.supercodeplatform.trace.service.template.TraceFunTemplateconfigService;
import com.jgw.supercodeplatform.trace.service.tracebatch.TraceBatchInfoService;
import com.jgw.supercodeplatform.utils.http.Requests;
import jxl.CellView;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlantingBatchInfoService  extends CommonUtil implements AbstractPageService {

    private static Logger logger = LoggerFactory.getLogger(PlantingBatchInfoService.class);

    @Autowired
    private BatchInfoMapper batchInfoMapper;

    @Autowired
    private TraceBatchInfoMapper traceBatchInfoMapper;

    @Autowired
    private TraceFunTemplateconfigService traceFunTemplateconfigService;

    @Autowired
    private TraceBatchInfoService traceBatchInfoService;

    @Autowired
    private TraceBatchRelationMapper traceBatchRelationMapper;

    @Autowired
    private TraceApplicationContextAware applicationAware;

    @Autowired
    private TraceObjectBatchInfoMapper traceObjectBatchInfoMapper;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Autowired
    private BatchStoragePlaceRelationMapper batchStoragePlaceRelationMapper;


    @Autowired
    private CommonUtil commonUtil;

    @Value("${rest.user.url}")
    private String restUserUrl;

    private String sowingDateFieldCode="bzsj";

    private String plantingDateFieldCode="dzsj";

    private String pickingDateFieldCode="cssj";


    /**
     * 根据功能字段名称获取功能id
     * @param searchParams
     * @throws Exception
     */
    public void setSearchParam(DaoSearch searchParams) throws Exception{
        Map<String,String> map=new HashMap<>();
        map.put("functionId",getPlantingFunctionId());
        map.put("organizationId",getOrganizationId());
        searchParams.setOther(map);
    }

    public String getPlantingFunctionId() throws Exception{
        String organizationId = getOrganizationId();
        //PlantingTime
        TraceFunFieldConfig packingSpecField = batchInfoMapper.selectByFieldCode(organizationId,plantingDateFieldCode);
        String functionId= packingSpecField.getFunctionId();
        return functionId;
    }

    @Override
    public Object searchResult(DaoSearch searchParams) {
        List<PlantingBatchDto> plantingBatchDtos= traceBatchInfoMapper.selectProcessingBatchByFunctionId(searchParams);
        //List<PlantingBatchDto> plantingBatchDtos= traceBatchInfos.stream().map(e->convert(e, PlantingBatchDto.class)).collect(Collectors.toList());

        try{
            selectSowingInfo2(plantingBatchDtos);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        return plantingBatchDtos;
    }

    public List<ObjectUniqueValueResult> selectProcessingBatch(DaoSearch searchParams) {
        List<PlantingBatchDto> traceBatchInfos= traceBatchInfoMapper.selectProcessingBatchByFunctionId(searchParams);
        List<ObjectUniqueValueResult> valueResults= traceBatchInfos.stream().map(p->new ObjectUniqueValueResult(p.getTraceBatchInfoId(),p.getTraceBatchName(),commonUtil.convert(p,Map.class)) ).collect(Collectors.toList());
        return valueResults;
    }

    public void selectSowingInfo2(List<PlantingBatchDto> plantingBatchDtos) throws Exception{
        List<String> batchIds= plantingBatchDtos.stream().map(e->String.format("'%s'",e.getTraceBatchInfoId())).collect(Collectors.toList());

        //播种->播种时间
        TraceFunFieldConfig sowingField = batchInfoMapper.selectByFieldCode(getOrganizationId(),plantingDateFieldCode);
        String enTableName= sowingField.getEnTableName();
        DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,sowingField.getFunctionId());

        String sql=String.format("SELECT * FROM %s WHERE TraceBatchInfoId IN (%s)", enTableName, StringUtils.join(batchIds,","));
        List<LinkedHashMap<String, Object>> batchList= dao.select(sql);

        List<JsonNode> massIfs=null;
        if(CollectionUtils.isNotEmpty(plantingBatchDtos)){
            massIfs=listMassIf(plantingBatchDtos);
        }

        if(CollectionUtils.isNotEmpty(batchList)){
            for(PlantingBatchDto plantingBatchDto:plantingBatchDtos){
                String sowingBatchId= plantingBatchDto.getTraceBatchInfoId();
                List<LinkedHashMap<String, Object>> sowingBatchDatas= batchList.stream().filter(e->e.get("TraceBatchInfoId").toString().equals(sowingBatchId)).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(sowingBatchDatas)){
                    plantingBatchDto.setStartDate( sowingBatchDatas.get(0).get(plantingDateFieldCode).toString());
                    if(plantingBatchDto.getStartDate().indexOf(".")>0){
                        plantingBatchDto.setStartDate( plantingBatchDto.getStartDate().substring(0, plantingBatchDto.getStartDate().indexOf(".")));
                    }
                }
                if(CollectionUtils.isNotEmpty(massIfs)){
                    String massIfId= plantingBatchDto.getMassId();
                    List<JsonNode> matchMassIfs= massIfs.stream().filter(e->e.get("massId").asText().equals(massIfId)).collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(matchMassIfs)){
                        plantingBatchDto.setMassIfName( matchMassIfs.get(0).get("massIfName").asText());
                        plantingBatchDto.setMassArea( matchMassIfs.get(0).get("massArea").asText()+ matchMassIfs.get(0).get("areaUnitName").asText());
                    }
                }
            }
        }
    }

    /**
     * 获取批次的育苗时间、种植地块名称面积信息
     * @param plantingBatchDtos
     * @throws Exception
     */
    public void selectSowingInfo3(List<PlantingBatchDto> plantingBatchDtos) throws Exception{
        List<String> batchIds= plantingBatchDtos.stream().map(e->String.format("'%s'",e.getTraceBatchInfoId())).collect(Collectors.toList());
        List<TraceBatchRelation> batchRelations= traceBatchRelationMapper.selectParentBatch(StringUtils.join(batchIds,","));
        List<String> parentIds= batchRelations.stream().map(e->String.format("'%s'",e.getParentBatchId())).collect(Collectors.toList());

        //播种->播种时间
        TraceFunFieldConfig sowingField = batchInfoMapper.selectByFieldCode(getOrganizationId(),plantingDateFieldCode);
        String enTableName= sowingField.getEnTableName();
        DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,sowingField.getFunctionId());

        String sql=String.format("SELECT * FROM %s WHERE TraceBatchInfoId IN (%s)", enTableName, StringUtils.join(parentIds,","));
        List<LinkedHashMap<String, Object>> batchList= dao.select(sql);

        List<TraceObjectBatchInfo> traceObjectBatchInfos= traceObjectBatchInfoMapper.listBatchInfoByIds(StringUtils.join(parentIds,","));
        List<JsonNode> massIfs=null;
        if(CollectionUtils.isNotEmpty(traceObjectBatchInfos)){
            //massIfs=listMassIf(traceObjectBatchInfos);
        }

        if(CollectionUtils.isNotEmpty(batchList)){
            for(PlantingBatchDto plantingBatchDto:plantingBatchDtos){
                List<TraceBatchRelation> sowingBatchs= batchRelations.stream().filter(e->e.getCurrentBatchId().equals(plantingBatchDto.getTraceBatchInfoId())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(sowingBatchs)){
                    String sowingBatchId= sowingBatchs.get(0).getParentBatchId();
                    List<LinkedHashMap<String, Object>> sowingBatchDatas= batchList.stream().filter(e->e.get("TraceBatchInfoId").toString().equals(sowingBatchId)).collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(sowingBatchDatas)){
                        plantingBatchDto.setStartDate( sowingBatchDatas.get(0).get(sowingDateFieldCode).toString());
                        if(plantingBatchDto.getStartDate().indexOf(".")>0){
                            plantingBatchDto.setStartDate( plantingBatchDto.getStartDate().substring(0, plantingBatchDto.getStartDate().indexOf(".")));
                        }
                    }

                    if(CollectionUtils.isNotEmpty(massIfs)){
                        List<TraceObjectBatchInfo> sowingBatchInfos= traceObjectBatchInfos.stream().filter(e->e.getTraceBatchInfoId().equals(sowingBatchId)).collect(Collectors.toList());
                        if(CollectionUtils.isNotEmpty(sowingBatchDatas)){
                            String massIfId= sowingBatchInfos.get(0).getObjectId();
                            List<JsonNode> matchMassIfs= massIfs.stream().filter(e->e.get("massId").asText().equals(massIfId)).collect(Collectors.toList());
                            if(CollectionUtils.isNotEmpty(matchMassIfs)){
                                plantingBatchDto.setMassIfName( matchMassIfs.get(0).get("massIfName").asText());
                                plantingBatchDto.setMassId(matchMassIfs.get(0).get("massId").asText());
                                plantingBatchDto.setMassArea( matchMassIfs.get(0).get("massArea").asText()+ matchMassIfs.get(0).get("areaUnitName").asText());
                            }
                        }

                    }
                }
            }
        }


    }

    /**
     * 根据地块id数组获取地块信息列表
     * @param traceObjectBatchInfos
     * @return
     * @throws Exception
     */
    private List<JsonNode> listMassIf(List<PlantingBatchDto> traceObjectBatchInfos) throws Exception{
        List<String> massIfIds= traceObjectBatchInfos.stream().map(e->e.getMassId()).collect(Collectors.toList());
        List<JsonNode> jsonNodeList=new ArrayList<>();
        Map<String, Object> params=new HashMap<>();
        params.put("massIds",massIfIds);
        Map<String, String> header = getSuperCodeToken();
        header.put(getSysAuthHeaderKey(),getSecretKeyForUser());
        ResponseEntity<String> rest = restTemplateUtil.postJsonDataAndReturnJosn(restUserUrl + "/mass/listBaseMassifbaseByMassIds",JSONObject.toJSONString(params) , header );
        if (rest.getStatusCode().value() == 200) {
            String body = rest.getBody();
            ArrayNode resultsNode = (ArrayNode)new ObjectMapper().readTree(body).get("results");
            for(JsonNode jsonObject:resultsNode){
                jsonNodeList.add(jsonObject);
            }
            //List<JSONObject> massIfs = (List<JSONObject>) JSONObject.parseObject(body, ArrayList.class);

        }
        return jsonNodeList;
    }

    /**
     * 获取批次采收时间
     * @param plantingBatchDtos
     * @throws Exception
     */
    public void selectPickingInfo(List<PlantingBatchDto> plantingBatchDtos) throws Exception{
        List<String> batchIds= plantingBatchDtos.stream().map(e->String.format("'%s'",e.getTraceBatchInfoId())).collect(Collectors.toList());
        List<TraceBatchRelation> batchRelations= traceBatchRelationMapper.selectChildBatch(StringUtils.join(batchIds,","));
        if(CollectionUtils.isEmpty(batchRelations)){
            return;
        }
        List<String> parentIds= batchRelations.stream().map(e->String.format("'%s'",e.getCurrentBatchId())).collect(Collectors.toList());
        //采收->采收时间
        TraceFunFieldConfig pickingField = batchInfoMapper.selectByFieldCode(getOrganizationId(),pickingDateFieldCode);
        String enTableName= pickingField.getEnTableName();
        DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,pickingField.getFunctionId());

        String sql=String.format("SELECT * FROM %s WHERE TraceBatchInfoId IN (%s)", enTableName, StringUtils.join(parentIds,","));
        List<LinkedHashMap<String, Object>> batchList= dao.select(sql);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if(CollectionUtils.isNotEmpty(batchList)){
            for(PlantingBatchDto plantingBatchDto:plantingBatchDtos){
                List<TraceBatchRelation> sowingBatchs= batchRelations.stream().filter(e->e.getParentBatchId().equals(plantingBatchDto.getTraceBatchInfoId())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(sowingBatchs)){
                    String batchId= sowingBatchs.get(0).getCurrentBatchId();
                    List<LinkedHashMap<String, Object>> pickingBatchDatas= batchList.stream().filter(e->e.get("TraceBatchInfoId").toString().equals(batchId)).collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(pickingBatchDatas)){
                        plantingBatchDto.setEndDate(pickingBatchDatas.get(0).get(pickingDateFieldCode).toString());
                        if(StringUtils.isNotEmpty(plantingBatchDto.getEndDate())&& StringUtils.isNotEmpty(plantingBatchDto.getStartDate())){
                            int total= (int)((sdf.parse(plantingBatchDto.getEndDate()).getTime()-sdf.parse(plantingBatchDto.getStartDate()).getTime())/ (1000 * 60 * 60 * 24));
                            plantingBatchDto.setTotalDays(total+1);
                        }
                        if(plantingBatchDto.getEndDate().indexOf(".")>0){
                            plantingBatchDto.setEndDate( plantingBatchDto.getEndDate().substring(0, plantingBatchDto.getEndDate().indexOf(".")));
                        }
                    }
                }

            }
        }
    }

    @Override
    public int count(DaoSearch searchParams) {
        Integer total=traceBatchInfoMapper.countProcessingBatchByFunctionId(searchParams);
        return total;
    }

    /**
     * 根据批次id获取生产详情信息
     * @param traceBatchInfoId
     * @param isEndBatch
     * @return
     * @throws Exception
     */
    public RestResult<HashMap<String, Object>> listBatchProductionInfo(String traceBatchInfoId, boolean isEndBatch) throws Exception {
        Date start=null, end=null;
        TraceBatchInfo traceBatchInfo = traceBatchInfoMapper.selectByTraceBatchInfoId(traceBatchInfoId);
        RestResult<List<Map<String, Object>>> nodeDataResult = traceFunTemplateconfigService.queryNodeInfo(traceBatchInfo.getTraceBatchInfoId(), traceBatchInfo.getTraceTemplateId(), true, null,start,end);
        List<Map<String, Object>> batchDatas =nodeDataResult.getResults();
        HashMap<String, Object> dataMap = new HashMap<>();

        Date nodeEndTime=null;
        if(StringUtils.isNotEmpty(traceBatchInfo.getCreateTime())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            nodeEndTime =sdf.parse( traceBatchInfo.getCreateTime());
        }

        //递归查询所有父级批次，查询所有父级批次对应的溯源信息数据并返回
        traceBatchInfoService.getParentNodeInfo(traceBatchInfoId,batchDatas,true, null, nodeEndTime);

        for(Map<String, Object> nodeMap:batchDatas){
            List<Field> fields= (List<Field>)nodeMap.get("lineData");
            String operateTime= fields.stream().filter(e->e.getFieldType().equals("6") || e.getFieldType().equals("8")).collect(Collectors.toList()).get(0).getFieldValue().toString();
            operateTime=operateTime.substring(0,10);
            nodeMap.put("operateTime",operateTime);
        }
        Map<String,List<Map<String, Object>>> dateNodeMap= batchDatas.stream().collect(Collectors.groupingBy(e->e.get("operateTime").toString()));
        batchDatas=new ArrayList<>();

        Date startDate=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> sortDateList=dateNodeMap.keySet().stream().sorted().collect(Collectors.toList());
        for(String date:sortDateList){
            Map<String, Object> dateNode=new HashMap<>();
            dateNode.put("operateTime",date);
            dateNode.put("nodeInfo",dateNodeMap.get(date));

            int index=1;
            if(CollectionUtils.isEmpty(batchDatas)){
                startDate=sdf.parse(date);
            } else {
                Date currDate=sdf.parse(date);
                index =1+ (int)((currDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)); // 计算天
            }
            dateNode.put("index",index);
            batchDatas.add(dateNode);
        }

        List<PlantingBatchDto> plantingBatchDtos=new ArrayList<>();
        PlantingBatchDto plantingBatchDto=null;
        if(isEndBatch){
            List<TraceBatchRelation> traceBatchRelations= traceBatchRelationMapper.selectByBatchId(traceBatchInfo.getTraceBatchInfoId());
            TraceBatchRelation relation=traceBatchRelations.get(0);
            plantingBatchDto=new PlantingBatchDto(traceBatchInfo.getTraceBatchName(),relation.getParentBatchId(),traceBatchInfo.getProductName(),traceBatchInfo.getTraceBatchInfoId());
        } else {
            plantingBatchDto=new PlantingBatchDto(traceBatchInfo.getTraceBatchName(),traceBatchInfo.getTraceBatchInfoId(),traceBatchInfo.getProductName());
        }
        BatchStoragePlaceRelation batchStoragePlaceRelation= batchStoragePlaceRelationMapper.selectByTraceBatchInfoId(plantingBatchDto.getTraceBatchInfoId());
        if(batchStoragePlaceRelation!=null){
            plantingBatchDto.setMassId(batchStoragePlaceRelation.getStoragePlaceId());
        }
        plantingBatchDtos.add(plantingBatchDto);

        selectSowingInfo2(plantingBatchDtos);
        selectPickingInfo(plantingBatchDtos);

        dataMap.put("productInfo", plantingBatchDtos.get(0));
        dataMap.put("nodeInfo", batchDatas);
        RestResult<HashMap<String, Object>> backResult = new RestResult<>();
        backResult.setState(200);
        backResult.setResults(dataMap);
        return backResult;
    }

    /**
     * 根据基地id获取地块信息列表
     * @param baseId
     * @return
     * @throws Exception
     */
    public List<JSONObject> listMassIfBatchInfo(String baseId) throws Exception{
        List<JSONObject> baseAreas =new ArrayList<>();
        Map<String, Object> params=new HashMap<>();
        params.put("baseId",baseId);
        Map<String, String> header = getSuperCodeToken();
        header.put(getSysAuthHeaderKey(),getSecretKeyForUser());
        ResponseEntity<String> rest = restTemplateUtil.getRequestAndReturnJosn(restUserUrl + "/base/child-greenhouse/list",params , header );
        if (rest.getStatusCode().value() == 200) {
            String body = rest.getBody();
            JsonNode resultsNode=new ObjectMapper().readTree(body).get("results");
            baseAreas= (List<JSONObject>)JSONObject.parseObject(resultsNode.toString(), ArrayList.class);
            Map<String,JSONObject> massIfs=new HashMap<>();
            baseAreas.stream().forEach(e->{
                List<JSONObject> childs= (List<JSONObject>)e.get("child");
                if(CollectionUtils.isNotEmpty(childs)){
                    childs.stream().forEach(c->{
                        String areaId= c.get("areaId").toString();
                        massIfs.put(areaId,c);
                    });
                }
            });
            List<String> massIfIds= massIfs.keySet().stream().map(e->String.format("'%s'",e)).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(massIfIds)){
                String functionId= getPlantingFunctionId();
                List<MassIfBatchDto> massIfBatchDtos= batchStoragePlaceRelationMapper.selectBatchByStoragePlaceIds(StringUtils.join(massIfIds,","), functionId);
                for(MassIfBatchDto massIfBatchDto:massIfBatchDtos){
                    String massIfId= massIfBatchDto.getStoragePlaceId();
                    ((JSONObject)massIfs.get(massIfId)).put("productName",massIfBatchDto.getProductName());
                }
                return baseAreas;
            }
        }
        return baseAreas;
    }

    /**
     * 生产详情导出excel
     * @param traceBatchInfoId
     * @param isEndBatch
     * @param res
     * @throws Exception
     */
    public void exportProductionInfo(String traceBatchInfoId, boolean isEndBatch, HttpServletResponse res) throws Exception{
        RestResult<HashMap<String, Object>> restResult= listBatchProductionInfo(traceBatchInfoId,isEndBatch);
        PlantingBatchDto plantingBatchDto= (PlantingBatchDto)restResult.getResults().get("productInfo");
        List<Map<String, Object>> nodeInfos=(List<Map<String, Object>>)restResult.getResults().get("nodeInfo");

        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
        fieldMap.put("traceBatchName", "种植批次");
        fieldMap.put("productName", "种植产品");
        fieldMap.put("startDate", "开始时间");
        if(isEndBatch){
            fieldMap.put("endDate", "结束时间");
            fieldMap.put("totalDays", "共计（天）");
        }

        fieldMap.put("massArea", "种植面积");
        fieldMap.put("massIfName", "种植大棚");

        List<HashMap<String,Object>> hashMaps=new ArrayList<HashMap<String,Object>>();
        hashMaps.add(JSONObject.parseObject(JSONObject.toJSONString(plantingBatchDto), HashMap.class));


        ExcelUtils.listToExcel(Arrays.asList(plantingBatchDto), fieldMap, "生产详情", res, new WriteSheet() {
            @Override
            public void write(WritableSheet sheet) {
                try{
                    for(int i=0;i<nodeInfos.size();i++){
                        Map<String, Object> nodeInfo= nodeInfos.get(i);
                        String dayNum=nodeInfo.get("index").toString();
                        Label lblDayNum = new Label(i, 3, String.format("第%s天",dayNum));
                        StringBuilder contents=new StringBuilder();
                        List<Field> fields= (List<Field>)(((ArrayList<LinkedHashMap<String,Object>>)nodeInfo.get("nodeInfo")).get(0).get("lineData"));
                        for(Field field:fields){
                            if(!field.getFieldType().equals("9")){
                                contents.append(String.format("%s：%s",field.getFieldName(),field.getFieldValue())+"\n");
                            }
                        }

                        WritableFont writableFont = new WritableFont(WritableFont.createFont("宋体"),11, WritableFont.NO_BOLD, false);
                        WritableCellFormat writableCellFormat = new WritableCellFormat(writableFont);
                        writableCellFormat.setVerticalAlignment(VerticalAlignment.TOP);
                        writableCellFormat.setWrap(true);

                        Label lblContent = new Label(i, 4, contents.toString(),writableCellFormat);
                        try{
                            sheet.addCell(lblDayNum);
                            sheet.addCell(lblContent);
                            CellView cellView = new CellView();
                            cellView.setAutosize(true); //设置自动大小
                            sheet.setColumnView(i,35);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    sheet.setRowView(4, 2200, false);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });//导出文件
    }

    public Map<String, Object> listTraceBatchInfoByOrgPage(Map<String, Object> map) throws Exception {
        map.put("organizationId", getOrganizationId());
        Integer total=null;
        ReturnParamsMap returnParamsMap=null;
        StringBuilder idsBuilder = new StringBuilder();
        Map<String, Object> dataMap=null;

        total = traceBatchInfoMapper.selectProcessingBatchCount(map);//获取总记录数
        returnParamsMap = getPageAndRetuanMap(map, total);
        List<ReturnTraceBatchInfo> traceBatchInfoList = traceBatchInfoMapper.selectProcessingBatch(returnParamsMap.getParamsMap());
        for (ReturnTraceBatchInfo returnTraceBatchInfo : traceBatchInfoList) {
            idsBuilder.append(returnTraceBatchInfo.getTraceTemplateId()).append(",");
        }
        dataMap = returnParamsMap.getReturnMap();
        getRetunMap(dataMap, traceBatchInfoList);

        return dataMap;
    }

}
