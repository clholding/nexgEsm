import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;

public class TimeDifferenceCalculator {

	  private final String timeFormat;

	  
	    public static long getSecDiff(String tarTime, String curTime) throws ParseException  {
	    	
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date targetDate = dateFormat.parse(tarTime);
	        long seconds1 = targetDate.getTime() / 1000;

	        Date currentDate = dateFormat.parse(curTime);
	        long seconds2 = currentDate.getTime() / 1000;

	        long secDiff = seconds2 - seconds1;
	        return secDiff;
	    }
	    
	    public TimeDifferenceCalculator(String timeFormat) {
	        this.timeFormat = timeFormat;
	    }

	    public static long getMinDiff(String tarTime, String curTime) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        LocalDateTime d1 = LocalDateTime.parse(tarTime, formatter);
	        LocalDateTime d2 = LocalDateTime.parse(curTime, formatter);
	        
	        Duration duration = Duration.between(d1, d2);
	        
	        if (duration.toDays() > 0) {
	            return duration.toDays() * 24 * 60;
	        } else {
	            return duration.toMinutes();
	        }
	    }

	    public static void main(String[] args) throws ParseException {
	    	
	        Date currentTime = new Date();
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String formattedTime = formatter.format(currentTime);
	        String curTime = formattedTime;
	        
	        String tarTime = "2039-03-21 16:35:11";
	        System.out.println("curTime: " + curTime);
	        long minDiff = getMinDiff(tarTime, curTime);
	        System.out.println("Minutes difference: " + minDiff);
	        
	        long secDiff = getSecDiff(tarTime, curTime);
	        System.out.println("Minutes secDiff: " + secDiff);
	        
	        
	        
            Date expireDate = formatter.parse("2038-01-19 12:14:07");
            Date currentTime2 = formatter.parse(tarTime);

            if (expireDate.before(currentTime2)) {
            	
            	System.out.println("1111111111111111");
			}
	    }
}
