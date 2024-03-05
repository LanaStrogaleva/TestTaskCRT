package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    private By loginHeader = By.xpath(".//h3[@class = 'title']");
    private By emailField = By.xpath(".//input[@name ='email']");
    private By passwordField = By.xpath(".//input[@name ='password']");
    private By loginButton = By.xpath(".//button[text()='Login']");
    private By profileLink = By.xpath(".//a[@href][2]");


    public String getLoginHeaderText() {
        return driver.findElement(loginHeader).getText();
    }

    public LoginPage inputЕmailField(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }

    public LoginPage inputPasswordField(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public String getProfilrLinkText() {
        return driver.findElement(profileLink).getText();
    }
}
