package ch.bfh.ti.project1.RPiHSM.API;

import javax.naming.OperationNotSupportedException;

import java.io.FileNotFoundException;

/**
 * <h1>EncryptDecrypt</h1>
 * Ask the IoT application using the {@link SerialHelperI} to encrypt or decrypt the given file with the given key set.
 */
public final class EncryptDecrypt {

    private SerialHelperI serialHelper;
    private String name;
    private String userPath;
    private String file;

    /**
     * Sets {@link SerialHelperI} instance to communicate with the IoT application and the encryption/decryption parameters.
     *
     * @param serialHelper an instance of {@link SerialHelperI}
     * @param userPath     home path of the IoT application user
     * @param name         name of the key set
     * @param file         absolute path of the file to encrypt/decrypt
     *                     Prerequisite: if the key set use the rsa algorithm the file size must be smaller than 470 bytes.
     *                     The given key set must be created with crypt purpose.
     */
    public EncryptDecrypt(SerialHelperI serialHelper, String userPath, String name, String file) {
        this.serialHelper = serialHelper;
        this.name = name;
        this.userPath = userPath;
        this.file = file;
    }

    /**
     * Checks if the key set with the {@link CreateKey#name} exists and if yes, it ask to the IoT application to encrypt the given file with the given key set.
     *
     * @return true if the file has been encrypted, otherwise false
     * @throws OperationNotSupportedException if an error occurs in the {@link SerialHelperI}
     * @throws FileNotFoundException          if the given file does not exist
     */
    public boolean encrypt() throws OperationNotSupportedException, FileNotFoundException {
        String[] data = {Constants.ENCRYPT, userPath, name};
        return execute(String.join(" ", data));
    }

    /**
     * Checks if the key set with the {@link CreateKey#name} exists and if yes, it ask to the IoT application to decrypt the given file with the given key set.
     *
     * @return true if the file has been decrypted, otherwise false
     * @throws OperationNotSupportedException if an error occurs in the {@link SerialHelperI}
     * @throws FileNotFoundException          if the given file does not exist
     */
    public boolean decrypt() throws OperationNotSupportedException, FileNotFoundException {
        String[] data = {Constants.DECRYPT, userPath, name};
        return execute(String.join(" ", data));
    }

    /**
     * Called by the {@link EncryptDecrypt#decrypt()} and {@link EncryptDecrypt#encrypt()} methods. It check if the key set
     * with the {@link CreateKey#name} exists and if yes, it ask to the IoT application to decrypt or encrypt the given file with the given key set.
     *
     * @param command data the array defined in {@link EncryptDecrypt#encrypt()} or {@link EncryptDecrypt#decrypt()} as string
     * @return true if the file has been encrypted/decrypted, otherwise false
     * @throws OperationNotSupportedException if an error occurs in the {@link SerialHelperI}
     * @throws FileNotFoundException          if the given file does not exist
     */
    private boolean execute(String command) throws OperationNotSupportedException, FileNotFoundException {

        KeyExistence ke = new KeyExistence(serialHelper, userPath, name);

        if (ke.keyexist()) {
            serialHelper.writeLine(command);
            serialHelper.sendFile(file);

            if (!serialHelper.status()) {
            	return false;
            }

            serialHelper.readFile(file);

            return true;
        } else {
            return false;
        }

    }
}
