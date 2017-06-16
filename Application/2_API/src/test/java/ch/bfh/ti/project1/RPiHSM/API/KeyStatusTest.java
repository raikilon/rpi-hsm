package ch.bfh.ti.project1.RPiHSM.API;

import ch.bfh.ti.project1.RPiHSM.API.MockObjects.MockSerialHelper;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test all {@link KeyStatus} functionality.
 */
public class KeyStatusTest {

    SerialHelperI mockSerialHelper;


    @Test
    /**
     * Tests a positive demote.
     */
    public void testPositiveDemote() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, -1);
        KeyStatus ks = new KeyStatus(mockSerialHelper, "", "-", 0);
        assertTrue(ks.demote());
    }

    @Test
    /**
     * Tests a negative demote.
     */
    public void testNegativeDemote() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 1);
        KeyStatus ks = new KeyStatus(mockSerialHelper, "", "-", 0);
        assertFalse(ks.demote());
    }

    @Test
    /**
     * Tests a positive promote.
     */
    public void testPositivePromote() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, -1);
        KeyStatus ks = new KeyStatus(mockSerialHelper, "", "-", 0);
        assertTrue(ks.promote());
    }

    @Test
    /**
     * Tests a negative promote.
     */
    public void testNegativePromote() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 1);
        KeyStatus ks = new KeyStatus(mockSerialHelper, "", "-", 0);
        assertFalse(ks.promote());
    }

    @Test
    /**
     * Tests a positive revoke.
     */
    public void testPositiveRevoke() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, -1);
        KeyStatus ks = new KeyStatus(mockSerialHelper, "", "-", 0);
        assertTrue(ks.revoke());
    }

    @Test
    /**
     * Tests a negative revoke.
     */
    public void testNegativeRevoke() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 1);
        KeyStatus ks = new KeyStatus(mockSerialHelper, "", "-", 0);
        assertFalse(ks.revoke());
    }

    @Test
    /**
     * Tests a negative revoke when the key set do not exist.
     */
    public void testKeySetNotExist() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 0);
        KeyStatus ks = new KeyStatus(mockSerialHelper, "", "-", 0);
        assertFalse(ks.revoke());
    }

    @Test(expected = OperationNotSupportedException.class)
    /**
     * Tests if an OperationNotSupportedException is thrown.
     */
    public void testOperationNotSupportedException() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(true, false, -1);
        KeyStatus ks = new KeyStatus(mockSerialHelper, "", "-", 0);
        ks.revoke();
    }
}
