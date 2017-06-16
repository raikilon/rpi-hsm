package ch.bfh.ti.project1.RPiHSM.API;

import javax.naming.OperationNotSupportedException;

/**
 * <h1>DeleteKeySet</h1>
 * Ask the IoT application using the {@link SerialHelperI} to delete a key set with the given name.
 */
public final class DeleteKeySet {

    private SerialHelperI serialHelper;
    private String[] data;

    /**
     * Sets {@link SerialHelperI} instance to communicate with the IoT application and the delete key set parameters.
     *
     * @param serialHelper an instance of {@link SerialHelperI}
     * @param userPath     home path of the IoT application user
     * @param name         name of the key set
     */
    public DeleteKeySet(SerialHelperI serialHelper, String userPath, String name) {
        this.serialHelper = serialHelper;
        data = new String[]{Constants.DELETE, userPath, name};
    }

    /**
     * Ask to the IoT application to delete a key set with the given name.
     *
     * @return true if the key set has been deleted, false if the key set does not exist or if the deletion has failed
     * @throws OperationNotSupportedException iif an error occurs in the {@link SerialHelperI}
     */
    public boolean delete() throws OperationNotSupportedException {
        serialHelper.writeLine(String.join(" ", data));

        if (!serialHelper.status()) {
            return false;
        }
        return true;
    }
}
