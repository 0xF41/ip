package yapper.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // Images for the user and Yapper
    private static final String USER_IMAGE = "/images/jesse.jpg";
    private static final String YAPPER_IMAGE = "/images/heisenberg.jpg";

    // Assert messages
    private static final String ASSERT_RESPONSE_LIST_EMPTY = "Response list should not be empty.";

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
     * Sets the Yapper object.
     *
     * @param y Yapper object to be set.
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
        try {
            Command command = CommandParser.parse(
                    input,
                    yapper.getTaskList(),
                    yapper.getNoteList(),
                    yapper.getTaskFile(),
                    yapper.getNoteFile(),
                    yapper.getTaskFileManager(),
                    yapper.getNoteFileManager());

            command.execute(responseList);

        } catch (InvalidCommandSyntaxException | IndexOutOfBoundsException | IllegalArgumentException e) {
            this.addResponses(responseList, e.getMessage());

        }

        assert responseList.size() > 0 : ASSERT_RESPONSE_LIST_EMPTY;

        this.displayResponses(input, responseList.toArray(new String[0]));

        userInput.clear();
    }


    /**
     * Adds multiple responses to the response list using varargs.
     *
     * @param responseList The list to store responses.
     * @param messages     The messages to be added.
     */
    private void addResponses(List<String> responseList, String... messages) {
        responseList.addAll(Arrays.asList(messages));
    }


    /**
     * Displays the user input and Yapper's response in the dialog container.
     *
     * @param input     The user's input.
     * @param responses The responses to be displayed.
     */
    private void displayResponses(String input, String... responses) {
        String fullResponseString = String.join("\n", responses);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getYapperDialog(fullResponseString, yapperImage));

    }


    /**
     * Displays Yapper's greeting in the dialog container.
     */
    public void displayChatbotGreeting() {
        String greeting = Ui.printGreet(yapper.getName());
        dialogContainer.getChildren().add(DialogBox.getYapperDialog(greeting, yapperImage));

    }
}
