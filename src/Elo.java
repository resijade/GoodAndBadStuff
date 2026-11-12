import java.io.Serializable;

public class Elo implements Serializable {
    private int score;
    private static final int K_FACTOR = 32;
    private static final int SCALE_FACTOR = 400;
    private static final int EXPONENT_BASE = 10;

    public Elo(int score) {
        this.score = score;
    }

    public int get() {
        return score;
    }
    public void set(int elo) {
        this.score = elo;
    }

    public static void updateElo(Song winner, Song loser) {
        double winnerProb = 1 / (1 + Math.pow(EXPONENT_BASE, (double) (loser.getEloScore() - winner.getEloScore()) / SCALE_FACTOR));
        double loserProb = 1 / (1 + Math.pow(EXPONENT_BASE, (double) (winner.getEloScore() - loser.getEloScore()) / SCALE_FACTOR));
        IO.println(winner.getName() + " probability: " + winnerProb);
        IO.println(loser.getName() + " probability: " + loserProb);
        winner.setElo((int) Math.round(winner.getEloScore() + K_FACTOR * (1 - winnerProb)));
        loser.setElo((int) (loser.getEloScore() + K_FACTOR * (0 - loserProb)));
    }
}
