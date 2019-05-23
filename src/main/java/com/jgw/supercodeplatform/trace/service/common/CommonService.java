package com.jgw.supercodeplatform.trace.service.common;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Service
public class CommonService  extends CommonUtil {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Value("${rest.user.url}")
    private String restUserUrl;

    @Value("${qiniu.upload.domain}")
    private String domain;


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

                fileId=domain+"/"+fileId;
            }
        }
        return fileId;
    }

    public  boolean createQrCode(String content, OutputStream output) throws WriterException, IOException {
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
        return ImageIO.write(image, "JPEG", output);
    }

    public String getRoot(){
        File directory = new File("");
        String root=directory.getAbsolutePath()+"/files/";
        System.out.println(root);
        return root;
    }

    public String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

}
