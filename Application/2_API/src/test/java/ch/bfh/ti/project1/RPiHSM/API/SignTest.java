package ch.bfh.ti.project1.RPiHSM.API;

import ch.bfh.ti.project1.RPiHSM.API.MockObjects.MockSerialHelper;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test all {@link Sign} functionality.
 */
public class SignTest {
    SerialHelperI mockSerialHelper;

    @Test
    /**
     * Tests a positive signing.
     */
    public void testPositiveSign() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, -1);
        Sign s = new Sign(mockSerialHelper, "", "-", "");
        assertTrue(s.sign());
    }

    @Test
    /**
     * Tests a negative signing.
     */
    public void testNegativeSign() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 1);
        Sign s = new Sign(mockSerialHelper, "", "-", "");
        assertFalse(s.sign());
    }

    @Test
    /**
     * Tests a negative signing if the key set do not exist.
     */
    public void testKeySetNotExist() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 0);
        Sign s = new Sign(mockSerialHelper, "", "-", "");
        assertFalse(s.sign());
    }

    @Test(expected = OperationNotSupportedException.class)
    /**
     * Tests if an {@link OperationNotSupportedException} is thrown.
     */
    public void testOperationNotSupportedException() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(true, false, -1);
        Sign s = new Sign(mockSerialHelper, "", "-", "");
        s.sign();
    }

    @Test(expected = FileNotFoundException.class)
    /**
     * Tests if a {@link FileNotFoundException} is thrown.
     */
    public void testFileNotFoundException() throws FileNotFoundException, OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, true, -1);
        Sign s = new Sign(mockSerialHelper, "", "-", "");
        s.sign();
    }
}
