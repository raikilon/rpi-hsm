package ch.bfh.ti.project1.RPiHSM.API;

import ch.bfh.ti.project1.RPiHSM.API.MockObjects.MockSerialHelper;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test all {@link CreateKeySet} functionality.
 */
public class CreateKeySetTest {

    SerialHelperI mockSerialHelper;

    @Test
    /**
     * Tests a positive key set creation.
     */
    public void testPositiveKeySetCreation() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, -1);
        CreateKeySet cks = new CreateKeySet(mockSerialHelper, "", "", "-", "");
        assertTrue(cks.create());
    }

    @Test
    /**
     * Tests a negative key set creation.
     */
    public void testNegativeKeySetCreation() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 0);
        CreateKeySet cks = new CreateKeySet(mockSerialHelper, "", "", "-", "");
        assertFalse(cks.create());
    }


    @Test(expected = OperationNotSupportedException.class)
    /**
     * Tests if a OperationNotSupportedException is thrown.
     */
    public void testOperationNotSupportedException() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(true, false, -1);
        CreateKeySet cks = new CreateKeySet(mockSerialHelper, "", "", "-", "");
        cks.create();
    }
}
