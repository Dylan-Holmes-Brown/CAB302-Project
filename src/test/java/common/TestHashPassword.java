package common;

import common.HashPassword;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestHashPassword {

    /**
     * Test that hashing a password returns the expected hash
     */
    @Test
    public void testHashPassword() {
        assertEquals("7110eda4d09e062aa5e4a390b0a572ac0d2c0220", HashPassword.hashPassword("1234"));
    }

    //TODO: Create Test
    /**
     * Test the encode hex for hashing a password
     */
    @Test
    public void testEncodeHex() {

    }
}
