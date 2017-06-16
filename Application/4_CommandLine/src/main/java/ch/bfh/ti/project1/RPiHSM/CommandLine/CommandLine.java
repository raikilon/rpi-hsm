package ch.bfh.ti.project1.RPiHSM.CommandLine;

import ch.bfh.ti.project1.RPiHSM.API.Exception.SerialPortException;
import ch.bfh.ti.project1.RPiHSM.API.Login;
import ch.bfh.ti.project1.RPiHSM.API.SerialHelper;
import ch.bfh.ti.project1.RPiHSM.CommandLine.Command.CommandFactory;
import ch.bfh.ti.project1.RPiHSM.CommandLine.Command.CommandI;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import javax.naming.OperationNotSupportedException;
import java.io.Console;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <h1>CommandLine</h1>
 * Command line application that use the RPiHSM-API.
 * To parse the command line parameters the <a href="http://jcommander.org">JCommander</a> framework is used.
 *
 * @author Noli Manzoni, Sandro Tiago Carlao
 * @version alpha 0.3
 * @since 29.03.2017
 */
public class CommandLine {

    /**
     * An instance of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI} is created. If errors occurs, the application is terminated.
     * The application ask for the user authentication and use the {@link Login} to authenticate the credentials.
     * If the user is authenticated a new {@link CommandI} instance is created thanks to the {@link CommandFactory}.
     * Then the JCommander framework parses the command parameters and if there are no errors the {@link CommandI} is executed.
     *
     * @param args the command parameters (to more information look at {@link CommandI#print()})
     */
    public static void main(String[] args) {


        SerialHelper serialHelper;
        ResourceBundle b = ResourceBundle.getBundle("language", Locale.getDefault());

        try {
            serialHelper = new SerialHelper();


            //asks user info
            Console console = System.console();
            String userName = console.readLine(b.getString("login.enter.username"));
            char password[] = console.readPassword(b.getString("login.enter.password"));

            Login login = new Login(serialHelper, userName, new String(password));//performs login

            if (login.checkCredentials()) { //if user is authenticated

                CommandFactory cmFactory = new CommandFactory();//factory
                CommandI cm = cmFactory.getCommand(args[0].trim().toLowerCase(), login.getUserPath(), serialHelper);

                //Create new parsing for command line arguments
                try {
                    new JCommander(cm, args);


                    String message = cm.execute();//Execute command


                    System.out.println(message);//print the returned message

                } catch (ParameterException e) { // if the JCommand find some syntax errors
                    System.out.println(b.getString("error.illegal.argument"));
                    System.out.println(cm.print());//print right syntax
                } finally {
                    try {
                        serialHelper.closeConnection(); //The serial connection is closed
                    } catch (SerialPortException e) {
                        System.out.println(b.getString("error.serial.port"));
                    }
                }
            } else {
                System.out.println(b.getString("error.login.credentials"));
            }

        } catch (PortInUseException e) {
            System.out.println(b.getString("error.serial.port.in.use"));
        } catch (UnsupportedCommOperationException e) {
            System.out.println(b.getString("error.unsupported.com.operation"));
        } catch (SerialPortException e) {
            System.out.println(b.getString("error.serial.port"));
        } catch (OperationNotSupportedException e) {
            System.out.println(b.getString("error.unsupported.operation"));
        } catch (NullPointerException e) {
            System.out.println(b.getString("error.port.not.connected"));
        } finally {
            System.exit(0); // System exist with status 0 -> all the errors are catch and printed to the user
        }

    }
}
