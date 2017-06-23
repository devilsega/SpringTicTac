package ua.devilsega.JPA.DAO;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.devilsega.JPA.Model.Player;

/**
 * Created by sorokin_si on 21.06.2017.
 */

public interface PlayerRepo extends JpaRepository<Player, Integer> {
    Player findByName(String name);
}
