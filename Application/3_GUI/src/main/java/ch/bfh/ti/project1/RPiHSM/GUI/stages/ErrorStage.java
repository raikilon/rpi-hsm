package ch.bfh.ti.project1.RPiHSM.GUI.stages;

import javafx.scene.control.Label;

/**
 * <h1>ErrorStage</h1>
 * An implementation of {@link AbstractStage} that is shown when an error with the {@link ch.bfh.ti.project1.RPiHSM.API.SerialHelperI} occurs.
 */
public class ErrorStage extends AbstractStage {

    /**
     * Creates a {@link Label} and calls the {@link AbstractStage#error(String)}.
     *
     * @param message to display
     */
    public ErrorStage(String message) {
        super(null, ""); //empty userPath and null serial helper
        this.sceneTitle = new Label(b.getString("error.port"));
        error(message); //prints the message error
        addHeader(false);
    }
}
