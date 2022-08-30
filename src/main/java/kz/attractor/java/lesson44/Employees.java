package kz.attractor.java.lesson44;

import java.util.ArrayList;
import java.util.List;

public class Employees {
    private List<Employee> employees = new ArrayList<>();

    public Employees(List<Employee> employees) {
        this.employees = employees;
    }

    public Employees() {
        FileServiceEmployees fse = new FileServiceEmployees();
        this.employees = fse.readString();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
