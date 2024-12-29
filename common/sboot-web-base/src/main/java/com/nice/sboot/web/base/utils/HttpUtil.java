package com.nice.sboot.web.base.utils;

import com.nice.sboot.base.comm.Const;
import com.nice.sboot.base.comm.MediaTypes;
import com.nice.sboot.base.utils.collect.MapUtil;
import com.nice.sboot.base.utils.text.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.GzipDecompressingEntity;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.entity.mime.StringBody;
import org.apache.hc.client5.http.impl.classic.AbstractHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.util.Timeout;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient5 工具类
 * @author 罗勇
 * @since 2024-12-29
 */
@Slf4j
public final class HttpUtil {

	/** Create global default request configuration */
	private static RequestConfig defaultRequestConfig = RequestConfig.custom().setExpectContinueEnabled(true)
			.setConnectionRequestTimeout(Timeout.ofSeconds(60)).setResponseTimeout(Timeout.ofSeconds(60)).build();
	private static PoolingHttpClientConnectionManager connManager;

	static {
		final DefaultClientTlsStrategy tlsStrategy = new DefaultClientTlsStrategy(getSslContext());
		connManager = PoolingHttpClientConnectionManagerBuilder.create().setMaxConnTotal(200).setMaxConnPerRoute(20)
				.setTlsSocketStrategy(tlsStrategy).build();
	}

	private HttpUtil() {
	}

	/**
	 * https 的支持
	 *
	 * @return
	 */
	private static SSLContext getSslContext() {
		// 全部信任 不做身份鉴定
		try {
			return SSLContextBuilder.create().loadTrustMaterial(new TrustAllStrategy()).build();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
			log.error("ssl 连接出错：", e);
			throw new RuntimeException(e);
		}
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
	public static String post(final String url, final String s, final String contentType) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HttpHeaders.CONTENT_TYPE, contentType);
		httpPost.setEntity(new StringEntity(s, Charsets.UTF_8));
		return execute(httpPost);
	}

	/**
	 * 上传文件
	 *
	 * @param url
	 *            服务器地址
	 * @param localPath
	 *            本地文件路径
	 * @return
	 */
	public static String postFile(final String url, final String localPath) {
		HttpPost httpPost = new HttpPost(url);
		FileBody file = new FileBody(new File(localPath));

		HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", file).build();
		httpPost.setEntity(reqEntity);
		return execute(httpPost);
	}

	/**
	 * 上传文件
	 *
	 * @param url
	 *            服务器地址
	 * @param localPath
	 *            本地文件路径
	 * @param username
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return
	 */
	public static String postFile(final String url, final String localPath, final String username, final String pwd) {
		HttpPost httpPost = new HttpPost(url);
		FileBody file = new FileBody(new File(localPath));

		ContentType textContentType = ContentType.create(MediaTypes.TEXT_PLAIN, Charsets.UTF_8);
		StringBody nameBody = new StringBody(username, textContentType);
		StringBody pwdBody = new StringBody(pwd, textContentType);

		HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", file).addPart("username", nameBody)
				.addPart("password", pwdBody).build();
		httpPost.setEntity(reqEntity);
		return execute(httpPost);
	}

	/**
	 * 执行http请求
	 *
	 * @param t
	 * @return
	 */
	public static <T extends ClassicHttpRequest> String execute(T t) {
		String res = Const.EMPTY;
		try (CloseableHttpClient client = getHttpClient()) {
			res = client.execute(t, new HttpClientResponseHandler());
		} catch (IOException e) {
			log.error("HttpClient请求失败：", e);
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
		try (CloseableHttpClient client = getHttpClient()) {
			client.execute(httpGet, new HttpClientDownloadHandler(localFile));
		} catch (Exception e) {
			log.error("HttpClient download 请求失败：", e);
		}
	}
}

class HttpClientResponseHandler extends AbstractHttpClientResponseHandler<String> {

	/**
	 * Returns the entity as a body as a String.
	 */
	@Override
	public String handleEntity(final HttpEntity entity) throws IOException {
		try {
			// 如果响应结果是GZIP格式的，则进行解压缩
			if (entity != null && entity.getContentType() != null && MediaTypes.X_GZIP.equals(
					entity.getContentType())) {
				HttpEntity entityNew = new GzipDecompressingEntity(entity);
				return EntityUtils.toString(entityNew);
			} else {
				return EntityUtils.toString(entity);
			}
		} catch (final ParseException ex) {
			throw new ClientProtocolException(ex);
		}
	}

}

class HttpClientDownloadHandler extends AbstractHttpClientResponseHandler<Void> {

	private File localFile;

	public HttpClientDownloadHandler(File localFile) {
		this.localFile = localFile;
	}

	@Override
	public Void handleEntity(final HttpEntity entity) throws IOException {
		try (InputStream is = entity.getContent(); FileOutputStream fos = new FileOutputStream(localFile)) {
			byte[] bytes = new byte[4096];
			int len;
			while ((len = is.read(bytes)) != -1) {
				fos.write(bytes, 0, len);
			}
		} catch (IOException ex) {
			throw new ClientProtocolException(ex);
		}
		return null;
	}

}
