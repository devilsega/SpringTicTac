package ua.devilsega;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ua.devilsega.JPA.DAO.GameInstanceRepo;
import ua.devilsega.JPA.Model.GameInstance;

import static org.assertj.core.api.Assertions.assertThat;

/*
* Test that adds gameInstance object to DB, find it in DB by id, then compare these two objects for identity.
*/

@RunWith(SpringRunner.class)
@DataJpaTest
public class GameInstanceRepoTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GameInstanceRepo gameInstanceRepo;

    @Test
    public void test(){
        GameInstance testInstance = new GameInstance();
        entityManager.persist(testInstance);
        entityManager.flush();

        GameInstance foundInstance = gameInstanceRepo.findOne(testInstance.getId());

        assertThat(testInstance).isEqualTo(foundInstance);
    }

}
