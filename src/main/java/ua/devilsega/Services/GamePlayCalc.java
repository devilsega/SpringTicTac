package ua.devilsega.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.devilsega.JPA.DAO.GameInstanceRepo;
import ua.devilsega.JPA.DAO.PlayerRepo;
import ua.devilsega.JPA.Model.GameInstance;
import ua.devilsega.JPA.Model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main service responsible for the game pipeline
 */

@Service
public class GamePlayCalc {
    @Autowired
    private GameInstanceRepo gameInstanceRepo;
    @Autowired
    private PlayerRepo playerRepo;

    public GameInstance createNewGame(String playerName){
        try {
            GameInstance gameInstance = new GameInstance();
            Player player = addPlayerToDB(playerName);
            List<Player>players = new ArrayList<>();
            players.add(player);
            gameInstance.setPlayers(players);
            return gameInstanceRepo.saveAndFlush(gameInstance);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public GameInstance joinGame(int id, String playerName){
        try {
            GameInstance gameInstance = gameInstanceRepo.findOne(id);
            Player player = addPlayerToDB(playerName);
            List<Player>players = gameInstance.getPlayers();
            if (!players.isEmpty() & players.size()<2){
                players.add(player);
                Random random = new Random();
                Player randomCurrentPlayer = players.get(random.nextInt(2));
                gameInstance.setCurrentPlayer(randomCurrentPlayer);
                gameInstance.setPlayers(players);
                gameInstanceRepo.saveAndFlush(gameInstance);
                return gameInstance;
            }
            else return null;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Object getCurrentState(int id){
        return gameInstanceRepo.findOne(id);
    }

    public boolean setMove(int id, String playerName, String move){
        GameInstance gameInstance = gameInstanceRepo.findOne(id);
        Player currentPlayer = gameInstance.getCurrentPlayer();
        try {
            if (currentPlayer.getName().equals(playerName) & gameInstance.getPlayers().size()==2 & !gameInstance.isClosed()){
                if (gameInstance.getPositionValue(move)==null){
                    gameInstance.setPositionValue(move,currentPlayer);
                    Player winner = calculateResult(gameInstance);
                    for (Player item:gameInstance.getPlayers()) {
                        if (!currentPlayer.equals(item)){
                            gameInstance.setCurrentPlayer(item);
                            break;
                        }
                    }
                    if (winner!=null){
                        gameInstance.setClosed(true);
                        gameInstance.setWinner(winner);
                    }
                    gameInstanceRepo.saveAndFlush(gameInstance);
                    return true;
                }
            }
            return false;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public Player getWinner(int id){
        GameInstance gameInstance = gameInstanceRepo.findOne(id);
        return gameInstance.getWinner();
    }

    public boolean checkGameIsEnded(int id){
        GameInstance gameInstance = gameInstanceRepo.findOne(id);
        return gameInstance.isClosed();
    }

    public boolean deleteGame(int id){
        GameInstance gameInstance = gameInstanceRepo.findOne(id);
        if (gameInstance!=null){
            try {
                gameInstanceRepo.delete(gameInstance);
                return true;
            }
            catch (Exception ex){
                ex.printStackTrace();
                return false;
            }
        }
        else return false;
    }

    public boolean endGame(int id){
        GameInstance gameInstance = gameInstanceRepo.findOne(id);
        if (gameInstance!=null){
            try {
                gameInstance.setClosed(true);
                gameInstanceRepo.saveAndFlush(gameInstance);
                return true;
            }
            catch (Exception ex){
                ex.printStackTrace();
                return false;
            }
        }
        else return false;
    }

    private Player addPlayerToDB(String playerName){
        Player player = playerRepo.findByName(playerName);
        if (player==null){
            player = new  Player(playerName);
            return playerRepo.saveAndFlush(player);
        }
        return player;
    }

    private Player calculateResult(GameInstance gameInstance){
        String a1,a2,a3,b1,b2,b3,c1,c2,c3;

        a1=gameInstance.getA1();
        a2=gameInstance.getA2();
        a3=gameInstance.getA3();
        b1=gameInstance.getB1();
        b2=gameInstance.getB2();
        b3=gameInstance.getB3();
        c1=gameInstance.getC1();
        c2=gameInstance.getC2();
        c3=gameInstance.getC3();

        if((a1!=null && a2!=null && a3!=null)&&(a1.equals(a2) & a1.equals(a3))){
            return playerRepo.findByName(gameInstance.getA1());
        }
        if((b1!=null && b2!=null && b3!=null)&&(b1.equals(b2) & b1.equals(b3))){
            return playerRepo.findByName(gameInstance.getB1());
        }
        if((c1!=null && c2!=null && c3!=null)&&(c1.equals(c2) & c1.equals(c3))){
            return playerRepo.findByName(gameInstance.getC1());
        }
        if((a1!=null && b1!=null && c1!=null)&&(a1.equals(b1) & a1.equals(c1))){
            return playerRepo.findByName(gameInstance.getA1());
        }
        if((a2!=null && b2!=null && c2!=null)&&(a2.equals(b2) & a2.equals(c2))){
            return playerRepo.findByName(gameInstance.getA2());
        }
        if((a3!=null && b3!=null && c3!=null)&&(a3.equals(b3) & a3.equals(c3))){
            return playerRepo.findByName(gameInstance.getA3());
        }
        if((a1!=null && b2!=null && c3!=null)&&(a1.equals(b2) & a1.equals(c3))){
            return playerRepo.findByName(gameInstance.getA1());
        }
        if((c1!=null && b2!=null && a3!=null)&&(c1.equals(b2) & c1.equals(a3))){
            return playerRepo.findByName(gameInstance.getC1());
        }
        return null;
    }
}