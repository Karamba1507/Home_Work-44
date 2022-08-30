package kz.attractor.java.lesson44;

public class Message {

    public Message(String text) {
        this.text = text;
    }

    public Message() {
    }

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
