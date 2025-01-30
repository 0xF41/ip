package yapper.commands;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import yapper.data.exception.InvalidCommandSyntaxException;
import yapper.parser.CommandParser;
import yapper.task.Task;
import yapper.task.ToDos;

/**
 * Tests the MarkCommand class.
 */
public class MarkCommandTest {

    // Constants for testing
    private static final String MARK_COMMAND = "mark 1";
    private static final String MARK_COMMAND_INVALID = "mark 0";

    // Stub task list
    private ArrayList<Task> taskList = new ArrayList<>();
    {
        taskList.add(new ToDos("read book"));
    }

    /**
     * Tests the execute method in MarkCommand.
     */
    @Test
    public void testExecute() {
        Command command;
        try {
            command = CommandParser.parse(MARK_COMMAND, taskList, null);
            assert (command.execute());
        } catch (IndexOutOfBoundsException | InvalidCommandSyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the execute method in MarkCommand with an invalid index.
     */
    @Test
    public void testExecuteInvalid() {
        Command command;
        try {
            command = CommandParser.parse(MARK_COMMAND_INVALID, taskList, null);
            assert (command.execute());
        } catch (IndexOutOfBoundsException | InvalidCommandSyntaxException e) {
            e.printStackTrace();
        }
    }
}