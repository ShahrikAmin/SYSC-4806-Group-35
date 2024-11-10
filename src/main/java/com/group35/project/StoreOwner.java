public class StoreOwner {
    private Inventory inventory;

    public StoreOwner() {
        this.inventory = new Inventory();
    }

    public StoreOwner(Inventory inventory) {
        this.inventory = inventory;
    }

    public void addBook(String title, String author, String isbn) {
        Book book = new Book(title, author, isbn);
        inventory.addBook(book);
        System.out.println("Book added to inventory: " + book);
    }

    public boolean removeBook(String isbn) {
        boolean removed = inventory.removeBook(isbn);
        if (removed) {
            System.out.println("Book removed from inventory with ISBN: " + isbn);
        } else {
            System.out.println("Book not found with ISBN: " + isbn);
        }
        return removed;
    }

    public void displayInventory() {
        inventory.displayBooks();
    }

    public boolean editBook(int index, String newTitle, String newAuthor, String newIsbn) {
        Book book = inventory.getBook(index);
        if (book != null) {
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            book.setIsbn(newIsbn);
            System.out.println("Book at index " + index + " has been updated: " + book);
            return true;
        } else {
            System.out.println("No book found at index: " + index);
            return false;
        }
    }
}
