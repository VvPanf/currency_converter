package converter.controller;

import converter.entity.User;
import converter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

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
        return "/";
    }

    @GetMapping("logout")
    public String logout(){
        SecurityContextHolder.clearContext();
        return "redirect:/index";
    }
}
