package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import seedu.address.model.Model;


/**
 * Lists all items in the address book to the user.
 */
public class ListItemCommand extends Command {

    public static final String COMMAND_WORD = "listi";

    public static final String MESSAGE_SUCCESS = "Listed all items";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}