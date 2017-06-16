package ch.bfh.ti.project1.RPiHSM.IoT.Commands;


import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;

import java.util.Arrays;

/**
 * <h1>CommandFactory</h1>
 * Creates a {@link CommandI} instance.
 * This factory follows the Command Factory design pattern.
 */
public class CommandFactory {


    /**
     * Create a {@link CommandI} instance by parsing the input value.
     *
     * @param commands     command parameters
     * @param serialHelper an instance of {@link SerialHelperI}
     * @return a {@link CommandI} instance
     */
    public CommandI getCommand(String[] commands, SerialHelperI serialHelper) {

        String command = commands[0].trim(); // parameter to decide which ICommand is return

        String[] parameters = Arrays.copyOfRange(commands, 1, commands.length); //deleting first element

        if (command.equals(Constants.CREATEKEYSET)) {
            return new CreateKeySet(parameters, serialHelper);
        } else if (command.equals(Constants.CREATEKEY)) {
            return new CreateKey(parameters, serialHelper);
        } else if (command.equals(Constants.LOGIN)) {
            return new Login(parameters, serialHelper);
        } else if (command.equals(Constants.CHECK)) {
            return new KeyExistence(parameters, serialHelper);
        } else if (command.equals(Constants.DECRYPT)) {
            return new Decrypt(parameters, serialHelper);
        } else if (command.equals(Constants.ENCRYPT)) {
            return new Encrypt(parameters, serialHelper);
        } else if (command.equals(Constants.SIGN)) {
            return new Sign(parameters, serialHelper);
        } else if (command.equals(Constants.VERIFY)) {
            return new Verify(parameters, serialHelper);
        } else if (command.equals(Constants.REVOKE)) {
            return new RevokeKey(parameters, serialHelper);
        } else if (command.equals(Constants.DEMOTE)) {
            return new DemoteKey(parameters, serialHelper);
        } else if (command.equals(Constants.PROMOTE)) {
            return new PromoteKey(parameters, serialHelper);
        } else if (command.equals(Constants.PUBLICKEY)) {
            return new PublicKey(parameters, serialHelper);
        } else if (command.equals(Constants.DELETE)) {
            return new DeleteKeySet(parameters, serialHelper);
        } else {
            return null; //this must not happen
        }
    }
}
