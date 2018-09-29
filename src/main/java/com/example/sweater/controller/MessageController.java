package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import com.example.sweater.service.FileWriter;
import org.jets3t.service.S3ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class MessageController {

    @Autowired
    private MessageRepo repo;

    @Autowired
    private FileWriter fileWriter;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/user-messages/{user}")
    public String userMessages(@AuthenticationPrincipal User currentUser,
                               @PathVariable User user,
                               @RequestParam(required = false) Message message,
                               Model model,
                               String fromPostForm) {
        Set<Message> messages = user.getMessages();
        Set<Message> set = new TreeSet<>();
        set.addAll(messages);
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscription().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("messagesCount", user.getMessages().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("messages", set);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("textError", fromPostForm);
        model.addAttribute("fiveSubscriptions", ControllerUtils.getFiveUser(user.getSubscription()));
        model.addAttribute("fiveSubscribers", ControllerUtils.getFiveUser(user.getSubscribers()));
        return "userMessages";
    }

    @PostMapping("/delete")
    public String deleteMessage(
            @AuthenticationPrincipal User currentUser,
            @RequestParam("id") Message message
                                ) {
        repo.delete(message);
        return "redirect:/user-messages/" + currentUser.getId();
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            @RequestParam(value = "id", required = false) Message message,
            Model model,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file
    ) throws IOException, S3ServiceException {
        if (text.isEmpty()) {
            return userMessages(currentUser, user, message, model, "Field text cannot be empty");
        }
        if (message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            }
            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }

            if (!file.isEmpty()) {
                String newName = fileWriter.writeToAmazonS3(file, message.getFilename());
                message.setFilename(newName);
            }
            repo.save(message);
        }
        return "redirect:/user-messages/" + user.getId();
    }
}
