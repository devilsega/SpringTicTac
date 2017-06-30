package ua.devilsega.JPA.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Model class of game instance
 */

@Entity
@Table(name = "games")
public class GameInstance {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "a1")
    private String a1;

    @Column(name = "a2")
    private String a2;

    @Column(name = "a3")
    private String a3;

    @Column(name = "b1")
    private String b1;

    @Column(name = "b2")
    private String b2;

    @Column(name = "b3")
    private String b3;

    @Column(name = "c1")
    private String c1;

    @Column(name = "c2")
    private String c2;

    @Column(name = "c3")
    private String c3;

    @Column(name = "isclosed")
    private boolean isClosed;

    @ManyToOne(fetch = FetchType.EAGER)
    private Player currentPlayer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Player winner;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private List<Player>players;

    public GameInstance(){
    }

    public void setPlayers(List<Player>players){
        this.players = players;
    }


    public List<Player> getPlayers(){
        return players;
    }

    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public String getB1() {
        return b1;
    }

    public void setB1(String b1) {
        this.b1 = b1;
    }

    public String getB2() {
        return b2;
    }

    public void setB2(String b2) {
        this.b2 = b2;
    }

    public String getB3() {
        return b3;
    }

    public void setB3(String b3) {
        this.b3 = b3;
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    public String getC3() {
        return c3;
    }

    public void setC3(String c3) {
        this.c3 = c3;
    }

    public void setWinner(Player winner){
        this.winner = winner;
    }

    public Player getWinner(){
        return winner;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public String getPositionValue(String position){
        switch (position){
            case "a1":return getA1();
            case "a2":return getA2();
            case "a3":return getA3();
            case "c1":return getC1();
            case "c2":return getC2();
            case "c3":return getC3();
            case "b1":return getB1();
            case "b2":return getB2();
            case "b3":return getB3();
            default:return null;
        }
    }

    public boolean setPositionValue(String position, Player player){
        switch (position){
            case "a1": {
                setA1(player.getName());
                break;
            }
            case "a2": {
                setA2(player.getName());
                break;
            }
            case "a3": {
                setA3(player.getName());
                break;
            }
            case "c1": {
                setC1(player.getName());
                break;
            }
            case "c2": {
                setC2(player.getName());
                break;
            }
            case "c3": {
                setC3(player.getName());
                break;
            }
            case "b1": {
                setB1(player.getName());
                break;
            }
            case "b2": {
                setB2(player.getName());
                break;
            }
            case "b3": {
                setB3(player.getName());
                break;
            }
            default:return false;
        }
        return true;
    }

}
