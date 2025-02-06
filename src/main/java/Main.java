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

    private Scene scene;
    private Yapper yapper;
    private Person person;

    /**
     * Starts the JavaFX application. (GUI version)
     *
     * @param stage Stage to start the application.
     */
    @Override
    public void start(Stage stage) {
        Person p1 = new Person(FILE_PATH_CSV_DATA);
        Yapper y1 = new Yapper(CHATBOT_NAME, p1.getTaskList(), p1.getFile());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(FILE_PATH_MAIN_WINDOW_FXML));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setYapper(y1); // inject the Yapper instance
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
