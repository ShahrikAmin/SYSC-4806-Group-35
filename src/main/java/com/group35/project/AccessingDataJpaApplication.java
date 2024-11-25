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
            Book book1 = new Book("12345", "The Book", "The Author", "The Publisher", "The Epic Description", 9.99, "The Ultimate Picture URL");
            Book book2 = new Book("23451", "The Book 2", "The Author", "The Publisher", "The Epic Description 2", 9.99, "The Ultimate Picture URL");
            Book book3 = new Book("34512", "The Book 3", "The Author", "The Publisher", "The Epic Description 3", 9.99, "The Ultimate Picture URL");

            // Save books to the repository and ensure they are managed
            book1 = bookRepository.save(book1);
            book2 = bookRepository.save(book2);
            book3 = bookRepository.save(book3);

            Inventory bookInventory = new Inventory();  //Creates Book inventory
            inventoryRepository.save(bookInventory);

            bookInventory.addBook(book1);
            bookInventory.addBook(book2);
            bookInventory.addBook(book3);

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
