package com.nice.sboot.base.utils.security;

import com.nice.sboot.base.exception.RunException;
import com.nice.sboot.base.utils.text.Charsets;
import com.nice.sboot.base.utils.RandomUtil;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

/**
 * AES加密/解密的工具类
 * 支持Hex与Base64两种编码方式
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class AesUtil {

	private static final String AES = "AES";
	private static final String AES_CBC = "AES/CBC/PKCS5Padding";

	private static final int DEFAULT_AES_KEY_SIZE = 128;
	private static final int DEFAULT_IV_SIZE = 16;

	private static SecureRandom random = RandomUtil.secureRandom();

	/**
	 * 使用AES加密原始字符串.
	 *
	 * @param input 原始输入字符数组
	 * @param key 符合AES要求的密钥
	 */
	public static byte[] encrypt(byte[] input, byte[] key) {
		return aes(input, key, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 使用AES加密原始字符串.
	 *
	 * @param input 原始输入字符数组
	 * @param key 符合AES要求的密钥
	 * @param iv 初始向量
	 */
	public static byte[] encrypt(byte[] input, byte[] key, byte[] iv) {
		return aes(input, key, iv, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 使用AES解密字符串, 返回原始字符串.
	 *
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 */
	public static String decrypt(byte[] input, byte[] key) {
		byte[] decryptResult = aes(input, key, Cipher.DECRYPT_MODE);
		return new String(decryptResult, Charsets.UTF_8);
	}

	/**
	 * 使用AES解密字符串, 返回原始字符串.
	 *
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 * @param iv 初始向量
	 */
	public static String decrypt(byte[] input, byte[] key, byte[] iv) {
		byte[] decryptResult = aes(input, key, iv, Cipher.DECRYPT_MODE);
		return new String(decryptResult, Charsets.UTF_8);
	}

	/**
	 * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 *
	 * @param input 原始字节数组
	 * @param key 符合AES要求的密钥
	 * @param mode Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] aes(byte[] input, byte[] key, int mode) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(mode, secretKey);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw new RunException(e);
		}
	}

	/**
	 * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 *
	 * @param input 原始字节数组
	 * @param key 符合AES要求的密钥
	 * @param iv 初始向量
	 * @param mode Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(AES_CBC);
			cipher.init(mode, secretKey, ivSpec);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw new RunException(e);
		}
	}

	/**
	 * 生成AES密钥,返回字节数组, 默认长度为128位(16字节).
	 */
	public static byte[] generateKey() {
		return generateKey(DEFAULT_AES_KEY_SIZE);
	}

	/**
	 * 生成AES密钥,可选长度为128,192,256位.
	 */
	public static byte[] generateKey(int keySize) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
			keyGenerator.init(keySize);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw new RunException(e);
		}
	}

	/**
	 * 生成随机向量,默认大小为cipher.getBlockSize(), 16字节.
	 */
	public static byte[] generateIV() {
		byte[] bytes = new byte[DEFAULT_IV_SIZE];
		random.nextBytes(bytes);
		return bytes;
	}

	private AesUtil() {
	}
}
