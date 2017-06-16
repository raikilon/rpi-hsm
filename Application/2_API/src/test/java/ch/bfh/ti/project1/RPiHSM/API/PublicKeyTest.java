package ch.bfh.ti.project1.RPiHSM.API;

import ch.bfh.ti.project1.RPiHSM.API.Exception.KeySetNotAsymmetricException;
import ch.bfh.ti.project1.RPiHSM.API.MockObjects.MockSerialHelper;
import ch.bfh.ti.project1.RPiHSM.API.Exception.KeySetIsEmptyException;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test all {@link PublicKey} functionality.
 */
public class PublicKeyTest {
    SerialHelperI mockSerialHelper;

    @Test
    /**
     * Tests a positive public keys generation.
     */
    public void testPositivePublicKey() throws KeySetIsEmptyException, OperationNotSupportedException, KeySetNotAsymmetricException, FileNotFoundException {
        mockSerialHelper = new MockSerialHelper(false, false, -1);
        PublicKey pk = new PublicKey(mockSerialHelper, "", "-", "");
        assertTrue(pk.generate());
    }

    @Test(expected = KeySetNotAsymmetricException.class)
    /**
     * Tests if a {@link KeySetNotAsymmetricException} is thrown.
     */
    public void testKeySetNotAsymmetric() throws KeySetIsEmptyException, OperationNotSupportedException, KeySetNotAsymmetricException, FileNotFoundException {
        mockSerialHelper = new MockSerialHelper(false, false, 1);
        PublicKey pk = new PublicKey(mockSerialHelper, "", "-", "");
        pk.generate();
    }

    @Test(expected = KeySetIsEmptyException.class)
    /**
     * Tests if a {@link KeySetIsEmptyException} is thrown.
     */
    public void testKeySetIsEmpty() throws KeySetIsEmptyException, OperationNotSupportedException, KeySetNotAsymmetricException, FileNotFoundException {
        mockSerialHelper = new MockSerialHelper(false, false, 2);
        PublicKey pk = new PublicKey(mockSerialHelper, "", "-", "");
        pk.generate();
    }

    @Test
    /**
     * Tests a negative public keys generation if the key set do not exist.
     */
    public void testKeySetNotExist() throws KeySetIsEmptyException, OperationNotSupportedException, FileNotFoundException, KeySetNotAsymmetricException {
        mockSerialHelper = new MockSerialHelper(false, false, 0);
        PublicKey pk = new PublicKey(mockSerialHelper, "", "-", "");
        assertFalse(pk.generate());
    }


    @Test(expected = OperationNotSupportedException.class)
    /**
     * Tests if an {@link OperationNotSupportedException} is thrown.
     */
    public void testOperationNotSupportedException() throws KeySetIsEmptyException, OperationNotSupportedException, KeySetNotAsymmetricException, FileNotFoundException {
        mockSerialHelper = new MockSerialHelper(true, false, -1);
        PublicKey pk = new PublicKey(mockSerialHelper, "", "-", "");
        pk.generate();
    }

    @Test(expected = FileNotFoundException.class)
    /**
     * Tests if a {@link FileNotFoundException} is thrown.
     */
    public void testFileNotFoundException() throws KeySetIsEmptyException, OperationNotSupportedException, KeySetNotAsymmetricException, FileNotFoundException {
        mockSerialHelper = new MockSerialHelper(false, true, -1);
        PublicKey pk = new PublicKey(mockSerialHelper, "", "-", "");
        pk.generate();
    }
}
