package ch.bfh.ti.project1.RPiHSM.IoT.Commands;

/**
 * <h1>SerialHelperI</h1>
 * Interface of the serial helper so that an user can create its own and for testing purpose.
 * (the real serial helper cannot be tested because it needs a valid serial connection).
 */
public interface SerialHelperI {

    /**
     * Communicates that the asked operation was executed correctly
     */
    void ready();

    /**
     * Communicates that the asked operation was not executed correctly
     */
    void error();

    /**
     * Transfers a file
     *
     * @param data the bytes of the file
     */
    void sendFile(byte[] data);


    /**
     * Sends a file
     *
     * @param path the path of the file
     */
    void sendFile(String path);


    /**
     * Reads a file
     *
     * @return byte array that contain the file
     */
    byte[] readFile();

    /**
     * Writes a string
     *
     * @param message string to send
     */
    void writeLine(String message);

    /**
     * Reads a string
     *
     * @return read string
     */
    String readLine();

}