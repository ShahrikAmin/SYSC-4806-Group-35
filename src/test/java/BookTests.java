import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import com.group35.project.Book.Book;

/**
 * tests methods contained in Book class.
 * @author chibuzo okpara v1
 */
public class BookTests {

    private Book book;

    @Before
    public void setUp() {
        book = new Book(
                "123-456-789",
                "Test Title",
                "Test Author",
                "Test Publisher",
                "Test Description",
                19.99,
                "http://example.com/image.jpg"
        );
    }

    @Test
    public void testIsbnGetterAndSetter() {
        assertEquals("123-456-789", book.getIsbn());
        book.setIsbn("987-654-321");
        assertEquals("987-654-321", book.getIsbn());
    }

    @Test
    public void testTitleGetterAndSetter() {
        assertEquals("Test Title", book.getTitle());
        book.setTitle("New Title");
        assertEquals("New Title", book.getTitle());
    }

    @Test
    public void testAuthorGetterAndSetter() {
        assertEquals("Test Author", book.getAuthor());
        book.setAuthor("New Author");
        assertEquals("New Author", book.getAuthor());
    }

    @Test
    public void testPublisherGetterAndSetter() {
        assertEquals("Test Publisher", book.getPublisher());
        book.setPublisher("New Publisher");
        assertEquals("New Publisher", book.getPublisher());
    }

    @Test
    public void testDescriptionGetterAndSetter() {
        assertEquals("Test Description", book.getDescription());
        book.setDescription("New Description");
        assertEquals("New Description", book.getDescription());
    }

    @Test
    public void testPriceGetterAndSetter() {
        assertEquals(19.99, book.getPrice(), 0.01);
        book.setPrice(29.99);
        assertEquals(29.99, book.getPrice(), 0.01);
    }

    @Test
    public void testPictureUrlGetterAndSetter() {
        assertEquals("http://example.com/image.jpg", book.getPictureUrl());
        book.setPictureUrl("http://example.com/new-image.jpg");
        assertEquals("http://example.com/new-image.jpg", book.getPictureUrl());
    }

    @Test
    public void testToString() {
        String expected = "Book{id=null, isbn='123-456-789', title='Test Title', author='Test Author', publisher='Test " +
                "Publisher', price=19.99, description=Test Description', pictureUrl='http://example.com/image.jpg'}";
        assertEquals(expected, book.toString());
    }

}
