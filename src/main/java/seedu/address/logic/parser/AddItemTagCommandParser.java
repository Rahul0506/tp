package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddItemTagCommand.MESSAGE_TAG_NOT_PROVIDED;
import static seedu.address.logic.commands.AddQuantityToItemCommand.MESSAGE_ITEM_NOT_PROVIDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddItemTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

public class AddItemTagCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddItemTagCommand
     * and returns an AddItemTagCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddItemTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ITEM_NAME, PREFIX_ITEM_TAG);

        String itemName;
        Set<Tag> tags;

        if (argMultimap.getValue(PREFIX_ITEM_NAME).isPresent()) {
            itemName = ItemParserUtil.parseName(argMultimap.getValue(PREFIX_ITEM_NAME).get());
        } else {
            throw new ParseException(MESSAGE_ITEM_NOT_PROVIDED);
        }
        if (argMultimap.getValue(PREFIX_ITEM_TAG).isPresent()) {
            tags = ItemParserUtil.parseTags(argMultimap.getAllValues(PREFIX_ITEM_TAG));
        } else {
            throw new ParseException(MESSAGE_TAG_NOT_PROVIDED);
        }

        return new AddItemTagCommand(itemName, tags);
    }
}
