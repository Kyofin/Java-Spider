package com.wugui.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 *
 * 测试使用okhttp上传图片到公网图床，方便存放自己爬虫获取的图片
 *
 * @program: java-crawler
 * @author: huzekang
 * @create: 2019-06-17 12:08
 **/
@Slf4j
public class FileUpload2Cloud {

    /**
    * 新浪图床
    *
    * @author: huzekang
    * @Date: 2019-06-17
    */
    @Test
    public void sinaImageUpload() throws IOException {
        String apiUrl = "https://apis.yum6.cn/api/5bd44dc94bcfc?token=f07b711396f019a05bc7129c4507fb65c5";
        String filePath = "/Users/huzekang/Pictures/07.jpg";

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "08.jpg",
                        RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)))
                .build();

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(requestBody)
                .build();


        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        System.out.println(response.body().string());

    }

    /**
     * 待完善(上传不成功)
     * @throws IOException
     */
    @Test
    public void v2eXImageUpload() throws IOException {
        String apiUrl = "https://sm.ms/api/upload";
        String filePath = "/Users/huzekang/Pictures/07.jpg";

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("smfile", "07.jpg",
                        RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)))
                .build();

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(requestBody)
                .build();


        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        System.out.println(response.body().string());

    }
}
