package com.jgw.supercodeplatform.trace.controller.common;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.jgw.supercodeplatform.trace.service.producttesting.ProductTestingService;
import com.jgw.supercodeplatform.trace.service.tracefun.CodeRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jgw.supercodeplatform.trace.common.cache.FunctionFieldCache;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.ValidationUtils;
import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParamListP;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/trace/test")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = "测试接口")
public class TestController {
	
    @Autowired
    private FunctionFieldCache functionFieldCache;

    @Autowired
    private CodeRelationService codeRelationService;
    
    @Autowired
    private CommonUtil commonUtil;
	/**
	 * 新增功能
	 * 
	 * @param param
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 * 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(value = "测试参数非空接口", notes = "")
	@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
	public RestResult<List<String>> add(  @RequestBody @Valid TraceFunFieldConfigParamListP param, HttpServletRequest request) throws IOException, ParseException {
		ValidationUtils.validate(param);
		RestResult<List<String>> result=new RestResult<List<String>>();
		System.out.println(param);
		return result;
	}
   /**
    * 创建二维码
    * @param content
    * @param response
    * @return
    * @throws WriterException
    * @throws IOException
    */
	@RequestMapping(value = "/qrCode", method = RequestMethod.GET)
	@ApiOperation(value = "测试生成二维码接口", notes = "")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
        @ApiImplicitParam(name = "content", paramType = "query", defaultValue = "64b379cd47c843458378f479a115c322", value = "", required = true),
   })
   public  boolean createQrCode(String content,HttpServletResponse response) throws WriterException, IOException{  
           //设置二维码纠错级别ＭＡＰ
           Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();  
           hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  // 矫错级别  
           QRCodeWriter qrCodeWriter = new QRCodeWriter();  
           //创建比特矩阵(位矩阵)的QR码编码的字符串  
           BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 900, 900, hintMap);  
           // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
           int matrixWidth = byteMatrix.getWidth();  
           BufferedImage image = new BufferedImage(matrixWidth-200, matrixWidth-200, BufferedImage.TYPE_INT_RGB);  
           image.createGraphics();  
           Graphics2D graphics = (Graphics2D) image.getGraphics();  
           graphics.setColor(Color.WHITE);  
           graphics.fillRect(0, 0, matrixWidth, matrixWidth);  
           // 使用比特矩阵画并保存图像
           graphics.setColor(Color.BLACK);  
           for (int i = 0; i < matrixWidth; i++){
               for (int j = 0; j < matrixWidth; j++){
                   if (byteMatrix.get(i, j)){
                       graphics.fillRect(i-100, j-100, 1, 1);  
                   }
               }
           }
           return ImageIO.write(image, "JPEG", response.getOutputStream());  
   }  
	  /**
	    * @param content
	    * @param response
	    * @return
	    * @throws WriterException
	    * @throws IOException
	 * @throws SuperCodeTraceException 
	    */
		@RequestMapping(value = "/flushCacheFiled", method = RequestMethod.GET)
		@ApiOperation(value = "刷新节点或功能字段缓存接口接口", notes = "")
	    @ApiImplicitParams({
	        @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
	        @ApiImplicitParam(name = "traceTemplateId", paramType = "query", defaultValue = "e5aa3b38afcc418eb1b99a5ab386edc7", value = "", required = true),
	        @ApiImplicitParam(name = "functionId", paramType = "query", defaultValue = "c46138774d424f1d9791a9c5c4362b6f", value = "", required = true),
	        @ApiImplicitParam(name = "typeClass", paramType = "query", defaultValue = "2", value = "", required = true),
	   })
	   public  RestResult<Map<String, Map<String, TraceFunFieldConfig>>> flushCacheFiled(String traceTemplateId,String functionId,int typeClass,HttpServletResponse response) throws WriterException, IOException, SuperCodeTraceException{  
			Map<String, TraceFunFieldConfig> map1= functionFieldCache.getFunctionIdFields(traceTemplateId, functionId, typeClass);
			functionFieldCache.flush(traceTemplateId, functionId, typeClass);
			Map<String, TraceFunFieldConfig> map2= functionFieldCache.getFunctionIdFields(traceTemplateId, functionId, typeClass);
			RestResult<Map<String, Map<String, TraceFunFieldConfig>>> res2t=new RestResult<Map<String, Map<String, TraceFunFieldConfig>>>();
			Map<String, Map<String, TraceFunFieldConfig>> all=new LinkedHashMap<String, Map<String, TraceFunFieldConfig>>();
			all.put("former", map1);
			all.put("after", map2);
			res2t.setState(200);
			res2t.setResults(all);
			return res2t;
	   }

	   @Autowired
	   private ProductTestingService productTestingService;

	@PostMapping("/getval")
	@ApiOperation(value = "test", notes = "")
	public String getval(String json) throws Exception{

		Map<String, String> headerMap = new HashMap<String, String>();

		headerMap.put("super-token", commonUtil.getSuperToken());

		String url= codeRelationService.getUrl(headerMap);
		return "ok";
	}
}
