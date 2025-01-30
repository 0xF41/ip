package yapper.parser;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import yapper.task.Task;
import yapper.commands.TaskCommand;
import yapper.commands.ToDosTaskCommand;

public class CommandParserTest {

    /**
     * Stub task list.
     */
    public ArrayList<Task> taskList = new ArrayList<Task>();

    /**
     * Tests the parse method in CommandParser for a ToDos task.
     *
     * @throws Exception if an error occurs within the test.
     */
    @Test
    public void parseTest() throws Exception {
        TaskCommand command = (TaskCommand) CommandParser.parse("todo read book", this.taskList, null);
        assert (command instanceof ToDosTaskCommand);
        assert (command.getTaskDescription().equals("read book"));
    }

    /**
     * Tests the parse method in CommandParser for a Deadline task.
     *
     * @throws Exception if an error occurs within the test.
     */
    @Test
    public void parseTest2() throws Exception {
        TaskCommand command = (TaskCommand) CommandParser.parse("deadline return book /by 2021-08-21 18:00",
                this.taskList, null);
        assert (command instanceof ToDosTaskCommand);
        assert (command.getTaskDescription().equals("return book"));
    }

    /**
     * Tests the parse method in CommandParser for an Events task.
     *
     * @throws Exception if an error occurs within the test.
     */
    @Test
    public void parseTest3() throws Exception {
        TaskCommand command = (TaskCommand) CommandParser.parse("event drink water /by 2025-30-01 19:54", this.taskList,
                null);
        assert (command instanceof ToDosTaskCommand);
        assert (command.getTaskDescription().equals("drink water"));
    }
}
