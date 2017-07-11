package com.ns.greg.library.easy_encryptor;

/**
 * Created by Gregory on 2017/7/11.
 */

public class ConvertsUtils {

  private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

  public static String bytes2HexString(final byte[] bytes) {
    if (bytes == null) {
      return null;
    }

    int len = bytes.length;
    if (len <= 0) {
      return null;
    }

    char[] ret = new char[len << 1];
    for (int i = 0, j = 0; i < len; i++) {
      ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
      ret[j++] = hexDigits[bytes[i] & 0x0f];
    }

    return new String(ret);
  }

  public static byte[] hexString2Bytes(String hexString) {
    if (isSpace(hexString)) {
      return null;
    }

    int len = hexString.length();
    if (len % 2 != 0) {
      hexString = "0" + hexString;
      len = len + 1;
    }

    char[] hexBytes = hexString.toUpperCase().toCharArray();
    byte[] ret = new byte[len >> 1];
    for (int i = 0; i < len; i += 2) {
      ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
    }

    return ret;
  }

  public static boolean isSpace(final String s) {
    if (s == null) {
      return true;
    }

    for (int i = 0, len = s.length(); i < len; ++i) {
      if (!Character.isWhitespace(s.charAt(i))) {
        return false;
      }
    }

    return true;
  }

  public static int hex2Dec(final char hexChar) {
    if (hexChar >= '0' && hexChar <= '9') {
      return hexChar - '0';
    } else if (hexChar >= 'A' && hexChar <= 'F') {
      return hexChar - 'A' + 10;
    } else {
      throw new IllegalArgumentException();
    }
  }
}
