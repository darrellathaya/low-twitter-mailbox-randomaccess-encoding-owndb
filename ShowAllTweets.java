import java.util.List;
import java.util.Map;

public class ShowAllTweets {
    public static void main(String[] args) {
        List<Map<String, Object>> allTweets = HotColdManager.getAllTweets();
        for (Map<String, Object> tweet : allTweets) {
            System.out.println(tweet);
        }
    }
}
