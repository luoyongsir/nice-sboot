package com.nice.sboot.base.utils.security;

import com.nice.sboot.base.exception.RunException;
import com.nice.sboot.base.utils.text.Charsets;
import com.nice.sboot.base.utils.text.EncodeUtil;
import com.nice.sboot.base.utils.RandomUtil;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密/解密的工具类
 * 支持Hex与Base64两种编码方式
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class RsaUtil {

	private static final String RSA = "RSA";
	private static final String RSA_ECB = "RSA/ECB/PKCS1Padding";
	private static final int DEFAULT_RSA_KEY_SIZE = 2048;

	private static SecureRandom random = RandomUtil.secureRandom();

	/**
	 * 使用RSA加密无编码的原始字节数组, 返回无编码的字节数组结果.
	 *
	 * @param input 原始输入字符数组
	 * @param publicKey 符合RSA要求的公钥
	 */
	public static byte[] encrypt(byte[] input, byte[] publicKey) {
		try {
			PublicKey pubKey = KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(publicKey));
			//RSA加密
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw new RunException(e);
		}
	}

	/**
	 * 使用RSA解密无编码的原始字节数组, 返回原始字符串
	 *
	 * @param input 原始字节数组
	 * @param privateKey 符合RSA要求的私钥
	 */
	public static String decrypt(byte[] input, byte[] privateKey) {
		try {
			PrivateKey priKey = KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(privateKey));
			//RSA解密
			Cipher cipher = Cipher.getInstance(RSA_ECB);
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			byte[] decryptResult = cipher.doFinal(input);
			return new String(decryptResult, Charsets.UTF_8);
		} catch (GeneralSecurityException e) {
			throw new RunException(e);
		}
	}

	/**
	 * 默认密钥长度是2048
	 * @return 返回数组第一个为公钥，第二个为私钥
	 */
	public static String[] generateKey() {
		return generateKey(DEFAULT_RSA_KEY_SIZE);
	}

	/**
	 * 密钥长度必须是64的倍数，在512到65536位之间
	 * @param keySize 密钥长度
	 * @return 返回数组第一个为公钥，第二个为私钥
	 */
	public static String[] generateKey(int keySize) {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);
			generator.initialize(keySize, random);
			KeyPair keyPair = generator.generateKeyPair();
			String publicKey = EncodeUtil.encodeBase64(keyPair.getPublic().getEncoded());
			String privateKey = EncodeUtil.encodeBase64(keyPair.getPrivate().getEncoded());
			return new String[]{publicKey, privateKey};
		} catch (GeneralSecurityException e) {
			throw new RunException(e);
		}
	}

	private RsaUtil() {
	}
}
