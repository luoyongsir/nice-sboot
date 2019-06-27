package com.nice.sboot.base.utils.text;

import com.nice.sboot.base.exception.RunException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 字符串zip压缩和解压工具类
 *
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class ZipUtil {

	/**
	 * 压缩Gzip
	 *
	 * @return
	 */
	public static String gzip(final String input) {
		return EncodeUtil.encodeBase64(gzipBytes(input));
	}

	/**
	 * 压缩Gzip
	 *
	 * @return
	 */
	public static byte[] gzipBytes(final String input) {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); GZIPOutputStream gzip = new GZIPOutputStream(bos)) {
			gzip.write(input.getBytes(Charsets.UTF_8));
			gzip.finish();
			return bos.toByteArray();
		} catch (Exception e) {
			throw new RunException("ZIP 压缩出错：", e);
		}
	}

	/**
	 * 解压Gzip
	 * 
	 * @return
	 */
	public static String unGzip(final String input) {
		return unGzipBytes(EncodeUtil.decodeBase64(input));
	}

	/**
	 * 解压Gzip
	 *
	 * @return
	 */
	public static String unGzipBytes(final byte[] inputBytes) {
		try (ByteArrayInputStream bis = new ByteArrayInputStream(inputBytes);
				GZIPInputStream gzip = new GZIPInputStream(bis);
				ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			byte[] buf = new byte[1024];
			int num = -1;
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				bos.write(buf, 0, num);
			}
			byte[] bytes = bos.toByteArray();
			return new String(bytes, Charsets.UTF_8);
		} catch (Exception e) {
			throw new RunException("ZIP 解压出错：", e);
		}
	}

	private ZipUtil() {
	}
}
