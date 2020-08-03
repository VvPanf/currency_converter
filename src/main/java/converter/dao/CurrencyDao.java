package converter.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.List;

/**
 * Класс для вывода списка валют из базы данных.
 * @author Панферов Владимир
 */
@Repository
@Transactional
public class CurrencyDao {
    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    /**
     * Вывод списка валют и их значений из базы данных по дате.
     * @param date - дата, по которой производится выборка из базы данных
     * @return
     */
    public List<Object> findByDate(Date date){
        Session session = getSession();
        Query query = session.createQuery("select c.id, c.numCode, c.code, c.currName, c.nominal, r.value, r.date " +
                "from Currency as c join Rate as r on c.id=r.currId where r.date=:date order by r.date");
        query.setParameter("date", date);
        return query.getResultList();
    }
}
