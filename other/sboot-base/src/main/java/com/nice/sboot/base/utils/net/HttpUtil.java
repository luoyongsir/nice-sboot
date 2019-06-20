package com.nice.sboot.base.utils.net;

import com.nice.sboot.base.comm.Const;
import com.nice.sboot.base.comm.MediaTypes;
import com.nice.sboot.base.utils.collect.MapUtil;
import com.nice.sboot.base.utils.text.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * HttpClient工具类
 *
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class HttpUtil {

	private static final Logger LOG = LoggerFactory.getLogger(HttpUtil.class.getName());
	private static SSLConnectionSocketFactory socketFactory;
	private static PoolingHttpClientConnectionManager connManager;

	static {
		// connManager = new PoolingHttpClientConnectionManager();
		connManager = new PoolingHttpClientConnectionManager(getRegistry());
		// 整个连接池最大连接数
		connManager.setMaxTotal(100);
		// 每路由最大连接数
		connManager.setDefaultMaxPerRoute(10);
	}

	/**
	 * https 的支持
	 *
	 * @return
	 */
	private static Registry<ConnectionSocketFactory> getRegistry() {
		SSLContextBuilder builder = new SSLContextBuilder();
		// 全部信任 不做身份鉴定
		try {
			builder.loadTrustMaterial(null, (chain, authType) -> true);
			socketFactory = new SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
			return RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", socketFactory)
					.build();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
			LOG.error("ssl 连接出错：", e);
		}
		return RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
	}

	/** Create global default request configuration */
	private static RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT)
			.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
			.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).setExpectContinueEnabled(true)
			.setConnectionRequestTimeout(30000).setConnectTimeout(30000).setSocketTimeout(30000).build();

	/**
	 * 超时设置（单位毫秒）默认30秒
	 * @param connectionRequestTimeout 连接请求超时
	 * @param connectTimeout 连接超时时间
	 * @param socketTimeout 传输超时时间
	 */
	public static void initConfig(Integer connectionRequestTimeout, Integer connectTimeout, Integer socketTimeout) {
		RequestConfig.Builder builder = RequestConfig.copy(defaultRequestConfig);
		if (connectionRequestTimeout != null) {
			builder.setConnectionRequestTimeout(connectionRequestTimeout);
		}
		if (connectTimeout != null) {
			builder.setConnectTimeout(connectTimeout);
		}
		if (socketTimeout != null) {
			builder.setSocketTimeout(socketTimeout);
		}
		HttpUtil.defaultRequestConfig = builder.build();
	}

	public static CloseableHttpClient getHttpClient() {
		return HttpClients.custom().setConnectionManager(connManager).setConnectionManagerShared(true)
				.setDefaultRequestConfig(defaultRequestConfig).build();
	}

	/**
	 * http get 请求<br />
	 * 返回值默认编码为GBK，超时时间默认为5秒
	 *
	 * @param url
	 *            服务器地址
	 * @return
	 */
	public static String get(final String url) {
		HttpGet httpGet = new HttpGet(url);
		return execute(httpGet);
	}

	/**
	 * http get 请求
	 *
	 * @param url
	 *            服务器地址
	 * @param charset
	 *            返回值编码格式
	 * @return
	 */
	public static String get(final String url, final Charset charset) {
		HttpGet httpGet = new HttpGet(url);
		return execute(httpGet, charset);
	}

	/**
	 * http get 请求
	 *
	 * @param url
	 *            服务器地址
	 * @param timeout
	 *            请求超时时间
	 * @return
	 */
	public static String get(final String url, final int timeout) {
		RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig).setSocketTimeout(timeout)
				.setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).build();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		return execute(httpGet);
	}

	/**
	 * http post 请求
	 *
	 * @param url
	 *            服务器地址
	 * @param params
	 *            请求参数
	 * @return
	 */
	public static String post(final String url, final Map<String, String> params) {
		return post(url, params, null);
	}

	/**
	 * http post 请求
	 *
	 * @param url
	 *            服务器地址
	 * @param params
	 *            请求参数
	 * @param headers
	 *            请求头
	 * @return
	 */
	public static String post(final String url, final Map<String, String> params, final Map<String, String> headers) {
		HttpPost httpPost = new HttpPost(url);
		if (MapUtil.isNotEmpty(params)) {
			List<NameValuePair> lists = new ArrayList<>();
			for (Map.Entry<String, String> param : params.entrySet()) {
				lists.add(new BasicNameValuePair(param.getKey(), param.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(lists, Charsets.UTF_8));
		}
		if (MapUtil.isNotEmpty(headers)) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				httpPost.addHeader(header.getKey(), header.getValue());
			}
		}
		return execute(httpPost);
	}

	/**
	 * http post 请求
	 *
	 * @param url
	 *            服务器地址
	 * @param s
	 *            字符串: xml / json
	 * @param contentType
	 *            MediaTypes.XML_UTF_8 / MediaTypes.JSON_UTF_8
	 * @return
	 */
	public static String post(final String url, final String s, String contentType) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HttpHeaders.CONTENT_TYPE, contentType);
		httpPost.setEntity(new StringEntity(s, Charsets.UTF_8));
		return execute(httpPost);
	}

	/**
	 * 执行http请求
	 *
	 * @param t
	 * @return
	 */
	public static <T extends HttpRequestBase> String execute(T t) {
		return execute(t, Charsets.UTF_8);
	}

	/**
	 * 执行http请求
	 *
	 * @param t
	 * @param charset
	 * @return
	 */
	public static <T extends HttpRequestBase> String execute(T t, Charset charset) {
		String res = Const.EMPTY;
		// 创建httpClient实例.
		try (CloseableHttpClient client = getHttpClient(); CloseableHttpResponse response = client.execute(t)) {
			int status = response.getStatusLine().getStatusCode();
			if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
				HttpEntity entity = response.getEntity();
				// 如果响应结果是GZIP格式的，则进行解压缩
				if (entity != null && entity.getContentType() != null && MediaTypes.X_GZIP
						.equals(entity.getContentType().getValue())) {
					entity = new GzipDecompressingEntity(entity);
				}
				res = EntityUtils.toString(entity, charset);
				// 销毁
				EntityUtils.consume(entity);
			} else {
				LOG.info("HttpClient请求返回状态：" + status);
			}
		} catch (IOException e) {
			LOG.error("HttpClient请求失败：", e);
		}
		return res;
	}

	/**
	 * 文件下载
	 *
	 * @param url 远程文件地址
	 * @param localFile 本地输出文件
	 * @return
	 */
	public static void download(final String url, File localFile) {
		HttpGet httpGet = new HttpGet(url);
		// 创建httpClient实例.
		try (CloseableHttpClient client = getHttpClient();
				CloseableHttpResponse response = client.execute(httpGet);
				InputStream is = response.getEntity().getContent();
				FileOutputStream fos = new FileOutputStream(localFile)) {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				byte[] bytes = new byte[4096];
				int len;
				while ((len = is.read(bytes)) != -1) {
					fos.write(bytes, 0, len);
				}
			}
		} catch (IOException e) {
			LOG.error("HttpClient download 请求失败：", e);
		}
	}

	private HttpUtil() {
	}

}
