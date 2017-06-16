package ch.bfh.ti.project1.RPiHSM.API.Exception;

/**
 * <h1>KeySetNotAsymmetricException</h1>
 * This exception is thrown when asymmetric operations are executed on a not asymmetric key set.
 */
public class KeySetNotAsymmetricException extends Exception {
    public KeySetNotAsymmetricException() {
        super();
    }

}
