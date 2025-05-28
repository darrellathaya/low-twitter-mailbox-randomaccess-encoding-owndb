import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Post {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: Post <userId> <message>");
            return;
        }

        String userId = args[0];
        StringBuilder message = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }

        Map<String, Object> tweet = new HashMap<>();
        tweet.put("userId", userId);
        tweet.put("timestamp", LocalDateTime.now().toString());
        tweet.put("content", message.toString().trim());

        HotColdManager.storeTweet(userId, tweet, null);

        System.out.println("Tweet saved for " + userId);
    }
}
