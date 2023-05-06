package logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptoTest {

    @Test
    void testEncryptionByKey() {
        String expected = "khoor";
        String result = Crypto.encryptByKey("Hello", 3);
        assertEquals(expected, result);
    }

    @Test
    void testDecryptionByKey() {
        String expected = "hello";
        String result = Crypto.decryptByKey("khoor", 3);
        assertEquals(expected, result);
    }

    @Test
    void testLengthOfAlphabet() {
        int length = Crypto.getLengthOfAlphabet();
        assertEquals(35, length);
    }
}