package com.jgw.supercodeplatform.trace.service.antchain;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.jgw.supercodeplatform.trace.dto.antchain.TraceabilityCommonMessage.*;
import com.jgw.supercodeplatform.trace.dto.antchain.TraceabilityMessage.*;
import com.jgw.supercodeplatform.trace.dto.antchain.TraceabilityQueryMessage.ScanUser;
import com.jgw.supercodeplatform.trace.dto.antchain.TraceabilityQueryMessage.TraceInfo;
import com.jgw.supercodeplatform.trace.dto.antchain.TraceabilityQueryMessage.TraceabilityDetails;
import com.jgw.supercodeplatform.trace.dto.antchain.TraceabilityQueryMessage.TxCodeQuery;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;

import java.util.ArrayList;
import java.util.List;

import static com.jgw.supercodeplatform.trace.dto.antchain.TraceabilityQueryMessage.ScanCodeQuery;


/**
 * 蚂蚁区块链通用溯源接口
 * 注意实际生产测试之前需要在服务端配置 APP_KEY，以及appkey对应的公私钥
 *
 * @author zhanjun
 * @version $Id: TraceInputLYDemoTest.java, v 0.1 2018-12-13 20:51 zhanjun Exp $$
 */
public class AntChainUtils {

    public static final String DEF_APP_KEY = "def_test_app_key";
    public static final String HOST_URL = "https://qa-top.baas.alipay.com/";
    //上链----------------------------------------
    // load public key

    // load private key
    public static final String publicKey = "-----BEGIN PUBLIC KEY-----\n"
            + "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5oqjwCcdLYPcljVgLNJDyfKlA\n"
            + "n2NchsCihfoZJ1IPDibPTBcXRUw9vJkBi0rPXpsDC45hrwP6Sa/BCf5CjLqjpWTd\n"
            + "q6o9O+0CizD+8g1P/T56EWPUUGlQSmn8bkW1dcv3WKp2Kiud1NWVdiYwS0gMYqaL\n"
            + "pcq+AVL4JqvBLoLYmwIDAQAB\n"
            + "-----END PUBLIC KEY-----";

    public static final String privateKey = "-----BEGIN PRIVATE KEY-----\n"
            + "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALmiqPAJx0tg9yWN\n"
            + "WAs0kPJ8qUCfY1yGwKKF+hknUg8OJs9MFxdFTD28mQGLSs9emwMLjmGvA/pJr8EJ\n"
            + "/kKMuqOlZN2rqj077QKLMP7yDU/9PnoRY9RQaVBKafxuRbV1y/dYqnYqK53U1ZV2\n"
            + "JjBLSAxipoulyr4BUvgmq8EugtibAgMBAAECgYBYyo/JuLOlv2yXfqL1QSBvi+cc\n"
            + "7OAG2sF7O2Aj9eXPI7K2hquoytGr2fDJh0myJIs6iqWA3cimR+aUC+adCFkgqg4M\n"
            + "ywj49tYx/kCCVV7BQB9XUJ0mqRaEpPlIlUjseE4hsxnWYMZ7kdDw0LpNAKU2D0K2\n"
            + "3zIgB7EjZJSa8dci2QJBAOtI+1iufEFSKwM1xVDDBACRa2uI7olDfJwALKEfUpiH\n"
            + "PIIzC2ZgF8EmRRN4yekx8+jKLt/heAL4kc5DRWWT1ocCQQDJ+qON+RmD4HSx2QCH\n"
            + "oUAQkKyuXGTXRGvc4dUa5G8vmYhEmPhLyYc77x+AqhH07dpOwDop3v1gkFss+EDN\n"
            + "PJ5NAkBcAAuYH5IikJiVBr+C1t6HwlT4lXCAZ80fysmorvDDB45XctIwU762HdrA\n"
            + "xGhfMNMo4XX2wNkGgF+zAYbvMyGdAkBlOOL2p7wNx1M0Phhx4HIG2zpvN4aiC3wy\n"
            + "+kqea5T9Oeh82Fy3PowzkScsiA7vvLQHe3aqvUImPrIy20c+k/6RAkAjxyB1rPod\n"
            + "IDbeK294kN/lkwlhXnzivG6kI2ryO6gDVy/T+NES/Zwoj/2uYV7r/ZR7Ag0i6bjq\n"
            + "E1EuAko0ySIe\n"
            + "-----END PRIVATE KEY-----\n";

    public static final int SUCCESS_CODE = 200;

    public static final String code = "http://trace.kf315.net/?traceBatchInfoId=";// 溯源h5地址

    //    public static final String BUSSINESSCODE = "JGWTRACE";
    public static final String BUSSINESSCODE = "BLOCKTRACE";

    /**
     * 注册环节名称
     */
    public static Long regPhase(String phaseName, String memo) throws Exception {
        String surl = HOST_URL + "api/register/phase";
        RegPhaseNameRequest phaseRegRequest1 = RegPhaseNameRequest.newBuilder()
                .setMemo(memo)
                .setPhaseName(phaseName)
                .build();
        //包装到payload中
        Any payload = Any.pack(phaseRegRequest1);
        CommonReply regReply = getCommonReply(surl, payload);
        if (regReply.getCode() == SUCCESS_CODE) {
            RegPhaseNameReply regPhaseNameReply = regReply.getPayload().unpack(RegPhaseNameReply.class);
            return regPhaseNameReply.getPayloadCategoryCode();
        } else {
            throw new SuperCodeTraceException(regReply.getMessage());
        }

    }

    public static CommonReply getCommonReply(String url, Any payload) throws Exception {
        //对payload内容签名
        byte[] sign = CertUtil.sign(payload.toByteArray(), privateKey);
        CommonRequest commonRequest = CommonRequest.newBuilder().
                setPayload(payload)
                .setAppId(DEF_APP_KEY)
                .setSign(ByteString.copyFrom(sign))
                .build();
        byte[] bytes1 = HttpStreamPost.okHttpPost(url, commonRequest.toByteArray());
        return CommonReply.parseFrom(bytes1);
    }

    /**
     * 注册 产品, 此接口只需执行一次
     */
    private static RegBusinessReply regProductCode(String batchId) throws Exception {
        String surl = HOST_URL + "api/register/businessCode";
        List<CodeRuleConfig> configList = new ArrayList<>();
        CodeRuleConfig ruleConfig2 = CodeRuleConfig.newBuilder()
                .setRealCode(code + batchId)
                .setRegExp("^http://trace.kf315.net/\\?traceBatchInfoId=(.*)")
                .setCodeType(QrCodeTypeEnum.TRACE_BATCH_CODE)
                .build();
        configList.add(ruleConfig2);
        // 定义商家码规则
        RegBusinessRequest ruleConfig = RegBusinessRequest.newBuilder()
                .setBusinessName(BUSSINESSCODE)
                .setBusinessCode(BUSSINESSCODE)
                .addAllCodeRuleConfig(configList)
                .build();
        //包装到payload中
        Any payload = Any.pack(ruleConfig);
        CommonReply regReply = getCommonReply(surl, payload);
        if (regReply.getCode() == SUCCESS_CODE) {
            //解析出返回对象的方法
            return regReply.getPayload().unpack(RegBusinessReply.class);
        } else {
            throw new SuperCodeTraceException(regReply.getMessage());
        }
    }

    /**
     * 注册 码包
     */

    public static RegProductListReply regProductList(String batchId) throws Exception {
        String surl = HOST_URL + "api/register/productList";
        //产品码列表
        List<ProductCode> prodList = new ArrayList<>();
        // 码1
        ProductCode productCode1 = ProductCode.newBuilder()
                .setCode(code + batchId)
                .setCodeLevel(1)
                .setParentCode("-1")
                .setCodeType(QrCodeTypeEnum.TRACE_BATCH_CODE)
                .build();
        prodList.add(productCode1);
        RegProductListRequest listRequest = RegProductListRequest.newBuilder()
                .setBusinessCode(BUSSINESSCODE)
                .setBatchCode(BUSSINESSCODE)
                .setProductTotal(prodList.size())
                .addAllProductList(prodList)
                .build();
        //包装到payload中
        Any payload = Any.pack(listRequest);
        CommonReply regReply = getCommonReply(surl, payload);
        if (regReply.getCode() == SUCCESS_CODE) {
            //解析出返回对象的方法
            return regReply.getPayload().unpack(RegProductListReply.class);
        } else {
            throw new SuperCodeTraceException(regReply.getMessage());
        }
    }


    /**
     * 溯源上链
     */

    public static CommonReply sendCoChain(String batchId, ItemGroup.Builder group, Long loadCategoryCode, long txTime) throws Exception {
        // 设置一个产品批次码，这个值是
        CodeNum pgk001 = CodeNum.newBuilder()
                .setCode(code + batchId)
                .setCodeType(QrCodeTypeEnum.TRACE_BATCH_CODE)
                .build();
        List<CodeNum> codeNumList = new ArrayList<>();
        codeNumList.add(pgk001);
        TraceabilityPhaseRequest.Builder phaseRequest = TraceabilityPhaseRequest.newBuilder()
                .setBizRequestTime(txTime)
                .setBusinessCode(BUSSINESSCODE)
                .setPayloadCategoryCode(loadCategoryCode)
                .setCodesTotal(codeNumList.size())
                .addAllCodes(codeNumList);
        phaseRequest.setPayload(group);
        String surl = HOST_URL + "api/send/tracePhase";
        TraceabilityPhaseRequest phaseRequestData = phaseRequest.build();
        //包装到payload中
        Any payload = Any.pack(phaseRequestData);
        return getCommonReply(surl, payload);
    }

    /**
     * 查询二维码对应的链上内容
     */
    public static TraceabilityDetails testQrCode(String batchId) throws Exception {
        String surl = HOST_URL + "api/scan/qrCode";

        // user 在内码情况下必须传，
        ScanUser user = ScanUser.newBuilder().setScanTime(System.currentTimeMillis()).setLatitude("120.000")
                .setLongitude("30.000").setSrcType("alipay").setUserId("user11").build();

        //ScanCodeQuery scanCodeQuery = ScanCodeQuery.newBuilder().setQrCode("http://show.tmall.com:9090/h5/132132")
        //    .setScanUser(
        //        user).build();

        ScanCodeQuery scanCodeQuery = ScanCodeQuery.newBuilder().setQrCode(
                code + batchId)
//                .setScanUser(user)
                .build();

        //包装到payload中
        Any anyload = Any.pack(scanCodeQuery);
        CommonReply regReply = getCommonReply(surl, anyload);
        if (regReply.getCode() == SUCCESS_CODE) {
            //解析出返回对象的方法
            return regReply.getPayload().unpack(TraceabilityDetails.class);
        } else {
            throw new SuperCodeTraceException(regReply.getMessage());
        }

    }

    /**
     * 查询txhash 是否在链上
     */

    public static TraceInfo getTxByTxHash(String txHash) throws Exception {
        String surl = HOST_URL + "api/tx/check";
        TxCodeQuery query = TxCodeQuery.newBuilder().setTxHash(txHash).build();
        //包装到payload中
        Any anyload = Any.pack(query);
        CommonReply regReply = getCommonReply(surl, anyload);
        if (regReply.getCode() == SUCCESS_CODE) {
            return regReply.getPayload().unpack(TraceInfo.class);
        } else {
            throw new SuperCodeTraceException(regReply.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {


        String batchId = "23";

//        RegBusinessReply regBusinessReply = regProductCode(batchId);
//        System.out.println(regBusinessReply.toString());
//        RegProductListReply regProductListReply = regProductList(batchId);
//        System.out.println(regProductListReply.toString());
//        Thread.sleep(20000L);
        Item.Builder item = Item.newBuilder()
                .setKey("KUAQU")
                .setTitle("品名")
                .setType("222")
                .setValue("润肌赋活修护油")
                .setBizTime(6)
                .putExtInfo("1", "2");

        UploadInfo.Builder uploadInfo = UploadInfo.newBuilder()
                .setUploader("天猫信息公司")
                .setCertHash("f0a133d03f4b32038c3e2ba396ce21dcadf8de6912ad54f3290b054a092a95f7")
                .setUploadTime(1538201922)
                .setLatitude("")
                .setLongitude("");

        ItemGroup.Builder group = ItemGroup.newBuilder()
                .setKey("")
                .setTitle("产品认证信息")
                .setSubTitle("天猫溯源")
                .setComment("")
                .addItems(item.build())
                .setUploadInfo(uploadInfo.build());
        CommonReply commonReply = sendCoChain(batchId, group,System.currentTimeMillis(),System.currentTimeMillis());
        System.out.println(commonReply.toString());
        if (commonReply.getCode() == AntChainUtils.SUCCESS_CODE) {
            TraceabilityPhaseReply traceabilityPhaseReply = commonReply.getPayload().unpack(TraceabilityPhaseReply.class);
            Thread.sleep(10000);
            TraceInfo txByTxHash = getTxByTxHash(traceabilityPhaseReply.getTxHash());
            System.out.println(txByTxHash.toString());
        }
//        TraceabilityDetails traceabilityDetails = testQrCode(batchId);
//        System.out.println(traceabilityDetails.toString());
    }
}