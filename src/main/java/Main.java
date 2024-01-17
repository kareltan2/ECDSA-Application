import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.ec.ECPair;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECPoint;

/**
 * @author kareltan
 */
public class Main {


  public static void main(String[] args) {
//    // Define the known values
//    ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256r1");
//    BigInteger d = new BigInteger("2"); // Replace with the actual private key
//    ECPoint G = spec.getG();
//    ECPoint Q = G.multiply(d).normalize();
//
//    // Print the result
//    System.out.println("Q = " + Q.getAffineXCoord().toBigInteger()+", "+Q.getAffineYCoord().toBigInteger());

    BigInteger s = new BigInteger("56515219790691171413109057904011688695424810155802929973526481321309856242040");
//    BigInteger k = new BigInteger("2");
    BigInteger mod = new BigInteger("115792089210356248762697446949407573529996955224135760342422259061068512044369");

    System.out.println(s.mod(mod));

//    //signature
//    BigInteger kinv = new BigInteger("57896044605178124381348723474703786764998477612067880171211129530534256022185");
//    BigInteger e = new BigInteger("54471906402954693198879917020241062082069071070711873448958570290843603745517");
//    BigInteger d = new BigInteger("27348952629179008429061293767070633170512136742263196279829949779636302092736");
//    BigInteger r = new BigInteger("56515219790691171413109057904011688695424810155802929973526481321309856242040");
//    BigInteger n = new BigInteger("115792089210356248762697446949407573529996955224135760342422259061068512044369");
//
//    System.out.println(kinv.multiply(e.add(d.multiply(r))).mod(n));

    //u1 dan u2
//    BigInteger w = new BigInteger("91588392821685639248174843409189808564410462731110200008773042898854270156656");
//    System.out.println("u1: " + e.multiply(w).mod(n));
//    System.out.println("u2: " + r.multiply(w).mod(n));

//    //X = u1.G + u2.Q
//    ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256r1");
//    BigInteger d = new BigInteger("27348952629179008429061293767070633170512136742263196279829949779636302092736"); // Replace with the actual private key
//    ECPoint G = spec.getG();
//    ECPoint Q = G.multiply(d);
//
//    BigInteger u1 = new BigInteger("78537862309117756686323834063156562471048771299118756226863421504966547663065");
//    BigInteger u2 = new BigInteger("113961774071608082223433756188056018683576973786174618408797468892526808537122");
//
//    ECPoint process1 = G.multiply(u1);
//    ECPoint process2 = Q.multiply(u2);
//    ECPoint result = process1.add(process2).normalize();
//
//    System.out.println(result.getAffineXCoord().toBigInteger()+", "+result.getAffineYCoord().toBigInteger());
  }

}