package ch.bfh.ti.project1.RPiHSM.API;

import ch.bfh.ti.project1.RPiHSM.API.MockObjects.MockSerialHelper;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test all {@link CreateKey} functionality.
 */
public class CreateKeyTest {

    SerialHelperI mockSerialHelper;


    @Test
    /**
     * Tests a positive key creation.
     */
    public void testPositiveKeyCreation() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, -1);
        CreateKey ck = new CreateKey(mockSerialHelper, "", "-", "", 0);

        assertTrue(ck.create());

    }

    @Test
    /**
     * Test a negative key creation.
     */
    public void testNegativeKeyCreation() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 1);
        CreateKey ck = new CreateKey(mockSerialHelper, "", "-", "", 0);

        assertFalse(ck.create());

    }

    @Test
    /**
     * Test i negative key set creation when the key set do not exist.
     */
    public void testKeySetNotExist() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 0);
        CreateKey ck = new CreateKey(mockSerialHelper, "", "-", "", 0);

        assertFalse(ck.create());
    }

    @Test(expected = OperationNotSupportedException.class)
    /**
     * Test if a {@link OperationNotSupportedException} is thrown.
     */
    public void testOperationNotSupportedException() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(true, false, 1);
        CreateKey ck = new CreateKey(mockSerialHelper, "", "-", "", 0);
        ck.create();
    }
}
