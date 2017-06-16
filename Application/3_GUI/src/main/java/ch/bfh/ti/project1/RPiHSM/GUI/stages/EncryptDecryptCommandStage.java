package ch.bfh.ti.project1.RPiHSM.GUI.stages;

import ch.bfh.ti.project1.RPiHSM.API.EncryptDecrypt;
import ch.bfh.ti.project1.RPiHSM.API.SerialHelperI;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * <h1>EncryptDecryptCommandStage</h1>
 * An implementation of {@link AbstractStage}. This stage allows the user to encrypt or decrypt a file by using the {@link EncryptDecrypt} class.
 */
public class EncryptDecryptCommandStage extends AbstractStage {
    private Button executeButton, fileChooserButton;
    private Label keySetLabel, fileLabel, fileNameLabel;
    private TextField keySetTextField;
    private FileChooser filechooser;
    private File file;

    /**
     * Generates all the graphical elements for the encrypt or decrypt command.
     *
     * @param serialHelper an instance of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI}
     * @param userPath     user home directory on the Raspberry Pi
     * @param encrypt      true to encrypt false to decrypt
     */
    public EncryptDecryptCommandStage(SerialHelperI serialHelper, String userPath, boolean encrypt) {
        super(serialHelper, userPath);

        //creates the scene objects
        if (encrypt) sceneTitle = new Label(b.getString("stage.title.encrypt"));
        else sceneTitle = new Label(b.getString("stage.title.decrypt"));
        keySetLabel = new Label(b.getString("text.keyset"));
        keySetTextField = new TextField();
        fileLabel = new Label(b.getString("text.file"));
        filechooser = new FileChooser();
        fileNameLabel = new Label();
        executeButton = new Button(sceneTitle.getText().contains(b.getString("stage.title.encrypt")) ? b.getString("button.text.encrypt") : b.getString("button.text.decrypt")); //Choices text for the execute button
        fileChooserButton = new Button(sceneTitle.getText().contains(b.getString("stage.title.encrypt")) ? b.getString("file.chooser.text.file.encrypt") : b.getString("file.chooser.text.file.decrypt")); //Choices text for the filechooser button

        //On mouse clicked checks, using the stage title,if the file needs to be encrypted or decrypted, then tries to perform the action and displays the success or error messages
        executeButton.setOnMouseClicked(e1 -> {
            if (file == null) { //checks if a file has been selected
                error(b.getString("file.chooser.text.file.not.chosen"));
            } else {
                EncryptDecrypt ed = new EncryptDecrypt(serialHelper, userPath, keySetTextField.getText(), file.getAbsolutePath());

                if (sceneTitle.getText().equals(b.getString("stage.title.encrypt"))) {
                    try { //begin encrypt
                        if (ed.encrypt()) {
                            clearElements();
                            success(b.getString("command.success.encrypt"));
                        } else {
                            error(b.getString("command.error.encrypt"));
                        }
                    } catch (OperationNotSupportedException e) {
                        error(b.getString("error.unsupported.operation"));
                    } catch (FileNotFoundException e) {
                        error(b.getString("error.file.not.found"));
                    }
                } else { //begin decrypt
                    try {
                        if (ed.decrypt()) {
                            clearElements();
                            success(b.getString("command.success.decrypt"));
                        } else {
                            error(b.getString("command.error.decrypt"));
                        }
                    } catch (OperationNotSupportedException e) {
                        error(b.getString("error.unsupported.operation"));
                    } catch (FileNotFoundException e) {
                        error(b.getString("error.file.not.found"));
                    }
                }
            }
        });

        //On mouse clicked, sets the file to encrypt/decrypt as the file selected with the file chooser
        fileChooserButton.setOnMouseClicked(e -> {
            try {
                file = filechooser.showOpenDialog(this);
                fileNameLabel.setText(file.getAbsolutePath());
                success(b.getString("file.chooser.text.file.chosen"));
            } catch (Exception e2) {
                fileNameLabel.setText(b.getString("file.chooser.text.file.not.chosen"));
            }
        });

        //disables the file chooser if the key set does not exist
        fileChooserButton.disableProperty().bind(messages.textProperty().isEqualTo(b.getString("command.success.keyset.exist")).not());
        //disables the execute button if no file has been selected using the file chooser
        executeButton.disableProperty().bind(messages.textProperty().isEqualTo(b.getString("file.chooser.text.file.chosen")).not());

        //on focus out checks if the key set exists
        keySetTextField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                this.checkKeyExistence(keySetTextField.getText());
            }
        });

        //places the objects in the Grid
        addHeader(true);
        grid.setGridLinesVisible(false);
        grid.add(keySetLabel, 0, 1);
        grid.add(keySetTextField, 1, 1);
        grid.add(fileLabel, 0, 2);
        grid.add(fileChooserButton, 1, 2);
        grid.add(fileNameLabel, 1, 3);
        grid.add(executeButton, 1, 4);
    }

    /**
     * Resets the elements to their default values
     */
    private void clearElements() {
        keySetTextField.clear();
        fileNameLabel.setText(null);
    }
}
