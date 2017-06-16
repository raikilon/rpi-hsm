package ch.bfh.ti.project1.RPiHSM.CommandLine.Command;

import ch.bfh.ti.project1.RPiHSM.API.SerialHelper;
import ch.bfh.ti.project1.RPiHSM.API.Verify;
import com.beust.jcommander.Parameter;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static ch.bfh.ti.project1.RPiHSM.CommandLine.Utils.Constants.*;

/**
 * <h1>VerifyCommand</h1>
 * Parses the command line parameters and use the {@link Verify} to verify a signature on the Raspberry Pi.
 */
public class VerifyCommand implements CommandI {

    private SerialHelper serialHelper;
    private ResourceBundle b;
    private String userPath;

    @Parameter(description = PARAMETERS_LIST)//JCommander requirements (all others parameters)
    private List<String> command = new ArrayList<>();

    @Parameter(names = {NAME_COMPLETE_VALUE, NAME_SHORT_VALUE}, description = NAME_VALUE, required = true)
    private String keySetName;

    @Parameter(names = {FILE_COMPLETE_VALUE, FILE_SHORT_VALUE}, description = FILE_VERIFY_VALUE, required = true)
    private String filePath;

    @Parameter(names = {SIGNATURE_COMPLETE_VALUE, SIGNATURE_SHORT_VALUE}, description = SIGNATURE_VALUE, required = true)
    private String signaturePath;

    /**
     * Saves the userPath and  an instance of the {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI} for future use.
     *
     * @param userPath     user home directory on the Raspberry Pi
     * @param serialHelper an instance of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI}
     */
    public VerifyCommand(String userPath, SerialHelper serialHelper) {
        this.userPath = userPath;
        this.serialHelper = serialHelper;
        this.b = ResourceBundle.getBundle("language", Locale.getDefault());
    }


    /**
     * Verifies a signature with the received parameters on the Raspberry Pi using {@link Verify}.
     *
     * @return a message that describes the result of the operation (error or success)
     */
    @Override
    public String execute() {
        Verify v = new Verify(serialHelper, userPath, keySetName, filePath, signaturePath);

        try {
            if (v.verify()) {
                return b.getString("success.verify");
            } else {
                return b.getString("error.verify");
            }
        } catch (OperationNotSupportedException e) {
            return b.getString("error.unsupported.operation");
        } catch (FileNotFoundException e) {
            return b.getString("error.file.not.found");
        }
    }


    /**
     * Returns a message that contains the right command syntax.
     *
     * @return right command syntax
     */
    @Override
    public String print() {
        return VERIFY_COMMAND;
    }
}
