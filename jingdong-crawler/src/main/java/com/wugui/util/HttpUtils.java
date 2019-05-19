package com.wugui.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import com.google.common.collect.Lists;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

/**
* 自定义http工具
*
* @author: huzekang
* @Date: 2019-05-19
*/
@Component
public class HttpUtils {
	private PoolingHttpClientConnectionManager cm;

	public HttpUtils() {
		this.cm = new PoolingHttpClientConnectionManager();
		// 设置最大连接数
		cm.setMaxTotal(200);
		// 设置每个主机的并发数
		cm.setDefaultMaxPerRoute(10);
	}

    /**
     * 根据请求地址下载页面数据
     *
     * @param url
     * @return 页面数据
     */
	public String getHtml(String url) {
		CloseableHttpClient client = HttpClients.custom()
				.setDefaultHeaders(Lists.newArrayList(new BasicHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36")))
				.setConnectionManager(this.cm).build();
		HttpGet httpGet = new HttpGet(url);
		// 设置请求参数RequestConfig
		httpGet.setConfig(this.getConfig());
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String html = "";
				// 如果response.getEntity获取的结果是空，在执行EntityUtils.toString会报错
				// 需要对Entity进行非空的判断
				if (response.getEntity() != null) {
					html = EntityUtils.toString(response.getEntity(), "utf8");
				}
				return html;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					// 关闭连接
					response.close();
				}
				// 不能关闭，现在使用的是连接管理器
				// httpClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	// 获取请求参数对象
	private RequestConfig getConfig() {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(1000)// 设置创建连接的超时时间
				.setConnectionRequestTimeout(500) // 设置获取连接的超时时间
				.setSocketTimeout(10000) // 设置连接的超时时间
				.build();

		return config;
	}

	// 获取图片
	public String getImage(String url) {
		// 获取HttpClient对象
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

		// 声明httpGet请求对象
		HttpGet httpGet = new HttpGet(url);
		// 设置请求参数RequestConfig
		httpGet.setConfig(this.getConfig());
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String extName = url.substring(url.lastIndexOf("."));
				// 使用uuid生成图片名
				String imageName = UUID.randomUUID().toString() + extName;
				File dir = new File("./images/");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File targetFile = new File("./images/" + imageName);
				FileOutputStream out = new FileOutputStream(targetFile);
				response.getEntity().writeTo(out);
				return targetFile.getPath();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					// 关闭连接
					response.close();
				}
				// 不能关闭，现在使用的是连接管理器
				// httpClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return "";
	}

}
