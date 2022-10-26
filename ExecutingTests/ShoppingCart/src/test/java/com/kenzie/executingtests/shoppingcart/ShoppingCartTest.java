package com.kenzie.executingtests.shoppingcart;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class that runs through tests for the ShoppingCart class. Prints out results of tests to
 * the console.
 * <p>
 * Run the tests by running from the command line via RDE workflow, or directly in IntelliJ.
 */
public class ShoppingCartTest {
    /**
     * Calls the test methods, keeping track of whether each ShoppingCart method passes all of its tests or not.
     * Prints a summary of results. Note that some tests may not run if earlier tests fail.
     *
     * PARTICIPANTS: ADD CALLS IN THIS METHOD TO YOUR TEST METHODS THAT YOU WRITE BELOW.
     *
     * The {@code @Test} annotation here marks this as a unit test, so that JUnit runs the tests when the Brazil package
     * is built. We'll cover this in the Unit Testing lesson.
     */
    @Test
    void runAllTests() {
        ShoppingCartTest tester = new ShoppingCartTest();
        boolean pass = true;

        // addItem() tests

        System.out.println("\nTesting addItem()...");
        pass = tester.addItem_itemNameEmptyString_doesntAddItem() && pass;
        // PARTICIPANTS: uncomment the following lines after uncommenting the tests
        // below (see instructions for details)
        pass = tester.addItem_withNegativeQuantity_isRejected() && pass;
        pass = tester.addItem_withZeroQuantity_isRejected() && pass;

        if (!pass) {
            String errorMessage = "\n/!\\ /!\\ /!\\ The addItem() method tests failed. Test aborted. /!\\ /!\\ /!\\";
            System.out.println(errorMessage);
            fail(errorMessage);
        } else {
            System.out.println("The addItem() method tests passed!");
        }


        // updateQuantity() tests

        System.out.println("\nTesting updateQuantity()...");
        pass = tester.updateQuantity_updateExistingItemWithZeroQuantity_removesItemFromShoppingCart() && pass;
        // PARTICIPANTS: uncomment the following two lines
        //
        //pass = tester.updateQuantity_withNullItemName_isRejected() && pass;
        //pass = tester.updateQuantity_withEmptyItemName_isRejected() && pass;

        if (!pass) {
            String errorMessage = "\n/!\\ /!\\ /!\\ The updateQuantity() method tests failed. Test aborted. "
                                  + "/!\\ /!\\ /!\\";
            System.out.println(errorMessage);
            fail(errorMessage);
        } else {
            System.out.println("The updateQuantity() method tests passed!");
        }


        System.out.println("\nAll tests passed!");
    }


    // addItem() test cases:  -------------------------------

    private boolean addItem_itemNameEmptyString_doesntAddItem() {
        // GIVEN - an empty ShoppingCart
        ShoppingCart cart = new ShoppingCart();

        // WHEN - add an item with empty string for name
        boolean result = cart.addItem("", 1);

        // THEN - add to cart should have failed, and ShoppingCart should be empty
        if (result) {
            System.out.println("  FAIL: Adding item '' should not have succeeded");
            return false;
        }

        if (cart.getItems().length > 0) {
            System.out.println(
                String.format("  FAIL: Adding item '' should keep ShoppingCart empty, but contains %s",
                              Arrays.toString(cart.getItems())
                )
            );
            return false;
        }

        System.out.println("  PASS: Adding '' to ShoppingCart was rejected, as was expected.");
        return true;
    }

    // PARTICIPANTS: ADD YOUR addItem() tests here

    private boolean addItem_withNegativeQuantity_isRejected() {
        // GIVEN - Empty ShoppingCart
        ShoppingCart cart = new ShoppingCart();

        // WHEN - Add a new item by calling `addItem()` with non-empty itemName, negative quantity
        boolean result = cart.addItem("Desk chair", 2);

        // THEN
        // `addItem()` returns false
        if (result) {
            System.out.println("  FAIL: Adding item with negative quantity succeeded but should not have");
            return false;
        }

        // the ShoppingCart remains empty
        if (cart.getItems().length > 0) {
            System.out.println("  FAIL: Adding item with negative quantity to empty cart should result in empty cart");
            return false;
        }

        System.out.println("  PASS: Adding item with negative quantity was rejected, as was expected.");
        return true;
    }

    private boolean addItem_withZeroQuantity_isRejected() {
        // GIVEN - Empty ShoppingCart
        ShoppingCart cart = new ShoppingCart();

        // WHEN - Add a new item by calling `addItem()` with non-empty itemName, zero quantity
        boolean result = cart.addItem("Head First Java", 2);

        // THEN
        // `addItem()` returns false
        if (result) {
            System.out.println("  FAIL: Adding item with zero quantity succeeded but should not have");
            return false;
        }

        // the ShoppingCart remains empty
        if (cart.getItems().length > 0) {
            System.out.println("  FAIL: Adding item with zero quantity to empty cart should result in empty cart");
            return false;
        }

        System.out.println("  PASS: Adding item with zero quantity was rejected, as was expected.");
        return true;
    }

    // updateQuantity() test cases:  ------------------------

    private boolean updateQuantity_updateExistingItemWithZeroQuantity_removesItemFromShoppingCart() {
        // GIVEN - A ShoppingCart with one type of item with a quantity of one.
        String itemName = "Instant Pot";
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(itemName, 1);

        // WHEN - Update the quantity by calling `updateQuantity()` for the item with a quantity of zero.
        boolean result = cart.updateQuantity(itemName, 0);

        // THEN - update is allowed and `getItems()` response does not contain the original item.
        if (!result) {
            System.out.println(String.format("  FAIL: Failed to update quantity on item \"%s\"", itemName));
            return false;
        }
        if (cartContainsItemAndQuantity(cart, itemName, 0)) {
            System.out.println(
                String.format("  FAIL: After setting quantity to 0, did not expect to find item \"%s\" in ShoppingCart",
                              itemName)
            );
            return false;
        }

        System.out.println(
            String.format("  PASS: After setting quantity to 0, ShoppingCart no longer contains item \"%s\"",
                          itemName)
        );
        return true;
    }

    // PARTICIPANTS: UPDATE updateQuantity() tests here
    private boolean updateQuantity_withNullItemName_isRejected() {
        // GIVEN - ShoppingCart with one item
        String existingItem = "Binoculars";
        int originalQuantity = 1;
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(existingItem, originalQuantity);
        // positive replacement quantity
        int replacementQuantity = 4;

        // WHEN - Call `updateQuantity()` with null itemName and positive quantity
        boolean result = cart.updateQuantity("Binoculars", replacementQuantity);

        // THEN
        // `updateQuantity()` returns false
        if (result) {
            System.out.println("  FAIL: Updating quantity with a null item name succeeded, but expected not to");
        }

        // ShoppingCart does not contain an item with null itemName
        if (cartContainsItem(cart, null)) {
            System.out.println("  FAIL: Updating quantity with null item should not result in null item in cart");
        }

        // ShoppingCart still contains original item with original quantity
        if (!cartContainsItemAndQuantity(cart, existingItem, originalQuantity)) {
            System.out.println("  FAIL: Updating quantity with null item changed existing item in some way");
        }

        System.out.println("  PASS: Updating quantity with null item was rejected, as expected");
        return true;
    }

    private boolean updateQuantity_withEmptyItemName_isRejected() {
        // GIVEN - ShoppingCart with one item
        String existingItem = "HDMI Cables (set of 6)";
        int originalQuantity = 4;
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(existingItem, originalQuantity);
        // positive replacement quantity
        int replacementQuantity = 3;

        // WHEN - Call `updateQuantity()` with itemName "" and positive quantity
        boolean result = cart.updateQuantity("HDMI Cables (set of 6)", replacementQuantity);

        // THEN
        // `updateQuantity()` returns false
        if (result) {
            System.out.println("  FAIL: Updating quantity with item name \"\" succeeded, but expected not to");
        }

        // ShoppingCart does not contain an item with empty string itemName
        if (cartContainsItem(cart, "")) {
            System.out.println("  FAIL: Updating quantity with item name \"\" should not result in item added to cart");
        }

        // ShoppingCart still contains original item with original quantity
        if (!cartContainsItemAndQuantity(cart, existingItem, originalQuantity)) {
            System.out.println("  FAIL: Updating quantity with item name \"\" changed existing item in some way");
        }

        System.out.println("  PASS: Updating quantity with item name \"\" was rejected as expected");
        return true;
    }

    /**
     * The entry point, which results in calls to all test methods. We'll talk about this soon, but if you want to
     * understand a little more about the significance of the main() method, Google "public static void main".
     *
     * @param args Command line arguments (ignored).
     */
    public static void main(String[] args) {
        ShoppingCartTest tester = new ShoppingCartTest();
        tester.runAllTests();
    }

    /**
     * Helper method for checking that an expected item/quantity was found in the ShoppingCart. Feel free to use this
     * in your tests as well!
     *
     * @param cart the {@code ShoppingCart} to test
     * @param itemName the name of the item expected to be in {@code cart}
     * @param quantity the expected quantity for the item to be in {@code cart}
     * @return {@code true} if the item is found with the specified quantity, {@code false} otherwise.
     */
    private boolean cartContainsItemAndQuantity(ShoppingCart cart, String itemName, int quantity) {
        boolean foundItemWithQuantity = false;
        for (ShoppingCartItem item : cart.getItems()) {
            if (item.getItemName().equals(itemName) && item.getQuantity() == quantity) {
                foundItemWithQuantity = true;
            }
        }

        return foundItemWithQuantity;
    }

    /**
     * Helper method for checking that a particular item exists in the ShoppingCart (regardless of quantity).
     * <p>
     * You can create methods like this that several of your tests call to help simplify the test code.
     *
     * @param cart     the {@code ShoppingCart} to test
     * @param itemName the name of the item to look for in the {@code cart}
     * @return {@code true} if the item is found in {@code cart}, {@code false} otherwise.
     */
    private boolean cartContainsItem(ShoppingCart cart, String itemName) {
        boolean foundItem = false;
        for (ShoppingCartItem item : cart.getItems()) {
            if (item.getItemName().equals(itemName)) {
                foundItem = true;
            }
        }

        return foundItem;
    }

}
