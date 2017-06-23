package ua.devilsega.JPA.DAO;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.devilsega.JPA.Model.GameInstance;

/**
 * Created by sorokin_si on 21.06.2017.
 */

public interface GameInstanceRepo extends JpaRepository<GameInstance, Integer> {
}
