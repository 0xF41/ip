package yapper.parser;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import yapper.commands.ByeCommand;
import yapper.commands.Command;
import yapper.commands.DeadlineTaskCommand;
import yapper.commands.DeleteCommand;
import yapper.commands.EventsTaskCommand;
import yapper.commands.FindTaskCommand;
import yapper.commands.HelpCommand;
import yapper.commands.ListCommand;
import yapper.commands.MarkCommand;
import yapper.commands.ToDosTaskCommand;
import yapper.commands.UnmarkCommand;
import yapper.commands.RescheduleCommand;
import yapper.data.exception.InvalidCommandSyntaxException;
import yapper.task.DeadlineScheduleTask;
import yapper.task.EventsScheduleTask;
import yapper.task.Task;
import yapper.task.ToDosTask;

/**
 * CommandParser parses the commands entered by the user into the chatbot.
 */
public class CommandParser {

    private static final String ASSERT_FAIL_STRING = "Command not handled!";

    private static final int BY_MAGIC_LENGTH_INT = 4;
    private static final int DEADLINE_MAGIC_LENGTH_INT = 9;
    private static final int FROM_MAGIC_LENGTH_INT = 6;

    private static final String COMMAND_EVENT_STRING = "event";
    private static final String COMMAND_DEADLINE_STRING = "deadline";
    private static final String COMMAND_TODO_STRING = "todo";
    private static final String SUBCOMMAND_FROM_STRING = "/from";
    private static final String SUBCOMMAND_TO_STRING = "/to";
    private static final String SUBCOMMAND_BY_STRING = "/by";

    private static final String DATE_TIME_FORMATTER_PATTERN_STRING = "dd-MM-yyyy HHmm";

    private static final String ERR_CANNOT_RESCHEDULE_TODO_TASK_STRING = "Cannot reschedule a todo task!";
    private static final String ERR_SEE_USAGE_STRING = "See usage with \"help\"";
    private static final String ERR_EMPTY_LIST_STRING = "List is empty!";
    private static final String ERR_MISSING_END_DATE_STRING = "Missing end date! Please specify using /by.";
    private static final String ERR_INVALID_DATE_FORMAT_STRING = "Invalid date format! Please use dd-MM-yyyy HHmm.";
    private static final String ERR_MISSING_START_END_DATE_STRING = "Missing start/end date! Please specify using /from and /to.";

    /**
     * Enum to represent the different types of commands.
     */
    public enum CommandOption {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, BYE, HELP, FIND, RESCHEDULE;

        /**
         * Converts a string to a CommandOption.
         *
         * @param command Command string.
         * @return CommandOption.
         * @throws InvalidCommandSyntaxException If the command is invalid.
         */
        public static CommandOption fromString(String command) throws InvalidCommandSyntaxException {
            try {
                return CommandOption.valueOf(command.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
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
    private static Command buildListCommand(String cmd, ArrayList<Task> taskList) throws InvalidCommandSyntaxException {
        if (cmd.split(" ").length != 1) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }
        if (taskList.isEmpty()) {
            throw new InvalidCommandSyntaxException(ERR_EMPTY_LIST_STRING);
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
    private static Command buildMarkCommand(String cmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException {
        if (cmd.split(" ").length != 2) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }
        int idx = -1;
        try {
            idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
            return MarkCommand.buildMarkCommand(taskList, idx);
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }
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
    private static Command buildUnmarkCommand(String cmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException, IndexOutOfBoundsException {
        if (cmd.split(" ").length != 2) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }
        int idx = -1;
        try {
            idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
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
    private static Command buildToDosCommand(String cmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException {
        return ToDosTaskCommand.buildToDosCommand(taskList,
                new ToDosTask(cmd.substring(COMMAND_TODO_STRING.length() + 1)));
    }

    /**
     * Builds a deadline command.
     *
     * @param cmd      Full command entered by the user.
     * @param taskList List of tasks.
     * @return DeadlineTask command.
     * @throws InvalidCommandSyntaxException If the command is invalid.
     */
    private static Command buildDeadlineCommand(String cmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException {
        if (cmd.split(" ").length < 2) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }

        int deadlineIndex = cmd.indexOf(COMMAND_DEADLINE_STRING) + DEADLINE_MAGIC_LENGTH_INT;
        int byIndex = -1;
        String description = "";
        String dueDateString = "";
        try {
            byIndex = cmd.indexOf(SUBCOMMAND_BY_STRING);
            description = cmd.substring(deadlineIndex, byIndex).trim();
            cmd.substring(deadlineIndex, byIndex).trim();
            dueDateString = cmd.substring(byIndex + BY_MAGIC_LENGTH_INT).trim();
        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(ERR_MISSING_END_DATE_STRING);
        }

        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_PATTERN_STRING);
            LocalDateTime byLocalDateTime = LocalDateTime.parse(dueDateString, dtf);
            return DeadlineTaskCommand.buildDeadlineCommand(taskList,
                    new DeadlineScheduleTask(description, byLocalDateTime));
        } catch (DateTimeParseException e) {
            throw new InvalidCommandSyntaxException(ERR_INVALID_DATE_FORMAT_STRING);
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
    private static Command buildEventCommand(String cmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException {

        if (cmd.split(" ").length < 2) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }

        int eventIndex = cmd.indexOf(COMMAND_EVENT_STRING) + 6; // Start after "event "
        int fromIndex = -1;
        int toIndex = -1;

        String description = "";
        String fromTimeString = "";
        String toTimeString = "";

        try {
            fromIndex = cmd.indexOf(SUBCOMMAND_FROM_STRING);
            toIndex = cmd.indexOf(SUBCOMMAND_TO_STRING);
            description = cmd.substring(eventIndex, fromIndex).trim();
            fromTimeString = cmd.substring(fromIndex + FROM_MAGIC_LENGTH_INT, toIndex).trim();
            toTimeString = cmd.substring(toIndex + BY_MAGIC_LENGTH_INT).trim();
        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(ERR_MISSING_START_END_DATE_STRING);
        }

        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_PATTERN_STRING);
            LocalDateTime fromLocalDateTime = LocalDateTime.parse(fromTimeString, dtf);
            LocalDateTime toLocalDateTime = LocalDateTime.parse(toTimeString, dtf);
            return EventsTaskCommand.buildEventsCommand(taskList,
                    new EventsScheduleTask(description, fromLocalDateTime, toLocalDateTime));
        } catch (DateTimeParseException e) {
            throw new InvalidCommandSyntaxException(ERR_INVALID_DATE_FORMAT_STRING);
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
    private static Command buildDeleteCommand(String cmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException, IndexOutOfBoundsException {
        if (cmd.split(" ").length != 2) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }
        int idx = -1;
        try {
            idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
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
    private static Command buildByeCommand(String fullCmd, ArrayList<Task> taskList, File file)
            throws InvalidCommandSyntaxException {
        if (fullCmd.split(" ").length != 1) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }
        return ByeCommand.buildByeCommand(taskList, file);
    }

    /**
     * Builds a find command.
     *
     * @return Find command.
     */
    private static Command buildFindCommand(String fullCmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException {
        if (fullCmd.split(" ").length < 2) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }
        return FindTaskCommand.buildFindCommand(taskList, fullCmd.substring(COMMAND_TODO_STRING.length() + 1));
    }

    /**
     * Builds a reschedule command.
     *
     * @return Reschedule command.
     */
    private static Command buildRescheduleCommand(String fullCmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException {
        if (fullCmd.split(" ").length < 4) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }

        int idx = -1;
        try {
            idx = Integer.parseInt(fullCmd.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }

        if (taskList.get(idx) instanceof ToDosTask) {
            throw new InvalidCommandSyntaxException(ERR_CANNOT_RESCHEDULE_TODO_TASK_STRING);
        }

        assert taskList.get(idx) instanceof EventsScheduleTask || taskList.get(idx) instanceof DeadlineScheduleTask;

        if (taskList.get(idx) instanceof EventsScheduleTask && fullCmd.contains(SUBCOMMAND_FROM_STRING)
                && fullCmd.contains(SUBCOMMAND_TO_STRING)) {
            // reschedule <event-index> /from <new-start-date-time> /to <new-end-date-time>
            try {
                int fromIndex = fullCmd.indexOf(SUBCOMMAND_FROM_STRING) + FROM_MAGIC_LENGTH_INT;
                int toIndex = fullCmd.indexOf(SUBCOMMAND_TO_STRING);
                String newStartDateTime = fullCmd.substring(fromIndex, toIndex).trim();
                String newEndDateTime = fullCmd.substring(toIndex + BY_MAGIC_LENGTH_INT).trim();
                return RescheduleCommand.buildRescheduleCommand(taskList, idx, newStartDateTime, newEndDateTime);
            } catch (StringIndexOutOfBoundsException e) {
                throw new InvalidCommandSyntaxException(ERR_MISSING_START_END_DATE_STRING);
            }
        } else if (taskList.get(idx) instanceof DeadlineScheduleTask && fullCmd.contains(SUBCOMMAND_BY_STRING)) {
            // reschedule <event-index> /by <new-date-time>
            try {
                int byIndex = fullCmd.indexOf(SUBCOMMAND_BY_STRING) + BY_MAGIC_LENGTH_INT;
                String newDateTime = fullCmd.substring(byIndex).trim();
                return RescheduleCommand.buildRescheduleCommand(taskList, idx, newDateTime);
            } catch (StringIndexOutOfBoundsException e) {
                throw new InvalidCommandSyntaxException(ERR_MISSING_END_DATE_STRING);
            }
        } else {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }
    }

    /**
     * Builds a help command.
     *
     * @return Help command.
     */
    private static Command buildHelpCommand() {
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
            return buildListCommand(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.MARK)) { // mark X
            return buildMarkCommand(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.UNMARK)) { // unmark X
            return buildUnmarkCommand(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.TODO)) { // todo X
            return buildToDosCommand(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.DEADLINE)) { // deadline X /by Y
            return buildDeadlineCommand(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.EVENT)) { // event X /from Y /to Z
            return buildEventCommand(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.DELETE)) { // delete X
            return buildDeleteCommand(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.BYE)) { // bye
            return buildByeCommand(fullCmd, taskList, file);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.HELP)) { // help
            return buildHelpCommand();
        } else if (CommandOption.fromString(cmd).equals(CommandOption.FIND)) { // find X
            return buildFindCommand(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.RESCHEDULE)) { // reschedule X /from Y /to Z
            return buildRescheduleCommand(fullCmd, taskList);
        }
        assert false : ASSERT_FAIL_STRING;
        return null;
    }

}
