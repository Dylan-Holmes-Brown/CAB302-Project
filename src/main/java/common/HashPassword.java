package common;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * This class Hash an users password using SHA-256 encryption
 *
 * @author Laku Jackson
 */
public class HashPassword {


    private final static int ITERATIONS = 1000;

    /**
     * This function returns a SHA-1 hash of a string, commonly used for the main.client side hashing.
     *
     * @param password The password the user has typed in the main.client.
     * @return String The hash of the password.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));

            return hexEncoded(crypt.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * Helper function to convert a byte array to a hex string
     * @param byteArray byte array converted to hex string
     * @return byte array string after converting to hex
     */
    public static String hexEncoded(byte[] byteArray) {
        return new BigInteger(1, byteArray).toString(16);
    }



}
