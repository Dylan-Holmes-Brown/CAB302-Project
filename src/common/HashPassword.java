package common; /**
 * This class Hash an users password using SHA-256 encryption
 *
 * @author Laku Jackson
 */

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public class HashPassword {


    private final static int ITERATIONS = 1000;

    /**
     * This function creates a PBKDF2WithHmacSHA1 hash using the password, the a byte array as the salt and a length.
     *
     * @param password The password to hash.
     * @param salt The salt to combine with the password.
     * @return byte[] A byte array with the hex of the hashed and salted password.
     * @throws Exception Pass through the server error.
     */
    public static byte[] hashAndSaltPassword(String password, byte[] salt) throws Exception {
        // Creating a hashing spec based on the supplied login password, the users saved salt, iterations and length
        PBEKeySpec HashingSpec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, 62);
        // Choose the cryptography standard for hashing
        SecretKeyFactory HashingStandard = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        // Attempt to hash the supplied password using the hashing standard and hashing spec
        return HashingStandard.generateSecret(HashingSpec).getEncoded();
    }

    /**
     * This function returns a SHA-1 hash of a string, commonly used for the client side hashing.
     *
     * @param password The password the user has typed in the client.
     * @return String The hash of the password.
     * @throws Exception Pass through exception that gets handled by the client.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));

            return encodeHex(crypt.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * This function creates a random salt.
     *
     * @return byte[] A byte array salt.
     * @throws Exception Pass through the server error.
     */
    public static byte[] getSalt() {
        return Random.String().getBytes();
    }

    /**
     * This function is a helper function that converts a hex string to a byte array.
     *
     * @param hex The hex string to be converted to a byte array.
     * @return byte[] The byte array after converting the hex string.
     */
    public static byte[] decodeHex(String hex) {
        // Convert using BigInteger then turn into a byte array
        byte[] byteArray = new BigInteger(hex, 16).toByteArray();
        // If the byte array has a garbage first value, remove it
        if (byteArray[0] == 0) {
            return Arrays.copyOfRange(byteArray, 1, byteArray.length);
        }
        return byteArray;
    }

    /**
     * This function is a helper function that converts a byte array to a hex string.
     * @param byteArray The byte array to be converted to a hex string.
     * @return String The byte array after converting the hex string.
     */
    public static String encodeHex(byte[] byteArray) {
        return new BigInteger(1, byteArray).toString(16);
    }



}
