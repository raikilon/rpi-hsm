package ch.bfh.ti.project1.RPiHSM.API;

import javax.naming.OperationNotSupportedException;

/**
 * <h1>CreateKey</h1>
 * Ask the IoT application using the {@link SerialHelperI} to create a new key with the given parameters.
 */
public final class CreateKey {

    private SerialHelperI serialHelper;
    private String userPath;
    private String name;
    private String status;
    private String size;


    /**
     * Sets {@link SerialHelperI} instance to communicate with the IoT application and the new key parameters.
     *
     * @param serialHelper an instance of {@link SerialHelperI}
     * @param userPath     home path of the IoT application user
     * @param name         name of the key set
     * @param status       status of the key.
     * @param size         size of the key. To have the default key size use 0 as value.
     *                     Prerequisites: the given size must be greater than 0 and pair. The given status must be "primary", "active or "inactive".
     */
    public CreateKey(SerialHelperI serialHelper, String userPath, String name, String status, int size) {
        assert size >= 0 && size % 2 == 0;
        this.serialHelper = serialHelper;
        this.userPath = userPath;
        this.name = name;
        this.status = status;
        this.size = Integer.toString(size);
    }

    /**
     * Checks if the key set with the {@link CreateKey#name} exists and if yes, it ask to the IoT application to create a key with the given parameters.
     *
     * @return true if the key has been created, false if the key set does not exist or if the creation has failed
     * @throws OperationNotSupportedException if an error occurs in the {@link SerialHelperI}
     */
    public boolean create() throws OperationNotSupportedException {

        KeyExistence ke = new KeyExistence(serialHelper, userPath, name);

        if (ke.keyexist()) {

            String[] data = {Constants.CREATEKEY, userPath, name, status, size};
            serialHelper.writeLine(String.join(" ", data));


            if (!serialHelper.status()) {
                return false;
            }

            return true;

        } else {
            return false;
        }
    }
}
