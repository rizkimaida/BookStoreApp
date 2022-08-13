package bookstore;
import bookstore.Book;

public class Transactions {
    public int bookId;
    public double price;
    public int bookCount;
    public Book bookRef;

    private double totalPrice;

    public Transactions(Book bookRef, int bookCount) {
        this.bookRef = bookRef;
        this.bookId = bookRef.bookId;
        this.price = bookRef.price;
        this.bookCount = bookCount;
        calculatePrice(price, bookCount);
    }

    public void calculatePrice(double price, int bookCount) {
        this.totalPrice = price * bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
        calculatePrice(this.price, bookCount);
    }

    public void setBookRef(Book bookRef) {
        this.bookRef = bookRef;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

}
