package tests;

import api.UserServices;
import api.pojo.Credentials;
import api.pojo.User;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.ProfilePage;

import static com.codeborne.selenide.Selenide.*;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;

public class ProfileTestCase {

    MainPage main;
    ProfilePage profile;
    LoginPage loginClass;
    private Credentials credentials;
    private UserServices userClient;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserServices();
        User user = User.randomUser();
        credentials = Credentials.getCredentials(user);

        accessToken = userClient.accessToken(userClient.register(user)
                .assertThat()
                .statusCode(SC_OK));

        loginClass = page(LoginPage.class);
        profile = page(ProfilePage.class);
        main = open(MainPage.URL, MainPage.class);
        main
                .clickLoginButton()
                .setEmail(credentials.getEmail())
                .setPassword(credentials.getPassword())
                .loginButtonClick();
    }

    @Tag("ProfileTestCase")
    @Test
    @DisplayName("Проверка перехода по клику на «Личный кабинет")
    public void checkTransitionByClickingOnPersonalAccount() {

        main.
                goToProfilePage();
        profile.
                checkProfilePage();
    }

    @Tag("ProfileTestCase")
    @Test
    @DisplayName("Проверка выхода по кнопке «Выйти» в личном кабинете")
    public void checkExitByClickLogoutButtonInYourAccount() {

        main.goToProfilePage();
        profile.logout();
        loginClass.checkAuthorised();

    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
        webdriver().driver().close();
    }
}
