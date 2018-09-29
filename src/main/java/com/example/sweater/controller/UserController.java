package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("subscribe/{user}")
    public String subscribe(@PathVariable User user,
                            @AuthenticationPrincipal User currentUser) {
        userService.subscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(@PathVariable User user,
                            @AuthenticationPrincipal User currentUser) {
        userService.unsubscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String userList(Model model,
                           @PathVariable User user,
                           @PathVariable String type) {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);
        System.out.println(user.getPhoto());

        if ("subscriptions".equals(type)) {
            model.addAttribute("users", user.getSubscription());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }
        return "subscription";
    }

}
