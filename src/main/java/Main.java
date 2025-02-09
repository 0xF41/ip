import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import yapper.chatbot.Yapper;
import yapper.data.user.Person;
import yapper.ui.MainWindow;

/**
 * Main class to run the chatbot.
 */
public class Main extends Application {

    private static final String CHATBOT_NAME = "Yapper";

    // File paths
    private static final String TASK_FILE_PATH_CSV = "usertaskdata.csv";
    private static final String NOTE_FILE_PATH_CSV = "usernotedata.csv";
    private static final String FILE_PATH_MAIN_WINDOW_FXML = "/view/MainWindow.fxml";

    // Assert messages
    private static final String ASSERT_YAPPER_NOT_NULL_STRING = "Yapper should not be null";
    private static final String ASSERT_PERSON_NOT_NULL_STRING = "Person should not be null";

    /**
     * Loads the main window scene.
     *
     * @param stage Stage to load the scene.
     * @param y1    Yapper instance to inject into the MainWindow controller.
     * @throws IOException If the FXML file is not found.
     */
    private void loadScene(Stage stage, Yapper y1) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FILE_PATH_MAIN_WINDOW_FXML));
        AnchorPane ap = fxmlLoader.load();

        MainWindow controller = fxmlLoader.getController();
        controller.setYapper(y1);

        // Create the scene and show the stage
        Scene scene = new Scene(ap);
        stage.setScene(scene);
        stage.setMinHeight(720);
        stage.setMinWidth(1280);
        stage.centerOnScreen();
        stage.setTitle(CHATBOT_NAME);
        stage.toFront();
        stage.show();

        controller.displayChatbotGreeting();
    }

    /**
     * Starts the JavaFX application. (GUI version)
     *
     * @param stage Stage to start the application.
     */
    @Override
    public void start(Stage stage) {
        Person p1 = new Person(TASK_FILE_PATH_CSV, NOTE_FILE_PATH_CSV);
        Yapper y1 = new Yapper(CHATBOT_NAME, p1.getTaskList(), p1.getNoteList(), p1.getTaskFile(), p1.getNoteFile());

        assert y1 != null : ASSERT_YAPPER_NOT_NULL_STRING;
        assert p1 != null : ASSERT_PERSON_NOT_NULL_STRING;

        try {
            loadScene(stage, y1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
