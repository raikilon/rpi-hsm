package ch.bfh.ti.project1.RPiHSM.API;

import ch.bfh.ti.project1.RPiHSM.API.MockObjects.MockSerialHelper;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test all {@link Verify} functionality.
 */
public class VerifyTest {
    SerialHelperI mockSerialHelper;

    @Test
    /**
     * Tests a positive verification.
     */
    public void testPositiveVerify() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, -1);
        Verify v = new Verify(mockSerialHelper, "", "-", "", "");
        assertTrue(v.verify());
    }

    @Test
    /**
     * Tests a negative verification.
     */
    public void testNegativeVerify() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 1);
        Verify v = new Verify(mockSerialHelper, "", "-", "", "");
        assertFalse(v.verify());
    }

    @Test
    /**
     * Tests a negative verification if the key set do not exist.
     */
    public void testKeySetNotExist() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 0);
        Verify v = new Verify(mockSerialHelper, "", "-", "", "");
        assertFalse(v.verify());
    }

    @Test(expected = OperationNotSupportedException.class)
    /**
     * Tests if an {@link OperationNotSupportedException} is thrown.
     */
    public void testOperationNotSupportedException() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(true, false, -1);
        Verify v = new Verify(mockSerialHelper, "", "-", "", "");
        v.verify();
    }

    @Test(expected = FileNotFoundException.class)
    /**
     * Tests if a {@link FileNotFoundException} is thrown.
     */
    public void testFileNotFoundException() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, true, -1);
        Verify v = new Verify(mockSerialHelper, "", "-", "", "");
        v.verify();
    }
}
