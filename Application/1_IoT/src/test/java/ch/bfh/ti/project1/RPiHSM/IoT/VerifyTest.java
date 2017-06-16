package ch.bfh.ti.project1.RPiHSM.IoT;

import ch.bfh.ti.project1.RPiHSM.IoT.Commands.CreateKey;
import ch.bfh.ti.project1.RPiHSM.IoT.Commands.CreateKeySet;
import ch.bfh.ti.project1.RPiHSM.IoT.Commands.Sign;
import ch.bfh.ti.project1.RPiHSM.IoT.Commands.Verify;
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
 * Test all {@link Sign} functionality.
 */
public class VerifyTest {
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
        name = "JUNIT_TEST_VERIFY";
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
     * Tests a positive file verify.
     * A key set with sign purpose and a key are created then a file is signed and then is verified.
     */
    public void testPositiveVerify() {
        CreateKeySet cks = new CreateKeySet(new String[]{path, name, "sign", ""}, mockSerialHelper);
        cks.execute();
        CreateKey ck = new CreateKey(new String[]{path, name, "primary", "0"}, mockSerialHelper);
        ck.execute();
        Sign s = new Sign(new String[]{path, name}, mockSerialHelper);
        s.execute();
        //now we have a signed text do verify
        mockSerialHelper.reset();
        mockSerialHelper.setDecrypt_verify(true);
        Verify v = new Verify(new String[]{path, name}, mockSerialHelper);
        v.execute();
        assertTrue(mockSerialHelper.isReady());
        assertFalse(mockSerialHelper.isError());
    }

    @Test
    /**
     * Tests a negative file verify.
     * A key set with sign purpose and a key are created then a file is verified without a valid signature.
     */
    public void testWrongSignature() {
        CreateKeySet cks = new CreateKeySet(new String[]{path, name, "sign", ""}, mockSerialHelper);
        cks.execute();
        CreateKey ck = new CreateKey(new String[]{path, name, "primary", "0"}, mockSerialHelper);
        ck.execute();
        //now we have a signed text do verify
        mockSerialHelper.reset();
        Verify v = new Verify(new String[]{path, name}, mockSerialHelper);
        v.execute();
        assertFalse(mockSerialHelper.isReady());
        assertTrue(mockSerialHelper.isError());
    }//This test create a org.keyczar.exceptions.KeyczarException (the right if is not checked but is guaranteed the code will work if this will happen).

    @Test
    /**
     * Tests a negative file verifying (wrong key set name).
     */
    public void testNegativeVerify() {
        Verify v = new Verify(new String[]{path, "Wrong name"}, mockSerialHelper);
        v.execute();
        assertFalse(mockSerialHelper.isReady());
        assertTrue(mockSerialHelper.isError());
    }


    @After
    /**
     * Deletes the created key set after the tests.
     */
    public void tear() throws IOException {
        mockSerialHelper.reset();
        File file = new File(path + Constants.DIRECTORY + name);
        FileUtils.deleteDirectory(file);
    }
}
