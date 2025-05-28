import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SearchTweetsByDate {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: SearchTweetsByDate <YYYY-MM-DD> <YYYY-MM-DD>");
            return;
        }

        LocalDate start = LocalDate.parse(args[0]);
        LocalDate end = LocalDate.parse(args[1]);

        List<Map<String, Object>> tweets = HotColdManager.searchTweetsByDateRange(start, end);
        for (Map<String, Object> tweet : tweets) {
            System.out.println(tweet);
        }
    }
}
