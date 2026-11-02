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
}
