package rating;

public class Player {
    private final Long id;
    private final int ability;
    private int rating;
    private int numOfGame;
    private int numOfWin;

    public Player(Long id, int ability, int rating) {
        this.id = id;
        this.ability = ability;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public int getAbility() {
        return ability;
    }

    public int getRating() {
        return rating;
    }

    public int getNumOfGame() {
        return numOfGame;
    }

    public int getNumOfWin() {
        return numOfWin;
    }

    public void addRating(int addRating) {
        this.rating += addRating;
    }

    public void playGame() {
        this.numOfGame++;
    }

    public void winGame() {
        this.numOfWin++;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Player)) return false;
        if(obj == this) return true;

        return ((Player) obj).getId().equals(this.id);
    }

    @Override
    public String toString() {
        return "Player{" +
                "ability=" + ability +
                ", rating=" + rating +
                ", numOfGame=" + numOfGame +
                ", numOfWin=" + numOfWin +
                '}';
    }
}
