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
  void testVerifyDocument_shouldSuccess() throws Exception{
    KeyPair keyPair = generateKeyPair.generateKeyPair();
    PrivateKey privateKey = keyPair.getPrivate();
    PublicKey publicKey = keyPair.getPublic();
    byte[] signature = signDocument.signData(data, privateKey);

    boolean isVerified = verifyDocument.verifySignature(data, signature, publicKey);

    Assertions.assertTrue(isVerified);
    Assertions.assertNotNull(signature);
    Assertions.assertNotNull(keyPair);
  }

  @Test
  void testVerifyDocument_withPublicKeyNotValid_resultWillNotValid_shouldSuccess() throws Exception{
    KeyPair keyPair = generateKeyPair.generateKeyPair();
    KeyPair keyPair2 = generateKeyPair.generateKeyPair();

    PrivateKey privateKey1 = keyPair.getPrivate();
    PublicKey publicKey2 = keyPair2.getPublic();

    byte[] signature = signDocument.signData(data, privateKey1);

    boolean isVerified = verifyDocument.verifySignature(data, signature, publicKey2);

    Assertions.assertFalse(isVerified);
    Assertions.assertNotNull(signature);
    Assertions.assertNotNull(keyPair);
  }

  @Test
  void testVerifyDocument_withDifferentOriginalFile_resultWillNotValid_shouldSuccess() throws Exception{
    KeyPair keyPair = generateKeyPair.generateKeyPair();
    PrivateKey privateKey = keyPair.getPrivate();
    PublicKey publicKey = keyPair.getPublic();
    byte[] signature = signDocument.signData(data, privateKey);

    boolean isVerified = verifyDocument.verifySignature(data.concat("123"), signature, publicKey);

    Assertions.assertFalse(isVerified);
    Assertions.assertNotNull(signature);
    Assertions.assertNotNull(keyPair);
  }

  @Test
  void testVerifyDocument_withDifferentSignedFile_resultWillNotValid_shouldSuccess() throws Exception{
    KeyPair keyPair = generateKeyPair.generateKeyPair();
    PrivateKey privateKey = keyPair.getPrivate();
    PublicKey publicKey = keyPair.getPublic();
    byte[] signature = signDocument.signData(data.concat("123"), privateKey);

    boolean isVerified = verifyDocument.verifySignature(data, signature, publicKey);

    Assertions.assertFalse(isVerified);
    Assertions.assertNotNull(signature);
    Assertions.assertNotNull(keyPair);
  }

}
