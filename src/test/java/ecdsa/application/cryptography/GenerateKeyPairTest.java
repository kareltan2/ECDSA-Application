package ecdsa.application.cryptography;

import ecdsa.application.cryptography.GenerateKeyPair;
import java.security.KeyPair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kareltan
 */
public class GenerateKeyPairTest {

  private final GenerateKeyPair generateKeyPair = new GenerateKeyPair();

  @Test
  void testGenerateKeyPair_shouldSuccess() throws Exception{
    KeyPair keyPair = generateKeyPair.generateKeyPair();
    Assertions.assertNotNull(keyPair);
  }

}
