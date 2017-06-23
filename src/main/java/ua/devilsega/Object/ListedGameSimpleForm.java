package ua.devilsega.Object;

/**
 * POJO class that forms list of active games
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
