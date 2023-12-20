package ecdsa.application.cryptography;

import static ecdsa.application.constant.CommonConstant.BC;
import static ecdsa.application.constant.CommonConstant.CURVE;
import static ecdsa.application.constant.CommonConstant.EC;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

/**
 * @author kareltan
 */
public class GenerateKeyPair {

  public KeyPair generateKeyPair() throws Exception {
    ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec(CURVE);
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EC, BC);
    keyPairGenerator.initialize(ecSpec, new SecureRandom());
    return keyPairGenerator.generateKeyPair();
  }

}
