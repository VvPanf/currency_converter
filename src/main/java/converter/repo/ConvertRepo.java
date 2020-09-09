package converter.repo;

import converter.entity.Convert;
import converter.entity.Currency;
import converter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * @author Панферов Владимир
 */
@Repository
public interface ConvertRepo extends JpaRepository<Convert, Long> {
    List<Convert> findByUser(User user);

    List<Convert> findByDateAndAmountCurrAndResultCurrAndUser(Date date, Currency amountCurr, Currency resultCurr, User user);
}
