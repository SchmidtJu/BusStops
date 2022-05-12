import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    static Timetable millerStreet = new Timetable();

    public static void main(String[] args) {

        //Generate Timetable for Millerstreet
        millerStreet.addArrivals(5, Arrays.asList(4, 39, 59));
        millerStreet.addArrivals(6, Arrays.asList(19, 29, 39, 49, 59));
        millerStreet.addArrivals(7, Arrays.asList(3, 9, 23, 29, 43, 49));
        millerStreet.addArrivals(8, Arrays.asList(3, 9, 23, 29, 43, 49));
        millerStreet.addArrivals(Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19) , Arrays.asList(3, 9, 19, 29, 39, 49, 56));
        millerStreet.addArrivals(20, Arrays.asList(6, 16, 36));
        millerStreet.addArrivals(Arrays.asList(21, 22, 23), Arrays.asList(16, 36, 56));


        printNextArrival(LocalTime.now().getHour(), LocalTime.now().getMinute());
        printNextArrival(5, 2);
        printNextArrival(10, 59);
        printNextArrival(23, 16);
        printNextArrival(23, 58);
        printNextArrival(3, 2);
    }


    static void printNextArrival (Integer hour, Integer minute) {
        System.out.println(LocalTime.of(hour, minute).toString() + " -> " + millerStreet.getNextArrival(hour, minute).toString());
    }


    static class Timetable {
        private HashMap<Integer, List<Integer>> arrivals;

        public Timetable() {
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
            Integer nextMinute = null;

            if (hour >= 24) {
                hour = 0;
            }

            List<Integer> minutes = arrivals.get(hour);

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
}


