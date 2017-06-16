package ch.bfh.ti.project1.RPiHSM.API;

import javax.naming.OperationNotSupportedException;

/**
 * <h1>CreateKeySet</h1>
 * Ask the IoT application using the {@link SerialHelperI} to create a new key set with the given parameters.
 */
public final class CreateKeySet {

    private SerialHelperI serialHelper;
    private String[] data;


    /**
     * Sets {@link SerialHelperI} instance to communicate with the IoT application and the new key set parameters.
     *
     * @param serialHelper instance of a {@link SerialHelperI}
     * @param userPath     home path of the IoT application user
     * @param purpose      new key set purpose
     * @param name         name of the new key set
     * @param algorithm    specific algorithm for the new key set. To have the default use a empty string
     *                     Prerequisites: the given purpose must be "crypt" or "sign". The given algorithm must be "dsa" or "rsa".
     *                     (the the "dsa" algorithm can be used only with the sign purpose).
     */
    public CreateKeySet(SerialHelperI serialHelper, String userPath, String purpose, String name, String algorithm) {
        this.serialHelper = serialHelper;
        data = new String[]{Constants.CREATEKEYSET, userPath, name, purpose, algorithm};
    }

    /**
     * Ask to the IoT application to create a new key set with the given parameters.
     *
     * @return true if the key set has been created, false if the key set already exist or if the creation has failed
     * @throws OperationNotSupportedException if an error occurs in the {@link SerialHelperI}
     */
    public boolean create() throws OperationNotSupportedException {
        serialHelper.writeLine(String.join(" ", data));

        if (!serialHelper.status()) {
            return false;
        }

        return true;
    }
}
