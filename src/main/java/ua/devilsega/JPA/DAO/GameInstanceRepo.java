package ua.devilsega.JPA.DAO;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.devilsega.JPA.Model.GameInstance;

public interface GameInstanceRepo extends JpaRepository<GameInstance, Integer> {
}
