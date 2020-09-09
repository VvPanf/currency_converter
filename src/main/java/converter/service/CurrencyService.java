package converter.service;

import converter.component.XmlHandler;
import converter.entity.Currency;
import converter.entity.Rate;
import converter.repo.CurrencyRepo;
import converter.repo.RateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с таблицей валют.
 * @author Панферов Владимир
 */
@Service
public class CurrencyService {
    @Autowired
    private CurrencyRepo currencyRepo;
    @Autowired
    private RateRepo rateRepo;
    @Autowired
    private XmlHandler xmlHandler;

    public List<Currency> findAll(){
        return currencyRepo.findAll();
    }

    public List<Rate> findByDate(Date date) {
        try {
            getCourses(date);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        List<Rate> rates = rateRepo.findByDate(date).stream()
                .sorted(Comparator.comparing(o -> o.getCurrency().getNumCode())).collect(Collectors.toList());
        return rates;
    }

    public void getCourses(Date date) throws IOException, ParserConfigurationException, SAXException {
        Date maxDate = rateRepo.findByMaxDate();
        String resultDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        try {
            // База данных пуста
            if (maxDate != null) {
                String findDate = new SimpleDateFormat("dd/MM/yyyy").format(maxDate);
                // В базе данныз не актуальные данные
                if (resultDate.equals(findDate))
                    return;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + resultDate);
        InputStream xmlStram = url.openStream();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SAXParser parser = factory.newSAXParser();
        parser.parse(xmlStram, xmlHandler);
        rateRepo.save(new Rate(0l, currencyRepo.findById(1l).get(),1., date));
    }
}
