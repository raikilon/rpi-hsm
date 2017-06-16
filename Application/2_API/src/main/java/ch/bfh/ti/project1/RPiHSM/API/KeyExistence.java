package ch.bfh.ti.project1.RPiHSM.API;

import javax.naming.OperationNotSupportedException;

/**
 * <h1>KeyExistence</h1>
 * Ask the IoT application using the {@link SerialHelperI} to check fi a key set with the given name exists.
 */
public final class KeyExistence {

    private SerialHelperI serialHelper;
    private String userPath;
    private String name;

    /**
     * Sets {@link SerialHelperI} instance to communicate with the IoT application and the key existence parameters.
     *
     * @param serialHelper an instance of {@link SerialHelperI}
     * @param userPath     home path of the IoT application user
     * @param name         name of the key set
     */
    public KeyExistence(SerialHelperI serialHelper, String userPath, String name) {
        this.serialHelper = serialHelper;
        this.name = name;
        this.userPath = userPath;
    }

    /**
     * Ask to the IoT application if the key set with the {@link CreateKey#name} exists.
     *
     * @return true if the key set exists, false if the {@link KeyExistence#name} is empty or the key set does not exist
     * @throws OperationNotSupportedException if an error occurs in the {@link SerialHelperI}
     */
    public boolean keyexist() throws OperationNotSupportedException {
        if(name.isEmpty()) return false;
        serialHelper.writeLine(String.join(" ", new String[]{Constants.CHECK, userPath, name}));

        if (!serialHelper.status()) {
            return false;
        }
        return true;
    }

}
