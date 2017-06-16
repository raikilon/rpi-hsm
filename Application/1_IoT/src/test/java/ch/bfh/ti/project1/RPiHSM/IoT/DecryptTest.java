package ch.bfh.ti.project1.RPiHSM.IoT;


import ch.bfh.ti.project1.RPiHSM.IoT.Commands.CreateKey;
import ch.bfh.ti.project1.RPiHSM.IoT.Commands.CreateKeySet;
import ch.bfh.ti.project1.RPiHSM.IoT.Commands.Decrypt;
import ch.bfh.ti.project1.RPiHSM.IoT.Commands.Encrypt;
import ch.bfh.ti.project1.RPiHSM.IoT.MockObjects.MockSerialHelper;
import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Test all {@link Decrypt} functionality.
 */
public class DecryptTest {
    private static MockSerialHelper mockSerialHelper;
    private static String path;
    private static String name;

    @BeforeClass
    /**
     * Creates a folder to store the key sets.
     */
    public static void setUp() {
        mockSerialHelper = new MockSerialHelper();
        path = System.getProperty("user.dir");
        name = "JUNIT_TEST_DECRYPTION";
        File file = new File(path + Constants.DIRECTORY);
        file.mkdir();
    }

    @AfterClass
    /**
     * Deletes the folder for the key sets.
     */
    public static void tearDown() throws IOException {
        File file = new File(path + Constants.DIRECTORY);
        FileUtils.deleteDirectory(file);
    }

    @Test
    /**
     * Tests the positive file decryption.
     * A key set with crypt purpose and a key are created then a test file is encrypted and finally the file is decrypted.
     */
    public void testPositiveDecryption() {
        CreateKeySet cks = new CreateKeySet(new String[]{path, name, "crypt", ""}, mockSerialHelper);
        cks.execute();
        CreateKey ck = new CreateKey(new String[]{path, name, "primary", "0"}, mockSerialHelper);
        ck.execute();
        Encrypt e = new Encrypt(new String[]{path, name}, mockSerialHelper);
        e.execute();
        //now we have and encrypted text do decrypt
        mockSerialHelper.reset();
        mockSerialHelper.setDecrypt_verify(true);//ask to return encrypted text

        Decrypt d = new Decrypt(new String[]{path, name}, mockSerialHelper);
        d.execute();
        assertTrue(mockSerialHelper.isReady());
        assertFalse(mockSerialHelper.isError());
        assertTrue(mockSerialHelper.isSendFile());
        assertTrue(mockSerialHelper.isReadFile());
    }

    @Test
    /**
     * Tests a negative decryption (wrong key set name).
     */
    public void testNegativeDecrypt() {
        Decrypt d = new Decrypt(new String[]{path, "Wrong name"}, mockSerialHelper); // encrypt with wrong keyset
        d.execute();
        assertFalse(mockSerialHelper.isReady());
        assertTrue(mockSerialHelper.isError());
        assertFalse(mockSerialHelper.isSendFile());
        assertTrue(mockSerialHelper.isReadFile());
    }

    @After
    /**
     * Deletes the key set directory.
     */
    public void tear() throws IOException {
        mockSerialHelper.reset();
        File file = new File(path + Constants.DIRECTORY + name);
        FileUtils.deleteDirectory(file);
    }
}
