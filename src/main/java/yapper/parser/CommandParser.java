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
import yapper.commands.FindCommand;
import yapper.commands.HelpCommand;
import yapper.commands.ListCommand;
import yapper.commands.MarkCommand;
import yapper.commands.NoteCommand;
import yapper.commands.RescheduleCommand;
import yapper.commands.ToDosTaskCommand;
import yapper.commands.UnmarkCommand;
import yapper.data.exception.InvalidCommandSyntaxException;
import yapper.data.notes.Note;
import yapper.data.task.DeadlineScheduleTask;
import yapper.data.task.EventsScheduleTask;
import yapper.data.task.Task;
import yapper.data.task.ToDosTask;

/**
 * CommandParser parses the commands entered by the user into the chatbot.
 */
public class CommandParser {

    // Assertion fail string
    private static final String ASSERT_FAIL_STRING = "Command not handled!";

    // Empty string
    private static final String EMPTY_STRING = "";

    // Subcommand lengths for index checking
    private static final int BY_MAGIC_LENGTH_INT = 4;
    private static final int DEADLINE_MAGIC_LENGTH_INT = 9;
    private static final int FROM_MAGIC_LENGTH_INT = 6;
    private static final int TITLE_MAGIC_LENGTH_INT = 7;
    private static final int CONTENT_MAGIC_LENGTH_INT = 9;

    // Commands
    private static final String COMMAND_EVENT_STRING = "event";
    private static final String COMMAND_DEADLINE_STRING = "deadline";
    private static final String COMMAND_TODO_STRING = "todo";

    // Subcommands
    private static final String SUBCOMMAND_FROM_STRING = "/from";
    private static final String SUBCOMMAND_TO_STRING = "/to";
    private static final String SUBCOMMAND_BY_STRING = "/by";
    private static final String SUBCOMMAND_NOTE_TITLE_STRING = "/title";
    private static final String SUBCOMMAND_NOTE_CONTENT_STRING = "/content";
    private static final String SUBCOMMAND_OPTION_TASK_STRING = "task";
    private static final String SUBCOMMAND_OPTION_NOTE_STRING = "note";

    // DateTimeFormatter String pattern
    private static final String DATE_TIME_FORMATTER_PATTERN_STRING = "dd-MM-yyyy HHmm";

    // Error messages
    private static final String ERR_CANNOT_RESCHEDULE_TODO_TASK_STRING =
        "Cannot reschedule a todo task!";
    private static final String ERR_SEE_USAGE_STRING =
        "See usage with \"help\"";
    private static final String ERR_MISSING_END_DATE_STRING =
        "Missing end date! Please specify using /by.";
    private static final String ERR_NOTE_EMPTY_TITLE_STRING =
        "Title cannot be empty!";
    private static final String ERR_NOTE_EMPTY_CONTENT_STRING =
        "Content cannot be empty!";
    private static final String ERR_NOTE_INVALID_SYNTAX_STRING =
        "Invalid syntax. Use: note /title <title> /content <content>";
    private static final String ERR_MISSING_START_END_DATE_STRING =
        "Missing start/end date! Please specify using /from and /to.";
    private static final String ERR_LIST_NOT_FOUND_FORMAT_STRING =
        "List of type %s not found!";
    private static final String ERR_INVALID_DATE_FORMAT_STRING =
        "Invalid date format! Please use dd-MM-yyyy HHmm.";
    private static final String ERR_INVALID_LIST_INDEX_FORMAT_STRING =
        "Invalid index in list %s!";

    /**
     * Enum to represent the different types of commands.
     */
    public enum CommandOption {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, BYE, HELP, FIND, RESCHEDULE, NOTE;

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
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static Command buildListCommand(String cmd, ArrayList<Task> taskList, ArrayList<Note> noteList)
            throws InvalidCommandSyntaxException {

        if (cmd.split(" ").length != 2) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }

        String listTypeString = cmd.split(" ")[1];
        try {
            if (listTypeString.strip().toLowerCase().equals(SUBCOMMAND_OPTION_TASK_STRING)) {
                return new ListCommand(taskList);
            } else if (listTypeString.strip().toLowerCase().equals(SUBCOMMAND_OPTION_NOTE_STRING)) {
                return new ListCommand(noteList);
            } else {
                throw new InvalidCommandSyntaxException(
                        String.format(ERR_LIST_NOT_FOUND_FORMAT_STRING, listTypeString));
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(
                    String.format(ERR_LIST_NOT_FOUND_FORMAT_STRING, listTypeString));
        }
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
        String description = EMPTY_STRING;
        String dueDateString = EMPTY_STRING;

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

        String description = EMPTY_STRING;
        String fromTimeString = EMPTY_STRING;
        String toTimeString = EMPTY_STRING;

        try {
            int eventIndex = cmd.indexOf(COMMAND_EVENT_STRING) + FROM_MAGIC_LENGTH_INT;
            int fromIndex = -1;
            int toIndex = -1;

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
     * @param noteList List of notes.
     * @return Delete command.
     * @throws InvalidCommandSyntaxException If the command is invalid.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static Command buildDeleteCommand(String cmd, ArrayList<Task> taskList, ArrayList<Note> noteList)
            throws InvalidCommandSyntaxException, IndexOutOfBoundsException {
        if (cmd.split(" ").length < 2) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }

        int idx = -1; // index of the selected item to delete
        String listTypeString = EMPTY_STRING;

        try {
            listTypeString = cmd.split(" ")[1];
            idx = Integer.parseInt(cmd.split(" ")[2]) - 1;

            taskList.get(idx);

            if (listTypeString.strip().toLowerCase().equals(SUBCOMMAND_OPTION_TASK_STRING)) {
                return new DeleteCommand(taskList, idx);
            } else if (listTypeString.strip().toLowerCase().equals(SUBCOMMAND_OPTION_NOTE_STRING)) {
                return new DeleteCommand(noteList, idx);
            } else {
                throw new InvalidCommandSyntaxException(
                        String.format(ERR_LIST_NOT_FOUND_FORMAT_STRING, listTypeString));
            }

        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(
                    String.format(ERR_LIST_NOT_FOUND_FORMAT_STRING, listTypeString));

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(
                    String.format(ERR_INVALID_LIST_INDEX_FORMAT_STRING, listTypeString));
        }

    }

    /**
     * Builds a bye command.
     *
     * @param fullCmd  Full command entered by the user.
     * @param taskList List of tasks.
     * @param noteList List of notes.
     * @param taskFile File to save the tasks to.
     * @param noteFile File to save the notes to.
     * @return Bye command.
     * @throws InvalidCommandSyntaxException If the command is invalid
     */
    private static Command buildByeCommand(String fullCmd, ArrayList<Task> taskList, ArrayList<Note> noteList,
            File taskFile, File noteFile)
            throws InvalidCommandSyntaxException {
        if (fullCmd.split(" ").length != 1) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }

        return new ByeCommand(taskList, noteList, taskFile, noteFile);
    }

    /**
     * Builds a find command.
     *
     * @param cmd      Full command entered by the user.
     * @param taskList List of tasks.
     * @param noteList List of notes.
     * @return Find command.
     * @throws InvalidCommandSyntaxException If the command is invalid
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static Command buildFindCommand(String cmd, ArrayList<Task> taskList, ArrayList<Note> noteList)
            throws InvalidCommandSyntaxException {
        if (cmd.split(" ").length != 3) {
            throw new InvalidCommandSyntaxException(ERR_SEE_USAGE_STRING);
        }

        String searchString = EMPTY_STRING;
        String listTypeString = EMPTY_STRING;

        try {
            listTypeString = cmd.split(" ")[1];
            searchString = cmd.split(" ")[2];

            if (listTypeString.strip().toLowerCase().equals(SUBCOMMAND_OPTION_TASK_STRING)) {
                return new FindCommand(taskList, searchString);
            } else if (listTypeString.strip().toLowerCase().equals(SUBCOMMAND_OPTION_NOTE_STRING)) {
                return new FindCommand(noteList, searchString);
            } else {
                throw new InvalidCommandSyntaxException(
                        String.format(ERR_LIST_NOT_FOUND_FORMAT_STRING, listTypeString));
            }

        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(
                    String.format(ERR_LIST_NOT_FOUND_FORMAT_STRING, listTypeString));

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(
                    String.format(ERR_INVALID_LIST_INDEX_FORMAT_STRING, listTypeString));
        }

    }

    /**
     * Builds a reschedule command.
     *
     * @param fullCmd  Full command to be entered by the user.
     * @param taskList List of tasks.
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
     * Build a Note command
     *
     * @param fullCmd  Full command entered by the user.
     * @param noteList List of user's notes.
     * @return Note command
     * @throws InvalidCommandSyntaxException if the command is invalid.
     */
    private static Command buildNoteCommand(String fullCmd, ArrayList<Note> noteList)
            throws InvalidCommandSyntaxException {
        if (!fullCmd.contains(SUBCOMMAND_NOTE_TITLE_STRING) || !fullCmd.contains(SUBCOMMAND_NOTE_CONTENT_STRING)) {
            throw new InvalidCommandSyntaxException(ERR_NOTE_INVALID_SYNTAX_STRING);
        }

        String title = EMPTY_STRING;
        String content = EMPTY_STRING;

        try {
            int titleIndex = fullCmd.indexOf(SUBCOMMAND_NOTE_TITLE_STRING) + TITLE_MAGIC_LENGTH_INT;
            int contentIndex = fullCmd.indexOf(SUBCOMMAND_NOTE_CONTENT_STRING) + CONTENT_MAGIC_LENGTH_INT;

            // Extract title and content
            if (titleIndex < contentIndex) {
                title = fullCmd.substring(titleIndex, contentIndex - CONTENT_MAGIC_LENGTH_INT).trim();
                content = fullCmd.substring(contentIndex).trim();
            } else {
                content = fullCmd.substring(contentIndex, titleIndex - TITLE_MAGIC_LENGTH_INT).trim();
                title = fullCmd.substring(titleIndex).trim();
            }

            if (title.isEmpty()) {
                throw new InvalidCommandSyntaxException(ERR_NOTE_EMPTY_TITLE_STRING);
            }
            if (content.isEmpty()) {
                throw new InvalidCommandSyntaxException(ERR_NOTE_EMPTY_CONTENT_STRING);
            }

        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(ERR_NOTE_INVALID_SYNTAX_STRING);
        }

        return NoteCommand.buildNoteCommand(noteList, new Note(title, content));
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
     * @param noteList List of notes.
     * @param taskFile File to save the tasks to.
     * @param noteFile File to save the notes to.
     * @return Command object.
     * @throws InvalidCommandSyntaxException If the command is invalid.
     * @throws IndexOutOfBoundsException     If the index is out of bounds.
     */
    public static Command parse(String fullCmd, ArrayList<Task> taskList, ArrayList<Note> noteList, File taskFile,
            File noteFile)
            throws InvalidCommandSyntaxException, IndexOutOfBoundsException {

        String cmd = fullCmd.split(" ")[0];
        if (CommandOption.fromString(cmd).equals(CommandOption.LIST)) { // list tasks
            return buildListCommand(fullCmd, taskList, noteList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.MARK)) {
            return buildMarkCommand(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.UNMARK)) {
            return buildUnmarkCommand(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.TODO)) {
            return buildToDosCommand(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.DEADLINE)) {
            return buildDeadlineCommand(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.EVENT)) {
            return buildEventCommand(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.DELETE)) {
            return buildDeleteCommand(fullCmd, taskList, noteList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.BYE)) {
            return buildByeCommand(fullCmd, taskList, noteList, taskFile, noteFile);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.HELP)) {
            return buildHelpCommand();
        } else if (CommandOption.fromString(cmd).equals(CommandOption.FIND)) {
            return buildFindCommand(fullCmd, taskList, noteList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.RESCHEDULE)) {
            return buildRescheduleCommand(fullCmd, taskList);
        } else if (CommandOption.fromString(cmd).equals(CommandOption.NOTE)) {
            return buildNoteCommand(fullCmd, noteList);
        }

        assert false : ASSERT_FAIL_STRING;
        return null;
    }
}
