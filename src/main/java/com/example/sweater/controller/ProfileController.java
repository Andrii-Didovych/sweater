package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.service.FileWriter;
import com.example.sweater.service.UserService;
import org.jets3t.service.S3ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileWriter fileWriter;

    @GetMapping
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
//        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        String photo = userService.findUser(user.getUsername()).getPhoto();
        model.addAttribute("photo", photo);
        return "profile";
    }


    @PostMapping
    public String updateProfile(@AuthenticationPrincipal User user,
                                @Valid User userRequest,
                                BindingResult bindingResult,
                                Model model
                                )  {
        boolean passwordIsEmpty = userRequest.getPassword().equals(",");
        if (StringUtils.isEmpty(userRequest.getEmail()) || passwordIsEmpty) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            if (passwordIsEmpty)   errors.put("passwordError", "Password cannot be empty");
            model.mergeAttributes(errors);
            return getProfile(model, user);
        }
        String pass = userRequest.getPassword().replaceFirst(",", "");
        if (passwordEncoder.matches(pass, user.getPassword())){
            if(!userService.updateEmail(user, userRequest.getEmail())){
                model.addAttribute("emailError", "Email is the same as previous");
                return getProfile(model, user);
            }
        }else{
            model.addAttribute("passwordError", "Password is wrong!");
            return getProfile(model, user);
        }

        return "redirect:/profile";
    }

    @PostMapping("/photo")
    public String updatePhoto(@AuthenticationPrincipal User user,
                                    @RequestParam("photoOfUser") MultipartFile file,
                                    Model model){
        if (file.isEmpty()) {
            model.addAttribute("fileError", "File was not selected!");
            return getProfile(model, user);
        }
        String newName = null;
        try {
            newName = fileWriter.writeToAmazonS3(file, userService.findUser(user.getUsername()).getPhoto());
        } catch (S3ServiceException e) {
            e.printStackTrace();
        }
        user.setPhoto(newName);
        userService.saveUser(user);
        return "redirect:/profile";
    }

    @PostMapping("/password")
    public String changePassword(@AuthenticationPrincipal User user,
                                 @RequestParam String password,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("repeatPassword") String repeatPassword,
                                 Model model) {
        String pass = password.replaceFirst(",", "");
        boolean fieldsIsFailed = (!StringUtils.isEmpty(password) && !StringUtils.isEmpty(newPassword) && !StringUtils.isEmpty(repeatPassword));
        if (fieldsIsFailed) {
            if (passwordEncoder.matches(pass, user.getPassword())) {
                if (!userService.updatePassword(user, newPassword, repeatPassword)){
                    model.addAttribute("newPasswordError", "New and repeated passwords are different");
                    model.addAttribute("repeatPasswordError", "New and repeated passwords are different");
                    return getProfile(model, user);
                }
            } else{
                model.addAttribute("oldPasswordError", "Old password is wrong");
                return getProfile(model, user);
            }
        } else {
            if (StringUtils.isEmpty(pass))model.addAttribute("oldPasswordError", "field cannot be empty");
            if (StringUtils.isEmpty(newPassword))model.addAttribute("newPasswordError", "field cannot be empty");
            if (StringUtils.isEmpty(repeatPassword))model.addAttribute("repeatPasswordError", "field cannot be empty");
            return getProfile(model, user);
        }

        return "redirect:/profile";
    }

}
