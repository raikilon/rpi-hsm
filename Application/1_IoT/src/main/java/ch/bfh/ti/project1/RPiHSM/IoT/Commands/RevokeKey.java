package ch.bfh.ti.project1.RPiHSM.IoT.Commands;

import ch.bfh.ti.project1.RPiHSM.IoT.Utils.Constants;
import ch.bfh.ti.project1.RPiHSM.IoT.Utils.JSONUtils;
import org.keyczar.KeyczarTool;

/**
 * <h1>RevokeKey</h1>
 * Revokes a key by using the <a href="https://github.com/google/keyczar/wiki/KeyczarTool">KeyczarTool</a>.
 */
public class RevokeKey implements CommandI {

    private SerialHelperI serialHelper;
    private String keyPath;
    private int version;

    /**
     * Parses the received parameters.
     *
     * @param commands  parameters for the key deletion ([0] userPath, [1] name, [2] version)
     * @param serialHelper an instance of {@link SerialHelperI}
     */
    public RevokeKey(String[] commands, SerialHelperI serialHelper) {
        this.serialHelper = serialHelper;
        keyPath = commands[0] + Constants.DIRECTORY + commands[1];
        version = Integer.parseInt(commands[2]);
    }


    /**
     * Revokes a key in a given key set using the <a href="https://github.com/google/keyczar/wiki/KeyczarTool">KeyczarTool</a>.
     * If the status of the key is INACTIVE the {@link SerialHelperI#ready()} is called otherwise {@link SerialHelperI#error()}.
     * Prerequisites: the given key set and key version must exist.
     */
    @Override
    public void execute() {

        if (JSONUtils.checkKeySetStatus(keyPath, version, Constants.INACTIVE, Constants.INACTIVE)) {//inactive is passed two time because the method is designed for the promote key and the demote key
            KeyczarTool.main(new String[]{Constants.REVOKE, "--location=" + keyPath, "--version=" + version});
            serialHelper.ready();
        } else {
            serialHelper.error();
        }
    }
}