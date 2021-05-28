package Utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;

/**
* This class Hash an users password using SHA-256 encryption
*
* @author Laku Jackson
*/


public class PasswordHash {

    private final static int ITERATIONS = 1000;


    public static byte[] HashsaltPassword(String password, byte[] salt) throws Exception {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, 55);
        SecretKeyFactory hmacSHA1 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return hmacSHA1.generateSecret(keySpec).getEncoded();
    }



    public static String Hashpassword(String password) {
        try {
            MessageDigest stash = MessageDigest.getInstance("SHA-1");
            stash.reset();
            stash.update(password.getBytes("UTF-8"));

            return CryptHex(stash.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static byte[] ObtainSalt() {
        return Random.String().getBytes();
    }


    public static byte[] DecryptHex(String hex) {
        // Convert using BigInteger then turn into a byte array
        byte[] byteArray = new BigInteger(hex, 16).toByteArray();
        // If the byte array has a garbage first value, remove it
        if (byteArray[0] == 0) {
            return Arrays.copyOfRange(byteArray, 1, byteArray.length);
        }
        return byteArray;
    }


    public static String CryptHex(byte[] byteArray) {
        return new BigInteger(1, byteArray).toString(16);
    }

}
