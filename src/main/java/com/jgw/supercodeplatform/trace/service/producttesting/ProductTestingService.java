package com.jgw.supercodeplatform.trace.service.producttesting;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.model.ReturnParamsMap;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.trace.dao.mapper1.producttesting.ProductTestingItemMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.producttesting.ProductTestingMapper;
import com.jgw.supercodeplatform.trace.dto.producttesting.ImageItem;
import com.jgw.supercodeplatform.trace.dto.producttesting.ProductTestingParam;
import com.jgw.supercodeplatform.trace.pojo.producttesting.ProductTesting;
import com.jgw.supercodeplatform.trace.pojo.producttesting.ProductTestingItem;
import com.jgw.supercodeplatform.trace.pojo.producttesting.ProductTestingItemEx;
import com.jgw.supercodeplatform.trace.pojo.producttesting.TestingType;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import com.jgw.supercodeplatform.trace.service.tracefun.TraceBatchRelationEsService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductTestingService extends AbstractPageService {

    private static Logger logger= LoggerFactory.getLogger(ProductTestingService.class);

    @Autowired
    private ProductTestingMapper productTestingMapper;

    @Autowired
    private ProductTestingItemMapper productTestingItemMapper;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Value("${rest.user.url}")
    private String restUserUrl;


    public String insert(ProductTestingParam productTestingParam) throws Exception{
        Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(productTestingParam), Map.class);

        ProductTesting productTesting = JSONObject.parseObject(JSONObject.toJSONString(map), ProductTesting.class);

        AccountCache userAccount = getUserLoginCache();
        String organizationId = getOrganizationId();
        String organizeName=getOrganizationName();

        productTesting.setSysId(getSysId());
        productTesting.setOrganizeName(organizeName);
        productTesting.setOrganizeId(organizationId);
        productTesting.setCreateId(userAccount.getUserId());
        productTesting.setCreateMan(userAccount.getUserName());
        productTesting.setProductTestingId(getUUID());
        productTesting.setCreateTime(new Date());
        productTestingMapper.insert(productTesting);
        String productTestingId=productTesting.getProductTestingId();

        for(ProductTestingItem productTestingItem: productTestingParam.getProductTestingItems()){

            productTestingItem.setProductTestingId(productTestingId);
            productTestingItem.setProductTestingItemId(getUUID());
            if(StringUtils.isNotEmpty(productTestingItem.getPdfs())){
                String imageJson= getImageJson(productTestingItem.getPdfs());
                productTestingItem.setPdfImgs(imageJson);
            }
            productTestingItemMapper.insert(productTestingItem);
        }

        return productTesting.getProductTestingId();
    }

    public List<ProductTesting> list()
    {
        return productTestingMapper.selectAll();
    }


    public Map<String, Object> getById(Integer productTestingId ) throws Exception {
        ProductTesting productTesting= productTestingMapper.selectByPrimaryKey(productTestingId);
        String prdTestId = productTesting.getProductTestingId();
        List<ProductTestingItem> productTestingItems= productTestingItemMapper.selectByProductTestingId(String.format("'%s'",prdTestId));
        Map<String, Object> testingMap= beanToMap(productTesting);
        testingMap.put("testingItems",productTestingItems);
        return testingMap;
    }

    public Map<String, Object> listProductTesting(Map<String, Object> map) throws Exception {
        String organizationId=commonUtil.getOrganizationId();
        map.put("organizeId", getOrganizationId());

        ReturnParamsMap returnParamsMap=null;
        Map<String, Object> dataMap=null;
        Integer total=null;
        total=productTestingMapper.getCountByCondition(map);
        returnParamsMap = getPageAndRetuanMap(map, total);

        List<ProductTesting> productTestings= productTestingMapper.selectProductTesting(map);
        List<Map<String, Object>> productTestingList=new ArrayList<Map<String, Object>>();
        if(productTestings.size()>0){
            List<String> productTestingIds= productTestings.stream().map(e->"'"+e.getProductTestingId()+"'").collect(Collectors.toList());
            String ids= StringUtils.join(productTestingIds,",");
            List<ProductTestingItem> productTestingItems= productTestingItemMapper.selectByProductTestingId(ids);

            for(ProductTesting productTesting:productTestings){
                Map<String, Object> testingMap= beanToMap(productTesting);
                productTestingList.add(testingMap);
                String productTestingId= productTesting.getProductTestingId();
                List<ProductTestingItem> productTestingItems1= productTestingItems.stream().filter(e->e.getProductTestingId().equals(productTestingId)).collect(Collectors.toList());
                testingMap.put("testingItems",productTestingItems1);
            }
        }

        dataMap = returnParamsMap.getReturnMap();
        getRetunMap(dataMap, productTestingList);

        return dataMap;
    }

    public void delete(int id)
    {
        productTestingMapper.deleteByPrimaryKey(id);
    }

    public void update(ProductTestingParam productTestingParam)  throws Exception {
        Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(productTestingParam), Map.class);

        ProductTesting productTesting = JSONObject.parseObject(JSONObject.toJSONString(map), ProductTesting.class);

        productTestingMapper.updateByPrimaryKey(productTesting);
        String productTestingId=productTesting.getProductTestingId();

        for(ProductTestingItem productTestingItem: productTestingParam.getProductTestingItems()){

            if(productTestingItem.getId()==null){
                productTestingItem.setProductTestingId(productTestingId);
                productTestingItem.setProductTestingItemId(getUUID());
                if(StringUtils.isNotEmpty(productTestingItem.getPdfs())){
                    String imageJson= getImageJson(productTestingItem.getPdfs());
                    productTestingItem.setPdfImgs(imageJson);
                }
                productTestingItemMapper.insert(productTestingItem);
            }else {
                ProductTestingItem productTestingItem1= productTestingItemMapper.selectByPrimaryKey(productTestingItem.getId());
                if(!productTestingItem.getPdfs().equals(productTestingItem1.getPdfs())){
                    String imageJson= getImageJson(productTestingItem.getPdfs());
                    productTestingItem.setPdfImgs(imageJson);
                }
                productTestingItemMapper.updateByPrimaryKey(productTestingItem);
            }
        }
    }

    public List<JSONObject> getProductTesting(String tracebatchinfoid){
        List<ProductTestingItemEx> productTestingItemExes= productTestingItemMapper.selectProductTestingItem(tracebatchinfoid);
        List<ProductTestingItemEx> testingItemExes=null,imgItems=null,pdfItems=null;
        List<JSONObject> result=new ArrayList<JSONObject>();
        if(productTestingItemExes!=null && productTestingItemExes.size()>0){
            imgItems= productTestingItemExes.stream().filter(e->!StringUtils.isEmpty(e.getImgs())).collect(Collectors.toList());

            String json=null;
            if(imgItems!=null && imgItems.size()>0){
                for(ProductTestingItemEx productTestingItemEx:imgItems){
                    json= productTestingItemEx.getImgs();
                    List<JSONObject> itemImgs= (List<JSONObject>)JSONObject.parseObject(json, ArrayList.class);
                    result.addAll(itemImgs);
                }
            } else {
                for(ProductTestingItemEx productTestingItemEx:productTestingItemExes){
                    json= productTestingItemEx.getPdfImgs();
                    List<JSONObject> itemImgs= (List<JSONObject>)JSONObject.parseObject(json, ArrayList.class);
                    result.addAll(itemImgs);
                }
            }
        }
        return result;
    }

    public String uploadImage(String fileName) throws Exception{
        String fileId=null;

        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, String> headerMap = new HashMap<String, String>();

        headerMap.put("super-token", commonUtil.getSuperToken());

        File resource = new File(fileName);
        params.put("file", resource);
        params.put("name",fileName);

        ResponseEntity<String> rest = restTemplateUtil.uploadFile(restUserUrl + "/file/upload","file", params,headerMap);
        if (rest.getStatusCode().value() == 200) {
            String body = rest.getBody();

            JsonNode node = new ObjectMapper().readTree(body);
            if (200 == node.get("state").asInt()) {
                fileId=node.get("results").asText();
            }
        }
        return fileId;
    }

    public String download(String url, String fileName) {
        String path=null;
        try{
            path=getRoot()+ commonUtil.getUUID()+"/"+fileName;
            FileUtils.copyURLToFile(new URL(url), new File(path));
        }catch(Exception e){
            e.printStackTrace();
        }
        return path;
    }

    private String getRoot(){
        File directory = new File("");
        String root=directory.getAbsolutePath()+"/files/";
        System.out.println(root);
        return root;
    }

    public String getImageJson(String pdfJson) throws Exception{
        String imageJson=null;
        try{
            JsonNode jsonNode = new ObjectMapper().readTree(pdfJson);
            ArrayNode arrayNode= (ArrayNode)jsonNode;
            List<String> imageIds=new ArrayList<String>();
            for(JsonNode node:arrayNode){
                String url= arrayNode.get(0).get("url").asText();
                String name= arrayNode.get(0).get("name").asText();
                List<String> ids= getImages(url,name);
                imageIds.addAll(ids);
            }
            List<ImageItem> imageItems= imageIds.stream().map(e->new ImageItem(e)).collect(Collectors.toList());
            imageJson= JSONObject.toJSONString(imageItems);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        return imageJson;
    }

    public List<String> getImages(String pdfUrl,String pdfName)
    {
        String pdfPath=download(pdfUrl,pdfName);
        List<String> imageIds=new ArrayList<String>();
        File file = new File(pdfPath);
        try {
            logger.info("PDDocument.load: "+file);
            PDDocument doc = PDDocument.load(file);
            logger.info("PDDocument.load complete");
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for(int i=0;i<pageCount;i++){
                String path=getRoot()+ commonUtil.getUUID()+"/"+String.valueOf(i)+".png";
                File imagefile = new File(path);
                File fileParent = imagefile.getParentFile();
                if(!fileParent.exists()){
                    fileParent.mkdirs();
                }
                if (!imagefile.exists()) {
                    imagefile.createNewFile();
                }

                logger.info("renderer.renderImage: "+String.valueOf(i));
                BufferedImage image = renderer.renderImageWithDPI(i, 296);
                logger.info("renderer.renderImage complete ");
                ImageIO.write(image, "PNG", imagefile);
                logger.info("ImageIO.write complete ");

                String fileId= uploadImage(path);
                logger.info("file.upload complete ");
                fileId= pdfUrl.substring(0,pdfUrl.lastIndexOf("/")+1)+fileId;
                imageIds.add(fileId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageIds;
    }
}
