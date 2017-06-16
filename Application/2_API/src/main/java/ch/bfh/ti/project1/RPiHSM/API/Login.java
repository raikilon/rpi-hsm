package ch.bfh.ti.project1.RPiHSM.API;

import javax.naming.OperationNotSupportedException;

/**
 * <h1>KeyStatus</h1>
 * Ask the IoT application using the {@link SerialHelperI} to authenticate the given user.
 */
public final class Login {

    private String user;
    private String password;
    private String userPath;
    private SerialHelperI serialHelper;

    /**
     * Sets {@link SerialHelperI} instance to communicate with the IoT application and the user parameters.
     *
     * @param sh       an instance of {@link SerialHelperI}
     * @param user     user name
     * @param password user password
     */
    public Login(SerialHelperI sh, String user, String password) {
        this.serialHelper = sh;
        this.user = user;
        this.password = password;
    }

    /**
     * Ask to the IoT application to authenticate the given user and if the user is authenticated it ask for the user home directory on the IoT device.
     * The user path can be asked with the {@link Login#getUserPath()}.
     *
     * @return true if the user is authenticated, false if the user does not exist or it is not authenticated
     * @throws OperationNotSupportedException if an error occurs in the {@link SerialHelperI}
     */
    public boolean checkCredentials() throws OperationNotSupportedException {

        String[] data = {Constants.LOGIN, user, password};

        serialHelper.writeLine(String.join(" ", data));

        if (serialHelper.status()) {
            userPath = serialHelper.readLine();
            return true;
        }

        return false;
    }

    /**
     * @return Home directory of the authenticated user on the IoT device
     */
    public String getUserPath() {
        return userPath;
    }
}
