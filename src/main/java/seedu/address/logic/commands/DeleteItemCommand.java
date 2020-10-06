package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.recipe.Recipe;

/**
 * DeleteItemCommand represents a delete item command with hidden
 * internal logic and the ability to be executed.
 */
public class DeleteItemCommand extends Command {
    public static final String COMMAND_WORD = "deli";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a item in the item list. "
            + "Parameters: "
            + PREFIX_ITEM_NAME + "ITEM NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ITEM_NAME + "Iron ";

    public static final String MESSAGE_SUCCESS = "Item and Recipes of the item Deleted: %1$s";
    public static final String MESSAGE_ITEM_NOT_FOUND = "Item is not found in the item list";

    private final String productName;

    /**
     * Creates an DeleteItemCommand to delete the specified {@code Item} and connecting recipes
     * @param itemName The item name to be deleted
     */
    public DeleteItemCommand(String itemName) {
        requireNonNull(itemName);
        this.productName = itemName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Item> itemList = new ArrayList<>(model.getFilteredItemList());

        // filter to only get matching and not deleted recipes
        itemList.removeIf(x -> !x.getName().equals(productName) || x.isDeleted());
        if (itemList.isEmpty()) {
            throw new CommandException(MESSAGE_ITEM_NOT_FOUND);
        }

        Item itemToDelete;
        itemToDelete = itemList.stream()
                .findFirst()// Get the first (and only) item matching or else throw Error
                .orElseThrow(()-> new CommandException(MESSAGE_ITEM_NOT_FOUND));

        model.deleteItem(itemToDelete);
        List<Recipe> recipeList = new ArrayList<>(model.getFilteredRecipeList());

        // remove recipes from consideration that are not soft deleted,
        // nor contain deleted item as product or ingredient
        recipeList.removeIf(y -> y.isDeleted()
                || y.getIngredients()
                .asUnmodifiableObservableList()
                .stream()
                .noneMatch(z -> z.isItem(itemToDelete.getId())));

        // delete recipes connected to this identified item as a product, or an ingredient
        recipeList.forEach(model::deleteRecipe);
        return new CommandResult(String.format(MESSAGE_SUCCESS, itemToDelete));
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteItemCommand // instanceof handles nulls
                && productName.equals(((DeleteItemCommand) other).productName));
    }
}