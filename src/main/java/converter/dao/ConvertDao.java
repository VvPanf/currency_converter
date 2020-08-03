package converter.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.List;

/**
 * Класс для вывода истории конвертации из базы данных.
 * @author Панферов Владимир
 */
@Repository
@Transactional
public class ConvertDao {
    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    /**
     * Вывод всей истории конвертаций пользователя.
     * @param user - пользователь, для которого производится поиск
     * @return
     */
    public List<Object> getHistory(Integer user){
        Session session = getSession();
        Query query = session.createQuery("select c.id, cu1.code, cu2.code, c.fromValue, c.toValue, c.date " +
                "from Convert as c join Currency as cu1 on c.fromCurr=cu1.id join Currency as cu2 on c.toCurr=cu2.id " +
                "where c.userId=:user");
        query.setParameter("user", user);
        return (List<Object>) query.getResultList();
    }

    /**
     * Вывод истории конвертации пользователя по дате и валютам.
     * @param user  - пользователь, для которого производится поиск
     * @param historyDate   - дата для поиска
     * @param historyFromCurr - исходная валюта для поиска
     * @param historyToCurr - результирующая валюта для поиска
     * @return
     */
    public List<Object> getHistoryFilter(Integer user, Date historyDate, Integer historyFromCurr, Integer historyToCurr) {
        Session session = getSession();
        Query query = session.createQuery("select c.id, cu1.code, cu2.code, c.fromValue, c.toValue, c.date " +
                "from Convert as c join Currency as cu1 on c.fromCurr=cu1.id join Currency as cu2 on c.toCurr=cu2.id " +
                "where c.userId=:user and c.date=:hDate and c.fromCurr=:hFromCurr and c.toCurr=:hToCurr");
        query.setParameter("user", user);
        query.setParameter("hDate", historyDate);
        query.setParameter("hFromCurr", historyFromCurr);
        query.setParameter("hToCurr", historyToCurr);
        //query.executeUpdate();
        return (List<Object>) query.getResultList();
    }
}
