package ch.bfh.ti.project1.RPiHSM.IoT.Commands;


import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;
import org.keyczar.Crypter;
import org.keyczar.exceptions.KeyczarException;

/**
 * <h1>Decrypt</h1>
 * Decrypts a file using the <a href="https://github.com/google/keyczar/wiki/OperationDecrypt">Keyczar Decrypt</a>.
 */
public class Decrypt implements CommandI {

    private SerialHelperI serialHelper;
    private String keyPath;

    /**
     * Parses the received parameters.
     *
     * @param commands   parameters for the file decryption ([0] userPath, [1] name).
     * @param serialHelper an instance of {@link SerialHelperI}
     */
    public Decrypt(String[] commands, SerialHelperI serialHelper) {
        keyPath = commands[0] + Constants.DIRECTORY + commands[1];
        this.serialHelper = serialHelper;
    }



    /**
     * Tries to decrypt a file by using the <a href="https://github.com/google/keyczar/wiki/OperationDecrypt">Keyczar Decrypt</a>.
     * Reads the file using {@link SerialHelperI#readFile()}, decrypts it using the primary key of the given
     * key set and sends the decrypted file back using {@link SerialHelperI#sendFile(byte[])}.
     * Prerequisites: the given key set must exist (to check it use {@link KeyExistence}) and it must been created with crypt purpose.
     */
    @Override
    public void execute() {

        byte[] encrypt = serialHelper.readFile();

        try {
            Crypter crypter = new Crypter(keyPath);

            byte[] data = crypter.decrypt(encrypt);

            serialHelper.ready();

            serialHelper.sendFile(data);
        } catch (KeyczarException e) {
            serialHelper.error();
        }

    }

}
