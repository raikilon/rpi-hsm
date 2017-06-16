package ch.bfh.ti.project1.RPiHSM.API;

import ch.bfh.ti.project1.RPiHSM.API.MockObjects.MockSerialHelper;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test all {@link Login} functionality.
 */
public class LoginTest {

    SerialHelperI mockSerialHelper;


    @Test
    /**
     * Tests a positive login.
     */
    public void testPositiveLogin() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, -1);
        Login l = new Login(mockSerialHelper, "", "");
        assertTrue(l.checkCredentials());
        assertTrue(l.getUserPath().equals("1"));
    }

    @Test
    /**
     * Tests a negative login.
     */
    public void testNegativeLogin() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(false, false, 0);
        Login l = new Login(mockSerialHelper, "", "");
        assertFalse(l.checkCredentials());
    }

    @Test(expected = OperationNotSupportedException.class)
    /**
     * Tests if an {@link OperationNotSupportedException} is thrown.
     */
    public void TestOperationNotSupportedException() throws OperationNotSupportedException {
        mockSerialHelper = new MockSerialHelper(true, false, -1);
        Login l = new Login(mockSerialHelper, "", "");
        l.checkCredentials();
    }
}
