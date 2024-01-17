package ecdsa.application.cryptography;

import static ecdsa.application.constant.CommonConstant.BC;
import static ecdsa.application.constant.CommonConstant.SHA256_ECDSA;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.math.ec.ECPoint;

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

    // Print the public key as an ECPoint
    ECPublicKey publicKeyE = (ECPublicKey) publicKey;
    ECPoint publicPoint = publicKeyE.getQ();

    log.info("Public Key (Point) Coordinates:");
    log.info("x: {}", publicPoint.getAffineXCoord().toBigInteger());
    log.info("y: {}", publicPoint.getAffineYCoord().toBigInteger());

    // Parse signatureBytes into r and s as BigIntegers
    BigInteger[] rs = parseSignatureBytes(signature);

    // Print the r and s values
    System.out.println("r: " + rs[0]);
    System.out.println("s: " + rs[1]);


    boolean isVerified = verifier.verify(signature);

    long endTime = System.nanoTime();
    double elapsedTimeInSeconds = (endTime - startTime) / 1e9;
    log.info("Completed verify signature with result: {}, with elapsedTime: {}s", isVerified, elapsedTimeInSeconds);

    return isVerified;
  }

  private static BigInteger[] parseSignatureBytes(byte[] signatureBytes) {
    // Assuming signatureBytes is in DER format
    // DER encoding for ECDSA signatures: 0x30 || totalLength || 0x02 || rLength || r || 0x02 || sLength || s
    int rLength = signatureBytes[3];
    int sLength = signatureBytes[5 + rLength];
    byte[] rBytes = new byte[rLength];
    byte[] sBytes = new byte[sLength];
    System.arraycopy(signatureBytes, 4, rBytes, 0, rLength);
    System.arraycopy(signatureBytes, 6 + rLength, sBytes, 0, sLength);

    // Parse bytes into BigIntegers
    BigInteger r = new BigInteger(1, rBytes);
    BigInteger s = new BigInteger(1, sBytes);

    return new BigInteger[]{r, s};
  }

}
