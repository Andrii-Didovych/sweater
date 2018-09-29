package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.CaptchaResponseDto;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Controller
public class RecaptchaController {

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/captcha/{user}")
    public String captcha(@PathVariable User user, Model model) {
        model.addAttribute("id", user.getId());
        return "captcha";
    }

    @PostMapping("/captcha/{user}")
    public String confirmCaptcha(@RequestParam(value = "g-recaptcha-response", required = false)  String captchaResponse,
                                 @PathVariable User user,
                                 Model model) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha");
            model.addAttribute("id", user.getId());
            return "captcha";
        }
        userService.activate(user);
        return "redirect:/login";
    }
}
