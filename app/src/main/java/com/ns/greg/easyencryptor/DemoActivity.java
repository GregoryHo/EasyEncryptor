package com.ns.greg.easyencryptor;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.ns.greg.library.easy_encryptor.ConvertsUtils;
import com.ns.greg.library.easy_encryptor.base64.Base64Encoder;
import com.ns.greg.library.easy_encryptor.des.DESEncryptor;
import com.ns.greg.library.easy_encryptor.hmac.HmacEncryptor;

/**
 * Created by Gregory on 2017/7/11.
 */

public class DemoActivity extends AppCompatActivity {

  private static final String TAG = "DemoActivity";

  @Override protected void onResume() {
    super.onResume();

    DESEncryptor desEncryptor = new DESEncryptor.Builder().setAlgorithm(DESEncryptor.AES)
        .setCipher(DESEncryptor.CBC)
        .setPadding(DESEncryptor.PKCS5_PADDING)
        .build();

    String content = "12345678";
    String iv = "abcdef0123456789";
    String key = "0123456789abcdef";

    byte[] aesEncrypt = desEncryptor.encrypt(content.getBytes(), key.getBytes(), iv.getBytes());
    byte[] encode64 = Base64Encoder.encode(aesEncrypt);
    byte[] decode64 = Base64Encoder.decode(encode64);
    byte[] aesDecrypt = desEncryptor.decrypt(decode64, key.getBytes(), iv.getBytes());

    Log.i(TAG,
        "content : "
            + content
            + "\n"
            + "encrypt2HexString(AES) : "
            + ConvertsUtils.bytes2HexString(aesEncrypt)
            + "\n"
            + "encode(BASE64) : "
            + new String(encode64)
            + "\n"
            + "decode(BASE64) : "
            + ConvertsUtils.bytes2HexString(decode64)
            + "\n"
            + "decrypt2HexString(AES) : "
            + new String(aesDecrypt));

    HmacEncryptor hmacEncryptor = new HmacEncryptor(HmacEncryptor.HMAC_MD5);
    String md5 = hmacEncryptor.encrypt2HexString(content.getBytes(), key.getBytes());
    Log.i(TAG, "encrypt2HexString(MD5) : " + md5);
  }
}
