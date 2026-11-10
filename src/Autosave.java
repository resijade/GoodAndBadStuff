import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

public class Autosave extends Thread {
    private static final long AUTOSAVE_INTERVAL_MS = 30000;
    private static final String BACKUP_PATH = "./data/gabsData.csv.bak";

    @Override
    public void run() {
        while (Server.autosave) {
            try {
                Thread.sleep(AUTOSAVE_INTERVAL_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!Server.changesMade) {
                IO.println("Autosave skipped: no changes made");
                continue;
            }
            IO.println("Autosave started");
            try (BufferedReader br = new BufferedReader(new FileReader(Server.DATA_PATH));
                 PrintWriter backupPw = new PrintWriter(new FileOutputStream(BACKUP_PATH))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    backupPw.println(line);
                }
                backupPw.flush();
                IO.println("Backup made");
            } catch (Exception e) {
                System.err.println("Error autosaving during backup: " + e.getMessage());
            }
            try (PrintWriter pw = new PrintWriter(Server.DATA_PATH)) {
                pw.println("Title,Arranged By,Tags,Elo");
                for (int i = 0; i < Server.songs.size(); i++) {
                    synchronized (Server.locks[i]) {
                        Song song = Server.songs.get(i);
                        pw.print(song.getName() + "," + song.getArranger() + ",");
                        for (int j = 0; j < song.getTags().size(); j++) {
                            pw.print(song.getTags().get(j) + "|");
                        }
                        pw.println("," + song.getEloScore());
                    }
                }
                pw.flush();
                Server.changesMade = false;
                IO.println("Autosave complete");
            } catch (Exception e) {
                System.err.println("Error autosaving during autosave: " + e.getMessage());
            }
        }

    }
}
