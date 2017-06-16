package ch.bfh.ti.project1.RPiHSM.CommandLine.Command;

import ch.bfh.ti.project1.RPiHSM.API.DeleteKeySet;
import ch.bfh.ti.project1.RPiHSM.API.SerialHelper;
import com.beust.jcommander.Parameter;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static ch.bfh.ti.project1.RPiHSM.CommandLine.Utils.Constants.*;

/**
 * <h1>DeleteKeySetCommand</h1>
 * Parses the command line parameters and use the {@link DeleteKeySet} to delete a key set on the Raspberry Pi.
 */
public class DeleteKeySetCommand implements CommandI {

    private SerialHelper serialHelper;
    private ResourceBundle b;
    private String userPath;


    @Parameter(description = PARAMETERS_LIST)//JCommander requirements (all others parameters)
    private List<String> command = new ArrayList<>();

    @Parameter(names = {NAME_COMPLETE_VALUE, NAME_SHORT_VALUE}, description = NAME_VALUE, required = true)
    private String keySetName;

    /**
     * Saves the userPath and  an instance of the {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI} for future use.
     *
     * @param userPath     user home directory on the Raspberry Pi
     * @param serialHelper an instance of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI}
     */
    public DeleteKeySetCommand(String userPath, SerialHelper serialHelper) {
        this.userPath = userPath;
        this.serialHelper = serialHelper;
        this.b = ResourceBundle.getBundle("language", Locale.getDefault());
    }

    /**
     * Deletes a key set on the Raspberry Pi using {@link DeleteKeySet}.
     *
     * @return a message that describes the result of the operation (error or success)
     */
    @Override
    public String execute() {
        DeleteKeySet dks = new DeleteKeySet(serialHelper, userPath, keySetName);
        try {
            if (dks.delete()) {
                return b.getString("success.delete.keyset");
            } else {
                return b.getString("error.delete.keyset");
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
        return DELETE_KEY_SET_COMMAND;
    }


}
