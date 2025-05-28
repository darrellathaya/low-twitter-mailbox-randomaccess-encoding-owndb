import java.io.*;
import java.time.LocalDateTime;

public class Follow {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: Follow <follower> <toFollow>");
            return;
        }

        String follower = args[0];
        String toFollow = args[1];
        String date = LocalDateTime.now().toString();

        String userDir = "users/" + follower;
        new File(userDir).mkdirs();

        File followFile = new File(userDir + "/follows.dat");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(followFile, true))) {
            writer.write(follower + "|" + date + "|" + toFollow);
            writer.newLine();
        }

        System.out.println(follower + " now follows " + toFollow);
    }
}
