package ch.bfh.ti.project1.RPiHSM.IoT.Commands;

import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;
import org.keyczar.KeyczarTool;

import java.io.File;

/**
 * <h1>CreateKey</h1>
 * Creates a new key by using the <a href="https://github.com/google/keyczar/wiki/KeyczarTool">KeyczarTool</a>.
 */
public class CreateKey implements CommandI {

    private SerialHelperI serialHelper;
    private String keyPath;
    private String status;
    private int size;

    /**
     * Parses the received parameters.
     *
     * @param commands     parameters for the key creation ([0] userPath, [1] name, [2] status, [3] size)
     * @param serialHelper an instance of {@link SerialHelperI}
     */
    public CreateKey(String[] commands, SerialHelperI serialHelper) {
        keyPath = commands[0] + Constants.DIRECTORY + commands[1];
        status = commands[2];
        size = Integer.parseInt(commands[3]);
        this.serialHelper = serialHelper;

    }



    /**
     * Creates a new key using the <a href="https://github.com/google/keyczar/wiki/KeyczarTool">KeyczarTool</a>.
     * If the key cannot be created an Exception is thrown and it cannot be catch.
     * To check if the command has been successful, the number of files in the key set directory are checked.
     * If the creation is successful the {@link SerialHelperI#ready()} is called otherwise {@link SerialHelperI#error()}.
     * Prerequisites: the given key set must exist (to check it use {@link KeyExistence}).
     */
    @Override
    public void execute() {

        //Check if key set already exists

        File varTmpDir = new File(keyPath + "//meta");

        long count = varTmpDir.getParentFile().list().length; // number of file before the key creation

        KeyczarTool.main(new String[]{"addkey", "--location=" + keyPath, "--status=" + status, (size != 0) ? "--size=" + size : ""}); // if there are errors an exception is thrown and cannot be catch (external application)

        if (varTmpDir.getParentFile().list().length <= count) {
            serialHelper.error(); //key set creation failed due to key size
        } else {
            serialHelper.ready();
        }

    }

}