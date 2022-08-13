package bookstore;

public class Book {
    public int bookId;
    public String categoryCode;
    public String title, author, publisher;
    public double price;

    public Book(int bookId, String title) {
        this.bookId = bookId;
        this.title = title;
    }


    public void setTitle(String sTitle) {
        this.title = sTitle;
    }

    public void setAuthor(String sAuthor) {
        this.author = sAuthor;
    }

    public void setPublisher(String sPublisher) {
        this.publisher = sPublisher;
    }

    public void setCategory(String sCategory) {
        this.categoryCode = sCategory;
    }

    public void setPrice(double dPrice) {
        this.price = dPrice;
    }

}