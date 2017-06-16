package ch.bfh.ti.project1.RPiHSM.IoT;

import ch.bfh.ti.project1.RPiHSM.IoT.Commands.CreateKeySet;
import ch.bfh.ti.project1.RPiHSM.IoT.Commands.DeleteKeySet;
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
 * Test all {@link DeleteKeySet} functionality.
 */
public class DeleteKeySetTest {
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
        name = "JUNIT_TEST_DELETE_KEY_SET";
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
     * Test a positive key set deletion.
     * A key set is created then it is deleted.
     */
    public void testPositiveKeySetDelete() {
        CreateKeySet cks = new CreateKeySet(new String[]{path, name, "crypt", ""}, mockSerialHelper);
        cks.execute();
        mockSerialHelper.reset();
        DeleteKeySet dks = new DeleteKeySet(new String[]{path, name}, mockSerialHelper);
        dks.execute();
        assertTrue(mockSerialHelper.isReady());
        assertFalse(mockSerialHelper.isError());
    }

    @Test
    /**
     * Test a negative key set deletion (wrong key set name).
     */
    public void testNegativeKeySetDelete() {
        DeleteKeySet dks = new DeleteKeySet(new String[]{path, "Wrong name"}, mockSerialHelper);
        dks.execute();
        assertFalse(mockSerialHelper.isReady());
        assertTrue(mockSerialHelper.isError());
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
