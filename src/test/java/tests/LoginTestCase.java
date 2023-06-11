package tests;

import api.pojo.Credentials;
import api.pojo.User;
import api.UserServices;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.MainPage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;

public class LoginTestCase {
    private Credentials credentials;
    private UserServices userClient;
    private String accessToken;

    MainPage main;

    @Before
    public void setUp() {
        userClient = new UserServices();
        User user = User.randomUser();
        credentials = Credentials.getCredentials(user);

        accessToken = userClient.accessToken(userClient.register(user)
                .assertThat()
                .statusCode(SC_OK));
        main = open(MainPage.URL, MainPage.class);
    }

    @Tag("LoginTestCase")
    @Test
    @DisplayName("Проверка входа по кнопке «Войти в аккаунт» на главной")
    public void checkLoginOnEntryLoginToYourAccountOnMainPage() {

        main
                .clickLoginButton()
                .setEmail(credentials.getEmail())
                .setPassword(credentials.getPassword())
                .loginButtonClick();

        boolean button = main.checkoutButtonVisible();

        webdriver().shouldHave(url("http://stellarburgers.nomoreparties.site/"));
        assertTrue("Button invisible", button);

    }

    @Tag("LoginTestCase")
    @Test
    @DisplayName("Проверка входа через кнопку «Личный кабинет»")
    public void checkLoginThroughButtonPersonalAccount() {

        main
                .clickCabinetButton()
                .setEmail(credentials.getEmail())
                .setPassword(credentials.getPassword())
                .loginButtonClick();

        boolean button = main.checkoutButtonVisible();

        webdriver().shouldHave(url("http://stellarburgers.nomoreparties.site/"));
        assertTrue("Button invisible", button);
    }

    @Tag("LoginTestCase")
    @Test
    @DisplayName("Проверка входа через кнопку в форме регистрации")
    public void checkLoginThroughButtonInRegistrationForm() {

        main
                .clickLoginButton()
                .regLinkClick()
                .goToLoginPageFromRegistrationPage()
                .setEmail(credentials.getEmail())
                .setPassword(credentials.getPassword())
                .loginButtonClick();

        boolean button = main.checkoutButtonVisible();

        webdriver().shouldHave(url("http://stellarburgers.nomoreparties.site/"));
        assertTrue("Button invisible", button);
    }

    @Tag("LoginTestCase")
    @Test
    @DisplayName("Проверка входа через кнопку в форме восстановления пароля")
    public void checkLoginThroughButtonInPasswordRecoveryForm() {

        main
                .clickLoginButton()
                .resetPasswordLinkClick()
                .loginLinkClick()
                .setEmail(credentials.getEmail())
                .setPassword(credentials.getPassword())
                .loginButtonClick();

        boolean button = main.checkoutButtonVisible();

        webdriver().shouldHave(url("http://stellarburgers.nomoreparties.site/"));
        assertTrue("Button invisible", button);
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
        webdriver().driver().close();
    }
}
