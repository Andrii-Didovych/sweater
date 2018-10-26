package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class UserServiceTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private MailSender mailSender;

    @MockBean
    private PasswordEncoder encoder;

    @Test
    public void addUser() {
        User user = new User();

        user.setEmail("some@mail.com");

        boolean isUserCreated = service.addUser(user);

        Assert.assertTrue(isUserCreated);

        Assert.assertNotNull(user.getActivationCode());
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.ADMIN)));

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(mailSender, Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    @Test
    public void addUserFailTest() {

        User user = new User();

        user.setUsername("John");

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername("John");

        boolean isUserCreated = service.addUser(user);

        Assert.assertFalse(isUserCreated);

        Mockito.verify(userRepo, Mockito.times(0)).save(any(User.class));
        Mockito.verify(mailSender, Mockito.times(0))
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    @Test
    public void addUserFailTestSecondIf() {

        User user = new User();

        user.setEmail("mail@mail");

        Mockito.doReturn(Long.valueOf(0))
                .when(userRepo)
                .count();

        boolean isUserCreated = service.addUser(user);

        Assert.assertTrue(isUserCreated);
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.ADMIN)));

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(mailSender, Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    @Test
    public void activateUserTest() {

        User user = new User();

        user.setActivationCode("bingo");

        Mockito.doReturn(user)
                .when(userRepo)
                .findByActivationCode("activate");

        boolean isUserActivated = service.activateUser("activate");

        Assert.assertTrue(isUserActivated);
        Assert.assertNull(user.getActivationCode());

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }



    @Test
    public void activateUserFailTest() {
        boolean isUserActivated = service.activateUser("activate me");

        Assert.assertFalse(isUserActivated);

        Mockito.verify(userRepo, Mockito.times(0)).save(any(User.class));
    }

    @Test
    public void updateEmailTest() {
        User user = new User();
        String email = "othermail@mail";
        user.setEmail("particularmail@mail");
        boolean isEmailCreated = service.updateEmail(user, email);
        Assert.assertTrue(isEmailCreated);
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    public void updateEmailFailTest() {
        User user = new User();
        String email = "mail@mail";
        user.setEmail("mail@mail");
        boolean isEmailCreated = service.updateEmail(user, email);
        Assert.assertFalse(isEmailCreated);
        Mockito.verify(userRepo, Mockito.times(0)).save(any(User.class));
    }

    @Test
    public void updatePasswordTest() {
        User user = new User();
        String password = "1";
        String newPassword = "1";
        boolean isUpdatePassword = service.updatePassword(user, password, newPassword);
        Assert.assertTrue(isUpdatePassword);
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }
}