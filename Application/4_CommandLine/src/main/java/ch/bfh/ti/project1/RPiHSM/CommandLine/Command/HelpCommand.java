package ch.bfh.ti.project1.RPiHSM.CommandLine.Command;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

import static ch.bfh.ti.project1.RPiHSM.CommandLine.Utils.Constants.*;

/**
 * <h1>HelpCommand</h1>
 * Prints all the possible command for this command line application.
 */
public class HelpCommand implements CommandI {

    @Parameter(description = PARAMETERS_LIST)//JCommander requirements (all others parameters)
    private List<String> command = new ArrayList<>();

    /**
     * Creates a message with all possible command line application commands.
     *
     * @return all possible command line application commands
     */
    @Override
    public String execute() {
        String newLine = System.getProperty("line.separator");
        return "The following commands are supported:" + newLine +
                KEY_COMMAND + newLine +
                KEY_SET_COMMAND + newLine +
                DECRYPT_COMMAND + newLine +
                DELETE_KEY_SET_COMMAND + newLine +
                ENCRYPT_COMMAND + newLine +
                KEY_STATUS_COMMAND + newLine +
                PUBLIC_KEY_COMMAND + newLine +
                SIGN_COMMAND + newLine +
                VERIFY_COMMAND;
    }

    /**
     * Do nothing - this method cannot be called because no error can occur
     *
     * @return empty string
     */
    @Override
    public String print() {
        return "";
    }//no errors can occur to call this method
}
