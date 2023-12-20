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

/**
 * @author kareltan
 */
public class VerifyDocument {

  public boolean verifySignature(String data, byte[] signature, PublicKey publicKey)
      throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
    Signature verifier = Signature.getInstance(SHA256_ECDSA, BC);
    verifier.initVerify(publicKey);
    verifier.update(data.getBytes(StandardCharsets.UTF_8));
    return verifier.verify(signature);
  }

}
