package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class SignUpPage {
    private final WebDriver driver;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    private By signUpHeader = By.xpath(".//h3[@class = 'title']");
    private By emailField = By.xpath(".//input[@placeholder ='Email']");
    private By passwordField = By.xpath(".//input[@placeholder ='Password']");
    private By nameField = By.xpath(".//input[@placeholder ='Name']");
    private By signUpButton = By.xpath(".//button[text()='Sign Up']");
    private By signUpLink = By.xpath(".//a[@href = '/signup']");
    private  By errorExistEmailMessage = By.xpath(".//div[contains(text(), 'Email address already exists')]");

    public String getSignUpHeaderText() {
        return driver.findElement(signUpHeader).getText();
    }

    public SignUpPage inputЕmailField(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }

    public SignUpPage inputPasswordField(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    public SignUpPage inputNameField(String name) {
        driver.findElement(passwordField).sendKeys(name);
        return this;
    }

    public SignUpPage clickSignUpButton() {
        driver.findElement(signUpButton).click();
        return this;
    }

    public SignUpPage clickSignUpLink() {
        driver.findElement(signUpLink).click();
        return  this;
    }

    public String getErrorExistEmailMessage() {
        return driver.findElement(errorExistEmailMessage).getText().substring(0,28);
    }
}
