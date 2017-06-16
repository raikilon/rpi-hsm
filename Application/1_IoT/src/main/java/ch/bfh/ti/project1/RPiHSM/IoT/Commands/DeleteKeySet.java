package ch.bfh.ti.project1.RPiHSM.IoT.Commands;

import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 *<h1>DeleteKeySet</h1>
 * Delete a key set by deleting its directory.
 */
public class DeleteKeySet implements CommandI {


    private SerialHelperI serialHelper;
    private String keyPath;

    /**
     * Parses the received parameters.
     *
     * @param commands  parameters for the key set deletion ([0] userPath, [1] name)
     * @param serialHelper an instance of {@link SerialHelperI}
     */
    public DeleteKeySet(String[] commands, SerialHelperI serialHelper) {
        keyPath = commands[0] + Constants.DIRECTORY + commands[1];
        this.serialHelper = serialHelper;
    }


    /**
     * Delete the directory of the given key set.
     * If the key set do not exists or it cannot be delete the {@link SerialHelperI#error()} is called
     * otherwise {@link SerialHelperI#ready()}.
     */
    @Override
    public void execute() {
        File varTmpDir = new File(keyPath);

        if (varTmpDir.exists()) { // check if the key set exists otherwise it send back an error
            try {
                FileUtils.forceDelete(varTmpDir);
                serialHelper.ready();
            } catch (IOException e) { // will never occur the directory existence is already checked.
                serialHelper.error();
            }
        } else {
            serialHelper.error();
        }
    }
}
