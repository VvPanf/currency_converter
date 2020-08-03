package converter.service;

import converter.entity.Rate;
import converter.repo.RateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * Сервис для работы с таблицей значения валют.
 * @author Панферов Владимир
 */
@Service
public class RateService {
    @Autowired
    private RateRepo rateRepo;

    public List<Rate> findAll(){
        return rateRepo.findAll();
    }

    public Date findByMaxDate(){
        return rateRepo.findByMaxDate();
    }
}
