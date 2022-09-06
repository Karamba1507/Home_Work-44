package kz.attractor.java.lesson44;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private List<User> users = new ArrayList<>();

    public Users() {
        this.users = users;
    }

    public void fillUserList() {

        User user1 = new User(1, "Bill", 40, "12@m.ru", "123");
        User user2 = new User(2, "Ben", 35, "15@m.ru", "123");
        User user3 = new User(3, "Join", 30, "18@m.ru", "123");
        User user4 = new User(4, "Donald", 28, "22@m.ru", "123");
        User user5 = new User(5, "Diego", 33, "32@m.ru", "123");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
