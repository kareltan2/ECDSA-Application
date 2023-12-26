package ecdsa.application.cryptography;

import static ecdsa.application.constant.CommonConstant.BC;
import static ecdsa.application.constant.CommonConstant.SHA256_ECDSA;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kareltan
 */
@Slf4j
public class SignDocument extends ECDSACryptographyAbstract{

  public byte[] signData(String data, PrivateKey privateKey)
      throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException {
    addProvider();

    Signature signature = Signature.getInstance(SHA256_ECDSA, BC);
    signature.initSign(privateKey);
    signature.update(data.getBytes(StandardCharsets.UTF_8));
    return signature.sign();
  }

}
