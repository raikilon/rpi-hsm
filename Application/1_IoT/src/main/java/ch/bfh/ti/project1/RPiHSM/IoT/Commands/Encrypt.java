package ch.bfh.ti.project1.RPiHSM.IoT.Commands;

import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;
import org.keyczar.Encrypter;
import org.keyczar.exceptions.KeyczarException;

/**
 * <h1>Encrypt</h1>
 * Encrypt a file using the <a href="https://github.com/google/keyczar/wiki/OperationEncrypt">Keyczar Encrypt</a>.
 */
public class Encrypt implements CommandI {

    private SerialHelperI serialHelper;
    private String keyPath;

    /**
     * Parses the received parameters.
     *
     * @param commands parameters for the file encryption ([0] userPath, [1] name).
     * @param serialHelper an instance of {@link SerialHelperI}
     */
    public Encrypt(String[] commands, SerialHelperI serialHelper) {
        keyPath = commands[0] + Constants.DIRECTORY + commands[1];
        this.serialHelper = serialHelper;
    }


    /**
     * Tries to encrypt a file using the <a href="https://github.com/google/keyczar/wiki/OperationEncrypt">Keyczar Encrypt</a>.
     * Reads the file using {@link SerialHelperI#readFile()}, encrypts it using the primary key of the given
     * key set and sends the encrypted file back using {@link SerialHelperI#sendFile(byte[])}.
     * Prerequisites: the given key set must exist (to check it use {@link KeyExistence}) and it must been created with crypt purpose.
     */
    @Override
    public void execute() {

        byte[] plain = serialHelper.readFile();

        try {
            Encrypter encrypter = new Encrypter(keyPath);

            byte[] data = encrypter.encrypt(plain);

            serialHelper.ready();

            serialHelper.sendFile(data);
        } catch (KeyczarException e) {
            serialHelper.error(); //if rsa byte array must < 470
        }
    }
}