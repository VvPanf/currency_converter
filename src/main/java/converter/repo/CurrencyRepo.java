package converter.repo;

import converter.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Панферов Владимир
 */
@Repository
public interface CurrencyRepo extends JpaRepository<Currency, Long> {
    Currency findByChrCode(String code);
}
