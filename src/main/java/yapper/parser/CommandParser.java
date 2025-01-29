package yapper.parser;

import java.io.File;
import java.util.ArrayList;

import yapper.commands.ByeCommand;
import yapper.commands.Command;
import yapper.commands.DeadlineCommand;
import yapper.commands.DeleteCommand;
import yapper.commands.EventsCommand;
import yapper.commands.HelpCommand;
import yapper.commands.ListCommand;
import yapper.commands.MarkCommand;
import yapper.commands.ToDosCommand;
import yapper.commands.UnmarkCommand;
import yapper.data.exception.InvalidCommandSyntaxException;
import yapper.task.Deadline;
import yapper.task.Events;
import yapper.task.Task;
import yapper.task.ToDos;
import yapper.ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * CommandParser parses the commands entered by the user into the chatbot.
 */
public class CommandParser {

    /**
     * Enum to represent the different types of commands.
     */
    public enum CommandOption {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, BYE, HELP;

        // Check for valid commands
        public static CommandOption fromString(String command) throws InvalidCommandSyntaxException {
            try {
                return CommandOption.valueOf(command.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidCommandSyntaxException("See usage with \"help\"");
            }
        }
    }

    /**
     * Builds a list command.
     *
     * @param cmd      Full command entered by the user.
     * @param taskList List of tasks.
     * @return List command.
     * @throws InvalidCommandSyntaxException If the command is invalid.
     */
    private static Command list(String cmd, ArrayList<Task> taskList) throws InvalidCommandSyntaxException {
        if (cmd.split(" ").length != 1) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        }
        if (taskList.isEmpty()) {
            Ui.printError("List is empty!");
            return null;
        }
        return ListCommand.buildListCommand(taskList);
    }

    /**
     * Builds a mark command.
     *
     * @param cmd      Full command entered by the user.
     * @param taskList List of tasks.
     * @return Mark command.
     * @throws InvalidCommandSyntaxException If the command is invalid.
     * @throws IndexOutOfBoundsException     If the index is out of bounds.
     */
    private static Command mark(String cmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException, IndexOutOfBoundsException {
        if (cmd.split(" ").length != 2) {
            throw new InvalidCommandSyntaxException("Usage: mark <task-index>");
        }
        int idx = -1;
        try {
            idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        }
        return MarkCommand.buildMarkCommand(taskList, idx);
    }

    /**
     * Builds an unmark command.
     *
     * @param cmd      Full command entered by the user.
     * @param taskList List of tasks.
     * @return Unmark command.
     * @throws InvalidCommandSyntaxException If the command is invalid.
     * @throws IndexOutOfBoundsException     If the index is out of bounds.
     */
    private static Command unmark(String cmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException, IndexOutOfBoundsException {
        if (cmd.split(" ").length != 2) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        }
        int idx = -1;
        try {
            idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        }
        return UnmarkCommand.buildUnmarkCommand(taskList, idx);
    }

    /**
     * Builds a todo command.
     *
     * @param cmd      Full command entered by the user.
     * @param taskList List of tasks.
     * @return Todo command.
     * @throws InvalidCommandSyntaxException If the command is invalid.
     */
    private static Command todo(String cmd, ArrayList<Task> taskList) throws InvalidCommandSyntaxException {
        return ToDosCommand.buildToDosCommand(taskList, new ToDos(cmd.substring("todo".length() + 1)));
    }

    /**
     * Builds a deadline command.
     *
     * @param cmd      Full command entered by the user.
     * @param taskList List of tasks.
     * @return Deadline command.
     * @throws InvalidCommandSyntaxException If the command is invalid.
     */
    private static Command deadline(String cmd, ArrayList<Task> taskList) throws InvalidCommandSyntaxException {
        if (cmd.split(" ").length < 2) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        }

        int deadlineIndex = cmd.indexOf("deadline") + 9;
        int byIndex = -1;
        String description = "";
        String dueDateString = "";
        try {
            byIndex = cmd.indexOf("/by");
            cmd.substring(deadlineIndex, byIndex).trim();
            dueDateString = cmd.substring(byIndex + 4).trim();
        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException("Missing end date! Please specify using /by.");
        }

        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
            LocalDateTime byLocalDateTime = LocalDateTime.parse(dueDateString, dtf);
            return DeadlineCommand.buildDeadlineCommand(taskList, new Deadline(description, byLocalDateTime));
        } catch (DateTimeParseException e) {
            throw new InvalidCommandSyntaxException("Invalid date format! Please use dd-MM-yyyy.");
        }
    }

    /**
     * Builds an event command.
     *
     * @param cmd      Full command entered by the user.
     * @param taskList List of tasks.
     * @return Event command.
     * @throws InvalidCommandSyntaxException If the command is invalid.
     */
    private static Command event(String cmd, ArrayList<Task> taskList) throws InvalidCommandSyntaxException {

        if (cmd.split(" ").length < 2) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        }

        int eventIndex = cmd.indexOf("event") + 6; // Start after "event "
        int fromIndex = -1;
        int toIndex = -1;

        String description = "";
        String fromTimeString = "";
        String toTimeString = "";

        try {
            fromIndex = cmd.indexOf("/from");
            toIndex = cmd.indexOf("/to");
            description = cmd.substring(eventIndex, fromIndex).trim();
            fromTimeString = cmd.substring(fromIndex + 6, toIndex).trim();
            toTimeString = cmd.substring(toIndex + 4).trim();
        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException("Missing start/end date! Please specify using /from and /to.");
        }

        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
            LocalDateTime fromLocalDateTime = LocalDateTime.parse(fromTimeString, dtf);
            LocalDateTime toLocalDateTime = LocalDateTime.parse(toTimeString, dtf);
            return EventsCommand.buildEventsCommand(taskList,
                    new Events(description, fromLocalDateTime, toLocalDateTime));
        } catch (DateTimeParseException e) {
            throw new InvalidCommandSyntaxException("Invalid date format! Please use dd-MM-yyyy HHmm.");
        }
    }

    /**
     * Builds a delete command.
     *
     * @param cmd      Full command entered by the user.
     * @param taskList List of tasks.
     * @return Delete command.
     * @throws InvalidCommandSyntaxException If the command is invalid.
     * @throws IndexOutOfBoundsException     If the index is out of bounds.
     */
    private static Command delete(String cmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException, IndexOutOfBoundsException {
        if (cmd.split(" ").length != 2) {
            throw new InvalidCommandSyntaxException("Type \"help\" for command list");
        }
        int idx = -1;
        try {
            idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        }
        return DeleteCommand.buildDeleteCommand(taskList, idx);
    }

    /**
     * Builds a bye command.
     *
     * @param fullCmd  Full command entered by the user.
     * @param taskList List of tasks.
     * @param file     File to save the tasks to.
     * @return Bye command.
     * @throws InvalidCommandSyntaxException If the command is invalid
     */
    private static Command bye(String fullCmd, ArrayList<Task> taskList, File file)
            throws InvalidCommandSyntaxException {
        if (fullCmd.split(" ").length != 1) {
            throw new InvalidCommandSyntaxException("Type \"help\" for command list");
        }
        return ByeCommand.buildByeCommand(taskList, file);
    }

    /**
     * Builds a help command.
     *
     * @return Help command.
     */
    private static Command help() {
        return HelpCommand.buildHelpCommand();
    }

    /**
     * Parses the command entered by the user.
     *
     * @param fullCmd  Full command entered by the user.
     * @param taskList List of tasks.
     * @param file     File to save the tasks to.
     * @return Command object.
     * @throws InvalidCommandSyntaxException If the command is invalid.
     * @throws IndexOutOfBoundsException     If the index is out of bounds.
     */
    public static Command parse(String fullCmd, ArrayList<Task> taskList, File file)
            throws InvalidCommandSyntaxException, IndexOutOfBoundsException {
        String cmd = fullCmd.split(" ")[0];
        if (CommandOption.fromString(cmd).equals(CommandOption.LIST)) { // list tasks
            return list(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.MARK)) { // mark X
            return mark(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.UNMARK)) { // unmark X
            return unmark(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.TODO)) { // todo X
            return todo(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.DEADLINE)) { // deadline X /by Y
            return deadline(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.EVENT)) { // event X /from Y /to Z
            return event(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.DELETE)) { // delete X
            return delete(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.BYE)) { // bye
            return bye(fullCmd, taskList, file);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.HELP)) { // help
            return help();
        }
        return null;
    }

}
