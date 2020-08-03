package converter.repo;

import converter.entity.Convert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Панферов Владимир
 */
@Repository
public interface ConvertRepo extends JpaRepository<Convert, Integer> {
}
