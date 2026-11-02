import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class Autosave extends Thread {
    private static final long AUTOSAVE_INTERVAL_MS = 300000;
    private static final String BACKUP_PATH = "../data/gabsData.csv.bak";

    @Override
    public void run() {
        while (Server.autosave) {
            try {
                Thread.sleep(AUTOSAVE_INTERVAL_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (Server.changesMade) {
                continue;
            }
            try (BufferedReader br = new BufferedReader(new FileReader(Server.DATA_PATH));
                 PrintWriter pw = new PrintWriter(Server.DATA_PATH)) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    pw.println(line);
                }
                pw.flush();
            } catch (Exception e) {
                System.err.println("Error autosaving: " + e.getMessage());
            }
        }

    }
}
