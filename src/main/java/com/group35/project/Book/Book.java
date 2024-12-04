package com.group35.project.Book;
import com.group35.project.Inventory.Inventory;
import jakarta.persistence.*;

import java.util.Objects;

/***
 * Book class contains info about a book
 * @author chibuzo okpara v1
 */

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generate the ID
    private Long id;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;
    private String author;
    private String publisher;
    private String description;
    private Double price;

    private String pictureUrl;


    public Book(String isbn, String title, String author, String publisher, String description, Double price, String
            pictureUrl){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;
    public Book(){}

    public Long getId(){
        return id;
    }
    public String getIsbn(){
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    public Inventory getInventory() {
        return inventory;
    }
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", price=" + price +
                ", description=" + description + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn); // Compare based on ISBN
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn); // Use ISBN for hash code
    }


}
