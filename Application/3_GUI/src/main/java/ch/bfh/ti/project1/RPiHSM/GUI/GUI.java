package ch.bfh.ti.project1.RPiHSM.GUI;

import ch.bfh.ti.project1.RPiHSM.API.Exception.SerialPortException;
import ch.bfh.ti.project1.RPiHSM.API.SerialHelper;
import ch.bfh.ti.project1.RPiHSM.GUI.stages.ErrorStage;
import ch.bfh.ti.project1.RPiHSM.GUI.stages.LoginStage;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <h1>GUI</h1>
 * Graphical application that use the RPiHSM-API.
 *
 * @author Noli Manzoni, Sandro Tiago Carlao
 * @version 0.1
 * @since 13.04.2017
 */
public class GUI extends Application {
    /**
     * Start point of the GUI application.
     * An instance of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI} is created. If errors occurs, the error message is displayed.
     * If the creation of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI} is successful the {@link LoginStage} is opened.
     *
     * @param primaryStage the primary stage of the application
     */
    @Override
    public void start(Stage primaryStage) {
        SerialHelper serialHelper;
        ResourceBundle bundle = ResourceBundle.getBundle("language", Locale.getDefault(), new UTF8Control());
        try {
            serialHelper = new SerialHelper();
            new LoginStage(serialHelper); //creates the first stage
        } catch (PortInUseException e) {
            new ErrorStage(bundle.getString("error.port.in.use"));
        } catch (UnsupportedCommOperationException e) {
            new ErrorStage(bundle.getString("error.com.unsupported.operation"));
        } catch (SerialPortException e) {
            new ErrorStage(bundle.getString("error.port"));
        } catch (NullPointerException e) {
            new ErrorStage(bundle.getString("error.port.not.connected"));
        }
    }

    /**
     * Used to start the GUI by calling the {@link Application#launch(String...)}.
     *
     * @param args no arguments are need for this application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
