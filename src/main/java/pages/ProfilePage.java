package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Selenide.page;

public class ProfilePage {

    @FindBy(how = How.CSS, using = ".Account_listItem__35dAP button")
    protected SelenideElement signOutButton;

    @FindBy(how = How.CSS, using = ".AppHeader_header__logo__2D0X2")
    protected SelenideElement logoButton;

    @Step("Клик по кнопке Выход")
    public LoginPage logout() {
        signOutButton.click();
        return page(LoginPage.class);
    }

    @Step("Проверка видимости кнопки выхода")
    public void checkProfilePage() {
        signOutButton.shouldHave(Condition.text("Выход"));
    }

    @Step("Переход на страницу конструктора по логотипу")
    public void goToLogoBuilderPage() {
        logoButton.click();
    }

}
