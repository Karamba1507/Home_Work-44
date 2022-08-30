package kz.attractor.java.lesson44;

import java.util.ArrayList;
import java.util.List;

public class Books {
    private List<Book> books = new ArrayList<>();

    public Books(List<Book> books) {
        this.books = books;
    }

    public Books() {
        FileServiceBooks fsb = new FileServiceBooks();
        this.books= fsb.readString();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
