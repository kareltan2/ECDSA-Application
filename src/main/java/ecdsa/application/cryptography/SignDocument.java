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
    log.info("Starting generate signature with privateKey: {}", privateKey);

    long startTime = System.nanoTime();
    Signature signature = Signature.getInstance(SHA256_ECDSA, BC);
    signature.initSign(privateKey);
    signature.update(data.getBytes(StandardCharsets.UTF_8));

    byte[] signatureBytes = signature.sign();
    long endTime = System.nanoTime();
    double elapsedTimeInSeconds = (endTime - startTime) / 1e9;
    log.info("Successfully generate signature with value: {}, with elapsedTime: {}s", signatureBytes, elapsedTimeInSeconds);

    return signatureBytes;
  }

}
