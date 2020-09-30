package seedu.address.model.item;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.ItemPrecursorBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalItemPrecursors.APPLE_PRECURSOR;
import static seedu.address.testutil.TypicalItemPrecursors.BANANA_PRECURSOR;

public class ItemPrecursorTest {

    /**
     * Test for strict item precursor equality, defined as two item precursors
     * having the exact same fields.
     */
    @Test
    public void equals() {
        // same values -> returns true
        ItemPrecursor appleCopy = new ItemPrecursorBuilder(APPLE_PRECURSOR).build();
        assertTrue(APPLE_PRECURSOR.equals(appleCopy));

        // same object -> returns true
        assertTrue(APPLE_PRECURSOR.equals(APPLE_PRECURSOR));

        // null -> returns false
        assertFalse(APPLE_PRECURSOR.equals(null));

        // different type -> returns false
        assertFalse(APPLE_PRECURSOR.equals(5));

        // different item -> returns false
        assertFalse(APPLE_PRECURSOR.equals(BANANA_PRECURSOR));

        // different name -> returns false
        ItemPrecursor editedApple = new ItemPrecursorBuilder(APPLE_PRECURSOR).withName(VALID_ITEM_NAME_BANANA).build();
        assertFalse(APPLE_PRECURSOR.equals(editedApple));

        // different quantity -> returns false
        editedApple = new ItemPrecursorBuilder(APPLE_PRECURSOR).withQuantity(VALID_ITEM_QUANTITY_BANANA).build();
        assertFalse(APPLE_PRECURSOR.equals(editedApple));

        // different description -> returns false
        editedApple = new ItemPrecursorBuilder(APPLE_PRECURSOR).withDescription(VALID_ITEM_DESCRIPTION_BANANA).build();
        assertFalse(APPLE_PRECURSOR.equals(editedApple));
    }
}
