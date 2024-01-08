package ecdsa.application.cryptography;

import static ecdsa.application.constant.CommonConstant.BC;
import static ecdsa.application.constant.CommonConstant.SHA256_ECDSA;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kareltan
 */
@Slf4j
public class VerifyDocument extends ECDSACryptographyAbstract{

  public boolean verifySignature(String data, byte[] signature, PublicKey publicKey)
      throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
    addProvider();
    log.info("Starting verify signature: {}, publicKey: {}", signature, publicKey);

    long startTime = System.nanoTime();
    Signature verifier = Signature.getInstance(SHA256_ECDSA, BC);
    verifier.initVerify(publicKey);
    verifier.update(data.getBytes(StandardCharsets.UTF_8));

    boolean isVerified = verifier.verify(signature);

    long endTime = System.nanoTime();
    double elapsedTimeInSeconds = (endTime - startTime) / 1e9;
    log.info("Completed verify signature with result: {}, with elapsedTime: {}s", isVerified, elapsedTimeInSeconds);

    return isVerified;
  }

}
