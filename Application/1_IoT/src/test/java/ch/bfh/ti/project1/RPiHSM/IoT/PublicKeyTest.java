package ch.bfh.ti.project1.RPiHSM.IoT;

import ch.bfh.ti.project1.RPiHSM.IoT.Commands.CreateKey;
import ch.bfh.ti.project1.RPiHSM.IoT.Commands.CreateKeySet;
import ch.bfh.ti.project1.RPiHSM.IoT.Commands.PublicKey;
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
 * Test all {@link PublicKey} functionality.
 */
public class PublicKeyTest {
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
        name = "JUNIT_TEST_PUBLIC_KEY";
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
     * Tests a positive public key exportation.
     * A asymmetric key set and a key are created then the created key is exported.
     */
    public void testPositivePublicKey() {
        //RSA KEY SET
        CreateKeySet cks = new CreateKeySet(new String[]{path, name, "crypt", "rsa"}, mockSerialHelper);
        cks.execute();
        //create key to export
        CreateKey ck = new CreateKey(new String[]{path, name, "primary", "0"}, mockSerialHelper);
        ck.execute();
        mockSerialHelper.reset();
        PublicKey pk = new PublicKey(new String[]{path, name}, mockSerialHelper);
        pk.execute();
        assertFalse(mockSerialHelper.isReady());//assertFalse because ready is called two times
        assertFalse(mockSerialHelper.isError());
        assertFalse(mockSerialHelper.isWriteLine()); //assertFalse because is called two times
        assertFalse(mockSerialHelper.isSendFile());//assertFalse because is called two times
    }

    @Test
    /**
     * Tests if the pubkeys directory is deleted.
     */
    public void testCleanFolder() throws IOException {
        //RSA KEY SET
        CreateKeySet cks = new CreateKeySet(new String[]{path, name, "crypt", "rsa"}, mockSerialHelper);
        cks.execute();
        //create key to export
        CreateKey ck = new CreateKey(new String[]{path, name, "primary", "0"}, mockSerialHelper);
        ck.execute();
        mockSerialHelper.reset();
        //create a pubkeys folder
        File pubkeys = new File(path+Constants.DIRECTORY+name+"//pubkeys");
        pubkeys.mkdirs();
        PublicKey pk = new PublicKey(new String[]{path, name}, mockSerialHelper);
        pk.execute();
        assertFalse(mockSerialHelper.isReady());//assertFalse because ready is called two times
        assertFalse(mockSerialHelper.isError());
        assertTrue(mockSerialHelper.isWriteLine());
        assertFalse(mockSerialHelper.isSendFile());//assertFalse because is called two times
    }


    @Test
    /**
     * Tests a negative public key exportation.
     * The key set is not asymmetric.
     */
    public void testNotAsymmetricKeySet() {
        CreateKeySet cks = new CreateKeySet(new String[]{path, name, "crypt", ""}, mockSerialHelper);//key set not asymmetric
        cks.execute();
        mockSerialHelper.reset();
        PublicKey pk = new PublicKey(new String[]{path, name}, mockSerialHelper);
        pk.execute();
        assertFalse(mockSerialHelper.isReady());
        assertTrue(mockSerialHelper.isError());
    }

    @Test
    /**
     * Test a negative public key exportation.
     * The key set is empty (no keys are available).
     */
    public void testEmptyKeySet() {
        //RSA KEY SET
        CreateKeySet cks = new CreateKeySet(new String[]{path, name, "crypt", "rsa"}, mockSerialHelper);
        cks.execute();
        mockSerialHelper.reset();
        PublicKey pk = new PublicKey(new String[]{path, name}, mockSerialHelper);
        pk.execute();
        assertTrue(mockSerialHelper.isReady()); //ready is called one time before error
        assertTrue(mockSerialHelper.isError());
    }


    @After
    /**
     * Deletes the created key set after the test.
     */
    public void tear() throws IOException {
        mockSerialHelper.reset();
        File file = new File(path + Constants.DIRECTORY + name);
        FileUtils.deleteDirectory(file);
    }
}
