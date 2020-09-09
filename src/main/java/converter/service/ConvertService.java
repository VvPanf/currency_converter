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

    public List<ConvertHistoryDto> findAll(){
        List<ConvertHistoryDto> convertHistoryDtos = convertRepo.findAll().stream()
                .map(c -> new ConvertHistoryDto(c)).collect(Collectors.toList());
        return convertHistoryDtos;
    }

    public ConvertHistoryDto addConvert(ConvertHistoryDto convert) {
        Date date = new Date(System.currentTimeMillis());
        try {
            currencyService.getCourses(date);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Currency amountCurr = currencyRepo.findById(convert.getAmountCurrId()).get();
        Currency resultCurr = currencyRepo.findById(convert.getResultCurrId()).get();
        Rate amountRate = rateRepo.findByCurrencyAndDate(amountCurr, date);
        Rate resultRate = rateRepo.findByCurrencyAndDate(resultCurr, date);

        // Calculate result value
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
        List<ConvertTableDto> convertHistoryDtos = convertRepo.findByUser(user).stream()
                .map(c -> new ConvertTableDto(c)).collect(Collectors.toList());
        return convertHistoryDtos;
    }

    public List<ConvertTableDto> getHistoryFilter(HistoryFilter historyFilter) {
        User user = historyFilter.getUser();
        Date date = historyFilter.getDate();
        Currency amountCurr = currencyRepo.findById(historyFilter.getAmountCurr()).get();
        Currency resultCurr = currencyRepo.findById(historyFilter.getResultCurr()).get();
        List<ConvertTableDto> list = convertRepo.findByDateAndAmountCurrAndResultCurrAndUser(date, amountCurr, resultCurr, user)
                .stream().map(c -> new ConvertTableDto(c)).collect(Collectors.toList());
        return list;
    }
}
