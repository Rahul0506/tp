package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditItemCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditItemCommandParser implements Parser<EditItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditItemCommand
     * and returns an EditItemCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditItemCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ITEM_NAME, PREFIX_ITEM_QUANTITY,
                        PREFIX_ITEM_DESCRIPTION, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditItemCommand.MESSAGE_USAGE), pe);
        }

        EditItemCommand.EditItemDescriptor editItemDescriptor = new EditItemCommand.EditItemDescriptor();
        if (argMultimap.getValue(PREFIX_ITEM_NAME).isPresent()) {
            editItemDescriptor.setName(ItemParserUtil.parseName(argMultimap.getValue(PREFIX_ITEM_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ITEM_QUANTITY).isPresent()) {
            editItemDescriptor.setQuantity(ItemParserUtil.parseQuantity(argMultimap.getValue(PREFIX_ITEM_QUANTITY).get()));
        }
        if (argMultimap.getValue(PREFIX_ITEM_DESCRIPTION).isPresent()) {
            editItemDescriptor.setDescription(ItemParserUtil.parseDescription(argMultimap.getValue(PREFIX_ITEM_DESCRIPTION).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editItemDescriptor::setTags);

        if (!editItemDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditItemCommand.MESSAGE_NOT_EDITED);
        }

        return new EditItemCommand(index, editItemDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
