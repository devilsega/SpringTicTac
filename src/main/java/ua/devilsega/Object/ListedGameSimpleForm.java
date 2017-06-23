package ua.devilsega.Object;

/**
 * Created by sorokin_si on 22.06.2017.
 */
public class ListedGameSimpleForm {
    private int id;
    private String player;

    public ListedGameSimpleForm(int id, String player){
        this.id = id;
        this.player = player;
    }

    public int getId(){
        return id;
    }

    public String getPlayer() {
        return player;
    }
}
