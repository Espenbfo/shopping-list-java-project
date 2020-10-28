package shoppinglist.core;

import java.util.HashMap;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;

import shoppinglist.core.Person;
public class Passwords {

    HashMap<String, String> hashes; //User, Password
    private static int hashLength = 256;
    private static int hashIterations = 10000;
    private static int saltLength = 16;
    public Passwords() {
        hashes = new HashMap<String, String>();
    }
    public Passwords(HashMap<String, String> hashes) {
        this.hashes=hashes;
    }

    public void setHashes(HashMap<String, String> hashes) {
        this.hashes = hashes;
    }

    public HashMap<String, String> getHashes() {
        return this.hashes;
    }

    public boolean checkPassword(String userName, String password, byte[] salt) {
        String hashedPassword = Passwords.hash(password, salt);
        if (hashes.get(userName).equals(hashedPassword)) {
            return true;
        }
        return false;
    }
    public boolean checkPassword(Person p, String password) {
        return checkPassword(p.getUserName(),password,p.getSalt());
    }

    public static String hash(String password, byte[] salt) {
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKey key = f.generateSecret(new PBEKeySpec(
                    password.toCharArray(), salt, hashIterations, hashLength));
            return Base64.encodeBase64String(key.getEncoded());
        }
        catch (Exception e) {
            System.out.println("Noe gikk feil med hashingen");
            return "";
        }
    }

    public static byte[] randomSalt() {
        byte[] salt = new byte[saltLength];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }
}