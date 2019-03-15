package com.jgw.supercodeplatform.trace.service.antchain;

import okhttp3.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author zhanjun
 * @version $Id: HttpStreamPost.java, v 0.1 2018-12-18 14:12 zhanjun Exp $$
 */
public class HttpStreamPost {

    public static byte[] okHttpPost(String surl, byte[] inputBytes) {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .readTimeout(20000, TimeUnit.SECONDS)
            .build();


        Request request = new Request.Builder()
            .url(surl)
            .post(RequestBody.create(MediaType.parse("application/x-protobuf"), inputBytes))
            .build();

        InputStream inputStream = null;
        try {
            Response response = client.newCall(request).execute();
            inputStream = response.body().byteStream();
            byte[] byt = read(inputStream);
            return byt;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static byte[] read(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int num = inputStream.read(buffer);
            while (num != -1) {
                baos.write(buffer, 0, num);
                num = inputStream.read(buffer);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
