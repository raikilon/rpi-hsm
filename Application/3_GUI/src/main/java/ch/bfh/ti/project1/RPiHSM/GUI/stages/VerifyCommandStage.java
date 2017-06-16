package ch.bfh.ti.project1.RPiHSM.GUI.stages;


import ch.bfh.ti.project1.RPiHSM.API.SerialHelperI;
import ch.bfh.ti.project1.RPiHSM.API.Verify;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * <h1>VerifyCommandStage</h1>
 * An implementation of {@link AbstractStage}. This stage allows the user to verify a signature by using the {@link Verify} class.
 */
public class VerifyCommandStage extends AbstractStage {
    private Button executeButton, signFileChooserButton, verifyFileChooserButton;
    private Label keySetLabel, signFileLabel, verifyFileLabel, signatureFilePathLabel, verifyFilePathLabel;
    private TextField keySetTextField;
    private FileChooser filechooser;
    private File signFile, verifyFile;

    /**
     * Generates all the graphical elements for the signature verification command.
     *
     * @param serialHelper an instance of {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI}
     * @param userPath     user home directory on the Raspberry Pi
     */
    public VerifyCommandStage(SerialHelperI serialHelper, String userPath) {
        super(serialHelper, userPath);

        //creates the scene objects
        this.sceneTitle = new Label(b.getString("stage.title.verify"));
        executeButton = new Button(b.getString("button.text.verify"));
        signFileChooserButton = new Button(b.getString("file.chooser.text.file.signature"));
        verifyFileChooserButton = new Button(b.getString("file.chooser.text.file.verify"));
        keySetLabel = new Label(b.getString("text.keyset"));
        keySetTextField = new TextField();
        signFileLabel = new Label(b.getString("text.sign.file"));
        verifyFileLabel = new Label(b.getString("text.verify.file"));
        filechooser = new FileChooser();
        signatureFilePathLabel = new Label();
        verifyFilePathLabel = new Label();

        //disables the sign file chooser if the key set does not exist
        signFileChooserButton.disableProperty().bind(messages.textProperty().isEqualTo(b.getString("command.success.keyset.exist")).not());
        //disables the verify file chooser if the signature file has not been selected
        verifyFileChooserButton.disableProperty().bind(messages.textProperty().isEqualTo(b.getString("file.chooser.text.signature.chosen")).not());
        //disables the execute button if the verify file has not been selected
        executeButton.disableProperty().bind(messages.textProperty().isEqualTo(b.getString("file.chooser.text.file.and.signature.chosen")).not());

        //On mouse clicked, sets the signature file as the file selected with the filechooser
        signFileChooserButton.setOnMouseClicked(e -> {
            try {
                signFile = filechooser.showOpenDialog(this);
                signatureFilePathLabel.setText(signFile.getAbsolutePath());
                if (messages.getText().equals(b.getString("file.chooser.text.file.chosen")))
                    success(b.getString("file.chooser.text.file.and.signature.chosen"));
                else success(b.getString("file.chooser.text.signature.chosen"));
            } catch (Exception e2) {
                signatureFilePathLabel.setText(b.getString("file.chooser.text.file.not.chosen"));
            }
        });

        //On mouse clicked, sets the verify file as the file selected with the filechooser
        verifyFileChooserButton.setOnMouseClicked(e -> {
            try {
                verifyFile = filechooser.showOpenDialog(this);
                verifyFilePathLabel.setText(verifyFile.getAbsolutePath());
                if (messages.getText().equals(b.getString("file.chooser.text.signature.chosen")))
                    success(b.getString("file.chooser.text.file.and.signature.chosen"));
                else success(b.getString("file.chooser.text.file.chosen"));
            } catch (Exception e2) {
                verifyFilePathLabel.setText(b.getString("file.chooser.text.file.not.chosen"));
            }
        });

        //On mouse click, tries to send booth files to the RPiHSM to verify the signature. Then displays the success or error messages
        executeButton.setOnMouseClicked(e -> {
            Verify v = new Verify(serialHelper, userPath, keySetTextField.getText(), verifyFile.getAbsolutePath(), signFile.getAbsolutePath());
            try {
                if (v.verify()) {
                    clearElements();
                    success(b.getString("command.success.verify"));
                } else {
                    error(b.getString("command.error.verify"));
                }
            } catch (OperationNotSupportedException e1) {
                error(b.getString("error.unsupported.operation"));
            } catch (FileNotFoundException e1) {
                error(b.getString("error.file.not.found"));
            }
        });

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
        grid.add(signFileLabel, 0, 2);
        grid.add(signFileChooserButton, 1, 2);
        grid.add(signatureFilePathLabel, 1, 3);
        grid.add(verifyFileLabel, 0, 4);
        grid.add(verifyFileChooserButton, 1, 4);
        grid.add(verifyFilePathLabel, 1, 5);
        grid.add(executeButton, 1, 6);
    }

    /**
     * Resets the elements to their default values
     */
    private void clearElements() {
        keySetTextField.clear();
        signatureFilePathLabel.setText(null);
        verifyFilePathLabel.setText(null);
    }
}
