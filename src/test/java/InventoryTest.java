import com.group35.project.Inventory.Inventory;
import com.group35.project.Book.Book;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class InventoryTest {
    private Inventory inventory;
    private Book book1;
    private Book book2;

    @Before
    public void setUp() {
        inventory = new Inventory();

        book1 = new Book("123-456-789", "Test Title 1", "Test Author 1", "Test Publisher 1",
                "Test Description 1", 19.99, "http://example.com/image1.jpg");
        book2 = new Book("987-654-321", "Test Title 2", "Test Author 2", "Test Publisher 2",
                "Test Description 2", 29.99, "http://example.com/image2.jpg");

        // Simulate persistence by adding books directly to the inventory with IDs
        inventory.getAllBooks().put(1L, book1);
        inventory.getStock().put(1L, 1);
        inventory.getAllBooks().put(2L, book2);
        inventory.getStock().put(2L, 1);
    }

    @Test
    public void testAddBook() {
        Book book3 = new Book("555-666-777", "Test Title 3", "Test Author 3",
                "Test Publisher 3", "Test Description 3", 39.99, "http://example.com/image3.jpg");
        inventory.getAllBooks().put(3L, book3);
        inventory.getStock().put(3L, 1);
        assertEquals(3, inventory.getSize());
        assertEquals(book3, inventory.getBook(3L));
    }

    @Test
    public void testGetAllBooks() {
        Map<Long, Book> books = inventory.getAllBooks();
        assertEquals(2, books.size());
        assertTrue(books.containsKey(1L));
        assertTrue(books.containsKey(2L));
    }


    @Test
    public void testGetBookByISBN() {
        Book retrievedBook = inventory.getBookByISBN("123-456-789");
        assertEquals(book1, retrievedBook);
    }

    @Test
    public void testGetBook() {
        Book retrievedBook = inventory.getBook(1L);
        assertEquals(book1, retrievedBook);
    }

    @Test
    public void testGetSize() {
        assertEquals(2, inventory.getSize());
    }

    @Test
    public void testRemoveBook() {
        assertTrue(inventory.removeBook(1L));
        assertFalse(inventory.hasBook("123-456-789"));
    }

    @Test
    public void testIncreaseStock() {
        assertTrue(inventory.increaseStock(1L, 3));
        assertEquals(4, inventory.getStock().get(1L).intValue());
    }

    @Test
    public void testDecreaseStock() {
        assertTrue(inventory.decreaseStock(1L, 1));
        assertEquals(0, inventory.getStock().get(1L).intValue());
    }

    @Test
    public void testDecreaseStockInsufficient() {
        assertFalse(inventory.decreaseStock(1L, 2));
    }

    @Test
    public void testRemoveBookByISBN() {
        assertTrue(inventory.removeBookByISBN("123-456-789"));
        assertFalse(inventory.hasBook("123-456-789"));
    }

    @Test
    public void testToString() {
        String inventoryString = inventory.toString();
        assertTrue(inventoryString.contains("Test Title 1"));
        assertTrue(inventoryString.contains("Test Title 2"));
    }

}
