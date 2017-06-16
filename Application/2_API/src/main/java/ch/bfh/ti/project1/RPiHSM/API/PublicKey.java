package ch.bfh.ti.project1.RPiHSM.API;

import ch.bfh.ti.project1.RPiHSM.API.Exception.KeySetIsEmptyException;
import ch.bfh.ti.project1.RPiHSM.API.Exception.KeySetNotAsymmetricException;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;

/**
 * <h1>PublicKey</h1>
 * Ask the IoT application using the {@link SerialHelperI} to generate the public key of the given key set.
 */
public final class PublicKey {

    private SerialHelperI serialHelper;
    private String userPath;
    private String name;
    private String destination;

    /**
     * Sets {@link SerialHelperI} instance to communicate with the IoT application and the public key generation parameters.
     *
     * @param serialHelper an instance of {@link SerialHelperI}
     * @param userPath     home path of the IoT application user
     * @param name         name of the key set
     * @param destination  absolute path where to save the public keys files
     */
    public PublicKey(SerialHelperI serialHelper, String userPath, String name, String destination) {
        this.serialHelper = serialHelper;
        this.destination = destination;
        this.userPath = userPath;
        this.name = name;
    }


    /**
     * Checks if the key set with the {@link CreateKey#name} exists and if yes, it ask to the IoT application to generate the public keys of the given key set.
     *
     * @return true if the public key generation has been successful, otherwise false.
     * @throws OperationNotSupportedException if an error occurs in the {@link SerialHelperI}
     * @throws KeySetNotAsymmetricException   if the given key set is not asymmetric
     * @throws KeySetIsEmptyException         if the given key set is empty (no keys)
     * @throws FileNotFoundException          if the destination path do not exists
     */
    public boolean generate() throws OperationNotSupportedException, KeySetNotAsymmetricException, KeySetIsEmptyException, FileNotFoundException {
        KeyExistence ke = new KeyExistence(serialHelper, userPath, name);

        if (ke.keyexist()) {

            String[] data = {Constants.PUBLICKEY, userPath, name};
            serialHelper.writeLine(String.join(" ", data));


            if (!serialHelper.status()) {
                throw new KeySetNotAsymmetricException();
            }

            if (!serialHelper.status()) {
                throw new KeySetIsEmptyException();
            }

            int files = Integer.parseInt(serialHelper.readLine()); //number of file to read
            String name;

            for (int i = 0; i < files; i++) {
                name = serialHelper.readLine();
                serialHelper.readFile(destination.concat("/" + name));
            }

            return true;

        } else {
            return false;
        }
    }
}
