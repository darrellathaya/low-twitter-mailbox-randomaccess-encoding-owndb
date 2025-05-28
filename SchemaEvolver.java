import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.Value;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class SchemaEvolver {
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public static void main(String[] args) {
        if (args.length < 2) {
            printUsage();
            return;
        }

        String action = args[0];

        switch (action) {
            case "add":
                if (args.length != 3) {
                    printUsage();
                    return;
                }
                String columnToAdd = args[1];
                String defaultVal = args[2];
                evolveAllData(tweet -> tweet.putIfAbsent(columnToAdd, defaultVal));
                break;

            case "rename":
                if (args.length != 3) {
                    printUsage();
                    return;
                }
                String oldKey = args[1];
                String newKey = args[2];
                evolveAllData(tweet -> {
                    if (tweet.containsKey(oldKey)) {
                        tweet.put(newKey, tweet.remove(oldKey));
                    }
                });
                break;

            case "changeType":
                if (args.length != 3) {
                    printUsage();
                    return;
                }
                String column = args[1];
                String targetType = args[2];
                evolveAllData(tweet -> {
                    if (tweet.containsKey(column)) {
                        Object value = tweet.get(column);
                        if (value instanceof String strValue) {
                            try {
                                switch (targetType) {
                                    case "LocalDate":
                                        tweet.put(column, LocalDate.parse(strValue.split("T")[0]));
                                        break;
                                    case "LocalDateTime":
                                        tweet.put(column, LocalDateTime.parse(strValue));
                                        break;
                                    case "Integer":
                                        tweet.put(column, Integer.parseInt(strValue));
                                        break;
                                    case "Boolean":
                                        tweet.put(column, Boolean.parseBoolean(strValue));
                                        break;
                                }
                            } catch (Exception e) {
                                System.err.println("Failed to convert value: " + value + " to " + targetType);
                            }
                        }
                    }
                });
                break;

            case "remove":
                if (args.length != 2) {
                    printUsage();
                    return;
                }
                String columnToRemove = args[1];
                evolveAllData(tweet -> tweet.remove(columnToRemove));
                break;

            default:
                System.out.println("Unrecognized action: " + action);
                printUsage();
        }

        System.out.println("Schema evolution completed.");
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  java SchemaEvolver add <column> <default>");
        System.out.println("  java SchemaEvolver rename <oldColumn> <newColumn>");
        System.out.println("  java SchemaEvolver changeType <column> LocalDate|LocalDateTime|Integer|Boolean");
        System.out.println("  java SchemaEvolver remove <column>");
    }

    public static void evolveAllData(EvolutionFunction func) {
        evolveHotData(func);
        evolveColdData(func);
    }

    private static void evolveHotData(EvolutionFunction func) {
        File hotRoot = new File("data/hot/");
        if (!hotRoot.exists()) {
            System.err.println("Hot data directory not found.");
            return;
        }

        for (File userDir : Objects.requireNonNull(hotRoot.listFiles())) {
            if (!userDir.isDirectory()) continue;

            for (File file : Objects.requireNonNull(userDir.listFiles())) {
                try {
                    byte[] data = Files.readAllBytes(file.toPath());
                    MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(data);
                    List<Map<String, Object>> tweets = new ArrayList<>();

                    while (unpacker.hasNext()) {
                        int size = unpacker.unpackMapHeader();
                        Map<String, Object> tweet = new HashMap<>();
                        for (int i = 0; i < size; i++) {
                            String key = unpacker.unpackString();
                            Value val = unpacker.unpackValue();
                            tweet.put(key, val.toString());
                        }
                        tweets.add(tweet);
                    }

                    // Apply schema evolution
                    for (Map<String, Object> tweet : tweets) {
                        func.apply(tweet);
                    }

                    // Save back to file
                    MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
                    for (Map<String, Object> tweet : tweets) {
                        packer.packMapHeader(tweet.size());
                        for (Map.Entry<String, Object> entry : tweet.entrySet()) {
                            packer.packString(entry.getKey());
                            packer.packString(entry.getValue().toString());
                        }
                    }

                    try (OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                        out.write(packer.toByteArray());
                    }

                } catch (Exception e) {
                    System.err.println("Failed to update hot file: " + file.getName());
                    e.printStackTrace();
                }
            }
        }
    }

    private static void evolveColdData(EvolutionFunction func) {
        File coldFile = new File("data/cold/all_tweets.jsonl");
        if (!coldFile.exists()) {
            System.err.println("Cold data file not found.");
            return;
        }

        List<Map<String, Object>> tweets = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(coldFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> tweet = jsonMapper.readValue(line, Map.class);
                tweets.add(tweet);
            }
        } catch (Exception e) {
            System.err.println("Failed to read cold data.");
            e.printStackTrace();
        }

        for (Map<String, Object> tweet : tweets) {
            func.apply(tweet);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(coldFile))) {
            for (Map<String, Object> tweet : tweets) {
                writer.write(jsonMapper.writeValueAsString(tweet));
                writer.newLine();
            }
        } catch (Exception e) {
            System.err.println("Failed to write cold data.");
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface EvolutionFunction {
        void apply(Map<String, Object> tweet);
    }
}
