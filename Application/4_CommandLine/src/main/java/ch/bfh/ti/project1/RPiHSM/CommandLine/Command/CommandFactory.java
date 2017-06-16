package ch.bfh.ti.project1.RPiHSM.CommandLine.Command;

import ch.bfh.ti.project1.RPiHSM.API.SerialHelper;
import ch.bfh.ti.project1.RPiHSM.CommandLine.Utils.Constants;

/**
 * <h1>CommandFactory</h1>
 * Creates a {@link CommandI} instance.
 * This factory follows the Command Factory design pattern.
 */
public class CommandFactory {

    /**
     * Creates a {@link CommandI} instance by parsing the input value.
     *
     * @param command      command to execute (the {@link CommandI} instance)
     * @param userPath     authenticated user home directory.
     * @param serialHelper an instance of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI}
     * @return a {@link CommandI} instance
     */
    public CommandI getCommand(String command, String userPath, SerialHelper serialHelper) {
        if (command.equals(Constants.SET)) {
            return new CreateKeySetCommand(userPath, serialHelper);
        } else if (command.equals(Constants.KEY)) {
            return new CreateKeyCommand(userPath, serialHelper);
        } else if (command.equals(Constants.ENCRYPT)) {
            return new EncryptCommand(userPath, serialHelper);
        } else if (command.equals(Constants.DECRYPT)) {
            return new DecryptCommand(userPath, serialHelper);
        } else if (command.equals(Constants.SIGN)) {
            return new SignCommand(userPath, serialHelper);
        } else if (command.equals(Constants.VERIFY)) {
            return new VerifyCommand(userPath, serialHelper);
        } else if (command.equals(Constants.CHANGE)) {
            return new KeyStatusCommand(userPath, serialHelper);
        } else if (command.equals(Constants.PUB)) {
            return new PublicKeyCommand(userPath, serialHelper);
        } else if (command.equals(Constants.DELETE)) {
            return new DeleteKeySetCommand(userPath, serialHelper);
        } else if (command.equals(Constants.HELP)) {
            return new HelpCommand();
        } else {
            return new HelpCommand();
        }
    }
}
