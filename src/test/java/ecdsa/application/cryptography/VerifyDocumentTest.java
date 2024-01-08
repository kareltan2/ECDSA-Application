package ecdsa.application.cryptography;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kareltan
 */
public class VerifyDocumentTest extends AbstractTestingVariable {

  private final VerifyDocument verifyDocument = new VerifyDocument();

  private final SignDocument signDocument = new SignDocument();

  private final GenerateKeyPair generateKeyPair = new GenerateKeyPair();

  @Test
  void testSignDocument_shouldSuccess() throws Exception{
    KeyPair keyPair = generateKeyPair.generateKeyPair();
    PrivateKey privateKey = keyPair.getPrivate();
    PublicKey publicKey = keyPair.getPublic();
    byte[] signature = signDocument.signData(data, privateKey);

    boolean isVerified = verifyDocument.verifySignature(data, signature, publicKey);

    Assertions.assertTrue(isVerified);
    Assertions.assertNotNull(signature);
    Assertions.assertNotNull(keyPair);
  }

}
