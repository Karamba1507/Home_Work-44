package kz.attractor.java.lesson44;

public class Employee {
    private int id;
    private String name;
    private int age;
    private Integer bookId;

    public Employee(int id, String name, int age, Integer bookId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}' + '\n';
    }
}
