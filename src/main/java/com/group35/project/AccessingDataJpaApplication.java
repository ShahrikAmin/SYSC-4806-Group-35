package com.group35.project;

import com.group35.project.Book.Book;
import com.group35.project.Book.BookRepository;
import com.group35.project.Inventory.Inventory;
import com.group35.project.Inventory.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class AccessingDataJpaApplication {
    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJpaApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner bookRun(BookRepository repository) {
//        return (args) -> {
//
//            repository.save(new Book("12345", "The Book", "The Author", "The Publisher", "The Epic Description", 9.99, "The Ultimate Picture URL"));
//            repository.save(new Book("23451", "The Book 2", "The Author", "The Publisher", "The Epic Description 2", 9.99, "The Ultimate Picture URL"));
//            repository.save(new Book("34512", "The Book 3", "The Author", "The Publisher", "The Epic Description 3", 9.99, "The Ultimate Picture URL"));
//            repository.save(new Book("45123", "The Book 4", "The Author's Pupil", "The Publisher's Pupil", "The Epic Description 4", 9.99, "The Ultimate Picture URL"));
//            repository.save(new Book("51234", "The Book 5", "The Author's Pupil", "The Publisher's Pupil", "The Epic Description 5", 9.99, "The Ultimate Picture URL"));
//            repository.save(new Book("54321", "The Book 6", "The Author's Pupil", "The Publisher's Pupil", "The Epic Description 6", 9.99, "The Ultimate Picture URL"));
//
//            // fetch all books
//            log.info("Books found with findAll():");
//            log.info("-------------------------------");
//            repository.findAll().forEach(book -> {
//                log.info(book.toString());
//            });
//            log.info("");
//
//            // fetch an individual buddyinfo by ID
//            Optional<Book> book1 = repository.findById(1L);
//            log.info("Book found with findById(1L):");
//            log.info("--------------------------------");
//            log.info(book1.toString());
//            log.info("");
//
//            // fetch book by title
//            log.info("Book found with findByTitle('The Book'):");
//            log.info("--------------------------------------------");
//            repository.findByTitle("The Book").forEach(title -> {
//                log.info(title.toString());
//            });
//            log.info("");
//        };
//    }


    @Bean
    public CommandLineRunner inventoryRun(BookRepository bookRepository, InventoryRepository inventoryRepository) {
        return (args) -> {
            Book book1 = new Book("978-0060934347", "Don Quixote", "Miguel de Cervantes", "Harper Perennial Modern Classics", "Often regarded as the first modern novel, this Spanish classic follows the adventures of a delusional nobleman who becomes a self-styled knight-errant, accompanied by his loyal squire, Sancho Panza.", 15.99, "The Ultimate Picture URL");
            Book book2 = new Book("978-0141439600", "A Tale of Two Cities", "Charles Dickens", "Penguin Classics", "Set against the backdrop of the French Revolution, this novel explores themes of resurrection and sacrifice through the intertwined lives of characters in London and Paris.", 9.99, "The Ultimate Picture URL");
            Book book3 = new Book("978-0544003415", "The Lord of the Rings", "J.R.R. Tolkien", "Mariner Books", "An epic fantasy trilogy chronicling the quest to destroy the One Ring and the battle between good and evil in Middle-earth.", 29.99, "The Ultimate Picture URL");
            Book book4 = new Book("978-0156012195", "The Little Prince", "Antoine de Saint-Exupéry", "Mariner Books", "A poetic tale of a young prince's travels and his reflections on life, love, and human nature.", 10.99, "The Ultimate Picture URL");
            Book book5 = new Book("978-1408855652", "Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Bloomsbury Children's Books", "The first book in the Harry Potter series introduces readers to the young wizard and his adventures at Hogwarts School of Witchcraft and Wizardry.", 11.99, "The Ultimate Picture URL");
            Book book6 = new Book("978-0062073488", "And Then There Were None", "Agatha Christie", "William Morrow Paperbacks", "A gripping mystery where ten strangers are invited to an isolated island, only to be killed one by one.", 14.99, "The Ultimate Picture URL");
            Book book7 = new Book("978-0547928227", "The Hobbit", "J.R.R. Tolkien", "Mariner Books", "A prelude to \"The Lord of the Rings,\" this novel follows Bilbo Baggins on an unexpected journey filled with adventure and peril.", 14.99, "The Ultimate Picture URL");
            Book book8 = new Book("978-0140442939", "Dream of the Red Chamber", "Cao Xueqin", "Penguin Classics", "One of China's Four Great Classical Novels, it offers a detailed, episodic record of the lives of two branches of a large, aristocratic family.", 19.99, "The Ultimate Picture URL");
            Book book9 = new Book("978-0064471046", "The Lion, the Witch and the Wardrobe", "C.S. Lewis", "HarperCollins", "The first published book in \"The Chronicles of Narnia\" series, it tells the story of four children who discover a magical world inside a wardrobe.", 7.99, "The Ultimate Picture URL");
            Book book10 = new Book("978-0307474278", "The Da Vinci Code", "Dan Brown", "Anchor", "A mystery thriller that follows symbologist Robert Langdon as he uncovers secrets hidden in works of art and religious texts.", 9.99, "The Ultimate Picture URL");


            // Save books to the repository and ensure they are managed
            book1 = bookRepository.save(book1);
            book2 = bookRepository.save(book2);
            book3 = bookRepository.save(book3);
            book4 = bookRepository.save(book4);
            book5 = bookRepository.save(book5);
            book6 = bookRepository.save(book6);
            book7 = bookRepository.save(book7);
            book8 = bookRepository.save(book8);
            book9 = bookRepository.save(book9);
            book10 = bookRepository.save(book10);

            Inventory bookInventory = new Inventory();  //Creates Book inventory
            inventoryRepository.save(bookInventory);

            bookInventory.addBook(book1);
            bookInventory.addBook(book2);
            bookInventory.addBook(book3);
            bookInventory.addBook(book4);
            bookInventory.addBook(book5);
            bookInventory.addBook(book6);
            bookInventory.addBook(book7);
            bookInventory.addBook(book8);
            bookInventory.addBook(book9);
            bookInventory.addBook(book10);

            bookInventory.increaseStock(1L, 4);
            bookInventory.increaseStock(3L, 2);
            bookInventory.increaseStock(4L, 6);
            bookInventory.increaseStock(7L, 8);
            bookInventory.increaseStock(9L, 4);
            bookInventory.increaseStock(10L, 1);

            inventoryRepository.save(bookInventory);

            log.info("bookInventory found with findAll():");
            log.info("-------------------------------");
            inventoryRepository.findAll().forEach(inventory -> {
                log.info(inventory.toString());
            });
            log.info("");


            // fetch an individual book by ID
            Inventory inventory = inventoryRepository.findById(1L);
            log.info("inventory found with findById(1L):");
            log.info("--------------------------------");
            log.info(inventory.toString());
            log.info("");

            /// get books in each address book
            log.info("books in address books:");
            inventoryRepository.findAll().forEach(ab -> {
                ab.getAllBooks().values().forEach(b -> log.info(b.getTitle() + " " + b.getId()));
            });
        };

    }

}
