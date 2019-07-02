package com.xhkj.server.energy.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 
 * @ClassName: HttpClientUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Fuwei
 * @date 2016年1月7日 上午10:54:25
 *
 */
public class HttpClientUtil {
	private static PoolingHttpClientConnectionManager connMgr;
	private static final int SIZ_POOLING = 100;
	private static final String DEFAULT_CODE = "UTF-8";
	private static final int MAX_TIMEOUT = 30000; //30秒
	private static RequestConfig requestConfig;

	private static void init() {
		if (connMgr == null) {
			// 设置连接池
			connMgr = new PoolingHttpClientConnectionManager();
			// 设置连接池大小
			connMgr.setMaxTotal(SIZ_POOLING);
			connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
			RequestConfig.Builder configBuilder = RequestConfig.custom();
			// 设置连接超时
			configBuilder.setConnectTimeout(MAX_TIMEOUT);
			// 设置读取超时
			configBuilder.setSocketTimeout(MAX_TIMEOUT);
			// 设置从连接池获取连接实例的超时
			configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
			requestConfig = configBuilder.build();
		}
	}

	/**
	 * 通过连接池获取HttpClient
	 * 
	 * @return
	 */
	private static CloseableHttpClient getHttpClient() {
		init();
		return HttpClients.custom().setConnectionManager(connMgr).build();
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String httpGetRequest(String url, String code) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
		return getResult(httpGet, code);
	}

	public static String httpGetRequest(String url, Map<String, String> params, String code) throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);
		HttpGet httpGet = new HttpGet(ub.build());
		return getResult(httpGet, code);
	}

	public static String httpGetRequest(String url, Map<String, String> headers, Map<String, String> params, String code) throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = new HttpGet(ub.build());
		for (Map.Entry<String, String> param : headers.entrySet()) {
			httpGet.addHeader(param.getKey(), param.getValue());
		}
		return getResult(httpGet, code);
	}

	public static String httpPostRequest(String url, String code) {
		HttpPost httpPost = new HttpPost(url);
		return getResult(httpPost, code);
	}

	public static String httpPostRequest(String url, Map<String, String> params, String code) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, DEFAULT_CODE));
		return getResult(httpPost, code);
	}

	public static String httpPostRequest(String url, Map<String, String> headers, Map<String, String> params, String code) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);

		for (Map.Entry<String, String> param : headers.entrySet()) {
			httpPost.addHeader(param.getKey(), param.getValue());
		}

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, StringUtils.isEmpty(code) ? DEFAULT_CODE : code));

		return getResult(httpPost, code);
	}

	private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, String> params) {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> param : params.entrySet()) {
			pairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
		}
		return pairs;
	}

	/**
	 * 处理Http请求
	 * 
	 * @param request
	 * @return
	 */
	private static String getResult(HttpRequestBase request, String code) {
		CloseableHttpClient httpClient = getHttpClient();
		return execute(request, code, httpClient);
	}


	/**
	 * 执行发送
	 * 
	 * @Title: execute
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param request
	 * @param @param code
	 * @param @param httpClient
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	private static String execute(HttpRequestBase request, String code, CloseableHttpClient httpClient) {
		try {
			request.setConfig(requestConfig);
			CloseableHttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// long len = entity.getContentLength();// -1 表示长度未知
				String result = EntityUtils.toString(entity, StringUtils.isEmpty(code) ? DEFAULT_CODE : code);
				response.close();
				// httpClient.close();
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}
	public static void main(String[] args) {
		
	}
}
