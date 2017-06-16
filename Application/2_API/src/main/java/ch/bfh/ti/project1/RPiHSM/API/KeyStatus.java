package ch.bfh.ti.project1.RPiHSM.API;

import javax.naming.OperationNotSupportedException;

/**
 * <h1>KeyStatus</h1>
 * Ask the IoT application using the {@link SerialHelperI} to promote, demote or revoke a key with the given parameters.
 */
public final class KeyStatus {

    private SerialHelperI serialHelper;
    private String userPath;
    private String name;
    private String version;

    /**
     * Sets {@link SerialHelperI} instance to communicate with the IoT application and key status operation parameters.
     *
     * @param serialHelper an instance of {@link SerialHelperI}
     * @param userPath     home path of the IoT application user
     * @param name         name of the key set
     * @param version      key version where execute the operation
     */
    public KeyStatus(SerialHelperI serialHelper, String userPath, String name, int version) {
        this.serialHelper = serialHelper;
        this.userPath = userPath;
        this.name = name;
        this.version = Integer.toString(version);
    }

    /**
     * Checks if the key set with the {@link CreateKey#name} exists and if yes, it ask to the IoT application to demote the given key version of the given key set.
     *
     * @return true if the key has been demoted, false otherwise
     * @throws OperationNotSupportedException if an error occurs in the {@link SerialHelperI}
     */
    public boolean demote() throws OperationNotSupportedException {
        String[] data = {Constants.DEMOTE, userPath, name, version};
        return execute(String.join(" ", data));
    }

    /**
     * Checks if the key set with the {@link CreateKey#name} exists and if yes, it ask to the IoT application to promote the given key version of the given key set.
     *
     * @return true if the key has been promoted, false otherwise
     * @throws OperationNotSupportedException if an error occurs in the {@link SerialHelperI}
     */
    public boolean promote() throws OperationNotSupportedException {
        String[] data = {Constants.PROMOTE, userPath, name, version};
        return execute(String.join(" ", data));
    }

    /**
     * Checks if the key set with the {@link CreateKey#name} exists and if yes, it ask to the IoT application to revoke the given key version of the given key set.
     *
     * @return true if the key has been revoked, false otherwise
     * @throws OperationNotSupportedException if an error occurs in the {@link SerialHelperI}
     */
    public boolean revoke() throws OperationNotSupportedException {
        String[] data = {Constants.REVOKE, userPath, name, version};
        return execute(String.join(" ", data));
    }

    /**
     * Called by the {@link KeyStatus#promote()} ()}, {@link KeyStatus#demote()} and {@link KeyStatus#revoke()} ()} methods. It check if the key set
     * with the {@link CreateKey#name} exists and if yes, it ask to the IoT application to promote, demote or revoke the given key version of the given key set.
     *
     * @param command data the array defined in {@link KeyStatus#demote()}, {@link KeyStatus#promote()} or {@link KeyStatus#revoke()} as string
     * @return true if the demotion, promotion or revoke have been successful, false otherwise
     * @throws OperationNotSupportedException if an error occurs in the {@link SerialHelperI}
     */
    private boolean execute(String command) throws OperationNotSupportedException {

        KeyExistence ke = new KeyExistence(serialHelper, userPath, name);

        if (ke.keyexist()) {
            serialHelper.writeLine(command);

            if (!serialHelper.status()) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }
}
