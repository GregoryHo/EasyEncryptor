package com.ns.greg.library.easy_encryptor.des;

/**
 * Created by Gregory on 2017/7/11.
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import com.ns.greg.library.easy_encryptor.ConvertsUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Work with DES, 3DES, AES
 */
public class DESEncryptor {

  /*--------------------------------
   * ALGORITHM
   *-------------------------------*/
  public static final String DES = "DES";
  public static final String TRIPLE_DES = "DESede";
  public static final String AES = "AES";
  @StringDef({ DES, TRIPLE_DES, AES }) @Retention(RetentionPolicy.SOURCE)
  public @interface DESAlgorithm {

  }

  /*--------------------------------
   * CIPHER
   *-------------------------------*/
  public static final String ECB = "ECB";
  public static final String CBC = "CBC";
  public static final String CFB = "CFB";
  public static final String OFB = "OFB";
  @StringDef({ ECB, CBC, CFB, OFB }) @Retention(RetentionPolicy.SOURCE) public @interface CipherMode {

  }

  /*--------------------------------
   * PADDING
   *-------------------------------*/
  public static final String NO_PADDING = "NoPadding";
  public static final String ZEROS_PADDING = "ZerosPadding";
  public static final String PKCS5_PADDING = "PKCS5Padding";
  @StringDef({ NO_PADDING, ZEROS_PADDING, PKCS5_PADDING }) @Retention(RetentionPolicy.SOURCE)
  public @interface PaddingType {

  }

  private String algorithm;
  private String cipher;
  private String padding;

  private DESEncryptor(String algorithm, String cipher, String padding) {
    this.algorithm = algorithm;
    this.cipher = cipher;
    this.padding = padding;
  }

  /*--------------------------------
   * ENCRYPT
   *-------------------------------*/

  /**
   * Encrypts data
   *
   * @param data content that need to be encrypted
   * @param key encrypted key (16/24/32 bits)
   * @return encrypted data
   */
  public byte[] encrypt(@NonNull byte[] data, @NonNull byte[] key) {
    return desModule(data, key, null, true);
  }

  /**
   * Encrypts data
   *
   * @param data content that need to be encrypted
   * @param key encrypted key (16/24/32 bits)
   * @param iv encrypted iv (16 bits)
   * @return
   */
  public byte[] encrypt(@NonNull byte[] data, @NonNull byte[] key, @Nullable byte[] iv) {
    return desModule(data, key, iv, true);
  }

  /**
   *  Encrypts data
   *
   * @param data content that need to be encrypted
   * @param key encrypted key (16/24/32 bits)
   * @param iv encrypted iv (16 bits)
   * @return encrypted  string
   */
  public String encrypt2HexString(@NonNull byte[] data, @NonNull byte[] key, @Nullable byte[] iv) {
    return ConvertsUtils.bytes2HexString(encrypt(data, key, iv));
  }

  /**
   * Encrypts data
   *
   * @param data content that need to be encrypted
   * @param key encrypted key (16/24/32 bytes)
   * @return encrypted  string
   */
  public String encrypt2HexString(@NonNull byte[] data, @NonNull byte[] key) {
    return encrypt2HexString(data, key, null);
  }

  /*--------------------------------
   * DECRYPT
   *-------------------------------*/

  /**
   * Decrypts data
   *
   * @param data content that need to be decrypted
   * @param key decrypted key (16/24/32 bits)
   * @return decrypted data
   */
  public byte[] decrypt(@NonNull byte[] data, @NonNull byte[] key) {
    return decrypt(data, key, null);
  }

  /**
   * Decrypts data
   *
   * @param data content that need to be decrypted
   * @param key decrypted key (16/24/32 bits)
   * @param iv decrypted iv (16 bits)
   * @return decrypted data
   */
  public byte[] decrypt(@NonNull byte[] data, @NonNull byte[] key, @Nullable byte[] iv) {
    return desModule(data, key, iv, false);
  }

  /**
   * Decrypts data
   *
   * @param data content that need to be decrypted
   * @param key decrypted key (16/24/32 bits)
   * @return decrypted string
   */
  public String decrypt2HexString(@NonNull byte[] data, @NonNull byte[] key) {
    return decrypt2HexString(data, key, null);
  }

  /**
   * Decrypts data
   *
   * @param data content that need to be decrypted
   * @param key decrypted key (16/24/32 bits)
   * @param iv decrypted iv (16 bits)
   * @return decrypted string
   */
  public String decrypt2HexString(@NonNull byte[] data, @NonNull byte[] key, @NonNull byte[] iv) {
    return ConvertsUtils.bytes2HexString(decrypt(data, key, iv));
  }

  /**
   * Data Encryption Standard Module
   *
   * @param data data that need to encrypt2HexString/decrypt
   * @param key encrypt2HexString/decrypt key
   * @param iv encrypt2HexString/decrypt iv
   * @param isEncrypt encrypt2HexString mode or decrypt mode
   * @return encrypted/decrypted data
   */
  private byte[] desModule(final byte[] data, final byte[] key, final byte[] iv,
      final boolean isEncrypt) {
    if (data.length == 0 || key.length == 0) {
      return null;
    }

    try {
      SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
      Cipher cipher = Cipher.getInstance(algorithm + "/" + this.cipher + "/" + padding);
      if (iv != null) {
        AlgorithmParameterSpec parameterSpec = new IvParameterSpec(iv);
        cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, parameterSpec);
      } else {
        SecureRandom random = new SecureRandom();
        cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, random);
      }

      return cipher.doFinal(data);
    } catch (Throwable e) {
      e.printStackTrace();
      return null;
    }
  }

  public static final class Builder {

    private String algorithm;
    private String cipher;
    private String padding;

    public Builder setAlgorithm(@NonNull @DESAlgorithm String algorithm) {
      this.algorithm = algorithm;

      return this;
    }

    public Builder setCipher(@NonNull @CipherMode String cipher) {
      this.cipher = cipher;

      return this;
    }

    public Builder setPadding(@NonNull @PaddingType String padding) {
      this.padding = padding;

      return this;
    }

    public DESEncryptor build() {
      if (algorithm == null) {
        throw new AssertionError("Algorithm can't be null");
      }

      if (cipher == null) {
        throw new AssertionError("Cipher can't be null");
      }

      if (padding == null) {
        throw new AssertionError("Padding can't be null");
      }

      return new DESEncryptor(algorithm, cipher, padding);
    }
  }
}
