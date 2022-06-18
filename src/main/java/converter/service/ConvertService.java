package converter.service;

import converter.dto.ConvertHistoryDto;
import converter.dto.ConvertTableDto;
import converter.dto.HistoryFilter;
import converter.entity.Convert;
import converter.entity.Currency;
import converter.entity.Rate;
import converter.entity.User;
import converter.repo.ConvertRepo;
import converter.repo.CurrencyRepo;
import converter.repo.RateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с операциями по конвертации валют.
 * @author Панферов Владимир
 */
@Service
public class ConvertService {
    @Autowired
    private ConvertRepo convertRepo;
    @Autowired
    private CurrencyRepo currencyRepo;
    @Autowired
    private RateRepo rateRepo;
    @Autowired
    private CurrencyService currencyService;

    public ConvertHistoryDto addConvert(ConvertHistoryDto convert) {
        Date date = new Date(System.currentTimeMillis());

        // Проверка наличия валют в базе
        try {
            currencyService.getCourses(date);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Currency amountCurr = currencyRepo.findById(convert.getAmountCurrId()).get();
        Currency resultCurr = currencyRepo.findById(convert.getResultCurrId()).get();
        Rate amountRate = amountCurr.getChrCode().equals("RUB")
                ? new Rate(0l,amountCurr,1., date)
                : rateRepo.findByCurrencyAndDate(amountCurr, date);
        Rate resultRate = resultCurr.getChrCode().equals("RUB")
                ? new Rate(0l,resultCurr,1., date)
                : rateRepo.findByCurrencyAndDate(resultCurr, date);

        // Расчёт результатов конвертации
        Double result = convert.getAmount() * amountCurr.getNominal() * amountRate.getValue()
                / (resultCurr.getNominal() * resultRate.getValue());
        convert.setResult(result);

        Convert c = new Convert();
        c.setUser(convert.getUser());
        c.setAmountCurr(amountCurr);
        c.setResultCurr(resultCurr);
        c.setAmount(convert.getAmount());
        c.setResult(convert.getResult());
        c.setDate(date);
        convertRepo.save(c);
        return convert;
    }

    public List<ConvertTableDto> getHistory(User user){
        return convertRepo.findByUser(user).stream()
                .map(ConvertTableDto::new).collect(Collectors.toList());
    }

    public List<ConvertTableDto> getHistoryFilter(HistoryFilter historyFilter) {
        Currency amountCurr = currencyRepo.findById(historyFilter.getAmountCurr()).get();
        Currency resultCurr = currencyRepo.findById(historyFilter.getResultCurr()).get();
        return convertRepo.findByDateAndAmountCurrAndResultCurrAndUser(historyFilter.getDate(), amountCurr, resultCurr, historyFilter.getUser())
                .stream().map(ConvertTableDto::new).collect(Collectors.toList());
    }
}
