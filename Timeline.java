import java.io.*;
import java.util.*;
import java.time.LocalDateTime;

public class Timeline {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: Timeline <userId>");
            return;
        }

        String userId = args[0];
        Set<String> following = new HashSet<>();
        String userDir = "users/" + userId;
        File followFile = new File(userDir + "/follows.dat");

        if (followFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(followFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    following.add(parts[2]);
                }
            }
        }

        for (String user : following) {
            List<Map<String, Object>> tweets = HotColdManager.getTweetsByUser(user);
            for (Map<String, Object> tweet : tweets) {
                System.out.println("User: " + tweet.get("userId"));
                System.out.println("Message: " + tweet.get("content"));
                System.out.println("Timestamp: " + tweet.get("timestamp"));
                System.out.println();
            }
        }
    }
}
