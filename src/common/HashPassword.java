package common; /**
 * This class Hash an users password using SHA-256 encryption
 *
 * @author Laku Jackson
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class HashPassword {

    public static String bytesToString(byte[] hash) {
        StringBuffer sb = new StringBuffer();
        for (byte b : hash) {
            sb.append(String.format("%02x", b & 0xFF));
        }
        return  sb.toString();
    }

    public static String getSaltedString() {
        Random rng = new Random();
        byte[] saltBytes = new byte[32];
        rng.nextBytes(saltBytes);
        return bytesToString(saltBytes);
    }

    public static String getHashedPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = md.digest(password.getBytes());
        return bytesToString(hashedPassword);
    }

    public static String getSaltedHashedPassword(String saltString, String hashedPassword) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String saltedPassword = bytesToString(md.digest((hashedPassword + saltString).getBytes()));
        return saltedPassword;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String input = "myPassword";
        byte[] hashedPassword = md.digest(input.getBytes());

        System.out.println("Hashed password: " + bytesToString(hashedPassword));

        Random rng = new Random();
        byte[] saltBytes = new byte[32];
        rng.nextBytes(saltBytes);
        String saltString = bytesToString(saltBytes);
        System.out.println("salt: " + saltString);

        String receivedHashedPassword = bytesToString(hashedPassword);
        String saltedPassword = bytesToString(md.digest((receivedHashedPassword + saltString).getBytes()));
        System.out.println("Salted password: " + saltedPassword);
    }



}
