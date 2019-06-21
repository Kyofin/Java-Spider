package com.wugui.httpclient;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;

/**
 * 使用httpclient进行代理测试
 * 连接ssr翻墙访问谷歌
 **/
@Slf4j
public class TestProxy {

    @Test
    public void  test() throws IOException {
        //设置代理IP、端口、协议（请分别替换）
        HttpHost proxy = new HttpHost("192.168.5.28", 1087, "http");

        //把代理设置到请求配置
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .build();

        //实例化CloseableHttpClient对象
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();

        //访问目标地址
        HttpGet httpGet = new HttpGet("https://www.google.com");

        //请求返回
        CloseableHttpResponse httpResp = httpclient.execute(httpGet);
        try {
            int statusCode = httpResp.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                log.info(IoUtil.read(httpResp.getEntity().getContent()).toString());

            }
        } catch (Exception e) {

        } finally {
            httpResp.close();
        }

    }
}
