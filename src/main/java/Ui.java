public class Ui {
    public static void printGreet(String botName) {
        System.out.println("____________________________________________________________");
        System.out.println(String.format("Hello! I'm %s!", botName));
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    public static void printLine() {
        System.out.println("____________________________________________________________");
    }

    public static void printMenu() {
        String menu = """
            __   __
            \\ \\ / /_ _ _ __  _ __   ___ _ __
             \\ V / _` | '_ \\| '_ \\ / _ \\ '__|
              | | (_| | |_) | |_) |  __/ |
              |_|\\__,_| .__/| .__/ \\___|_|
                      |_|   |_|

          Usage:
            list                  - Show current task list
            mark <task_number>    - Mark task with <task_number> as done
            unmark <task_number>  - Unmark task with <task_number> as incomplete
            todo <task_name>      - Create a new task specified with <task_name>
            deadline <task_name> /by <deadline>
                                  - Create a new Deadline task with a <deadline> with <dd-MM-yyyy HHmm> format
            event <task_name> /from <start_time> /to <end_time>
                                  - Create a new Event task with a <start_time> and <end_time> with <dd-MM-yyyy HHmm> format
            delete <task_number>  - Delete task with <task_number> on the list
            bye                   - End the conversation with the chatbot
            help                  - Show this help menu
          """;
        System.out.println(menu);
    }
}
