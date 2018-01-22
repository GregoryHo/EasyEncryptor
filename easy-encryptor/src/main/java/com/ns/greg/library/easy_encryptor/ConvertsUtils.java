package com.ns.greg.library.easy_encryptor;

/**
 * @author Gregory
 * @since 2017/7/11
 */

public class ConvertsUtils {

  private static final char[] HEX_DIGITS =
      { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

  public static String bytes2HexString(byte[] src) {
    if (src == null || src.length == 0) {
      return null;
    }

    return bytes2HexString(src, 0, src.length);
  }

  public static String bytes2HexString(byte[] src, int offset, int length) {
    if (src == null) {
      return null;
    }

    int srcLength = src.length;
    int copyLength = length + offset;
    if (srcLength < copyLength) {
      return null;
    }

    char[] hexChars = new char[copyLength << 1];
    for (int i = offset; i < copyLength; i++) {
      int value = src[i] & 0xFF;
      int index = i * 2;
      hexChars[index] = HEX_DIGITS[value >>> 4];
      hexChars[index + 1] = HEX_DIGITS[value & 0x0F];
    }

    return new String(hexChars);
  }

  /**
   * Converts 1 byte value to HEX.
   *
   * @param aByte byte data
   * @return HEX value as string
   */
  public static String byte2Hex(byte aByte) {
    char[] hexChars = new char[2];
    int value = aByte & 0xFF;
    int index = 0;
    hexChars[index] = HEX_DIGITS[value >>> 4];
    hexChars[index + 1] = HEX_DIGITS[value & 0x0F];
    return new String(hexChars);
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
