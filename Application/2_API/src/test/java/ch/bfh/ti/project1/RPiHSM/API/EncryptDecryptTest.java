package ch.bfh.ti.project1.RPiHSM.API;

import ch.bfh.ti.project1.RPiHSM.API.MockObjects.MockSerialHelper;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test all {@link EncryptDecrypt} functionality.
 */
public class EncryptDecryptTest {

    SerialHelperI mockSerialHelper;


    @Test
    /**
     * Tests a positive encryption.
     */
    public void testPositiveEncryption() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, -1);
        EncryptDecrypt ed = new EncryptDecrypt(mockSerialHelper, "", "-", "");
        assertTrue(ed.encrypt());
    }

    @Test
    /**
     * Tests a positive decryption.
     */
    public void testPositiveDecryption() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, -1);
        EncryptDecrypt ed = new EncryptDecrypt(mockSerialHelper, "", "-", "");
        assertTrue(ed.decrypt());
    }

    @Test
    /**
     * Tests a negative encryption.
     */
    public void testNegativeEncryption() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 1);
        EncryptDecrypt ed = new EncryptDecrypt(mockSerialHelper, "", "-", "");
        assertFalse(ed.encrypt());
    }

    @Test
    /**
     * Tests a negative decryption.
     */
    public void testNegativeDecryption() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 1);
        EncryptDecrypt ed = new EncryptDecrypt(mockSerialHelper, "", "-", "");
        assertFalse(ed.decrypt());
    }

    @Test
    /**
     * Tests a negative decryption when the key set do not exist.
     */
    public void testKeySetNotExist() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 0);
        EncryptDecrypt ed = new EncryptDecrypt(mockSerialHelper, "", "-", "");
        assertFalse(ed.decrypt());
    }

    @Test(expected = OperationNotSupportedException.class)
    /**
     * Tests if an OperationNotSupportedException is thrown.
     */
    public void testOperationNotSupportedException() throws OperationNotSupportedException, FileNotFoundException {
        mockSerialHelper = new MockSerialHelper(true, false, 1);
        EncryptDecrypt ed = new EncryptDecrypt(mockSerialHelper, "", "-", "");
        ed.encrypt();
    }

    @Test(expected = FileNotFoundException.class)
    /**
     * Tests if a FileNotFoundException is thrown.
     */
    public void testFileNotFoundException() throws OperationNotSupportedException, FileNotFoundException {
        mockSerialHelper = new MockSerialHelper(false, true, 1);
        EncryptDecrypt ed = new EncryptDecrypt(mockSerialHelper, "", "-", "");
        ed.encrypt();
    }
}
