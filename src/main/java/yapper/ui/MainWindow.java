package yapper.ui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import yapper.chatbot.Yapper;
import yapper.commands.Command;
import yapper.data.exception.InvalidCommandSyntaxException;
import yapper.parser.CommandParser;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {

    private static final String USER_IMAGE = "/images/jesse.jpg";
    private static final String YAPPER_IMAGE = "/images/heisenberg.jpg";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Yapper yapper;

    private Image userImage = new Image(this.getClass().getResourceAsStream(USER_IMAGE));
    private Image yapperImage = new Image(this.getClass().getResourceAsStream(YAPPER_IMAGE));

    /**
     * Initializes the main window.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Yapper instance into the controller.
     *
     * @param y Yapper instance to be injected.
     */
    public void setYapper(Yapper y) {
        yapper = y;
    }


    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Yapper's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {

        String input = userInput.getText();
        ArrayList<String> responseList = new ArrayList<>(); // List of responses to be displayed to the user
        String fullResponseString = "";
        try {
            Command command = CommandParser.parse(input, yapper.getTaskList(), yapper.getFile());
            command.execute(responseList);
        } catch (InvalidCommandSyntaxException e) {
            responseList.add(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            responseList.add(e.getMessage());
        } catch (IllegalArgumentException e) {
            responseList.add(e.getMessage());
        }

        for (String response : responseList) {
            fullResponseString += response + "\n";
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getYapperDialog(fullResponseString, yapperImage));
        userInput.clear();
    }

    /**
     * Handles the error message.
     *
     * @param errorMessage Error message to be displayed.
     */
    public void handleError(String errorMessage) {
        dialogContainer.getChildren().addAll(
                DialogBox.getYapperDialog(errorMessage, yapperImage));
        userInput.clear();
    }
}
