package com.group35.project.Book;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/***
 * BookRepository class for CRUD operation use.
 * @author Chibuzo Okpara v1
 */
public interface BookRepository extends CrudRepository<Book, Long>{
    List<Book> findByTitle(String title);
    List<Book> findByTitleContaining(String word);

}
