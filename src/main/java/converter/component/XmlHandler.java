package converter.component;

import converter.entity.Currency;
import converter.entity.Rate;
import converter.repo.CurrencyRepo;
import converter.repo.RateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для парсинга xml-файла
 * @author Панферов Владимир
 */

@Component
public class XmlHandler extends DefaultHandler {
    @Autowired
    private CurrencyRepo currencyRepo;

    @Autowired
    private RateRepo rateRepo;

    private List<Rate> list = new ArrayList<>();
    private String elementValue;
    private Currency currency;
    private Rate rate;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (currency == null) {
            if (qName.equalsIgnoreCase("Valute")) {
                currency = new Currency();
            }
        } else {
            if (qName.equalsIgnoreCase("Value")) {
                rate = new Rate();
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName.toUpperCase()) {
            case "NUMCODE" :
                currency.setNumCode(elementValue);
                break;
            case "CHARCODE" :
                currency.setCode(elementValue);
                break;
            case "NOMINAL" :
                currency.setNominal(Integer.parseInt(elementValue));
                break;
            case "NAME" :
                currency.setCurrName(elementValue);
                if(currencyRepo.findByCode(currency.getCode())==null){
                    currencyRepo.save(currency);
                }
                currency = currencyRepo.findByCode(currency.getCode());
                break;
            case "VALUE" :
                rate.setCurrId(currency.getId());
                rate.setDate(new Date(System.currentTimeMillis()));
                rate.setValue(Double.valueOf(elementValue.replace(',','.')));
                list.add(rate);
                rate = null;
                break;
            case "VALUTE" :
                currency = null;
                break;
            case "VALCURS" :
                rateRepo.saveAll(list);
                break;
        }
    }
}
