package ch.bfh.ti.project1.RPiHSM.IoT.Commands;

import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;
import org.keyczar.Signer;
import org.keyczar.exceptions.KeyczarException;

/**
 * <h1>Verify</h1>
 * Verify a file using the <a href="https://github.com/google/keyczar/wiki/OperationVerify">Keyczar Verify</a>.
 */
public class Verify implements CommandI {

    private SerialHelperI serialHelper;
    private String keyPath;

    /**
     * Parses the received parameters.
     *
     * @param commands parameters for the file verification ([0] userPath, [1] name)
     * @param serialHelper an instance of {@link SerialHelperI}
     */
    public Verify(String[] commands, SerialHelperI serialHelper) {
        this.serialHelper = serialHelper;
        keyPath = commands[0] + Constants.DIRECTORY + commands[1];
    }



    /**
     * Tries to verify a file by using the <a href="https://github.com/google/keyczar/wiki/OperationVerify">Keyczar Verify</a>.
     * Reads two file using {@link SerialHelperI#readFile()} (sign, file), verify it using the received sign
     * and if it is successful {@link SerialHelperI#ready()} is called otherwise {@link SerialHelperI#error()}.
     *
     * Prerequisites: the given key set must exist (to check it use {@link KeyExistence}) and it must been created with sign purpose.
     */
    @Override
    public void execute() {

        byte[] signature = serialHelper.readFile();

        byte[] file = serialHelper.readFile();

        try {
            Signer signer = new Signer(keyPath);

            if (signer.verify(file, signature)) {
                serialHelper.ready();
            } else {
                serialHelper.error();
            }
        } catch (KeyczarException e) {
            serialHelper.error();
        }

    }


}
