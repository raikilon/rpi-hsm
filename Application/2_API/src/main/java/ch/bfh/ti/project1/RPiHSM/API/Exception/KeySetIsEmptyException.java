package ch.bfh.ti.project1.RPiHSM.API.Exception;

/**
 * <h1>KeySetIsEmptyException</h1>
 * This exception is thrown when cryptographic operations are executed on a empty key set
 */
public class KeySetIsEmptyException extends Exception {
    public KeySetIsEmptyException() {
        super();
    }

}
