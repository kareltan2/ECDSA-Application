package ecdsa.application.cryptography;

import java.security.KeyPair;
import java.security.PrivateKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kareltan
 */
public class SignDocumentTest extends AbstractTestingVariable {

  private final SignDocument signDocument = new SignDocument();

  private final GenerateKeyPair generateKeyPair = new GenerateKeyPair();

  @Test
  void testSignDocument_shouldSuccess() throws Exception{
    KeyPair keyPair = generateKeyPair.generateKeyPair();
    PrivateKey privateKey = keyPair.getPrivate();

    byte[] signature = signDocument.signData(data, privateKey);

    Assertions.assertNotNull(signature);
    Assertions.assertNotNull(keyPair);
  }

}
