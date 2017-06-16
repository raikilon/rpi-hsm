package ch.bfh.ti.project1.RPiHSM.IoT.MockObjects;

import ch.bfh.ti.project1.RPiHSM.IoT.Commands.SerialHelperI;

/**
 * Mock object for test purpose ( the real {@link ch.bfh.ti.project1.RPiHSM.IoT.SerialHelper} can not be test because it need a valid serial connection).
 */
public class MockSerialHelper implements SerialHelperI {
    private boolean ready = false;
    private boolean error = false;
    private boolean readFile = false;
    private boolean sendFile = false;
    private boolean writeLine = false;

    private boolean decrypt_verify = false;
    private byte[] data;

    @Override
    /**
     * Sets the value on it opposite.
     */
    public void ready() {
        ready = !ready;
    }

    @Override
    /**
     * Sets the value on it opposite.
     */
    public void error() {
        error = !error;
    }

    @Override
    /**
     * Saves the given data.
     */
    public void sendFile(byte[] data) {
        this.data = data;
        sendFile = !sendFile;
    }

    @Override
    /**
     * Sets the value on it opposite.
     */
    public void sendFile(String message) {
        sendFile = !sendFile;
    }

    @Override
    /**
     * Returns a plain text byte array if the decrypt_variable is false otherwise is return the crypt or signed text.
     * @return if {@link MockSerialHelper#decrypt_verify} is true an encrypted or a signed byte array is returned otherwise a normal byte array.
     */
    public byte[] readFile() {
        readFile = !readFile;
        if (decrypt_verify) { //if ask for decrypt_verify a crypt o a signed text is returned
            decrypt_verify = false; //reset value for verify method -> first time ask for signed file, second time for real text
            return data;
        } else
            return "This is a test".getBytes();
    }

    @Override
    /**
     * Sets the value on it opposite.
     */
    public void writeLine(String message) {
        writeLine = !writeLine;
    }

    @Override
    /**
     * It is not used for test.
     * @return null
     */
    public String readLine() {
        return null;
    }

    /**
     * Resets all values to false.
     */
    public void reset() {
        ready = false;
        error = false;
        sendFile = false;
        readFile = false;
    }
    /**
     * @return true if {@link MockSerialHelper#ready()}called odd times
     *
     */
    public boolean isReady() {
        return ready;
    }


    /**
     * @return true if {@link MockSerialHelper#error()}called odd times
     */
    public boolean isError() {
        return error;
    }


    /**
     * @return true if {@link MockSerialHelper#readFile()} called odd times
     */
    public boolean isReadFile() {
        return readFile;
    }

    /**
     * @return true if {@link MockSerialHelper#sendFile(String)}  called odd times
     */
    public boolean isSendFile() {
        return sendFile;
    }

    /**
     * Sets if an encrypt or signed file must be returned in the method readFile.
     * @param decrypt_verify true if a decrypted o a verified text must be returned in {@link MockSerialHelper#readFile()}
     */
    public void setDecrypt_verify(boolean decrypt_verify) {
        this.decrypt_verify = decrypt_verify;
    }

    /**
     * @return true if {@link MockSerialHelper#writeLine(String)} called odd times
     */
    public boolean isWriteLine() {
        return writeLine;
    }

}
