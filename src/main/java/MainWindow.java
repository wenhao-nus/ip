import aeolian.Aeolian;
import aeolian.Parser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Aeolian aeolian;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/UserProfile.png"));
    private Image aeolianImage = new Image(this.getClass().getResourceAsStream("/images/AeolianProfile.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Aeolian instance */
    public void setAeolian(Aeolian a) {
        aeolian = a;
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(aeolian.getGreetings(), aeolianImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Aeolian's reply
     * and then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = aeolian.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, aeolianImage)
        );
        userInput.clear();

        if (Parser.isByeCommand(input)) {
            Platform.exit();
        }
    }
}
