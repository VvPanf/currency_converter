package converter.service;

import converter.dao.ConvertDao;
import converter.entity.Convert;
import converter.entity.Currency;
import converter.entity.Rate;
import converter.repo.ConvertRepo;
import converter.repo.CurrencyRepo;
import converter.repo.RateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * Сервис для работы с операциями по конвертации валют.
 * @author Панферов Владимир
 */
@Service
public class ConvertService {
    @Autowired
    private ConvertRepo convertRepo;

    @Autowired
    private ConvertDao convertDao;

    @Autowired
    private CurrencyRepo currencyRepo;

    @Autowired
    private RateRepo rateRepo;

    @Autowired
    private CurrencyService currencyService;

    public List<Convert> findAll(){
        return convertRepo.findAll();
    }

    public Convert addConvert(Convert convert) {
        Date date = new Date(System.currentTimeMillis());
        try {
            currencyService.getCourses(date);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Currency fromCurrency = currencyRepo.findById(convert.getFromCurr()).get();
        Currency toCurrency = currencyRepo.findById(convert.getToCurr()).get();
        Rate fromRate = rateRepo.findByCurrIdAndDate(fromCurrency.getId(), date);
        Rate toRate = rateRepo.findByCurrIdAndDate(toCurrency.getId(), date);

        // Calculate result value
        Double result = convert.getFromValue() * fromCurrency.getNominal() * fromRate.getValue() / (toCurrency.getNominal() * toRate.getValue());

        Convert c = new Convert();
        c.setUserId(convert.getUserId());
        c.setFromCurr(convert.getFromCurr());
        c.setToCurr(convert.getToCurr());
        c.setFromValue(convert.getFromValue());
        c.setToValue(result);
        c.setDate(date);
        convertRepo.save(c);
        return c;
    }

    public List<Object> getHistory(Integer user){
        return convertDao.getHistory(user);
    }

    public List<Object> getHistoryFilter(Integer user, Date historyDate, Integer historyFromCurr, Integer historyToCurr) {
        return convertDao.getHistoryFilter(user, historyDate, historyFromCurr, historyToCurr);
    }
}
