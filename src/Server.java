import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Server {
    private static ArrayList<Song> songs = new ArrayList<Song>();
    protected static boolean changesMade = false;
    protected static final String DATA_PATH = "../data/songs.csv";
    protected static boolean autosave = true;

    public static void startup() {
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_PATH))) {
            do {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] split = line.split(",");
                if (split[1].equals("Title")) {
                    continue;
                }
                String name = split[0];
                String arranger = split[1];
                int elo = Integer.parseInt(split[3]);
                String[] readTags = split[2].split("|");
                ArrayList<Tag> tags = new ArrayList<>();
                for (int i = 0; i < readTags.length; i++) {
                    try {
                        tags.add(Tag.valueOf(readTags[i]));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid Tag for " + name);
                    }
                }
                songs.add(new Song(name, arranger, elo, tags));
            } while (true);
        } catch (Exception e) {
            System.err.println("Error reading csv:\n" + e.getMessage());
        }

    }

}


