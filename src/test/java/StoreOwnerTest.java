import com.group35.project.Book.Book;
import com.group35.project.Inventory.Inventory;
import com.group35.project.StoreOwner;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * StoreOwner test class. tests methods contained in Storeowner.
 * @author Chibuzo Okpara v1
 */
public class StoreOwnerTest {
    private StoreOwner storeOwner;
    private Inventory inventory;

    @Before
    public void setUp() {
        inventory = new Inventory();
        storeOwner = new StoreOwner(inventory);

        Book book1 = new Book("123-456-789", "Test Title 1", "Test Author 1", "Test Publisher 1", "Test Description 1", 19.99, "http://example.com/image1.jpg");
        Book book2 = new Book("987-654-321", "Test Title 2", "Test Author 2", "Test Publisher 2", "Test Description 2", 29.99, "http://example.com/image2.jpg");

        inventory.getAllBooks().put(1L, book1);
        inventory.getStock().put(1L, 1);
        inventory.getAllBooks().put(2L, book2);
        inventory.getStock().put(2L, 1);
    }

    @Test
    public void testAddBook() {
        Book newBook = new Book("555-666-777", "New Book", "New Author",
                "New Publisher", "New Description", 39.99, "http://example.com/image3.jpg");

        inventory.getAllBooks().put(3L, newBook);
        inventory.getStock().put(3L, 1);

        assertEquals(3, inventory.getSize());
        assertNotNull(inventory.getBookByISBN("555-666-777"));
    }
    @Test
    public void testRemoveBook() {
        boolean removed = storeOwner.removeBook("123-456-789");

        assertTrue(removed);
        assertFalse(inventory.hasBook("123-456-789"));
    }


    @Test
    public void testEditBook() {
        boolean updated = storeOwner.editBook(1L, "111-111-111", "Updated Title",
                "Updated Author", "Updated Publisher", "Updated Description", 49.99, "http://example.com/image4.jpg");

        assertTrue(updated);
        Book updatedBook = inventory.getBook(1L);
        assertNotNull(updatedBook);
        assertEquals("111-111-111", updatedBook.getIsbn());
        assertEquals("Updated Title", updatedBook.getTitle());
        assertEquals("Updated Author", updatedBook.getAuthor());
        assertEquals("Updated Publisher", updatedBook.getPublisher());
        assertEquals("Updated Description", updatedBook.getDescription());
        assertEquals(49.99, updatedBook.getPrice(), 0.01);
        assertEquals("http://example.com/image4.jpg", updatedBook.getPictureUrl());
    }

    @Test
    public void testGetInventory() {
        Inventory retrievedInventory = storeOwner.getInventory();

        assertNotNull(retrievedInventory);
        assertEquals(inventory, retrievedInventory);
    }
}
