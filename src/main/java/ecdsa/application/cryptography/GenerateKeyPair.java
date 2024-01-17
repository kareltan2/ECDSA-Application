package ecdsa.application.cryptography;

import static ecdsa.application.constant.CommonConstant.BC;
import static ecdsa.application.constant.CommonConstant.CURVE;
import static ecdsa.application.constant.CommonConstant.EC;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECPoint;

/**
 * @author kareltan
 */
@Slf4j
public class GenerateKeyPair extends ECDSACryptographyAbstract {

  public KeyPair generateKeyPair()
      throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
    addProvider();
    log.info("Starting generate key pair with curve: {}", CURVE);

    // Retrieve the curve parameters
    ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec(CURVE);

    // Print the order (n) of the curve
    BigInteger order = ecSpec.getN();
    log.info("Order (n): {}", order);

    // Convert Bouncy Castle ECPoint to java.security.spec.ECPoint
    ECPoint gBC = ecSpec.getG();
    java.security.spec.ECPoint g = new java.security.spec.ECPoint(
        gBC.getAffineXCoord().toBigInteger(),
        gBC.getAffineYCoord().toBigInteger()
    );

    // Print the coordinates of the base point G
    log.info("Base Point (G) Coordinates:");
    log.info("x: {}", g.getAffineX());
    log.info("y: {}", g.getAffineY());

    long startTime = System.nanoTime();

    // Generate key pair
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EC, BC);
    keyPairGenerator.initialize(ecSpec, new SecureRandom());
    KeyPair keyPairGenerated = keyPairGenerator.generateKeyPair();

    long endTime = System.nanoTime();
    double elapsedTimeInSeconds = (endTime - startTime) / 1e9;

    // Print the private key as an integer
    ECPrivateKey privateKey = (ECPrivateKey) keyPairGenerated.getPrivate();
    log.info("Private Key (integer): {}", privateKey.getD());

    // Print the public key as an ECPoint
    ECPublicKey publicKey = (ECPublicKey) keyPairGenerated.getPublic();
    ECPoint publicPoint = publicKey.getQ();

    log.info("Public Key (Point) Coordinates:");
    log.info("x: {}", publicPoint.getAffineXCoord().toBigInteger());
    log.info("y: {}", publicPoint.getAffineYCoord().toBigInteger());

    log.info("Successfully generated key pair with value, privateKey: {}, publicKey: {}, with elapsedTime: {}s",
        privateKey, publicKey, elapsedTimeInSeconds);

    return keyPairGenerated;
  }
}
