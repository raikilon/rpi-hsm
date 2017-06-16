package ch.bfh.ti.project1.RPiHSM.IoT.Commands;

import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;
import org.keyczar.KeyczarTool;

import java.io.File;

/**
 * <h1>CreateKeySet</h1>
 * Creates a new key set by using the <a href="https://github.com/google/keyczar/wiki/KeyczarTool">KeyczarTool</a>.
 */
public class CreateKeySet implements CommandI {

    private SerialHelperI serialHelper;
    private String keyPath;
    private String purpose;
    private String name;
    private String algorithm;

    /**
     * Parses the received parameters.
     *
     * @param commands  parameters for the key set creation  ([0] userPath, [1] name, [2] purpose, [3] algorithm)
     * @param serialHelper an instance of {@link SerialHelperI}
     */
    public CreateKeySet(String[] commands, SerialHelperI serialHelper) {
        keyPath = commands[0] + Constants.DIRECTORY + commands[1];
        name = commands[1];
        purpose = commands[2];
        algorithm = ((commands[3].equals("dsa")) ? "--asymmetric=dsa" : (commands[3].equals("rsa")) ? "--asymmetric=rsa" : "");
        this.serialHelper = serialHelper;
    }


    /**
     * Creates a new key set using the <a href="https://github.com/google/keyczar/wiki/KeyczarTool">KeyczarTool</a>.
     * If the key set cannot be created an Exception is thrown and it cannot be catch.
     * If the key set already exists the {@link SerialHelperI#error()} is called otherwise {@link SerialHelperI#ready()}.
     */
    @Override
    public void execute() {
        //Create a file with the given path
        File theDir = new File(keyPath);

        // If the directory does not exist, create it
        if (!theDir.exists()) {
            try {
                theDir.mkdir();


                KeyczarTool.main(new String[]{"create", "--location=" + keyPath, "--purpose=" + purpose, "--name=" + name, algorithm});

                serialHelper.ready();

            } catch (SecurityException e) { //cannot be tested a security exception occurs if you do not have the right to write in the destination folder.
                serialHelper.error();
            }

        } else {
            serialHelper.error();
        }
    }

}
