package ch.bfh.ti.project1.RPiHSM.IoT.Commands;

import org.jvnet.libpam.PAM;
import org.jvnet.libpam.PAMException;
import org.jvnet.libpam.UnixUser;

/**
 * <h1>Login</h1>
 * Check if the received username and password correspond to a OS user by using <a href="http://jpam.sourceforge.net/">JPAM</a>.
 */
public class Login implements CommandI {

    private SerialHelperI serialHelper;
    private String user;
    private String password;

    /**
     * Parses the received parameters.
     *
     * @param commands   parameters for user authentication ([0] user, [1] password)
     * @param serialHelper an instance of {@link SerialHelperI}
     */
    public Login(String[] commands, SerialHelperI serialHelper) {
        user = commands[0];
        password = commands[1];
        this.serialHelper = serialHelper;
    }


    /**
     * Tries to authenticate a user with the received parameters.
     * If the users is authenticated the {@link SerialHelperI#ready()} is called otherwise {@link SerialHelperI#error()}.
     */
    @Override
    public void execute() { //this part of code cannot be tested because it need a linux OS.

        try {
            UnixUser u = new PAM("sshd").authenticate(user, password);

            serialHelper.ready();

            serialHelper.writeLine(u.getDir()); // write the user base directory

        } catch (PAMException e) {
            serialHelper.error();
        }
    }

}