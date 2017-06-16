package ch.bfh.ti.project1.RPiHSM.API;

import ch.bfh.ti.project1.RPiHSM.API.MockObjects.MockSerialHelper;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test all {@link KeyExistence} functionality.
 */
public class KeyExistenceTest {

    SerialHelperI mockSerialHelper;

    @Test
    /**
     * Tests a positive key existence.
     */
    public void testPositiveKeyExistence() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, -1);
        KeyExistence ke = new KeyExistence(mockSerialHelper, "", "-");
        assertTrue(ke.keyexist());
    }

    @Test
    /**
     * Tests a negative key existence.
     */
    public void testNegativeKeyExistence() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 0);
        KeyExistence ke = new KeyExistence(mockSerialHelper, "", "-");
        assertFalse(ke.keyexist());
    }

    @Test
    /**
     * Tests a negative key existence if the name is empty.
     */
    public void testNegativeKeyExistence2() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 0);
        KeyExistence ke = new KeyExistence(mockSerialHelper, "", "");
        assertFalse(ke.keyexist());
    }

    @Test(expected = OperationNotSupportedException.class)
    /**
     * Tests if an {@link OperationNotSupportedException} is thrown.
     */
    public void testOperationNotSupportedException() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(true, false, -1);
        KeyExistence ke = new KeyExistence(mockSerialHelper, "", "-");
        ke.keyexist();
    }


}
