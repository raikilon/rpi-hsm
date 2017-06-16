package ch.bfh.ti.project1.RPiHSM.IoT.Commands;

import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;
import ch.bfh.ti.project1.RPiHSM.IoT.Utils.JSONUtils;
import org.apache.commons.io.FileUtils;
import org.keyczar.KeyczarTool;

import java.io.File;
import java.io.IOException;

/**
 * <h1>PublicKey</h1>
 * Exports all the public keys of a RSA or DSA key set by using the <a href="https://github.com/google/keyczar/wiki/KeyczarTool">KeyczarTool</a>.
 */
public class PublicKey implements CommandI {

    private SerialHelperI serialHelper;
    private String keyPath;

    /**
     * Parses the received parameters.
     *
     * @param commands parameters for the public keys exportation ([0] userPath, [1] name)
     * @param serialHelper an instance of {@link SerialHelperI}
     */
    public PublicKey(String[] commands, SerialHelperI serialHelper) {
        this.serialHelper = serialHelper;
        keyPath = commands[0] + Constants.DIRECTORY + commands[1];
    }



    /**
     * Exports all the public keys of the given key set using the <a href="https://github.com/google/keyczar/wiki/KeyczarTool">KeyczarTool</a>.
     * If the public key destination folder exists it is deleted than it is recreate (to be sure is empty).Then the
     *  <a href="https://github.com/google/keyczar/wiki/KeyczarTool">KeyczarTool</a> command is execute.
     * If there are errors the {@link SerialHelperI#error()} is called otherwise {@link SerialHelperI#ready()}. Once the keys are exported
     * the number of the keys is writed using {@link SerialHelperI#writeLine(String)} then the files are send one by one with the {@link SerialHelperI#sendFile(String)}.
     */
    @Override
    public void execute() {

        if (JSONUtils.checkAsymmetricSet(keyPath)) {// check if the key set is RSA or DSA
            //CLEAN FOLDER
            File pubkeys = new File(keyPath + "//pubkeys");
            if (pubkeys.exists()) {
                try {
                    FileUtils.deleteDirectory(pubkeys);
                } catch (IOException e) {
                    serialHelper.error(); //will never happen -> folder existence is already checked
                }
            }
            pubkeys.mkdirs();

            KeyczarTool.main(new String[]{"pubkey", "--location=" + keyPath, "--destination=" + pubkeys.getAbsolutePath()});


            serialHelper.ready();

            File[] files = pubkeys.listFiles(); // list of files

            if (files.length > 1) {//there will be always the meta file
                serialHelper.ready();

                serialHelper.writeLine(Integer.toString(files.length)); // send the files count

                for (int i = 0; i < files.length; i++) {
                    serialHelper.writeLine(files[i].getName());
                    serialHelper.sendFile(files[i].getAbsolutePath());
                }
            } else {
                serialHelper.error(); //no key are present
            }


        } else {
            serialHelper.error(); // key set is not RSA or DSA
        }

    }


}
