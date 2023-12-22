package ecdsa.application.cryptography;

import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @author kareltan
 */
public abstract class ECDSACryptographyAbstract {

  protected void addProvider(){
    Security.addProvider(new BouncyCastleProvider());
  }

}
