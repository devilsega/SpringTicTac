package ua.devilsega.Unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.devilsega.JPA.DAO.GameInstanceRepo;
import ua.devilsega.JPA.DAO.PlayerRepo;
import ua.devilsega.JPA.Model.GameInstance;
import ua.devilsega.JPA.Model.Player;
import ua.devilsega.Services.GamePlayCalc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.*;


@RunWith(SpringRunner.class)
public class GamePlayCalcTest {

    @TestConfiguration
    static class GamePlayCalcTestContextConfiguration{
        @Bean
        public GamePlayCalc gamePlayCalc(){
            return new GamePlayCalc();
        }
    }

    @Autowired
    private GamePlayCalc gamePlayCalc;

    @MockBean
    private GameInstanceRepo gameInstanceRepo;
    @MockBean
    private PlayerRepo playerRepo;

    @Before
    public void setUp(){
        GameInstance gameInstance = new GameInstance();
        Player player1 = new Player("testPlayer1");
        Player player2 = new Player("testPlayer2");

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        gameInstance.setPlayers(players);
        gameInstance.setCurrentPlayer(player2);
        gameInstance.setA1("testPlayer1");

        Mockito.when(gameInstanceRepo.saveAndFlush(gameInstance)).thenReturn(gameInstance);
        Mockito.when(gameInstanceRepo.findOne(1)).thenReturn(gameInstance);
        Mockito.when(playerRepo.findByName("testPlayer1")).thenReturn(player1);
        Mockito.when(playerRepo.findByName("testPlayer2")).thenReturn(player2);
    }

    @Test
    public void testNewGameCreation(){
        Mockito.reset(gameInstanceRepo);
        Mockito.reset(playerRepo);
        GameInstance gameInstance = new GameInstance();
        Player player = new Player("testPlayer");
        Player player2 = new Player("testPlayer2");
        Mockito.when(gameInstanceRepo.findOne(anyInt())).thenReturn(gameInstance);
        Mockito.when(playerRepo.findByName("testPlayer")).thenReturn(player);
        Mockito.when(playerRepo.findByName("testPlayer2")).thenReturn(player2);
        Mockito.when(gameInstanceRepo.saveAndFlush(any())).thenReturn(gameInstance);

        GameInstance testInstance = gamePlayCalc.createNewGame("testPlayer");
        assertThat(testInstance)
                .isNotNull()
                .isEqualTo(gameInstance);
    }

    @Test
    public void testGameJoining(){
        Mockito.reset(gameInstanceRepo);
        Mockito.reset(playerRepo);
        GameInstance gameInstance = new GameInstance();
        Player player = new Player("testPlayer");
        Player player2 = new Player("testPlayer2");
        Mockito.when(gameInstanceRepo.findOne(anyInt())).thenReturn(gameInstance);
        Mockito.when(playerRepo.findByName("testPlayer")).thenReturn(player);
        Mockito.when(playerRepo.findByName("testPlayer2")).thenReturn(player2);
        Mockito.when(gameInstanceRepo.saveAndFlush(any())).thenReturn(gameInstance);

        List<Player> players = new ArrayList<>();
        players.add(player);
        gameInstance.setPlayers(players);

        GameInstance JoinInstance = gamePlayCalc.joinGame(1,"testPlayer2");
        assertThat(JoinInstance)
                .isNotNull()
                .isEqualTo(gameInstance);
        assertThat(JoinInstance.getPlayers()).contains(player,player2);
    }

    @Test
    public void checkIfExistingGameReturns(){
        assertThat(gamePlayCalc.getCurrentState(1)).isNotNull();
        assertThat(gamePlayCalc.getCurrentState(2)).isNull();
    }

    @Test
    public void checkIncorrectMoves(){
        String [] incorrectMoves = {"a1","a5"};
        for (String move : incorrectMoves){
            Boolean result = gamePlayCalc.setMove(1,"testPlayer2",move);
            assertThat(result).isFalse();
            setUp();
        }
    }

    @Test
    public void checkPossibleMoves(){
        String [] posibleMoves = {"a2","a3","b1","b2","b3","c1","c2","c3"};
        for (String move : posibleMoves){
            Boolean result = gamePlayCalc.setMove(1,"testPlayer2",move);
            assertThat(result).isTrue();
            setUp();
        }
    }

    @Test
    public void checkIncorrectPlayer(){
        boolean result = gamePlayCalc.setMove(1,"testPlayer1","a2");
        assertThat(result).isFalse();

        result = gamePlayCalc.setMove(1,"testPlayerX","a2");
        assertThat(result).isFalse();
    }

    @Test
    public void checkGameBehaviorAndWinner(){
        assertThat(gamePlayCalc.setMove(1,"testPlayer2","a2")).isTrue();
        assertThat(gamePlayCalc.setMove(1,"testPlayer1","b2")).isTrue();
        assertThat(gamePlayCalc.setMove(1,"testPlayer2","a3")).isTrue();
        assertThat(gamePlayCalc.setMove(1,"testPlayer1","c3")).isTrue();
        assertThat(gamePlayCalc.checkGameIsEnded(1)).isTrue();
        assertThat(gamePlayCalc.getWinner(1).getName()).isEqualTo("testPlayer1");
    }

    @Test
    public void checkIfOneCanDeleteGameInstance(){
        assertThat(gamePlayCalc.deleteGame(1)).isTrue();
        assertThat(gamePlayCalc.deleteGame(2)).isFalse();
    }

}
