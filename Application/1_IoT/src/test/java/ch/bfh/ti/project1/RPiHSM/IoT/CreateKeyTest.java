package ch.bfh.ti.project1.RPiHSM.IoT;


import ch.bfh.ti.project1.RPiHSM.IoT.Commands.CreateKey;
import ch.bfh.ti.project1.RPiHSM.IoT.Commands.CreateKeySet;
import ch.bfh.ti.project1.RPiHSM.IoT.MockObjects.MockSerialHelper;
import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Test all {@link CreateKey} functionality.
 */
public class CreateKeyTest {
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
        name = "JUNIT_TEST_CREATE_KEY";
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
     * Tests positive key creation.
     */
    public void testPositiveKeyCreation() {
        CreateKey ck = new CreateKey(new String[]{path, name, "primary", "0"}, mockSerialHelper);
        ck.execute();
        assertTrue(mockSerialHelper.isReady());
        assertFalse(mockSerialHelper.isError());
    }

    @Test
    /**
     * Tests negative key creation (wrong size).
     */
    public void testNegativeKeyCreation() {
        CreateKey ck = new CreateKey(new String[]{path, name, "primary", "1"}, mockSerialHelper); // a keyczar exception is thrown -> size cannot be 1
        ck.execute();
        assertFalse(mockSerialHelper.isReady());
        assertTrue(mockSerialHelper.isError());
    }


    @Before
    /**
     * Creates a key set for the tests.
     */
    public void set() {
        CreateKeySet cks = new CreateKeySet(new String[]{path, name, "crypt", ""}, mockSerialHelper);
        cks.execute();
        mockSerialHelper.reset();
    }

    @After
    /**
     * Deletes the key set used for the tests.
     */
    public void tear() throws IOException {
        mockSerialHelper.reset();
        File file = new File(path + Constants.DIRECTORY + name);
        FileUtils.deleteDirectory(file);
    }

}
