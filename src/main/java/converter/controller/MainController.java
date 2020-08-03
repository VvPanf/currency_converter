package converter.controller;

import converter.dto.HistoryFilter;
import converter.entity.Convert;
import converter.entity.User;
import converter.service.ConvertService;
import converter.service.CurrencyService;
import converter.service.RateService;
import converter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

/**
 * Класс-контроллер
 * @author Панферов Владимир
 */
@Controller
public class MainController {
    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private RateService rateService;

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
        List<Object> currdto = currencyService.findByDate(date);
        model.addAttribute("currdto", currdto);
        return "currency";
    }

    @GetMapping("/converter")
    public String showConvert(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("currSelect", currencyService.findAll());
        model.addAttribute("conv", new Convert(0,user.getId(),0, 0,0.,0.,
                new Date(System.currentTimeMillis())));
        return "converter";
    }

    @PostMapping("/converter")
    public String calculate(
                             Model model,
                             @Valid Convert conv,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal User user
                             ){
        if (bindingResult.hasErrors()) {
            model.addAttribute("valueError","");
            model.addAttribute("currSelect", currencyService.findAll());
            model.addAttribute("conv", new Convert(0,user.getId(),0,0,0.,0.,
                    new Date(System.currentTimeMillis())));
            return "converter";
        }
        conv.setUserId(user.getId());
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
        model.addAttribute("converts", convertService.getHistory(user.getId()));
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
        model.addAttribute("converts", convertService.getHistoryFilter(user.getId(),
                                                                                    historyFilter.getHistoryDate(),
                                                                                    historyFilter.getHistoryFromCurr(),
                                                                                    historyFilter.getHistoryToCurr()));
        model.addAttribute("currSelect", currencyService.findAll());
        return "history";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Model model,
                          @Valid User user,
                          BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            model.addAttribute("valueError");
            return "registration";
        }
        if(!userService.addUser(user)){
            model.addAttribute("userError");
            return "registration";
        }
        return "index";
    }

    @GetMapping("logout")
    public String logout(){
        SecurityContextHolder.clearContext();
        return "redirect:/index";
    }
}
