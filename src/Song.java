import java.io.Serializable;
import java.util.ArrayList;

public class Song implements Serializable {
    private String name;
    private String arranger;
    private Elo elo;
    private ArrayList<Tag> tags;

    public Song(String name, String arranger, int elo, ArrayList<Tag> tags) {
        this.name = name;
        this.arranger = arranger;
        this.elo = new Elo(elo);
        this.tags = tags;
    }

    public Song(String name, String arranger) {
        this.name = name;
        this.arranger = arranger;
        elo = new Elo(1000);
        tags = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getArranger() {
        return arranger;
    }
    public void setArranger(String arranger) {
        this.arranger = arranger;
    }

    public int getEloScore() {
        return elo.get();
    }
    public void setElo(int elo) {
        this.elo.set(elo);
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }
    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }
    public void addTag(Tag tag) {
        if (tags.indexOf(tag) == -1) {
            tags.add(tag);
        }
    }
    public void removeTag(Tag tag) {
        if (tags.indexOf(tag) != -1) {
            tags.remove(tag);
        }
    }
}
