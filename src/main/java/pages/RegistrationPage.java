package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import model.ProfileForm;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Selenide.page;

public class RegistrationPage {

    public static final String URL = "http://stellarburgers.nomoreparties.site/register";

    @FindBy(how = How.XPATH, using = "//form/fieldset[1]/div/div/input")
    private SelenideElement nameInput;

    @FindBy(how = How.XPATH, using = "//form/fieldset[2]/div/div/input")
    private SelenideElement emailInput;

    @FindBy(how = How.CSS, using = "input[name=\"Пароль\"]")
    private SelenideElement passwordInput;

    @FindBy(how = How.CSS, using = ".Auth_form__3qKeq > button")
    private SelenideElement registrationButton;

    @FindBy(how = How.CSS, using = ".input__error ")
    private SelenideElement invalidPasswordText;

    @FindBy(how = How.XPATH, using = ".//a[text()='Войти']")
    private SelenideElement loginLink;

    @Step("Ввод имени")
    public void fillName(String name) {
        nameInput.setValue(name);
    }

    @Step("Ввод пароля")
    public void fillPassword(String password) {
        passwordInput.setValue(password);
    }

    @Step("Ввод электронной почты")
    public void fillEmail(String email) {
        emailInput.setValue(email);
    }

    @Step("Переход на страницу логина из регистрации")
    public LoginPage goToLoginPageFromRegistrationPage() {
        loginLink.click();
        return page(LoginPage.class);
    }

    @Step("Регистрация нового пользователя")
    public void registerNewUser(ProfileForm profile) {
        fillName(profile.getName());
        fillEmail(profile.getEmail());
        fillPassword(profile.getPassword());
        registrationButton.click();
    }

    @Step("Проверка уведомления о неверном пароле")
    public boolean checkInvalidPasswordTextDisplayed() {
        return invalidPasswordText.isDisplayed();
    }
}
