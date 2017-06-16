package ch.bfh.ti.project1.RPiHSM.API;

/**
 * <h1>Constants</h1>
 * Constants for the API application.
 * No access level so that the class variables can only be used in this package.
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html"> Controlling Access to Members of a Class </a>
 */
public final class Constants {
    final static String NAME = "RPiHSM-API";
    final static int TIME_TO_WAIT = 2000;
    final static int CONNECTION_SPEED = 115200;
    final static String SUCCESS = "200";
    final static String LOGIN = "login";
    final static String CREATEKEYSET = "keyset";
    final static String CREATEKEY = "key";
    final static String ENCRYPT = "encrypt";
    final static String DECRYPT = "decrypt";
    final static String PUBLICKEY = "pub";
    final static String DEMOTE = "demote";
    final static String PROMOTE = "promote";
    final static String REVOKE = "revoke";
    final static String SIGN = "sign";
    final static String CHECK = "check";
    final static String VERIFY = "verify";
    final static String DELETE = "remove";
}
