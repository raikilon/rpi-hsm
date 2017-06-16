package ch.bfh.ti.project1.RPiHSM.IoT;


import ch.bfh.ti.project1.RPiHSM.IoT.Commands.*;
import ch.bfh.ti.project1.RPiHSM.IoT.MockObjects.MockSerialHelper;
import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test all {@link org.keyczar.enums.KeyStatus} functionality.
 */
public class KeyStatusTest {
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
        name = "JUNIT_TEST_KEY_STATUS";
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
     * Tests positive key demotion.
     * A key is created with the status as primary. Then it is demoted.
     */
    public void testPositiveDemoteKey() {
        //Create key to demote
        CreateKey ck = new CreateKey(new String[]{path, name, "primary", "0"}, mockSerialHelper);
        ck.execute();
        mockSerialHelper.reset();
        DemoteKey dk = new DemoteKey(new String[]{path, name, "1"}, mockSerialHelper);
        dk.execute();
        assertTrue(mockSerialHelper.isReady());
        assertFalse(mockSerialHelper.isError());
    }

    @Test
    /**
     * Tests negative key demotion.
     * A key is created with the status as inactive. Then it is demoted.
     */
    public void testNegativeDemoteKey() {
        //Create key to demote
        CreateKey ck = new CreateKey(new String[]{path, name, "inactive", "0"}, mockSerialHelper);//a inactive key cannot be demoted
        ck.execute();
        mockSerialHelper.reset();
        DemoteKey dk = new DemoteKey(new String[]{path, name, "1"}, mockSerialHelper);
        dk.execute();
        assertFalse(mockSerialHelper.isReady());
        assertTrue(mockSerialHelper.isError());
    }

    @Test
    /**
     * Tests positive key promotion.
     * A key is created with the status as active. Then it is promoted.
     */
    public void testPositivePromoteKey() {
        //Create key to demote
        CreateKey ck = new CreateKey(new String[]{path, name, "active", "0"}, mockSerialHelper);
        ck.execute();
        mockSerialHelper.reset();
        PromoteKey pk = new PromoteKey(new String[]{path, name, "1"}, mockSerialHelper);
        pk.execute();
        assertTrue(mockSerialHelper.isReady());
        assertFalse(mockSerialHelper.isError());
    }

    @Test
    /**
     * Tests negative key promotion.
     * A key is created with the status as active. Then it is promoted.
     */
    public void testNegativePromoteKey() {
        //Create key to demote
        CreateKey ck = new CreateKey(new String[]{path, name, "primary", "0"}, mockSerialHelper);//a primary key cannot be promoted
        ck.execute();
        mockSerialHelper.reset();
        PromoteKey pk = new PromoteKey(new String[]{path, name, "1"}, mockSerialHelper);
        pk.execute();
        assertFalse(mockSerialHelper.isReady());
        assertTrue(mockSerialHelper.isError());
    }

    @Test
    /**
     * Tests positive key revoking.
     * A key is created with the status as inactive. Then it is revoked.
     */
    public void testPositiveRevokeKey() {
        //Create key to demote
        CreateKey ck = new CreateKey(new String[]{path, name, "inactive", "0"}, mockSerialHelper);
        ck.execute();
        mockSerialHelper.reset();
        RevokeKey rk = new RevokeKey(new String[]{path, name, "1"}, mockSerialHelper);
        rk.execute();
        assertTrue(mockSerialHelper.isReady());
        assertFalse(mockSerialHelper.isError());
    }

    @Test
    /**
     * Tests negative key revoking.
     * A key is created with the status as primary. Then it is revoked.
     */
    public void testNegativeRevokeKey() {
        //Create key to demote
        CreateKey ck = new CreateKey(new String[]{path, name, "primary", "0"}, mockSerialHelper);//a primary key cannot be revoked
        ck.execute();
        mockSerialHelper.reset();
        RevokeKey rk = new RevokeKey(new String[]{path, name, "1"}, mockSerialHelper);
        rk.execute();
        assertFalse(mockSerialHelper.isReady());
        assertTrue(mockSerialHelper.isError());
    }


    @Before
    /**
     * Creates a key set for the test.
     */
    public void set() {
        CreateKeySet cks = new CreateKeySet(new String[]{path, name, "crypt", ""}, mockSerialHelper);
        cks.execute();
    }

    @After
    /**
     * Deletes the create key set after the test.
     */
    public void tear() throws IOException {
        mockSerialHelper.reset();
        File file = new File(path + Constants.DIRECTORY + name);
        FileUtils.deleteDirectory(file);
    }
}
