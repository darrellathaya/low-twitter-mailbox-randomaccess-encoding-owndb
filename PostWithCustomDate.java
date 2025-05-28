import java.time.LocalDateTime;
import java.util.*;

public class PostWithCustomDate {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: PostWithCustomDate <userId> <message>");
            return;
        }

        String userId = args[0];
        String message = String.join(" ", Arrays.asList(args).subList(1, args.length));

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        Map<String, Object> tweet = new HashMap<>();
        tweet.put("userId", userId);
        tweet.put("content", message);

        HotColdManager.storeTweet(userId, tweet, yesterday); // <-- passing custom date

        System.out.println("Tweet saved with timestamp: " + yesterday);
    }
}
