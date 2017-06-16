package ch.bfh.ti.project1.RPiHSM.GUI.stages;

import ch.bfh.ti.project1.RPiHSM.API.CreateKeySet;
import ch.bfh.ti.project1.RPiHSM.API.SerialHelperI;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.naming.OperationNotSupportedException;

/**
 * <h1>CreateKeySetCommandStage</h1>
 * An implementation of {@link AbstractStage}. This stage allows the user to create a new key set by using the {@link CreateKeySet} class.
 */
public class CreateKeySetCommandStage extends AbstractStage {
    private Button executeButton;
    private Label purposeLabel, nameLabel, algorithmLabel;
    private TextField nameTextField;
    private ComboBox<String> purposeComboBox, algorithmComboBox;

    /**
     * Generates all the graphical elements for the create key set command.
     *
     * @param serialHelper an instance of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI}
     * @param userPath     user home directory on the Raspberry Pi
     */
    public CreateKeySetCommandStage(SerialHelperI serialHelper, String userPath) {
        super(serialHelper, userPath);

        //creates the scene objects
        this.sceneTitle = new Label(b.getString("stage.title.create.keyset"));
        executeButton = new Button(b.getString("button.text.create.keyset"));
        purposeLabel = new Label(b.getString("text.purpose"));
        purposeComboBox = new ComboBox<>();
        algorithmLabel = new Label(b.getString("text.algorithm"));
        algorithmComboBox = new ComboBox<>();
        nameLabel = new Label(b.getString("text.name"));
        nameTextField = new TextField();
        algorithmComboBox.getItems().addAll(AES, RSA);
        algorithmComboBox.getSelectionModel().selectFirst();
        purposeComboBox.getItems().addAll(b.getString("text.crypt"), b.getString("text.sign"));
        purposeComboBox.getSelectionModel().selectFirst();

        //disables purpose and algorithm choices, if the key set already exists
        purposeComboBox.disableProperty().bind(executeButton.disableProperty());
        algorithmComboBox.disableProperty().bind(purposeComboBox.disableProperty());

        //disables the execute button if the length of the key set entered is smaller than 2 or if the key set does not exist
        executeButton.disableProperty().bind(nameTextField.textProperty().length().greaterThan(2).not());

        //On mouse clicked, ties to create a key set and displays the success or error messages
        executeButton.setOnMouseClicked(e1 -> {
            String algorithm;
            if (algorithmComboBox.getValue().equals(AES))
                algorithm = "-"; //AES does not need to be sent (is the default symmetric algorithm)
            else algorithm = algorithmComboBox.getValue();
			
			String purpose = purposeComboBox.getValue().equals(b.getString("text.crypt")) ? CRYPT : SIGN;

            CreateKeySet cks = new CreateKeySet(serialHelper, userPath, purpose, nameTextField.getText(), algorithm);
            try {
                if (cks.create()) {
                    clearElements();
                    success(b.getString("command.success.create.keyset"));
                } else {
                    error(b.getString("command.error.create.keyset"));
                }
            } catch (OperationNotSupportedException e) {
                error(b.getString("error.unsupported.operation"));
            }
        });

        //changes the values of the purpose on algorithm choices (dsa can only sign)
        algorithmComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.toString().equals(DSA)) purposeComboBox.getItems().setAll(b.getString("text.sign"));
            else purposeComboBox.getItems().setAll(b.getString("text.crypt"), b.getString("text.sign"));
        });

        //changes the values of the algorithm on purpose choices (dsa can only sign)
        purposeComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.toString().equals(b.getString("text.sign")))
                algorithmComboBox.getItems().setAll(AES, RSA, DSA);
            else algorithmComboBox.getItems().setAll(AES, RSA);
        });


        //places the objects in the Grid
        addHeader(true);
        grid.setGridLinesVisible(false);
        grid.add(nameLabel, 0, 2);
        grid.add(nameTextField, 1, 2);
        grid.add(algorithmLabel, 0, 3);
        grid.add(algorithmComboBox, 1, 3);
        grid.add(purposeLabel, 0, 4);
        grid.add(purposeComboBox, 1, 4);
        grid.add(executeButton, 1, 5);
    }

    /**
     * Resets the elements to their default values
     */
    private void clearElements() {
        nameTextField.clear();
        purposeComboBox.getSelectionModel().select(b.getString("text.crypt"));
        algorithmComboBox.getSelectionModel().select(AES);
    }
}
