package common;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class Random {


    /**
     * Creates a random string.
     *
     * @return String Random string sequence.
     */
    public static String String() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    

    /**
     * Creates a random integer.
     *
     * @param max The max value to return.
     * @return int A positive integer between 0 and the supplied max.
     */
    public static int Int(int max) {
        java.util.Random random = new java.util.Random();
        return Math.abs(random.nextInt(max));
    }


}
