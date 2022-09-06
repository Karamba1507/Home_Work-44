package kz.attractor.java.lesson44;

import java.util.ArrayList;
import java.util.List;

public class UserBooks {

    private List<UserBook> books = new ArrayList<>();

    public List<UserBook> getBooks() {
        return books;
    }

    public void setBooks(List<UserBook> books) {
        this.books = books;
    }
}
