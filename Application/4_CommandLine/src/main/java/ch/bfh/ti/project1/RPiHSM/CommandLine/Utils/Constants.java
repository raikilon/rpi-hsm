package ch.bfh.ti.project1.RPiHSM.CommandLine.Utils;

/**
 * <h1>Constants</h1>
 * Constants for the command line application.
 */
public final class Constants {
    public static final String NAME_COMPLETE_VALUE = "-name";
    public static final String PURPOSE_COMPLETE_VALUE = "-purpose";
    public static final String STATUS_COMPLETE_VALUE = "-status";
    public static final String STATUS_DEFAULT_VALUE = "primary";
    public static final String SIZE_COMPLETE_VALUE = "-size";
    public static final String FILE_COMPLETE_VALUE = "-file";
    public static final String COMMAND_COMPLETE_VALUE = "-command";
    public static final String VERSION_COMPLETE_VALUE = "-version";
    public static final String DESTINATION_COMPLETE_VALUE = "-destination";
    public static final String SIGNATURE_COMPLETE_VALUE = "-signature";
    public static final String NAME_SHORT_VALUE = "-n";
    public static final String PURPOSE_SHORT_VALUE = "-p";
    public static final String STATUS_SHORT_VALUE = "-s";
    public static final String FILE_SHORT_VALUE = "-f";
    public static final String COMMAND_SHORT_VALUE = "-c";
    public static final String VERSION_SHORT_VALUE = "-v";
    public static final String DESTINATION_SHORT_VALUE = "-d";
    public static final String SIGNATURE_SHORT_VALUE = "-s";
    public static final String DSA_COMPLETE_VALUE = "-dsa";
    public static final String RSA_COMPLETE_VALUE = "-rsa";
    public static final String NAME_VALUE = "Key Set name";
    public static final String SIZE_VALUE = "Key size";
    public static final String STATUS_VALUE = "Key status";
    public static final String FILE_DECRYPT_VALUE = "File path to decrypt";
    public static final String FILE_ENCRYPT_VALUE = "File path to encrypt";
    public static final String FILE_SIGN_VALUE = "File to sign path";
    public static final String FILE_VERIFY_VALUE = "File to verify path";
    public static final String COMMAND_VALUE = "Key status command";
    public static final String VERSION_VALUE = "Key version";
    public static final String DESTINATION_VALUE = "Key destination folder";
    public static final String SIGNATURE_VALUE = "Signature file path";
    public static final String PURPOSE_VALUE = "Key Set purpose (crypt|sign)";
    public static final String DSA_VALUE = "Use dsa for sign purpose";
    public static final String RSA_VALUE = "Use rsa for sign or crypt purpose";
    public static final String DEMOTE = "demote";
    public static final String REVOKE = "revoke";
    public static final String PROMOTE = "promote";
    public static final String CRYPT = "crypt";
    public static final String SIGN = "sign";
    public static final String PRIMARY = "primary";
    public static final String ACTIVE = "active";
    public static final String INACTIVE = "inactive";
    public static final String SET = "keyset";
    public static final String KEY = "key";
    public static final String ENCRYPT = "encrypt";
    public static final String DECRYPT = "decrypt";
    public static final String VERIFY = "verify";
    public static final String CHANGE = "change";
    public static final String PUB = "pub";
    public static final String HELP = "help";
    public static final String DELETE = "delete";
    public static final String DSA = "dsa";
    public static final String RSA = "rsa";
    public static final String ALGORITHM_DEFAULT_VALUE = "-";
    public static final String KEY_COMMAND = "key -name|-n KeySetName (-status|-s primary|active|inactive) (-size KeySize)";
    public static final String KEY_SET_COMMAND = "keyset -name|-n KeySetName -purpose|-p sign|crypt ((-dsa|-DSA)|(-rsa|-RSA))";
    public static final String DECRYPT_COMMAND = "decrypt -name|-n KeySetName -file|-f FilePath";
    public static final String DELETE_KEY_SET_COMMAND = "delete -n|-name KeySetName";
    public static final String ENCRYPT_COMMAND = "encrypt -name|-n KeySetName -file|-f FilePath";
    public static final String KEY_STATUS_COMMAND = "change -command|-c revoke|promote|demote -name|-n KeySetName -version|-v KeyVersion";
    public static final String PUBLIC_KEY_COMMAND = "pub -name|-n KeySetName -destination|-d PubKeyDestinationDirectory";
    public static final String SIGN_COMMAND = "sign -name|-n KeySetName -file|-f FilePath";
    public static final String VERIFY_COMMAND = "verify -name|-n KeySetName -file|-f FilePath -s|-signature SignatureFilePath";
    public static final String PARAMETERS_LIST = "List of others parameters";
}
