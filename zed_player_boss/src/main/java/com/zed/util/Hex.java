package com.zed.util;

public class Hex {

  /**
   * 转化成十六进制
   * @param buffer byte[]
   * @return String
   */
  public static String encode(byte[] buffer) {
    String dump = "";
    try {
      int dataLen = buffer.length;
      for (int i = 0; i < dataLen; i++) {
        dump += Character.forDigit( (buffer[i] >> 4) & 0x0f, 16);
        dump += Character.forDigit(buffer[i] & 0x0f, 16);
      }
    }
    catch (Throwable t) {
    }
    return dump.toLowerCase();
  }



  /**
   * 将十六进制的转换成可见的字符串
   * @param hexStr String
   * @return String
   */
  public static byte[] decode(String hexStr) {
    byte[] bytes = new byte[hexStr.length() / 2];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) (0xff & Integer.parseInt(hexStr.substring(
          i * 2, i * 2 + 2), 16));
    }
    return bytes;
  }


}
