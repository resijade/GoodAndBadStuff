import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Server implements Runnable {
    private static ArrayList<Song> songs = new ArrayList<Song>();
    protected static boolean changesMade = false;
    protected static final String DATA_PATH = "../data/songs.csv";
    protected static boolean autosave = true;
    private static final int PORT = 1999; // When Purdue won the natty. #TakeMeBack
    private static Queue<Song[]> queue = new LinkedList<Song[]>();
    private Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

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
    public static void main(String[] args) {
        startup();
        Autosave a = new Autosave();
        a.start();
        try {
            ServerSocket ss = new ServerSocket(PORT);
            while (true) {
                Socket socket = ss.accept();
                Server s = new Server(socket);
                Thread thread = new Thread(s);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            int songOne = (int) (Math.random() * songs.size());
            int songTwo = (int) (Math.random() * songs.size());
            while (songOne == songTwo) {
                songTwo = (int) (Math.random() * songs.size());
            }
            oos.writeObject(new Song[]{songs.get(songOne), songs.get(songTwo)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


