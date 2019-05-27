package cricketworldcup.worldcup.Model;

public class team_model {
    String name;
    String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public team_model() {
    }

    public team_model(String name,String img) {
        this.name = name;
        this.img = img;
    }
}
