package ch.bfh.ti.project1.RPiHSM.GUI.stages;

import ch.bfh.ti.project1.RPiHSM.API.Login;
import ch.bfh.ti.project1.RPiHSM.API.SerialHelperI;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import javax.naming.OperationNotSupportedException;

/**
 * <h1>LoginStage</h1>
 * An implementation of {@link AbstractStage} that is shown when the application is opened. Uses the {@link Login} class to authenticate the user.
 */
public class LoginStage extends AbstractStage {

    private Text messageText;
    private Label userName, password;
    private TextField userTextField, passwordTextField;
    private Button loginButton;

    /**
     * Generates all the graphical elements needed for the login phase.
     *
     * @param serialHelper an instance of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI}
     */
    public LoginStage(SerialHelperI serialHelper) {
        super(serialHelper, "");//empty userPath

        //creates the scene objects
        this.sceneTitle = new Label(b.getString("stage.title.login"));
        loginButton = new Button(b.getString("button.text.sign.in"));
        userName = new Label(b.getString("text.username"));
        userTextField = new TextField("");
        password = new Label(b.getString("text.password"));
        passwordTextField = new PasswordField();
        messageText = new Text();

        //Event handler for "enter" key, ties to Login a user with the given credentials.
        //If the login succeeds, the main stage is displayed, otherwise an error message is displayed
        EventHandler<KeyEvent> enter = (ke -> {
            if (loginButton.isDisabled()) return;
            if (ke.getCode().equals(KeyCode.ENTER)) {
                if (checkLogin()) {
                    LoginStage.this.hide();
                    new MainStage(serialHelper, userPath);
                } else {
                    clearElements();
                    error(b.getString("error.authentication"));
                }
            }
        });

        //On enter key pressed event
        userTextField.setOnKeyPressed(enter);
        passwordTextField.setOnKeyPressed(enter);

        //On mouse clicked, ties to Login a user with the given credentials.
        //If the login succeeds, the main stage is displayed, otherwise an error message is displayed
        loginButton.setOnAction(e -> {
            if (checkLogin()) {
                LoginStage.this.hide();
                new MainStage(serialHelper, userPath);
            } else {
                error(b.getString("error.authentication"));
            }
        });

        loginButton.disableProperty().bind((userTextField.textProperty().length().greaterThan(1).and(passwordTextField.textProperty().length().greaterThan(1)).not()));

        //places the objects in the Grid
        addHeader(false);
        grid.add(userName, 0, 1);
        grid.add(password, 0, 2);
        grid.add(userTextField, 1, 1);
        grid.add(passwordTextField, 1, 2);
        grid.add(loginButton, 1, 4);
        GridPane.setHalignment(loginButton, HPos.RIGHT);
        grid.add(messageText, 1, 6);
    }

    /**
     * Resets the elements to their default values
     */
    private void clearElements() {
        passwordTextField.clear();
    }


    /**
     * Uses the {@link Login} to authenticate the given credentials.
     *
     * @return true if the user is authenticated false otherwise
     */
    private boolean checkLogin() {
        try {
            Login login = new Login(serialHelper, userTextField.getText(), passwordTextField.getText());
            if (login.checkCredentials()) { //checks if the credentials are right
                userPath = login.getUserPath();
                return true;
            } else {
                return false;
            }
        } catch (OperationNotSupportedException e) {
            return false;
        }
    }
}
