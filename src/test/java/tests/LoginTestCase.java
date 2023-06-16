package tests;

import api.UserServices;
import api.pojo.Credentials;
import api.pojo.User;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.MainPage;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;
import static pages.MainPage.URL;

public class LoginTestCase {
    MainPage main;
    private Credentials credentials;
    private UserServices userClient;
    private String accessToken;

    @Before
    public void setUp() throws IOException {

        System.getProperties().load(ClassLoader.getSystemResourceAsStream("browsers.properties"));
        String Browser = System.getProperty("browser");
        System.setProperty("selenide.browser", Browser);

        userClient = new UserServices();
        User user = User.randomUser();
        credentials = Credentials.getCredentials(user);

        accessToken = userClient.accessToken(userClient.register(user)
                .assertThat()
                .statusCode(SC_OK));
        main = open(URL, MainPage.class);
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

        webdriver().shouldHave(url(URL));
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

        webdriver().shouldHave(url(URL));
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

        webdriver().shouldHave(url(URL));
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

        webdriver().shouldHave(url(URL));
        assertTrue("Button invisible", button);
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
        webdriver().driver().close();
    }
}
