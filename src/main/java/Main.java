import java.io.IOException;

import yapper.chatbot.Yapper;
import yapper.data.user.Person;

/**
 * Main class to run the chatbot.
 */
public class Main {
    /**
     * Main method to run the chatbot.
     *
     * @param args Command line arguments.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        Person p1 = new Person("usertaskdata.csv");
        Yapper y1 = new Yapper("Yapper", p1.taskList, p1.file);
        y1.run();
    }
}
