package seedu.address.logic.commands.schedulecommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_ADD_EDIT_COMMAND_ERROR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_TO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.commons.util.DateTimeValidationUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

/**
 * Adds a schedule to the schedule list.
 */
public class AddScheduleCommand extends Command {

    public static final String COMMAND_WORD = "add_schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a schedule to the schedule list. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME_FROM + "TIME FROM "
            + PREFIX_TIME_TO + "TIME TO "
            + PREFIX_DESCRIPTION + "DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Math Tuition Homework "
            + PREFIX_DATE + "2021-6-1 "
            + PREFIX_TIME_FROM + "08:00am "
            + PREFIX_TIME_TO + "10:00am "
            + PREFIX_DESCRIPTION + "Page 10 to 13";

    public static final String MESSAGE_SUCCESS = "New schedule added: %1$s";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "This schedule already exists in the list";

    private final Schedule toAdd;

    /**
     * Primary constructor to accept a schedule and add it to schedule list.
     *
     * @param schedule Schedule to add
     */
    public AddScheduleCommand(Schedule schedule) {
        requireNonNull(schedule);
        toAdd = schedule;
    }

    /**
     * Main execute method that creates adds a new schedule to the schedule list
     *
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult indicating success or failure of add operation
     * @throws CommandException if {@code Schedule} already exists
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasSchedule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SCHEDULE);
        }

        boolean isValidateSuccess = DateTimeValidationUtil.validateDateTime(model, toAdd);

        if (isValidateSuccess) {
            model.addSchedule(toAdd);
            model.resetPredicates();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), TabName.SCHEDULE);
        } else {
            throw new CommandException(MESSAGE_ADD_EDIT_COMMAND_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddScheduleCommand // instanceof handles nulls
                && toAdd.equals(((AddScheduleCommand) other).toAdd));
    }
}
