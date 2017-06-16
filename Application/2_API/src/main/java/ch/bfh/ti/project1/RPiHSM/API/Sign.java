package ch.bfh.ti.project1.RPiHSM.API;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;

/**
 * <h1>Sign</h1>
 * Ask the IoT application using the {@link SerialHelperI} to sign a given file with a given key set.
 */
public final class Sign {

    private SerialHelperI serialHelper;
    private String userPath;
    private String name;
    private String file;

    /**
     * Sets {@link SerialHelperI} instance to communicate with the IoT application and the sign parameters.
     *
     * @param serialHelper an instance of {@link SerialHelperI}
     * @param userPath     home path of the IoT application user
     * @param name         name of the key set
     * @param file         absolute path of the file to sign
     *                     Prerequisite: The given key set must be created with sign purpose.
     */
    public Sign(SerialHelperI serialHelper, String userPath, String name, String file) {
        this.serialHelper = serialHelper;
        this.userPath = userPath;
        this.name = name;
        this.file = file;

    }

    /**
     * Checks if the key set with the {@link CreateKey#name} exists and if yes, it ask to the IoT application to sign the given file with a given key set.
     *
     * @return true if the sign has been successful, false if the key set do not exist or the sign process had some errors
     * @throws OperationNotSupportedException if an error occurs in the {@link SerialHelperI}
     * @throws FileNotFoundException          if the given file does not exist
     */
    public boolean sign() throws OperationNotSupportedException, FileNotFoundException {


        KeyExistence ke = new KeyExistence(serialHelper, userPath, name);

        if (ke.keyexist()) {

            String[] data = new String[]{Constants.SIGN, userPath, name};
            serialHelper.writeLine(String.join(" ", data));
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
