package ua.devilsega;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
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

    //@InjectMocks
    @Autowired
    private GamePlayCalc gamePlayCalc;

    @MockBean
    private GameInstanceRepo gameInstanceRepo;
    @MockBean
    private PlayerRepo playerRepo;

    @Before
    public void setUp() {
        GameInstance gameInstance = new GameInstance();
        Player player = new Player("testPlayer");

        Mockito.when(gameInstanceRepo.findOne(anyInt()))
                .thenReturn(gameInstance);

        Mockito.when(playerRepo.findByName(anyString())).thenReturn(player);

        Mockito.when(gameInstanceRepo.saveAndFlush(any())).thenReturn(gameInstance);
    }

    @Ignore
    @Test
    public void whenValidName_ThenPlayerShouldBeFound() {
        String name = "testPlayer";
        Player found = playerRepo.findByName(name);

        assertThat(found.getName()).isEqualTo(name);
    }

    @Test
    public void whenCreateNewGame_ShouldReturnNewGameInstance(){
        GameInstance testInstance = gamePlayCalc.createNewGame("testPlayer1");
        assertThat(testInstance).isNotNull();
    }

}
