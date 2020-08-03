package converter.repo;

import converter.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

/**
 * @author Панферов Владимир
 */
@Repository
public interface RateRepo extends JpaRepository<Rate, Integer> {
    Rate findByCurrIdAndDate(Integer id, Date date);

    @Query(value = "select max(date) from Rate")
    Date findByMaxDate();
}
