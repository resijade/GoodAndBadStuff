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

        }

    }
}
