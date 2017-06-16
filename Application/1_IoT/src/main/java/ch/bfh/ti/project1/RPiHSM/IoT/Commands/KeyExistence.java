package ch.bfh.ti.project1.RPiHSM.IoT.Commands;

import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;

import java.io.File;

/**
 * <h1>KeyExistence</h1>
 * Checks the existence of a key set by checking if the meta file exists.
 */
public class KeyExistence implements CommandI {

    private SerialHelperI serialHelper;
    private String keyPath;

    /**
     * Parses the received parameters.
     *
     * @param commands   parameters for the key existence check ([0] userPath, [1] name).
     * @param serialHelper an instance of {@link SerialHelperI}
     */
    public KeyExistence(String[] commands, SerialHelperI serialHelper) {
        keyPath = commands[0] + Constants.DIRECTORY + commands[1];
        this.serialHelper = serialHelper;
    }


    /**
     * Checks if a given key set exists by checking if the meta file exists.
     * If the key set exists the {@link SerialHelperI#ready()} is called otherwise {@link SerialHelperI#error()};
     */
    @Override
    public void execute() {
        File varTmpDir = new File(keyPath + "//meta");

        if (!varTmpDir.exists()) {
            serialHelper.error();
        }else {
            serialHelper.ready();
        }
    }
}
