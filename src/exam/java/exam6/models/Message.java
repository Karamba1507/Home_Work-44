package exam.java.exam6.models;

public class Message {

    public Message(String message) {
        this.message = message;
    }

    public Message() {
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
