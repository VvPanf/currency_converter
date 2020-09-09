package converter.repo;

import converter.entity.Currency;
import converter.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.sql.Date;

/**
 * @author Панферов Владимир
 */
@Repository
public interface RateRepo extends JpaRepository<Rate, Long> {
    Rate findByCurrencyAndDate(Currency currency, Date date);
    List<Rate> findByDate(Date date);
    @Query(value = "select max(date) from Rate")
    Date findByMaxDate();
}
