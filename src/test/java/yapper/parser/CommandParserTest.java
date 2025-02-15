package yapper.parser;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import yapper.commands.DeadlineTaskCommand;
import yapper.commands.EventsTaskCommand;
import yapper.commands.TaskCommand;
import yapper.commands.ToDosTaskCommand;
import yapper.data.task.Task;

public class CommandParserTest {

    /**
     * Stub task list.
     */
    private ArrayList<Task> taskList = new ArrayList<Task>();

    /**
     * Tests the parse method in CommandParser for a ToDos task.
     *
     * @throws Exception if an error occurs within the test.
     */
    @Test
    public void parse_todos_test() throws Exception {
        TaskCommand command = (TaskCommand) CommandParser.parse("todo read book", this.taskList, null, null, null, null,
                null);
        assert (command instanceof ToDosTaskCommand);
        assert (command.getTaskDescription().equals("read book"));
    }

    /**
     * Tests the parse method in CommandParser for a Deadline task.
     *
     * @throws Exception if an error occurs within the test.
     */
    @Test
    public void parse_deadline_test() throws Exception {
        DeadlineTaskCommand command = (DeadlineTaskCommand) CommandParser.parse(
                "deadline return book /by 21-08-2025 1800",
                this.taskList, null, null, null, null, null);
        assert (command instanceof DeadlineTaskCommand);
        assert (command.getTaskDescription().equals("return book"));
    }

    /**
     * Tests the parse method in CommandParser for an Events task.
     *
     * @throws Exception if an error occurs within the test.
     */
    @Test
    public void parse_events_test() throws Exception {
        EventsTaskCommand command = (EventsTaskCommand) CommandParser.parse(
                "event drink water /from 21-08-2025 1954 /to 22-08-2025 1954",
                this.taskList,
                null,
                null,
                null,
                null,
                null);
        assert (command instanceof EventsTaskCommand);
        assert (command.getTaskDescription().equals("drink water"));
    }
}
