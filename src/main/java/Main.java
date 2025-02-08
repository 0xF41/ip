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
    private static final String FILE_PATH_CSV_DATA = "usertaskdata.csv";
    private static final String FILE_PATH_MAIN_WINDOW_FXML = "/view/MainWindow.fxml";

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
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FILE_PATH_MAIN_WINDOW_FXML));
        AnchorPane ap = fxmlLoader.load();  // Load the FXML layout

        // Get the controller from FXMLLoader
        MainWindow controller = fxmlLoader.getController();

        // Check if the controller is null, and ensure it's being injected correctly
        if (controller == null) {
            throw new NullPointerException("Controller is null after FXML load.");
        }

        // Inject the Yapper instance into the controller
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

        // Call displayChatbotGreeting after Yapper is set
        controller.displayChatbotGreeting();
    }


    /**
     * Starts the JavaFX application. (GUI version)
     *
     * @param stage Stage to start the application.
     */
    @Override
    public void start(Stage stage) {
        Person p1 = new Person(FILE_PATH_CSV_DATA);
        Yapper y1 = new Yapper(CHATBOT_NAME, p1.getTaskList(), p1.getFile());

        assert y1 != null : ASSERT_YAPPER_NOT_NULL_STRING;
        assert p1 != null : ASSERT_PERSON_NOT_NULL_STRING;

        try {
            loadScene(stage, y1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
