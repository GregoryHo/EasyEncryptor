package com.ns.greg.library.easy_encryptor.hmac;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import com.ns.greg.library.easy_encryptor.ConvertsUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Gregory
 * @since 2017/7/11
 */

public class HmacEncryptor {

  public static final String HMAC_MD5 = "HmacMD5";
  public static final String HMAC_SHA1 = "HmacSHA1";
  public static final String HMAC_SHA224 = "HmacSHA224";
  public static final String HMAC_SHA256 = "HmacSHA256";
  public static final String HMAC_SHA384 = "HmacSHA384";
  public static final String HMAC_SHA512 = "HmacSHA512";
  @StringDef({HMAC_MD5, HMAC_SHA1, HMAC_SHA224, HMAC_SHA256, HMAC_SHA384, HMAC_SHA512})
  @Retention(RetentionPolicy.SOURCE)
  public @interface HmacAlgorithm {

  }

  private String algorithm;

  public HmacEncryptor(@NonNull @HmacAlgorithm String algorithm) {
    this.algorithm = algorithm;
  }

  public byte[] encrypt(@NonNull byte[] data, @NonNull byte[] key) {
    return hmacModule(data, key, algorithm);
  }

  public String encrypt2HexString(@NonNull byte[] data, @NonNull byte[] key) {
    return ConvertsUtils.bytes2HexString(encrypt(data, key));
  }

  private byte[] hmacModule(@NonNull final byte[] data, @NonNull final byte[] key,
      final String algorithm) {
    if (data.length == 0 || key.length == 0) {
      return null;
    }

    try {
      SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
      Mac mac = Mac.getInstance(algorithm);
      mac.init(secretKey);
      return mac.doFinal(data);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }
}
