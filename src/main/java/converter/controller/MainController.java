package converter.controller;

import converter.dto.ConvertHistoryDto;
import converter.dto.HistoryFilter;
import converter.entity.Rate;
import converter.entity.User;
import converter.service.ConvertService;
import converter.service.CurrencyService;
import converter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.util.List;

/**
 * Класс-контроллер
 * Обрабатывает страницы списка валют, конвертации и истории конвертаций
 * @author Панферов Владимир
 */
@Controller
public class MainController {
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private ConvertService convertService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String hello(){
        return "redirect:/currency";
    }

    @GetMapping("/currency")
    public String showCurrency(Model model) throws Exception{
        Date date = new Date(System.currentTimeMillis());
        List<Rate> currRates = currencyService.findByDate(date);
        model.addAttribute("currRates", currRates);
        return "currency";
    }

    @GetMapping("/converter")
    public String showConvert(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("currSelect", currencyService.findAll());
        model.addAttribute("conv", new ConvertHistoryDto());
        return "converter";
    }

    @PostMapping("/converter")
    public String calculate(
                             Model model,
                             ConvertHistoryDto conv,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal User user
                             ){
        if (bindingResult.hasErrors()) {
            model.addAttribute("valueError","");
            model.addAttribute("currSelect", currencyService.findAll());
            model.addAttribute("conv", new ConvertHistoryDto());
            return "converter";
        }
        conv.setUser(user);
        conv = convertService.addConvert(conv);
        model.addAttribute("currSelect", currencyService.findAll());
        model.addAttribute("conv", conv);
        return "converter";
    }

    @GetMapping("/history")
    public String showHistory(Model model,
                              @AuthenticationPrincipal User user
    ){
        model.addAttribute("historyFilter", new HistoryFilter());
        model.addAttribute("currSelect", currencyService.findAll());
        model.addAttribute("converts", convertService.getHistory(user));
        model.addAttribute("user", user);
        return "history";
    }

    @PostMapping("/history")
    public String history(Model model,
                          HistoryFilter historyFilter,
                          BindingResult bindingResult,
                          @AuthenticationPrincipal User user
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/history";
        }
        historyFilter.setUser(user);
        model.addAttribute("converts", convertService.getHistoryFilter(historyFilter));
        model.addAttribute("currSelect", currencyService.findAll());
        return "history";
    }
}
