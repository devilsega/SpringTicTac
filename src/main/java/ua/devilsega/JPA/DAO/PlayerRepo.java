package ua.devilsega.JPA.DAO;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.devilsega.JPA.Model.Player;

public interface PlayerRepo extends JpaRepository<Player, Integer> {
    Player findByName(String name);
}
