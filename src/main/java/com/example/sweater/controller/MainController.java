package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import com.example.sweater.service.FileWriter;
import com.example.sweater.service.UserService;
import org.jets3t.service.S3ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
public class MainController {

    @Autowired
    private MessageRepo repo;

    @Autowired
    private UserService userService;

    @Autowired
    private FileWriter fileWriter;

    @GetMapping("/")
    public String greeting(@AuthenticationPrincipal User user,
                            Model model,
                           @RequestParam(required = false, defaultValue = "") String filter) {
        if(user!=null) {
            Iterable<Message> messages;
            if (filter!=null && !filter.isEmpty()){
                messages = repo.findByTag(filter);
                ((List<Message>) messages).sort(null);
            }else {
                messages = repo.findAll();
                 ((List<Message>) messages).sort(null);
            }
            User user1 = userService.findUser(user.getUsername());

            model.addAttribute("authenticatedUser", user);
            model.addAttribute("messages", messages);
            model.addAttribute("filter", filter);
            model.addAttribute("photo", user1.getPhoto());
            model.addAttribute("subscriptionsCount", user1.getSubscription().size());
            model.addAttribute("subscribersCount", user1.getSubscribers().size());
            model.addAttribute("messagesCount", user1.getMessages().size());
            model.addAttribute("message", null);
            model.addAttribute("fiveSubscriptions", ControllerUtils.getFiveUser(user1.getSubscription()));
            model.addAttribute("fiveSubscribers", ControllerUtils.getFiveUser(user1.getSubscribers()));
            return "main";
        }
        else return  "greeting";
    }

    @PostMapping("/")
    public String add(
                      @AuthenticationPrincipal User user,
                      @Valid Message message,
                      BindingResult bindingResult,
                      Model model,
                      @RequestParam("file") MultipartFile file) throws IOException, S3ServiceException {
        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return greeting(user, model, "");
        }else {
            String newName = fileWriter.writeToAmazonS3(file, null);
            message.setFilename(newName);
            repo.save(message);
        }
        return "redirect:/";

    }
}
