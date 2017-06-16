package ch.bfh.ti.project1.RPiHSM.IoT;


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
 * Test all the {@link CreateKeySet} functionality.
 */
public class CreateKeySetTest {
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
        name = "JUNIT_TEST_CREATE_KEY_SET";
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
     * Tests a positive key set creation.
     */
    public void testPositiveKeySetCreation() {
        assertTrue(mockSerialHelper.isReady());
        assertFalse(mockSerialHelper.isError());
    }

    @Test
    /**
     * Tests if a error occur when the key set already exists.
     */
    public void testKeySetAlreadyExist() {
        mockSerialHelper.reset();
        createKeySet();//create a key set when already exist
        assertFalse(mockSerialHelper.isReady());
        assertTrue(mockSerialHelper.isError());
    }


    @Before
    /**
     * Creates a key set for the tests.
     */
    public void set() {
        createKeySet();
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

    /**
     * Creates a key set.
     */
    private void createKeySet() {
        CreateKeySet cks = new CreateKeySet(new String[]{path, name, "crypt", ""}, mockSerialHelper);
        cks.execute();
    }
}
