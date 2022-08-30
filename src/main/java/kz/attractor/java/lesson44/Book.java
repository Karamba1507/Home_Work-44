package kz.attractor.java.lesson44;

public class Book {
    private int id;
    private String author;
    private String title;
    private String description;
    private Integer employeeId;


    public Book(int id, String author, String title, String description, Integer employeeId) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.employeeId = employeeId;

    }

    public Book(Book book) {
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

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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
