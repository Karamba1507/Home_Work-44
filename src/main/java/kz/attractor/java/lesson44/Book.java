package kz.attractor.java.lesson44;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private int id;
    private String author;
    private String title;
    private String description;
    private String emplyeEmail;
    private List<String> usageHistory = new ArrayList<>();



    public Book(int id, String author, String title, String description) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmplyeEmail() {
        return emplyeEmail;
    }

    public void setEmplyeEmail(String emplyeEmail) {
        this.emplyeEmail = emplyeEmail;
    }

    public List<String> getUsageHistory() {
        return usageHistory;
    }

    public void setUsageHistory(List<String> usageHistory) {
        this.usageHistory = usageHistory;
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                + '\n';
    }
}
