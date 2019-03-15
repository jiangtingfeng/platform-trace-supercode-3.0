package com.jgw.supercodeplatform.trace.service.antchain;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.StringReader;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


/**
 * RSA 公私钥 工具类
 */
public class CertUtil {
    private static final Logger logger = LoggerFactory.getLogger(CertUtil.class);

    /**
     * 加签方法
     * @param: plain，明文
     * @param: priKey，私钥文本
     */
    public static byte[] sign(byte[] plain, String priKey) throws Exception {
        PrivateKey privateKey = getPrivateKey(priKey);
        Signature signature = Signature.getInstance("Sha256WithRSA");
        signature.initSign(privateKey);
        signature.update(plain);
        byte[] signed = signature.sign();
        return signed;
    }

    /**
     * 验签方法
     * @param: plain，明文
     * @param: signedData，秘文
     * @param: pubKey，公钥文本
     */
    public static boolean rsaCert(byte[] plain, byte[] signedData, String pubkey) throws Exception {
        PublicKey publicKey = getPublicKey(pubkey);
        Signature signature2 = Signature.getInstance("Sha256WithRSA");
        signature2.initVerify(publicKey);
        signature2.update(plain);
        return signature2.verify(signedData);
    }

    /**
     * 构造公钥数据
     * @param: pubKey，公钥文本
     */
    public static PublicKey getPublicKey(String pubKey) throws Exception{
        //1.获取公钥数据
        byte[] bytesPublicBase64 = readKeyDatas(pubKey);
        //2.对读取回来的数据进行Base64解码
        byte[] bytesPublic = Base64.getDecoder().decode(bytesPublicBase64);
        //3.把解码后的数据,重新封装成一个PublicKey对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytesPublic);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            logger.error("[Notary API Error] " + e.getMessage(), e);
            throw e;
        } catch (InvalidKeySpecException e) {
            logger.error("[Notary API Error] " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 构造私钥数据
     * @param: pubKey，私钥文本
     */
    public static PrivateKey getPrivateKey(String priKey) throws Exception{
        //1.读取私钥文件,获取私钥数据
        byte[] bytesPrivateBase64 = readKeyDatas(priKey);
        //2.对读取回来的数据进行Base64解码
        byte[] bytesPrivate = Base64.getDecoder().decode(bytesPrivateBase64);
        //3.把解码后的数据,重新封装成一个PrivateKey对象
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytesPrivate);
        KeyFactory keyFactory=null;
        keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    private static byte[] readKeyDatas(String key) throws Exception{
        BufferedReader bufferedReader = new BufferedReader(new StringReader(key));
        String str=null;
        StringBuilder stringBuilder=new StringBuilder();
        while ((str=bufferedReader.readLine())!=null){
            if(str.contains("---")){
                continue;
            }
            stringBuilder.append(str);
        }
        return stringBuilder.toString().getBytes();
    }
}
