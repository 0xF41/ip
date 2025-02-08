package yapper.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {

    private static final String DIALOG_REPLY_LABEL_FIELD_STRING = "reply-label";
    private static final String DIALOG_BOX_FXML_FILEPATH = "/view/DialogBox.fxml";
    private static final String ASSERT_EMPTY_DIALOG_LABEL_STRING = "Dialog label should not be null";
    private static final String ASSERT_EMPTY_OBSERVABLE_LIST_STRING = "Observable list should not be null";

    // Solution below adapted from https://se-education.org/guides/tutorials/javaFxPart4.html
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource(DIALOG_BOX_FXML_FILEPATH));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert dialog != null : ASSERT_EMPTY_DIALOG_LABEL_STRING;

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        assert tmp != null : ASSERT_EMPTY_OBSERVABLE_LIST_STRING;
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add(DIALOG_REPLY_LABEL_FIELD_STRING);
    }

    /**
     * Gets a dialog box for the user.
     *
     * @param text The text to display
     * @param img  The image to display
     * @return The dialog box for the user
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Gets a dialog box for Yapper.
     *
     * @param text The text to display
     * @param img  The image to display
     * @return The dialog box for Yapper
     */
    public static DialogBox getYapperDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
