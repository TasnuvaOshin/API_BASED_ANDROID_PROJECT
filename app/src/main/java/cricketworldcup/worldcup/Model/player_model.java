package cricketworldcup.worldcup.Model;

public class player_model {

    String player_name;
    String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public player_model() {
    }

    public player_model(String player_name,String img) {
        this.player_name = player_name;
        this.img = img;
    }
}
