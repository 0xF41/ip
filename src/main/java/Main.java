import java.io.IOException;

public class Main {
     /**
     * Main Function
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Person p1 = new Person("usertaskdata.csv");
        Yapper y1 = new Yapper("Yapper", p1.taskList, p1.file);
        y1.run();
    }
}
