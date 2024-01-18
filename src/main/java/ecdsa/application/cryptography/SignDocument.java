package ecdsa.application.cryptography;

import static ecdsa.application.constant.CommonConstant.BC;
import static ecdsa.application.constant.CommonConstant.SHA256_ECDSA;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.interfaces.ECPrivateKey;

/**
 * @author kareltan
 */
@Slf4j
public class SignDocument extends ECDSACryptographyAbstract{

  public byte[] signData(String data, PrivateKey privateKey)
      throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException {
    addProvider();
    log.info("Starting generate signature with privateKey: {}", privateKey);

    long startTime = System.nanoTime();
    Signature signature = Signature.getInstance(SHA256_ECDSA, BC);
    signature.initSign(privateKey);
    signature.update(data.getBytes(StandardCharsets.UTF_8));

    // Print the private key as an integer
    ECPrivateKey privateKeyENC = (ECPrivateKey) privateKey;
    log.info("Private Key (integer): {}", privateKeyENC.getD());

    BigInteger hashedData = hashData(data.getBytes(StandardCharsets.UTF_8));
    System.out.println("Hashed Data (as BigInteger): " + hashedData);

    byte[] signatureBytes = signature.sign();
    long endTime = System.nanoTime();
    double elapsedTimeInSeconds = (endTime - startTime) / 1e9;
    log.info("Successfully generate signature with value: {}, with elapsedTime: {}s", new BigInteger(signatureBytes), elapsedTimeInSeconds);
    // Parse signatureBytes into r and s as BigIntegers
    BigInteger[] rs = parseSignatureBytes(signatureBytes);

    // Print the r and s values
    System.out.println("r: " + rs[0]);
    System.out.println("s: " + rs[1]);
    return signatureBytes;
  }

  private static BigInteger hashData(byte[] data) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hashBytes = digest.digest(data);
    return new BigInteger(1, hashBytes); // 1 is for positive signum
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
