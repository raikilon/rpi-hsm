package ch.bfh.ti.project1.RPiHSM.API;


import ch.bfh.ti.project1.RPiHSM.API.Exception.SerialPortException;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;

/**
 * <h1>SerialHelperI</h1>
 * Interface of the serial helper so that an user can create its own and for testing purpose.
 * (the real serial helper cannot be tested because it needs a valid serial connection).
 */
public interface SerialHelperI {

    /**
     * Ask to the IoT application if the operations has been successful or not.
     *
     * @return true if the operation on the IoT side has been successful, otherwise false
     * @throws OperationNotSupportedException if an error occurs in the reading process
     */
    boolean status() throws OperationNotSupportedException;

    /**
     * Transfers a file
     *
     * @param path path of the file to send
     * @throws FileNotFoundException          the given file do not exists
     * @throws OperationNotSupportedException if an error occurs in the writing process
     */
    void sendFile(String path) throws FileNotFoundException, OperationNotSupportedException;

    /**
     * Reads a file
     *
     * @param path path of where the file is saved.
     * @throws FileNotFoundException          the given file path do not exists
     * @throws OperationNotSupportedException if an error occurs in the reading process
     */
    void readFile(String path) throws FileNotFoundException, OperationNotSupportedException;

    /**
     * Close the connection
     *
     * @throws SerialPortException if an error occurs in the closing process
     */
    void closeConnection() throws SerialPortException;

    /**
     * Writes a string
     *
     * @param message string to send
     * @throws OperationNotSupportedException if an error occurs in the writing process
     */
    void writeLine(String message) throws OperationNotSupportedException;

    /**
     * Read as string
     *
     * @return the read string
     * @throws OperationNotSupportedException if an error occurs in the reading process
     */
    String readLine() throws OperationNotSupportedException;

}
