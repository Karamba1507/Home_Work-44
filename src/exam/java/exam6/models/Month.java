package exam.java.exam6.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Month {

    private List<Day> days = new ArrayList<>();

    public Month() {
        fillMonth();
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    private void fillMonth() {

        for (int i = 1; i < 31; i++) {

            LocalDateTime dateTime = LocalDateTime.now();

            int dayOfMonth = dateTime.getDayOfMonth();

            Day day = new Day("September", i, i == dayOfMonth);

            days.add(day);
            
        }
    }

}
