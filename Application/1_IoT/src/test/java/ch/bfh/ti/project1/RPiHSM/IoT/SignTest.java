package ch.bfh.ti.project1.RPiHSM.IoT;


import ch.bfh.ti.project1.RPiHSM.IoT.Commands.CreateKey;
import ch.bfh.ti.project1.RPiHSM.IoT.Commands.CreateKeySet;
import ch.bfh.ti.project1.RPiHSM.IoT.Commands.Sign;
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
public class SignTest {

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
        name = "JUNIT_TEST_SIGN";
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
     * Tests a positive sign.
     * A key set with sign purpose and a key are created then a file is signed.
     */
    public void testPositiveSign() {

        CreateKeySet cks = new CreateKeySet(new String[]{path, name, "sign", ""}, mockSerialHelper);
        cks.execute();
        CreateKey ck = new CreateKey(new String[]{path, name, "primary", "0"}, mockSerialHelper);
        ck.execute();
        mockSerialHelper.reset();

        Sign s = new Sign(new String[]{path, name}, mockSerialHelper);
        s.execute();

        assertTrue(mockSerialHelper.isReady());
        assertFalse(mockSerialHelper.isError());
        assertTrue(mockSerialHelper.isSendFile());
    }

    @Test
    /**
     * Tests a negative sign (wrong key set name).
     */
    public void testNegativeSign() {
        Sign s = new Sign(new String[]{path, "Wrong name"}, mockSerialHelper);
        s.execute();
        assertFalse(mockSerialHelper.isReady());
        assertTrue(mockSerialHelper.isError());
    }


    @After
    /**
     * Deletes the create key set after the tests.
     */
    public void tear() throws IOException {
        mockSerialHelper.reset();
        File file = new File(path + Constants.DIRECTORY + name);
        FileUtils.deleteDirectory(file);
    }
}
