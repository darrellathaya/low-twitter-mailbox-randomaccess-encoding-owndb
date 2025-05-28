import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Tweet {
    public String userId;
    public LocalDateTime timestamp;
    public Map<String, Object> fields = new HashMap<>();

    public Tweet(String userId) {
        this.userId = userId;
        this.timestamp = LocalDateTime.now();
    }

    public void addField(String key, Object value) {
        fields.put(key, value);
    }
}
