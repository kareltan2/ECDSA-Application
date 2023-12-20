package ecdsa.application.cryptography;

import static ecdsa.application.constant.CommonConstant.BC;
import static ecdsa.application.constant.CommonConstant.SHA256_ECDSA;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;

/**
 * @author kareltan
 */
public class SignDocument {

  private static byte[] signData(String data, PrivateKey privateKey) throws Exception {
    Signature signature = Signature.getInstance(SHA256_ECDSA, BC);
    signature.initSign(privateKey);
    signature.update(data.getBytes(StandardCharsets.UTF_8));
    return signature.sign();
  }

}
