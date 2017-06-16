package ch.bfh.ti.project1.RPiHSM.CommandLine.Command;

import ch.bfh.ti.project1.RPiHSM.API.KeyStatus;
import ch.bfh.ti.project1.RPiHSM.API.SerialHelper;
import ch.bfh.ti.project1.RPiHSM.CommandLine.Utils.Constants;
import ch.bfh.ti.project1.RPiHSM.CommandLine.Utils.StatusCommandValidator;
import com.beust.jcommander.Parameter;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static ch.bfh.ti.project1.RPiHSM.CommandLine.Utils.Constants.*;

/**
 * <h1>KeyStatusCommand</h1>
 * Parses the command line parameters and use the {@link KeyStatus} to modify a key status on the Raspberry Pi.
 */
public class KeyStatusCommand implements CommandI {


    private SerialHelper serialHelper;
    private ResourceBundle b;
    private String userPath;

    @Parameter(description = PARAMETERS_LIST)//JCommander requirements (all others parameters)
    private List<String> command = new ArrayList<>();

    @Parameter(names = {NAME_COMPLETE_VALUE, NAME_SHORT_VALUE}, description = NAME_VALUE, required = true)
    private String keySetName;

    @Parameter(names = {COMMAND_COMPLETE_VALUE, COMMAND_SHORT_VALUE}, description = COMMAND_VALUE, required = true, validateWith = StatusCommandValidator.class)
    private String status;

    @Parameter(names = {VERSION_COMPLETE_VALUE, VERSION_SHORT_VALUE}, description = VERSION_VALUE, required = true)
    private int version;

    /**
     * Saves the userPath and  an instance of the {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI} for future use.
     *
     * @param userPath     user home directory on the Raspberry Pi
     * @param serialHelper an instance of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI}
     */
    public KeyStatusCommand(String userPath, SerialHelper serialHelper) {
        this.userPath = userPath;
        this.serialHelper = serialHelper;
        this.b = ResourceBundle.getBundle("language", Locale.getDefault());
    }

    /**
     * Changes a key status with the received parameters on the Raspberry Pi using {@link KeyStatus}.
     *
     * @return a message that describes the result of the operation (error or success)
     */
    @Override
    public String execute() {

        KeyStatus ks = new KeyStatus(serialHelper, userPath, keySetName, version);
        try {
            if (status.equals(Constants.DEMOTE)) {
                if (ks.demote()) {
                    return b.getString("success.demote");
                } else {
                    return b.getString("error.demote");
                }
            } else if (status.equals(Constants.PROMOTE)) {
                if (ks.promote()) {
                    return b.getString("success.promote");
                } else {
                    return b.getString("error.promote");
                }
            } else {
                if (ks.revoke()) {
                    return b.getString("success.revoke");
                } else {
                    return b.getString("error.revoke");
                }
            }
        } catch (OperationNotSupportedException e) {
            return b.getString("error.unsupported.operation");
        }

    }

    /**
     * Returns a message that contains the right command syntax.
     *
     * @return right command syntax
     */
    @Override
    public String print() {
        return KEY_STATUS_COMMAND;
    }
}
