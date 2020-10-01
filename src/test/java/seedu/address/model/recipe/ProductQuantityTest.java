package seedu.address.model.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ProductQuantityTest {
    /**
     * Tests for valid quantity. Only non-negative integers are allowed.
     */
    @Test
    public void isValidProductQuantity() {
        assertTrue(ProductQuantity.isValidQuantity("123"));
        assertTrue(ProductQuantity.isValidQuantity("0"));
        assertFalse(ProductQuantity.isValidQuantity("1 2"));
        assertFalse(ProductQuantity.isValidQuantity("-2"));
        assertFalse(ProductQuantity.isValidQuantity("1.2"));
        assertFalse(ProductQuantity.isValidQuantity("a"));
    }

    /**
     * Test for equality between quantities.
     */
    @Test
    public void equals() {
        assertEquals(new ProductQuantity("1"), new ProductQuantity("1"));
        assertNotEquals(new ProductQuantity("1"), new ProductQuantity("2"));
    }
}
