package ua.devilsega.Integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ua.devilsega.JPA.DAO.PlayerRepo;
import ua.devilsega.JPA.Model.Player;

import static org.assertj.core.api.Assertions.assertThat;

/*
* Test that adds Player object to DB, find it in DB by id, then compare these two objects for identity.
*/

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlayerRepoTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlayerRepo playerRepo;

    @Test
    public void WhenAddNewGameInstance_thenReturnThisGameInstance(){
        Player testPlayer = new Player("test");
        entityManager.persist(testPlayer);
        entityManager.flush();

        Player found = playerRepo.findByName("test");

        assertThat(testPlayer).isEqualTo(found);
    }

}
