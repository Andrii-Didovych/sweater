package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${default.photo.of.user}")
    private String defaultPhoto;

    @Value("${url.path}")
    private String urlPath;

    @Value("spring.jpa.hibernate.ddl-auto")
    private String dllAuto;

    public String getDllAuto() {
        return dllAuto;
    }

    public void setDllAuto(String dllAuto) {
        this.dllAuto = dllAuto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }
        if (userRepo.count()==0 && !dllAuto.equals("validate")) {
            user.setRoles(Collections.singleton(Role.ADMIN));
        }else user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        user.setPhoto(defaultPhoto);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        userRepo.save(user);

        sendMessage(user);

        return true;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://"+urlPath+"/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            System.out.println(message);
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        userRepo.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String,String> form) {
        user.setUsername(username);



        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        if (user.getId() == 1) {
            user.getRoles().add(Role.ADMIN);
        }
        userRepo.save(user);
    }

    public boolean updateEmail(User user, String email) {
        String userEmail = user.getEmail();
        boolean isEmailChanged = !email.equals(userEmail);
        if (isEmailChanged) {
            user.setEmail(email);
            user.setActivationCode(UUID.randomUUID().toString());
            userRepo.save(user);
            sendMessage(user);
            return true;
        }
        return false;
    }

    public boolean updatePassword(User user, String password, String repeatPassword) {

        if (password.equals(repeatPassword)) {
            user.setPassword(passwordEncoder.encode(password));
            userRepo.save(user);
            return true;
        }
        return false;
    }

    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        userRepo.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        userRepo.save(user);
    }


    public User findUser(String username) {
        return userRepo.findByUsername(username);
    }

    public void activate(User user) {
        user.setActive(true);
        userRepo.save(user);
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }
}