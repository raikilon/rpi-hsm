package ch.bfh.ti.project1.RPiHSM.API;

import ch.bfh.ti.project1.RPiHSM.API.MockObjects.MockSerialHelper;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test all {@link DeleteKeySet} functionality.
 */
public class DeleteKeySetTest {

    SerialHelperI mockSerialHelper;

    @Test
    /**
     * Tests a positive key set deletion.
     */
    public void testPositiveKeyExistence() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, -1);
        DeleteKeySet dks = new DeleteKeySet(mockSerialHelper, "", "-");
        assertTrue(dks.delete());
    }

    @Test
    /**
     * Tests a negative key set deletion.
     */
    public void testNegativeKeyExistence() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 0);
        DeleteKeySet dks = new DeleteKeySet(mockSerialHelper, "", "-");
        assertFalse(dks.delete());
    }


    @Test(expected = OperationNotSupportedException.class)
    /**
     * Test if a OperationNotSupportedException is thrown.
     */
    public void testOperationNotSupportedException() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(true, false, -1);
        DeleteKeySet dks = new DeleteKeySet(mockSerialHelper, "", "-");
        dks.delete();
    }


}
