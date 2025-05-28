import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;

public class HotColdManager {
    private static final int MAX_HOT_TWEETS = 10;
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * Menyimpan tweet, bisa dengan timestamp kustom
     */
    public static void storeTweet(String userId, Map<String, Object> tweetData, LocalDateTime customTimestamp) {
        try {
            LocalDateTime timestamp = (customTimestamp != null) ? customTimestamp : LocalDateTime.now();
            tweetData.put("timestamp", timestamp.toString());

            if (isToday(timestamp)) {
                storeToHot(userId, tweetData);
            } else {
                storeToCold(tweetData);
            }

        } catch (Exception e) {
            System.err.println("Error menyimpan tweet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Menyimpan tweet ke hot data (msgpack)
     */
    private static void storeToHot(String userId, Map<String, Object> tweetData) throws IOException {
        String hotPath = "data/hot/" + userId + "/";
        Path hotDir = Paths.get(hotPath);
        if (!Files.exists(hotDir)) Files.createDirectories(hotDir);

        int fileCount = countHotTweets(userId);
        int fileIndex = fileCount / MAX_HOT_TWEETS;
        String filePath = hotPath + "tweets_" + fileIndex + ".msgpack";

        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        packer.packMapHeader(tweetData.size());
        for (Map.Entry<String, Object> entry : tweetData.entrySet()) {
            packer.packString(entry.getKey());
            if (entry.getValue() instanceof String) {
                packer.packString((String) entry.getValue());
            } else if (entry.getValue() instanceof Integer) {
                packer.packInt((Integer) entry.getValue());
            } else {
                packer.packString(entry.getValue().toString());
            }
        }

        byte[] data = packer.toByteArray();
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(filePath, true))) {
            out.write(data);
        }
    }

    /**
     * Menyimpan tweet ke cold data (jsonlines)
     */
    private static void storeToCold(Map<String, Object> tweetData) throws IOException {
        String coldPath = "data/cold/all_tweets.jsonl";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(coldPath, true))) {
            writer.write(jsonMapper.writeValueAsString(tweetData));
            writer.newLine();
        }
    }

    /**
     * Mengecek apakah timestamp adalah hari ini
     */
    private static boolean isToday(LocalDateTime timestamp) {
        return timestamp.toLocalDate().isEqual(LocalDate.now());
    }

    /**
     * Menghitung jumlah tweet di hot data untuk rotasi file
     */
    private static int countHotTweets(String userId) throws IOException {
        String dirPath = "data/hot/" + userId;
        File dir = new File(dirPath);
        int count = 0;

        if (dir.exists()) {
            for (File f : Objects.requireNonNull(dir.listFiles())) {
                count += countInMsgPack(f.getAbsolutePath());
            }
        }
        return count;
    }

    private static int countInMsgPack(String path) {
        return 0; // Placeholder - implementasi tergantung format msgpack
    }

    /**
     * Ambil semua tweet dari seorang user (hot + cold)
     */
    public static List<Map<String, Object>> getTweetsByUser(String userId) {
        List<Map<String, Object>> tweets = new ArrayList<>();

        // Baca dari hot data
        String dirPath = "data/hot/" + userId;
        File dir = new File(dirPath);
        if (dir.exists()) {
            for (File f : Objects.requireNonNull(dir.listFiles())) {
                try {
                    byte[] data = Files.readAllBytes(f.toPath());
                    var unpacker = MessagePack.newDefaultUnpacker(data);
                    while (unpacker.hasNext()) {
                        int size = unpacker.unpackMapHeader();
                        Map<String, Object> tweet = new HashMap<>();
                        for (int i = 0; i < size; i++) {
                            String key = unpacker.unpackString();
                            tweet.put(key, unpacker.unpackValue().toString());
                        }
                        tweets.add(tweet);
                    }
                } catch (Exception e) {
                    System.err.println("Gagal membaca file hot data: " + f.getName());
                }
            }
        }

        // Baca dari cold data
        try {
            File coldFile = new File("data/cold/all_tweets.jsonl");
            if (coldFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(coldFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> tweet = jsonMapper.readValue(line, Map.class);
                        if (userId.equals(tweet.get("userId"))) {
                            tweets.add(tweet);
                        }
                    } catch (Exception e) {
                        System.err.println("Baris JSON tidak valid: " + line);
                    }
                }
                reader.close();
            }
        } catch (Exception e) {
            System.err.println("Gagal membaca cold data.");
            e.printStackTrace();
        }

        return tweets;
    }

    /**
     * Cari tweet berdasarkan rentang tanggal (hot + cold)
     */
    public static List<Map<String, Object>> searchTweetsByDateRange(LocalDate start, LocalDate end) {
        List<Map<String, Object>> results = new ArrayList<>();

        try {
            // Cold data
            File coldFile = new File("data/cold/all_tweets.jsonl");
            if (coldFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(coldFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> tweet = jsonMapper.readValue(line, Map.class);
                        String tsStr = (String) tweet.get("timestamp");
                        LocalDate date = LocalDate.parse(tsStr.split("T")[0]);
                        if (!date.isBefore(start) && !date.isAfter(end)) {
                            results.add(tweet);
                        }
                    } catch (Exception e) {
                        System.err.println("Baris JSON tidak valid: " + line);
                    }
                }
                reader.close();
            }

            // Hot data
            File hotRoot = new File("data/hot/");
            if (hotRoot.exists()) {
                for (File userDir : Objects.requireNonNull(hotRoot.listFiles())) {
                    for (File file : Objects.requireNonNull(userDir.listFiles())) {
                        byte[] data = Files.readAllBytes(file.toPath());
                        var unpacker = MessagePack.newDefaultUnpacker(data);
                        while (unpacker.hasNext()) {
                            int size = unpacker.unpackMapHeader();
                            Map<String, Object> tweet = new HashMap<>();
                            for (int i = 0; i < size; i++) {
                                String key = unpacker.unpackString();
                                tweet.put(key, unpacker.unpackValue().toString());
                            }
                            String tsStr = (String) tweet.get("timestamp");
                            LocalDate date = LocalDate.parse(tsStr.split("T")[0]);
                            if (!date.isBefore(start) && !date.isAfter(end)) {
                                results.add(tweet);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * Ambil semua tweet (hot + cold)
     */
    public static List<Map<String, Object>> getAllTweets() {
        List<Map<String, Object>> allTweets = new ArrayList<>();

        // Baca hot data
        File hotRoot = new File("data/hot/");
        if (hotRoot.exists()) {
            for (File userDir : Objects.requireNonNull(hotRoot.listFiles())) {
                for (File file : Objects.requireNonNull(userDir.listFiles())) {
                    try {
                        byte[] data = Files.readAllBytes(file.toPath());
                        var unpacker = MessagePack.newDefaultUnpacker(data);
                        while (unpacker.hasNext()) {
                            int size = unpacker.unpackMapHeader();
                            Map<String, Object> tweet = new HashMap<>();
                            for (int i = 0; i < size; i++) {
                                String key = unpacker.unpackString();
                                tweet.put(key, unpacker.unpackValue().toString());
                            }
                            allTweets.add(tweet);
                        }
                    } catch (Exception e) {
                        System.err.println("Gagal membaca file hot data: " + file.getName());
                    }
                }
            }
        }

        // Baca cold data
        try {
            File coldFile = new File("data/cold/all_tweets.jsonl");
            if (coldFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(coldFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> tweet = jsonMapper.readValue(line, Map.class);
                        allTweets.add(tweet);
                    } catch (Exception e) {
                        System.err.println("Baris JSON tidak valid: " + line);
                    }
                }
                reader.close();
            }
        } catch (Exception e) {
            System.err.println("Gagal membaca cold data.");
            e.printStackTrace();
        }

        return allTweets;
    }
}
