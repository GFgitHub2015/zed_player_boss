package com.zed.util;

import java.security.MessageDigest;

public class MD5 {

	private static MD5 md5 = null;

	/**
	 * 初始化
	 * 
	 * @return
	 * @throws Exception
	 */
	public static MD5 getInstance() throws Exception {
		if (md5 == null)
			md5 = new MD5();
		return md5;
	}

	/**
	 * 进行MD5数字摘要
	 * 
	 * @param buf
	 *            byte[] 要进行MD5加密的内容
	 * @return byte[]
	 */
	public static byte[] digest(byte[] buf) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(buf);
			return md.digest();
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	/**
	 * 取得16进制的MD5值
	 * 
	 * @param text
	 *            String
	 * @return String
	 */
	public static String getHexMD5(String text) {
		return getHexMD5(text.getBytes());
	}

	/**
	 * 取得16进制的MD5值
	 * 
	 * @param buf
	 *            byte[] 要进行MD5加密的内容
	 * @return String
	 */
	public static String getHexMD5(byte[] buf) {
		return Hex.encode(digest(buf));
	}

}
