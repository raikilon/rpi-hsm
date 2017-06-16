package ch.bfh.ti.project1.RPiHSM.CommandLine.Command;


import ch.bfh.ti.project1.RPiHSM.API.CreateKeySet;
import ch.bfh.ti.project1.RPiHSM.API.SerialHelper;
import ch.bfh.ti.project1.RPiHSM.CommandLine.Utils.PurposeValidator;
import com.beust.jcommander.Parameter;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static ch.bfh.ti.project1.RPiHSM.CommandLine.Utils.Constants.*;

/**
 * <h1>CreateKeySetCommand</h1>
 * Parses the command line parameters and use the {@link CreateKeySet} to create a new key set on the Raspberry Pi.
 */
public class CreateKeySetCommand implements CommandI {

    private SerialHelper serialHelper;
    private ResourceBundle b;
    private String userPath;


    @Parameter(description = PARAMETERS_LIST)//JCommander requirements (all others parameters)
    private List<String> command = new ArrayList<>();

    @Parameter(names = {NAME_COMPLETE_VALUE, NAME_SHORT_VALUE}, description = NAME_VALUE, required = true)
    private String keySetName;

    @Parameter(names = {PURPOSE_COMPLETE_VALUE, PURPOSE_SHORT_VALUE}, description = PURPOSE_VALUE, required = true, validateWith = PurposeValidator.class)
    private String purpose;

    @Parameter(names = {DSA_COMPLETE_VALUE}, description = DSA_VALUE)
    private Boolean dsa = false;

    @Parameter(names = {RSA_COMPLETE_VALUE}, description = RSA_VALUE)
    private Boolean rsa = false;

    /**
     * Saves the userPath and  an instance of the {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI} for future use.
     *
     * @param userPath     user home directory on the Raspberry Pi
     * @param serialHelper an instance of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI}
     */
    public CreateKeySetCommand(String userPath, SerialHelper serialHelper) {
        this.userPath = userPath;
        this.serialHelper = serialHelper;
        this.b = ResourceBundle.getBundle("language", Locale.getDefault());
    }


    /**
     * Creates a new key set with the received parameters on the RaspberryPi using {@link CreateKeySet}.
     *
     * @return a message that describes the result of the operation (error or success)
     */
    @Override
    public String execute() {
        //The RSA and dsa algorithm can be used together
        if (rsa && dsa) {
            return b.getString("error.dsa.rsa");
        }

        //The dsa can be use only if the purpose is to sign
        if (dsa && purpose.equals(CRYPT)) {
            return b.getString("error.dsa.only.sign");
        }

        String algorithm;
        //insert default value for the CreateKeySet class
        if (rsa) {
            algorithm = RSA;
        } else if (dsa) {
            algorithm = DSA;
        } else {
            algorithm = ALGORITHM_DEFAULT_VALUE;
        }

        CreateKeySet cks = new CreateKeySet(serialHelper, userPath, purpose, keySetName, algorithm);
        try {
            if (cks.create()) {
                return b.getString("success.create.keyset");
            } else {
                return b.getString("error.create.keyset");
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
        return KEY_SET_COMMAND;
    }
}
