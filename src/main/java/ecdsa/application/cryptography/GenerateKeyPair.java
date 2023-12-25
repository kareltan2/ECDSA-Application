package ecdsa.application.cryptography;

import static ecdsa.application.constant.CommonConstant.BC;
import static ecdsa.application.constant.CommonConstant.CURVE;
import static ecdsa.application.constant.CommonConstant.EC;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

/**
 * @author kareltan
 */
@Slf4j
public class GenerateKeyPair extends ECDSACryptographyAbstract {

  public KeyPair generateKeyPair()
      throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
    addProvider();

    log.info("Starting generate key pair with curve: {}", CURVE);
    ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec(CURVE);
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EC, BC);
    keyPairGenerator.initialize(ecSpec, new SecureRandom());

    KeyPair keyPairGenerated = keyPairGenerator.generateKeyPair();
    log.info("Successfully generated key pair with value, privateKey: {}, publicKey: {}",
        keyPairGenerated.getPrivate().getEncoded(), keyPairGenerated.getPublic().getEncoded());

    return keyPairGenerated;
  }

}
