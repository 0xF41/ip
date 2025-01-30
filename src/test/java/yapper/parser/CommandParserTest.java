package yapper.parser;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import yapper.task.Task;
import yapper.task.ToDos;
import yapper.commands.TaskCommand;
import yapper.commands.ToDosTaskCommand;

public class CommandParserTest {

    /**
     * Stub task list.
     */
    public ArrayList<Task> taskList = new ArrayList<Task>();
    {
        taskList.add(new ToDos("read book"));
    }

    /**
     * Tests the parse method in CommandParser.
     *
     * @throws Exception If an error occurs.
     */
    @Test
    public void parseTest() throws Exception {
        TaskCommand command = (TaskCommand) CommandParser.parse("todo read book", this.taskList, null);
        assert (command instanceof ToDosTaskCommand);
        assert (command.getTaskDescription().equals("read book"));
    }
}
