package ch.bfh.ti.project1.RPiHSM.IoT.Commands;

import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;
import org.keyczar.Signer;
import org.keyczar.exceptions.KeyczarException;

/**
 * <h1>Sign</h1>
 * Sign a file using the <a href="https://github.com/google/keyczar/wiki/OperationSign">Keyczar Sign</a>.
 */
public class Sign implements CommandI {

    private SerialHelperI serialHelper;
    private String keyPath;

    /**
     * Parses the received parameters.
     *
     * @param commands parameters for the file sign ([0] userPath, [1] name)
     * @param serialHelper an instance of {@link SerialHelperI}
     */
    public Sign(String[] commands, SerialHelperI serialHelper) {
        this.serialHelper = serialHelper;
        keyPath = commands[0] + Constants.DIRECTORY + commands[1];
    }



    /**
     * Tries to sign a file by using the <a href="https://github.com/google/keyczar/wiki/OperationSign">Keyczar Sign</a>.
     * Reads the file using {@link SerialHelperI#readFile()}, sign it using the primary key of the given key set
     * and sends the signed file back using {@link SerialHelperI#sendFile(byte[])}.
     *
     * Prerequisites: the given key set must exist (to check it use {@link KeyExistence}) and it must been created with sign purpose.
     */
    @Override
    public void execute() {

        byte[] file = serialHelper.readFile();

        try {
            Signer signer = new Signer(keyPath);
            byte[] data = signer.sign(file);

            serialHelper.ready();

            serialHelper.sendFile(data);
        } catch (KeyczarException e) {
            serialHelper.error();
        }


    }

}