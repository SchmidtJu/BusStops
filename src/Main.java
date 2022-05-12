import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

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

        //Generate Timetable with a given start and endtime and an interval
        //millerStreet.addArrivals(LocalTime.of(5,0),  LocalTime.of(23, 0), 18);

        System.out.println("Manuell");
        printNextArrival(LocalTime.now());
        printNextArrival(LocalTime.of(5, 2));
        printNextArrival(LocalTime.of(10, 59));
        printNextArrival(LocalTime.of(23, 16));
        printNextArrival(LocalTime.of(23, 58));
        printNextArrival(LocalTime.of(3, 2));

        System.out.println("Interval");
        printnextArrivals(LocalTime.of(15, 15), 23, 10);
    }


    static void printNextArrival (LocalTime time) {
        System.out.println(time.toString() + " -> " + millerStreet.getNextArrival(time.getHour(), time.getMinute()));
    }


    private static void printnextArrivals(LocalTime time, int interval, int numberOfArrivals) {
        for (int i = 0; i < numberOfArrivals; i++) {
            printNextArrival(time);
            time = time.plus(Duration.ofMinutes(interval));
        }
    }


    static class Timetable {
        private HashMap<Integer, TreeSet<Integer>> arrivals;

        public Timetable() {
            arrivals = new HashMap<>();
        }

        public void addArrivals(int hour, List<Integer> minutes) {
            arrivals.put(hour, new TreeSet<>(minutes));
        }

        public void addArrivals(List<Integer>hours, List<Integer> minutes) {
            for (int hour : hours) {
                addArrivals(hour, minutes);
            }
        }

        public void addArrivals(LocalTime start, LocalTime end, int interval) {
            while (start.isBefore(end)) {
                TreeSet<Integer> minutes = arrivals.get(start.getHour());
                if (minutes == null) {
                    minutes = new TreeSet<>();
                }
                minutes.add(start.getMinute());
                arrivals.put(start.getHour(), minutes);
                start = start.plus(Duration.ofMinutes(interval));
            }

        }


        public LocalTime getNextArrival(int hour, int minute) {
            Integer nextMinute = null;

            if (hour >= 24) {
                hour = 0;
            }

            TreeSet<Integer> minutes = arrivals.get(hour);
            if (minutes != null){
                //nextMinute = minutes.stream().filter(m -> m > minute).findFirst().orElse(null);
                nextMinute = minutes.higher(minute);
            }

            if (nextMinute == null) {
                return getNextArrival(hour+1, 0);
            } else {
                return LocalTime.of(hour, nextMinute);
            }
        }

    }
}