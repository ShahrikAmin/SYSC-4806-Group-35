import com.group35.project.Book.Book;
import com.group35.project.Inventory.Inventory;
import com.group35.project.ShoppingCart.ShoppingCart;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * tests methods contained in ShoppingCart class.
 * @author chibuzo okpara
 */

public class ShoppingCartTest {
    private ShoppingCart shoppingCart;
    private Inventory inventory;
    private Book book1;
    private Book book2;

    @Before
    public void setUp() {
        inventory = new Inventory();
        shoppingCart = new ShoppingCart(inventory);

        book1 = new Book("123-456-789", "Book One", "Author One", "Publisher One",
                "Description One", 19.99, "example.com");
        book2 = new Book("987-654-321", "Book Two", "Author Two", "Publisher Two",
                "Description Two", 29.99, "example.com");

        inventory.getAllBooks().put(1L, book1);
        inventory.getStock().put(1L, 5);
        inventory.getAllBooks().put(2L, book2);
        inventory.getStock().put(2L, 10);
    }

    @Test
    public void testAddToCart() {
        shoppingCart.addToCart(book1, 2);
        shoppingCart.addToCart(book2, 3);

        Map<Book, Integer> cartItems = shoppingCart.getCartItems();

        assertEquals(2, cartItems.get(book1).intValue());
        assertEquals(3, cartItems.get(book2).intValue());
    }

    @Test
    public void testRemoveFromCart() {
        shoppingCart.addToCart(book1, 2);

        assertTrue(shoppingCart.getCartItems().containsKey(book1));

        boolean removed = shoppingCart.removeFromCart(book1);
        assertTrue(removed);
        assertFalse(shoppingCart.getCartItems().containsKey(book1));
    }

    @Test
    public void testGetCartItems() {
        shoppingCart.addToCart(book1, 2);
        shoppingCart.addToCart(book2, 3);

        Map<Book, Integer> cartItems = shoppingCart.getCartItems();

        assertEquals(2, cartItems.size());
        assertTrue(cartItems.containsKey(book1));
        assertTrue(cartItems.containsKey(book2));
    }

    @Test
    public void testGetInventory() {

        assertNotNull(shoppingCart.getInventory());
        assertEquals(inventory, shoppingCart.getInventory());
    }

    @Test
    public void testClearCart() {
        shoppingCart.addToCart(book1, 2);
        shoppingCart.addToCart(book2, 3);

        shoppingCart.clearCart();
        assertTrue(shoppingCart.getCartItems().isEmpty());
    }
}
