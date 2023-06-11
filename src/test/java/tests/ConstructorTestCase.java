package tests;


import api.UserServices;
import api.pojo.Credentials;
import api.pojo.User;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.MainPage;
import pages.ProfilePage;


import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;

public class ConstructorTestCase {

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
        main
                .clickLoginButton()
                .setEmail(credentials.getEmail())
                .setPassword(credentials.getPassword())
                .loginButtonClick();
    }

    @Tag("ConstructorTestCase")
    @Test
    @DisplayName("Проверка перехода по клику на «Конструктор» и на логотип Stellar Burgers")
    public void checkingTransitionClickOnConstructorAndOnLogo() {

        main.goToProfilePage();

        ProfilePage profile = page(ProfilePage.class);
        profile.goToLogoBuilderPage();

        main.checkConstructorBlock();

    }

    @Tag("ConstructorTestCase")
    @Test
    @DisplayName("Проверка перехода в раздел «Булки»")
    public void checkTransitionToBunsSection() {

        main
                .goToFillingsClick()
                .goToBunsClick()
                .checkBunsClickOpen();
    }

    @Tag("ConstructorTestCase")
    @Test
    @DisplayName("Проверка перехода в раздел «Соусы»")
    public void checkTransitionToSaucesSection() {
        main
                .goToSaucesClick()
                .checkSaucesClickOpen();
    }

    @Tag("ConstructorTestCase")
    @Test
    @DisplayName("Проверка перехода в раздел «Начинки»")
    public void checkTransitionToFillingsSection() {

        main
                .goToFillingsClick()
                .checkFillingsClickOpen();
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
        Selenide.closeWebDriver();
    }
}
