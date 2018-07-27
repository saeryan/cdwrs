import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;


public class TimeFormatter {
    private static final int DAYS_IN_YEAR = 365;
    private static final int SECONDS_IN_YEAR = 31536000;
    private static final int HOURS_IN_DAY = 24;
    private static final int SECONDS_IN_DAY = 86400;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int SECONDS_IN_HOUR = 3600;
    private static final int MINUTES_IN_HOUR = 60;
    private static final String PLURAL_LETTER = "s";

    private static int getYears(int seconds){
        int result = 0;
        while(getDays(seconds)>=DAYS_IN_YEAR){
            result++;
            seconds-=SECONDS_IN_YEAR;
        }
        return result;
    }
    private static int getDays(int seconds){
        int result = 0;
        while(getHours(seconds)>=HOURS_IN_DAY){
            result++;
            seconds-=SECONDS_IN_DAY;
        }
        return result;
    }
    private static int getHours(int seconds){
        int result = 0;
        while(getMinutes(seconds)>=MINUTES_IN_HOUR){
            result++;
            seconds-=SECONDS_IN_HOUR;
        }
        return result;
    }
    private static int getMinutes(int seconds){
        int result = 0;
        while(seconds >= SECONDS_IN_MINUTE){
            result++;
            seconds-=SECONDS_IN_MINUTE;
        }
        return result;
    }

    private static LinkedHashMap<String, Integer> getMap (int seconds){
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        while(getYears(seconds)>=1){
            result.merge("year", getYears(seconds), Integer::sum);
            seconds-=getYears(seconds)*SECONDS_IN_YEAR;
        }
        while(getDays(seconds)>=1){
            result.merge("day", getDays(seconds), Integer::sum);
            seconds-=getDays(seconds)*SECONDS_IN_DAY;
        }
        while(getHours(seconds)>=1){
            result.merge("hour", getHours(seconds), Integer::sum);
            seconds-=getHours(seconds)*SECONDS_IN_HOUR;
        }
        while(getMinutes(seconds)>=1){
            result.merge("minute", getMinutes(seconds), Integer::sum);
            seconds-=getMinutes(seconds)*SECONDS_IN_MINUTE;
        }
        while(seconds>=1){
            result.merge("second", seconds, Integer::sum);
            seconds = 0;
        }
        return result;

    }
    public static void main(String[] rags){
        System.out.println(formatDuration(1));
    }
    private static String format(LinkedHashMap<String, Integer> time) {
        StringBuilder result = new StringBuilder();
        int setSize = time.keySet().size();
        LinkedList<AtomicInteger> arr = new LinkedList<>();
        arr.add(new AtomicInteger(0));
        if(setSize == 1){
            time.forEach((k, v)->{
                result.append(v);
                result.append(" ").append(k);
                if(v>1){
                    result.append(PLURAL_LETTER);
                }
            });
            return result.toString();
        }
        time.forEach((k, v)->{
            int current = arr.getLast().getAndIncrement();
            result.append(v);
            result.append(" ");
            result.append(k);
            if(v>1) result.append(PLURAL_LETTER);
            if(current== setSize-2){
                result.append(" and ");
            } else if(current < setSize-1){
                result.append(", ");
            }

        });
        return result.toString();
    }
    public static String formatDuration(int seconds) {
        return format(getMap(seconds));

    }

}