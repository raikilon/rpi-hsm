package ch.bfh.ti.project1.RPiHSM.CommandLine.Command;

import ch.bfh.ti.project1.RPiHSM.API.CreateKey;
import ch.bfh.ti.project1.RPiHSM.API.SerialHelper;
import ch.bfh.ti.project1.RPiHSM.CommandLine.Utils.KeySizeValidator;
import ch.bfh.ti.project1.RPiHSM.CommandLine.Utils.StatusValidator;
import com.beust.jcommander.Parameter;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static ch.bfh.ti.project1.RPiHSM.CommandLine.Utils.Constants.*;


/**
 * <h1>CreateKeyCommand</h1>
 * Parses the command line parameters and use the {@link CreateKey} to create a new key on the Raspberry Pi.
 */
public class CreateKeyCommand implements CommandI {

    private SerialHelper serialHelper;
    private ResourceBundle b;
    private String userPath;


    @Parameter(description = PARAMETERS_LIST)//JCommander requirements (all others parameters)
    private List<String> command = new ArrayList<>();

    @Parameter(names = {NAME_COMPLETE_VALUE, NAME_SHORT_VALUE}, description = NAME_VALUE, required = true)
    private String keySetName; //Key set name where store the new key

    @Parameter(names = {STATUS_COMPLETE_VALUE, STATUS_SHORT_VALUE}, description = STATUS_VALUE, validateWith = StatusValidator.class)
    //validate that the string is primary, active or inactive
    private String status = STATUS_DEFAULT_VALUE;

    @Parameter(names = {SIZE_COMPLETE_VALUE}, description = SIZE_VALUE, validateWith = KeySizeValidator.class)
    private int size = 0; //Default value

    /**
     * Saves the userPath and  an instance of the {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI} for future use.
     *
     * @param userPath     user home directory on the Raspberry Pi
     * @param serialHelper an instance of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI}
     */
    public CreateKeyCommand(String userPath, SerialHelper serialHelper) {
        this.userPath = userPath;
        this.serialHelper = serialHelper;
        this.b = ResourceBundle.getBundle("language", Locale.getDefault());
    }


    /**
     * Creates a new key with the received parameters on the Raspberry Pi using {@link CreateKey}.
     *
     * @return a message that describes the result of the operation (error or success)
     */
    @Override
    public String execute() {

        CreateKey ck = new CreateKey(serialHelper, userPath, keySetName, status, size);

        try {
            if (ck.create()) {
                return b.getString("success.create.key");
            } else {
                return b.getString("error.create.key");
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
        return KEY_COMMAND;
    }


}
