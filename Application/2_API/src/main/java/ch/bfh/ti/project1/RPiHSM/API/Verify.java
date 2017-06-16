package ch.bfh.ti.project1.RPiHSM.API;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;

/**
 * <h1>Verify</h1>
 * Ask the IoT application using the {@link SerialHelperI} to verify a given signature with a given key set.
 */
public final class Verify {

    private SerialHelperI serialHelper;
    private String userPath;
    private String name;
    private String file;
    private String signature;

    /**
     * Sets {@link SerialHelperI} instance to communicate with the IoT application and the verify parameters parameters.
     *
     * @param serialHelper an instance of {@link SerialHelperI}
     * @param userPath     home path of the IoT application user
     * @param name         name of the key set
     * @param file         absolute path of the original file
     * @param signature    absolute path of the signature to verify
     *                     Prerequisite: The given key set must be created with sign purpose.
     */
    public Verify(SerialHelperI serialHelper, String userPath, String name, String file, String signature) {
        this.serialHelper = serialHelper;
        this.userPath = userPath;
        this.name = name;
        this.file = file;
        this.signature = signature;
    }

    /**
     * Checks if the key set with the {@link CreateKey#name} exists and if yes, it ask to the IoT application to verify the given signature with a given key set.
     *
     * @return True if the signature is valid, false if the key set do not exist or the verify process had some errors.
     * @throws OperationNotSupportedException if an error occurs in the {@link SerialHelperI}
     * @throws FileNotFoundException          if the given files do not exist
     */
    public boolean verify() throws OperationNotSupportedException, FileNotFoundException {


        KeyExistence ke = new KeyExistence(serialHelper, userPath, name);

        if (ke.keyexist()) {

            String[] data = new String[]{Constants.VERIFY, userPath, name};
            serialHelper.writeLine(String.join(" ", data));

            serialHelper.sendFile(signature);
            serialHelper.sendFile(file);

            if (!serialHelper.status()) {
                return false;
            }

            return true;

        } else {
            return false;
        }
    }
}
