import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class Timetable2 {
    private HashMap<Integer, List<Integer>> arrivals;

    public Timetable2() {
        arrivals = new HashMap<>();
    }

    public void addArrivals(Integer hour, List<Integer> minutes) {
        arrivals.put(hour, minutes);

    }

    public void addArrivals(List<Integer>hours, List<Integer> minutes) {
        for (Integer hour : hours) {
            addArrivals(hour, minutes);
        }
    }


    public LocalTime getNextArrival(Integer hour, Integer minute) {
        if (hour >= 24) {
            hour = 0;
        }

        List<Integer> minutes = arrivals.get(hour);
        Integer nextMinute = null;

        if (minutes != null){
            nextMinute = minutes.stream().filter(m -> m > minute).findFirst().orElse(null);
        }

        if (nextMinute == null) {
            return getNextArrival(hour+1, 0);
        } else {
            return LocalTime.of(hour, nextMinute);
        }
    }

}