package com.ns.greg.library.easy_encryptor.base64;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Base64;
import com.ns.greg.library.easy_encryptor.ConvertsUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Gregory on 2017/7/11.
 */

public class Base64Encryptor {

  /**
   * Default values for encoder/decoder flags.
   */
  public static final int DEFAULT = 0;

  /**
   * Encoder flag bit to omit the padding '=' characters at the end
   * of the output (if any).
   */
  public static final int NO_PADDING = 1;

  /**
   * Encoder flag bit to omit all line terminators (i.e., the output
   * will be on one long line).
   */
  public static final int NO_WRAP = 2;

  /**
   * Encoder flag bit to indicate lines should be terminated with a
   * CRLF pair instead of just an LF.  Has no effect if {@code
   * NO_WRAP} is specified as well.
   */
  public static final int CRLF = 4;

  /**
   * Encoder/decoder flag bit to indicate using the "URL and
   * filename safe" variant of Base64 (see RFC 3548 section 4) where
   * {@code -} and {@code _} are used in place of {@code +} and
   * {@code /}.
   */
  public static final int URL_SAFE = 8;

  /**
   * Flag to pass to Base64OutputStream to indicate that it
   * should not close the output stream it is wrapping when it
   * itself is closed.
   */
  public static final int NO_CLOSE = 16;

  @IntDef({ DEFAULT, NO_PADDING, NO_WRAP, CRLF, URL_SAFE, NO_CLOSE })
  @Retention(RetentionPolicy.SOURCE) public @interface Base64Flag {

  }

  /*--------------------------------
   * ENCODE
   *-------------------------------*/

  public static String encode2String(@NonNull byte[] data) {
    return new String(encode(data));
  }

  public static String encode2String(@NonNull byte[] data, @Base64Flag final int flag) {
    return new String(encode(data, flag));
  }

  public static byte[] encode(@NonNull byte[] data) {
    return Base64.encode(data, Base64.DEFAULT);
  }

  public static byte[] encode(@NonNull byte[] data, @Base64Flag final int flag) {
    return Base64.encode(data, flag);
  }

  /*--------------------------------
   * DECODE
   *-------------------------------*/

  public static String decode2HexString(@NonNull byte[] data) {
    return ConvertsUtils.bytes2HexString(decode(data));
  }

  public static String decode2HexString(@NonNull byte[] data, @Base64Flag final int flag) {
    return ConvertsUtils.bytes2HexString(decode(data, flag));
  }

  public static byte[] decode(@NonNull byte[] data) {
    return Base64.decode(data, Base64.DEFAULT);
  }

  public static byte[] decode(@NonNull byte[] data, @Base64Flag final int flag) {
    return Base64.decode(data, flag);
  }
}
