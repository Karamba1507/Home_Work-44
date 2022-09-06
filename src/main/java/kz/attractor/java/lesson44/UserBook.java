package kz.attractor.java.lesson44;

public class UserBook {
    private int id;
    private String author;
    private String title;
    private String description;
    private String emplyeEmail;
    private boolean userHandling;

    public UserBook(int id, String author, String title, String description, String emplyeEmail, boolean userHandling) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.emplyeEmail = emplyeEmail;
        this.userHandling = userHandling;
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

    public boolean isUserHandling() {
        return userHandling;
    }

    public void setUserHandling(boolean userHandling) {
        this.userHandling = userHandling;
    }
}
