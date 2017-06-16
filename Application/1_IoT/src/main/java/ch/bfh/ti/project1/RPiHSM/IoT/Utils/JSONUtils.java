package ch.bfh.ti.project1.RPiHSM.IoT.Utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * <h1>JSONUtils</h1>
 * Helper that performs actions on JSON files.
 */
public class JSONUtils {

    /**
     * Check the status of the given key version by looking in the meta file of the given key set.
     *
     * @param keySetPath key set path
     * @param version    key version
     * @param status1    first status to check
     * @param status2    second status to check
     * @return true if the key has one of the given status otherwise false
     */
    public static boolean checkKeySetStatus(String keySetPath, int version, String status1, String status2) {

        Object obj = JSONUtils.readJSONFile(keySetPath);

        if (obj == null) return false;

        JSONObject jsonObject = (JSONObject) obj;

        JSONArray versionList = (JSONArray) jsonObject.get("versions");

        JSONObject versionObject;

        Iterator<JSONObject> it = versionList.iterator();
        while (it.hasNext()) {

            versionObject = it.next();

            //is a long and cannot be casted to a int ---- version is implicit converted in long without information loss
            if (version == (Long) versionObject.get("versionNumber")) {
                if (versionObject.get("status").equals(status1) || (versionObject.get("status").equals(status2))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if a key set is asymmetric (RSA or DSA) by checking in the meta file.
     *
     * @param keySetPath the key set path
     * @return true if the key set is asymmetric otherwise false
     */
    public static boolean checkAsymmetricSet(String keySetPath) {

        Object obj = JSONUtils.readJSONFile(keySetPath);

        if (obj == null) return false;

        JSONObject jsonObject = (JSONObject) obj;

        String type = (String) jsonObject.get("type");

        if (type.equals(Constants.RSA_PRIV) || type.equals(Constants.DSA_PRIV)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Read the given key set meta file and return a JSONObject.
     *
     * @param keySetPath the key set path
     * @return the meta file JSONObject
     */
    private static Object readJSONFile(String keySetPath) {

        File keySet = new File(keySetPath + "//meta");

        JSONParser parser = new JSONParser();

        try {
            FileReader fr = new FileReader(keySet);
            Object obj = parser.parse(fr);
            fr.close();
            return obj;
        } catch (ParseException | IOException e) {
            return null;
        }
    }
}
