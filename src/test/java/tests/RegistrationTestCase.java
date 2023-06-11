package tests;

import api.UserServices;
import api.pojo.Credentials;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import model.ProfileForm;
import model.ProfileGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;

import static com.codeborne.selenide.Selenide.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertTrue;

public class RegistrationTestCase {

    private UserServices userServices;
    private String accessToken;

    private ProfileForm profile;

    RegistrationPage registration;
    LoginPage login;
    MainPage mainPage;

    @Before
    public void setUp() {
        userServices = new UserServices();
        mainPage = open(MainPage.URL, MainPage.class);
        registration = open(RegistrationPage.URL, RegistrationPage.class);
        profile = ProfileGenerator.getRandom();
        login = page(LoginPage.class);
    }


    @Tag("RegistrationTestCase")
    @Test
    @DisplayName("Проверка успешной регистрации")
    public void checkSuccessfulRegistration() {

        registration.registerNewUser(profile);
        login.loginPage();
        Credentials credentials = new Credentials(profile.getEmail(), profile.getPassword());
        accessToken = userServices.accessToken(userServices.login(credentials)
                .assertThat()
                .statusCode(SC_OK));
    }

    @Tag("RegistrationTestCase")
    @Test
    @DisplayName("Проверка регистрации с паролем меньше минимальной длины в 6 символов")
    public void checkRegistrationWithPasswordLessMinimumLength() {

        profile.setPassword("false");
        registration.registerNewUser(profile);
        assertTrue(registration.checkInvalidPasswordTextDisplayed());
        assertTrue(registration.checkInvalidPasswordTextDisplayed());
        Credentials credentials = new Credentials(profile.getEmail(), profile.getPassword());
        accessToken = userServices.accessToken(userServices.login(credentials)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED));
    }

    @After
    public void tearDown() {

        if (accessToken != null) {
            userServices.deleteUser(accessToken);
        }
        webdriver().driver().close();

    }
}
