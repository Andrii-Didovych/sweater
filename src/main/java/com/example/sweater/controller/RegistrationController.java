package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping ("/registration")
    public String validate(@RequestParam("password2") String password2,
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model) {

        boolean notTheSame = !user.getPassword().equals(password2);
        boolean password2IsEmpty = StringUtils.isEmpty(password2);
        if (notTheSame) {
            model.addAttribute("passwordError", "Passwords are different!");
        }

        if (password2IsEmpty) {
            model.addAttribute("password2Error", "Field cannot be empty");
        }

        if (bindingResult.hasErrors() || notTheSame|| password2IsEmpty) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/captcha/" + user.getId();
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated! Please login again. ");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }

    @GetMapping("/problem")
    public String showProblemOfAuthorization() {
        return "problem";
    }

    @PostMapping("/problem")
    public String resolveProblem(@RequestParam(value = "username", required = false)  String username, Model model) {
        User user = userService.findUser(username);
        if (user==null){
            model.addAttribute("username", username);
            model.addAttribute("message", "User doesn't exist!");
            return "problem";
        }
        if (user.getActivationCode()!=null) {
            model.addAttribute("username", username);
            model.addAttribute("message", "You have to confirm your email!");
            return "problem";
        }
        if (!user.isActive()) return  "redirect:/captcha/"+ user.getId();
        else {
            model.addAttribute("username", username);
            model.addAttribute("message", "User has already successfully exist");
            return "problem";
        }
    }
}