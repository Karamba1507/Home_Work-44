package exam.java.exam6;

import java.util.List;

public class Events {
    private int date;
    private List<Event> eventList;

    public Events(int date, List<Event> eventList) {
        this.date = date;
        this.eventList = eventList;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }
}
