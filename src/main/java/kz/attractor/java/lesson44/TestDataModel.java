package kz.attractor.java.lesson44;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestDataModel {
    private SampleDocument document;
    private List<SampleDataModel.User> customers = new ArrayList<>();

    public TestDataModel() {
        this.document = new SampleDocument(1, LocalDate.now(), "test text");
        customers.add(new SampleDataModel.User("Marco"));
        customers.add(new SampleDataModel.User("Winston", "Duarte"));
        customers.add(new SampleDataModel.User("Amos", "Burton", "'Timmy'"));
        customers.get(1).setEmailConfirmed(true);
    }

    public void setDocument(SampleDocument document) {
        this.document = document;
    }

    public SampleDocument getDocument() {
        return document;
    }

    public List<SampleDataModel.User> getCustomers() {
        return customers;
    }
}
