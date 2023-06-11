package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;

public class MainPage {

    public static final String URL = "http://stellarburgers.nomoreparties.site/";

    @FindBy(how = How.CSS,using = ".AppHeader_header__logo__2D0X2 ~ a")
    protected SelenideElement linkToProfilePage;

    @FindBy(how = How.XPATH, using = "//p[text()='Конструктор']")
    protected SelenideElement constructorLabel;

    @FindBy(how = How.XPATH, using = ".//span [@class='text text_type_main-default'][text()='Булки']")
    protected SelenideElement bunsTab;

    @FindBy(how = How.XPATH, using = ".//span[text()='Булки']")
    protected SelenideElement bunsTitle;

    @FindBy(how = How.XPATH, using = ".//span [@class='text text_type_main-default'][text()='Соусы']")
    protected SelenideElement saucesTab;

    @FindBy(how = How.XPATH, using = ".//span[text()='Соусы']")
    protected SelenideElement saucesTitle;

    @FindBy(how = How.XPATH, using = ".//span [@class='text text_type_main-default'][text()='Начинки']")
    protected SelenideElement fillingsTab;

    @FindBy(how = How.XPATH, using = ".//span[text()='Начинки']")
    protected SelenideElement fillingsTitle;

    @FindBy(how = How.XPATH, using = ".//button [text()='Войти в аккаунт']")
    private SelenideElement loginButton;

    @FindBy(how = How.XPATH, using = "//* [@href='/account']")
    private SelenideElement cabinetButton;

    @FindBy(how = How.XPATH, using = ".//button[text()='Оформить заказ']")
    private SelenideElement checkout;

    @Step("Клик по кнопке 'Войти в аккаунт'")
    public LoginPage clickLoginButton() {
        loginButton.click();
        return page(LoginPage.class);
    }

    @Step("Клик по кнопке 'Личный кабинет'")
    public LoginPage clickCabinetButton() {
        cabinetButton.click();
        return page(LoginPage.class);
    }

    @Step("Перейти на страницу профиля")
    public LoginPage  goToProfilePage(){
        linkToProfilePage.click();
        return page(LoginPage.class);
    }

    @Step("Нажать на раздел Булки")
    public MainPage goToBunsClick(){
        bunsTab.click();
        return page(MainPage.class);
    }

    @Step("Проверка видимости раздела Булки")
    public boolean checkBunsClickOpen() {
        if (bunsTitle.getAttribute("class").contains("tab_tab__1SPyG tab_tab_type_current__2BEPc")) {
            return true;
        } else {
            return false;
        }
    }

    @Step("Нажать на раздел Соусы")
    public MainPage goToSaucesClick(){
        saucesTab.click();
        return page(MainPage.class);
    }

    @Step("Проверка видимости раздела Соусы")
    public boolean checkSaucesClickOpen(){
        if (saucesTitle.getAttribute("class").contains("tab_tab__1SPyG tab_tab_type_current__2BEPc")) {
            return true;
        } else {
            return false;
        }
    }

    @Step("Нажать на раздел Начинки")
    public MainPage goToFillingsClick(){
        fillingsTab.click();
        return page(MainPage.class);
    }

    @Step("Проверка видимости раздела Начинки")
    public boolean checkFillingsClickOpen(){
        if (fillingsTitle.getAttribute("class").contains("tab_tab__1SPyG tab_tab_type_current__2BEPc")) {
            return true;
        } else {
            return false;
        }
    }

    @Step("Проверка видимости блока Конструктор")
    public LoginPage checkConstructorBlock(){
        constructorLabel.shouldBe(visible);
        return page(LoginPage.class);
    }
    @Step("Отображение кнопки Оформить заказ")
    public boolean checkoutButtonVisible() {
        checkout.is(Condition.visible);
        return true;
    }

}
