package ch.bfh.ti.project1.RPiHSM.GUI.stages;

import ch.bfh.ti.project1.RPiHSM.API.Exception.SerialPortException;
import ch.bfh.ti.project1.RPiHSM.API.KeyExistence;
import ch.bfh.ti.project1.RPiHSM.API.SerialHelperI;
import ch.bfh.ti.project1.RPiHSM.GUI.UTF8Control;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.naming.OperationNotSupportedException;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <h1>AbstractStage</h1>
 * The base stage of the application that have all the common elements.
 */
public abstract class AbstractStage extends Stage {

    protected static final double WIDTH = 1280;
    protected static final double HEIGHT = 720;
    protected static final int MAIN_MENU_BUTTON_SIZE = 300;
    protected static final int PADDING_SMALL = 10;
    protected static final int PADDING_MEDIUM = 25;
    protected static final int HEADER_HEIGHT = 100;
    protected static final int TITLE_FONT_SIZE = 20;
    protected static final double BACK_BUTTON_HEIGHT = 30;
    protected static final double BACK_BUTTON_WIDTH = 200;
    protected static final double LANGUAGE_BUTTON_WIDTH = 100;

    protected static final String TITLE = "RPiHSM GUI";
    protected static final String FONT = "Tahoma";
    protected static final String PANE_BACKGROUND = "-fx-background-color: #FFFFFF;";
    protected static final String HEADER_TITLE_COLOR = "#697D91";
    protected static final String HEADER_BACKGROUND = "-fx-background-color: #FAC300;";
    protected static final String SECTIONS_TITLES_STYLE = "-fx-font: 15 arial; -fx-text-fill: #697D91; -fx-font-weight: bold;";
    protected static final String BUTTONS_STYLE = "-fx-font: 15 arial; -fx-base: #697D91; -fx-pref-height: 40px; -fx-focus-color: transparent; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;";
    protected static final String LANGUAGE_BUTTON_STYLE = "-fx-font: 15 arial; -fx-base: #FFFFFF; -fx-pref-height: 40px; -fx-focus-color: transparent; -fx-font-weight: bold;";
    protected static final String CONTAINERS_STYLE = "-fx-border-color: black; -fx-border-width: 1; -fx-border-style: solid;";
    protected static final String HEADER_IMAGE_PATH = "/images/bfh_logo.png";
    protected static final String NUMBER_FIELD_PATTERN = "^([1-9]|[1-9][0-9])$";
    protected static final String KEY_SIZES_FIELD_PATTERN = "^[0-9]{1,4}$";

    protected static final String ACTIVE = "active";
    protected static final String INACTIVE = "inactive";
    protected static final String PRIMARY = "primary";
    protected static final String PROMOTE = "promote";
    protected static final String DEMOTE = "demote";
    protected static final String DSA = "dsa";
    protected static final String RSA = "rsa";
    protected static final String AES = "aes";
	protected static final String CRYPT = "crypt";
	protected static final String SIGN = "sign";

    private VBox pane;

    protected Label messages;
    protected GridPane grid;
    protected Label sceneTitle;
    protected String userPath;
    protected SerialHelperI serialHelper;
    protected ResourceBundle b;

    /**
     * Generates all the common elements of the stages.
     *
     * @param serialHelper an instance of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI}
     * @param userPath     user home directory on the Raspberry Pi
     */
    public AbstractStage(SerialHelperI serialHelper, String userPath) {
        super();

        this.userPath = userPath;
        this.serialHelper = serialHelper;
        this.b = ResourceBundle.getBundle("language", Locale.getDefault(), new UTF8Control());
        this.setTitle(TITLE); //Application title

        //Creates the Label for error and success messages
        messages = new Label();
        messages.setTextAlignment(TextAlignment.CENTER);
        messages.setPadding(new Insets(10));
        messages.prefWidth(WIDTH);

        //Creates the GridPane to place the objects on the stage
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(PADDING_SMALL);
        grid.setVgap(PADDING_SMALL);
        grid.setPadding(new Insets(PADDING_MEDIUM));


        //Creates the VBox
        pane = new VBox();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.getChildren().add(this.messages);
        pane.getChildren().add(grid);
        pane.setStyle(PANE_BACKGROUND);
        grid.setPrefWidth(WIDTH);

        //sets and shows the scene
        this.setScene(new Scene(pane, WIDTH, HEIGHT));
        this.show();

        this.setOnCloseRequest(e -> {
            if (serialHelper != null) {
                try {
                    serialHelper.closeConnection(); //closes the serial connection while closing the application
                } catch (SerialPortException e1) {
                    error(b.getString("error.com.unsupported.operation"));
                }
            }
        });
    }


    /**
     * Generates the header elements of the application.
     *
     * @param backToMenu true if a button to go back in the menu must be displayed otherwise false
     */
    protected void addHeader(boolean backToMenu) {
        sceneTitle.setFont(Font.font(FONT, FontWeight.NORMAL, TITLE_FONT_SIZE));
        sceneTitle.setTextFill(Paint.valueOf(HEADER_TITLE_COLOR));
        ImageView logo = new ImageView(this.getClass().getResource(HEADER_IMAGE_PATH).toExternalForm());

        //Creates the GridPane for the header structure
        GridPane header = new GridPane();
        header.setMinSize(WIDTH, HEADER_HEIGHT);
        header.setPadding(new Insets(15, 12, 15, 12));
        header.setStyle(HEADER_BACKGROUND);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        col1.setPercentWidth(25);
        col2.setPercentWidth(50);
        col3.setPercentWidth(23);

        header.add(logo, 0, 0);
        header.add(sceneTitle, 1, 0);
        header.getColumnConstraints().addAll(col1, col2, col3);

        GridPane.setHalignment(logo, HPos.LEFT);
        GridPane.setHalignment(sceneTitle, HPos.CENTER);

        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll(b.getString("constants.languages").split(","));
        languageComboBox.getSelectionModel().select(Locale.getDefault().getLanguage());
        languageComboBox.setStyle(LANGUAGE_BUTTON_STYLE);
        languageComboBox.setPrefSize(LANGUAGE_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);

        languageComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
        	System.setProperty("user.language", newValue);
            Locale.setDefault(Locale.forLanguageTag(newValue));
        });

        if (backToMenu) { //Creates the backToMenu button if needed
            Button menuButton = new Button(b.getString("button.text.back"));
            menuButton.setPrefSize(BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
            menuButton.setOnMouseClicked(e -> {
                this.hide();
                new MainStage(serialHelper, userPath);
            });
            menuButton.setStyle(BUTTONS_STYLE);
            header.add(new HBox(menuButton, languageComboBox), 2, 0);
            GridPane.setHalignment(menuButton, HPos.RIGHT);
        } else {
            header.add(languageComboBox, 2, 0);
        }

        pane.getChildren().add(0, header); //adds the header on first position (on top)
    }

    /**
     * Shows an error in the {@link AbstractStage#messages} element.
     *
     * @param message to display
     */
    protected void error(String message) {
        messages.setTextFill(Color.FIREBRICK);
        messages.setText(message); //prints the error message in Firebrick (red)
    }

    /**
     * Shows a success message in the {@link AbstractStage#messages} element.
     *
     * @param message to display
     */
    protected void success(String message) {
        messages.setTextFill(Color.GREEN);
        messages.setText(message); //prints the success message in green
    }

    /**
     * Checks if a given key set exist using the {@link KeyExistence} class.
     *
     * @param keySet name to check
     */
    protected void checkKeyExistence(String keySet) {
        KeyExistence ke = new KeyExistence(serialHelper, userPath, keySet);
        try {
            if (ke.keyexist()) success(b.getString("command.success.keyset.exist")); //checks if the key set exists
            else error(b.getString("command.error.keyset.not.exist"));
        } catch (OperationNotSupportedException e) {
            error("error.unsupported.operation");
        }
    }


}
