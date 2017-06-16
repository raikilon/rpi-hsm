package ch.bfh.ti.project1.RPiHSM.CommandLine.Command;

import ch.bfh.ti.project1.RPiHSM.API.Exception.KeySetIsEmptyException;
import ch.bfh.ti.project1.RPiHSM.API.Exception.KeySetNotAsymmetricException;
import ch.bfh.ti.project1.RPiHSM.API.PublicKey;
import ch.bfh.ti.project1.RPiHSM.API.SerialHelper;
import com.beust.jcommander.Parameter;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static ch.bfh.ti.project1.RPiHSM.CommandLine.Utils.Constants.*;


/**
 * <h1>PublicKeyCommand</h1>
 * Parses the command line parameters and use the {@link PublicKey} to generate the public keys of a given key set on the Raspberry Pi.
 */
public class PublicKeyCommand implements CommandI {

    private SerialHelper serialHelper;
    private ResourceBundle b;
    private String userPath;


    @Parameter(description = PARAMETERS_LIST)//JCommander requirements (all others parameters)
    private List<String> command = new ArrayList<>();

    @Parameter(names = {NAME_COMPLETE_VALUE, NAME_SHORT_VALUE}, description = NAME_VALUE, required = true)
    private String keySetName;

    @Parameter(names = {DESTINATION_COMPLETE_VALUE, DESTINATION_SHORT_VALUE}, description = DESTINATION_VALUE, required = true)
    private String destination;

    /**
     * Saves the userPath and  an instance of the {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI} for future use.
     *
     * @param userPath     user home directory on the Raspberry Pi
     * @param serialHelper an instance of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI}
     */
    public PublicKeyCommand(String userPath, SerialHelper serialHelper) {
        this.userPath = userPath;
        this.serialHelper = serialHelper;
        this.b = ResourceBundle.getBundle("language", Locale.getDefault());
    }

    /**
     * Generates the public keys of the received key set on the Raspberry Pi using {@link PublicKey}.
     *
     * @return a message that describes the result of the operation (error or success)
     */
    @Override
    public String execute() {
        File[] keysToDelete = (new File(destination)).listFiles(); //delete destination folder files
        for (int i = 0; i < keysToDelete.length; i++) {
            keysToDelete[i].delete();
        }

        PublicKey pk = new PublicKey(serialHelper, userPath, keySetName, destination);

        try {
            if (pk.generate()) {
                return b.getString("success.public.key");
            } else {
                return b.getString("error.public.key");
            }
        } catch (OperationNotSupportedException e) {
            return b.getString("error.unsupported.operation");
        } catch (KeySetIsEmptyException keySetIsEmpty) {
            return b.getString("error.keyset.is.empty");
        } catch (FileNotFoundException e) {
            return b.getString("error.file.not.found");
        } catch (KeySetNotAsymmetricException keySetNotAsymmetric) {
            return b.getString("error.keyset.not.asymmetric");
        }

    }

    /**
     * Returns a message that contains the right command syntax.
     *
     * @return right command syntax
     */
    @Override
    public String print() {
        return PUBLIC_KEY_COMMAND;
    }


}
