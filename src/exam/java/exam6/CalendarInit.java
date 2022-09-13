package exam.java.exam6;

import java.util.ArrayList;
import java.util.List;

public class CalendarInit {

    private List<Events> all;

    public List<Events> getAll() {
        return all;
    }

    public void setAll(List<Events> all) {
        this.all = all;
    }

    public CalendarInit() {
        all = new ArrayList<>(29);

        for (int i = 0; i < 29; i++) {
            all.add(new Events(i + 1, new ArrayList<>()));
        }
    }
}
