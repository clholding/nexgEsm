import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class TimeDifferenceCalculator {

	  private final String timeFormat;

	    public TimeDifferenceCalculator(String timeFormat) {
	        this.timeFormat = timeFormat;
	    }

	    public long getMinDiff(String tarTime, String curTime) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
	        LocalDateTime d1 = LocalDateTime.parse(tarTime, formatter);
	        LocalDateTime d2 = LocalDateTime.parse(curTime, formatter);
	        
	        Duration duration = Duration.between(d1, d2);
	        
	        if (duration.toDays() > 0) {
	            return duration.toDays() * 24 * 60;
	        } else {
	            return duration.toMinutes();
	        }
	    }

	    public static void main(String[] args) {
	        TimeDifferenceCalculator calculator = new TimeDifferenceCalculator("yyyy-MM-dd HH:mm:ss");
	        String tarTime = "2024-03-18 12:00:00";
	        String curTime = "2024-03-18 13:30:00";
	        long minDiff = calculator.getMinDiff(tarTime, curTime);
	        System.out.println("Minutes difference: " + minDiff);
	    }
}
