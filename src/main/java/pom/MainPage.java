package pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class MainPage {
    private static final String URL = "http://localhost:5000";

    private final WebDriver webDriver;

    public MainPage(WebDriver webDriver) {

        this.webDriver = webDriver;
    }

    private By signUpLink = By.xpath(".//a[@href = '/signup']");

    @Step("Перейти на сайт по ссылке: http://localhost:5000")
    public void open() {
        webDriver.get(URL);
    }
    @Step("Кликнуть по ссылке Sign Up в правом верхнем углу")
    public void clickSignUpLink() {
        webDriver.findElement(signUpLink).click();
    }
}
