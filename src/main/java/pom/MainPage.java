package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class MainPage {
    private static final String URL = "http://localhost:5000";

    private final WebDriver webDriver;

    public MainPage(WebDriver webDriver) {

        this.webDriver = webDriver;
    }

    private By signUpLink = By.xpath(".//a[@href = '/signup']");

    // Открыть главную страницу
    public void open() {
        webDriver.get(URL);
    }

    public void clickSignUpLink() {
        webDriver.findElement(signUpLink).click();
    }
}
