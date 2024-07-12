package san_wulf.gg.testerpvp.player;

public class PlayerStats {
    private String uuid;
    private int wins;
    private int losses;
    private int deaths;

    public PlayerStats(String uuid, int wins, int losses, int deaths) {
        this.uuid = uuid;
        this.wins = wins;
        this.losses = losses;
        this.deaths = deaths;
    }

    public String getUuid() {
        return uuid;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}
