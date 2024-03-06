import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pom.LoginPage;
import pom.MainPage;
import pom.SignUpPage;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    WebDriver driver;
    String email;
    String password;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickSignUpLink();
        SignUpPage signUpPage = new SignUpPage(driver);
        Faker faker = new Faker();
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        signUpPage
                .inputЕmailField(email)
                .inputPasswordField(password)
                .clickSignUpButton();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }


    @DisplayName("Переход на страницу авторизации по ссылке Login")
    @Test
    @Story("Авторизация")
    public void switchToLoginPage() {
        String expected = "Login";
        LoginPage loginPage = new LoginPage(driver);
        assertEquals(expected, loginPage.getLoginHeaderText(), "Не происходит переход на страницу авторизации");
    }

    @DisplayName("Вход с существующими Email и Password")
    @Test
    @Story("Авторизация")
    public void loginWithExistEmailAndPassword() {
        String expected = "Profile";
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .inputЕmailField(email)
                .inputPasswordField(password)
                .clickLoginButton();
        assertEquals(expected, loginPage.getProfilrLinkText(), "Не удалось авторизоваться");
    }

    @DisplayName("Вход с незаполненными полями  Email и Password")
    @Test
    @Story("Авторизация")
    public void loginWithEmptyEmailAndPassword() {
        String expected = "Profile";
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .clickLoginButton();
        assertNotEquals(expected, loginPage.getProfilrLinkText(), "Произошла авторизация");
    }

    @DisplayName("Вход с существующим Email и неверным Password")
    @Test
    @Story("Авторизация")
    public void loginWithExistEmailAndInvalidPassword() {
        String expected = "Profile";
        String invalidPassword = password + "scs";
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .inputЕmailField(email)
                .inputPasswordField(invalidPassword)
                .clickLoginButton();
        assertNotEquals(expected, loginPage.getProfilrLinkText(), "Произошла авторизация");
    }

    @DisplayName("Вход с существующим Email и пустым Password")
    @Test
    @Story("Авторизация")
    public void loginWithExistEmailAndEmptyPassword() {
        String expected = "Profile";
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .inputЕmailField(email)
                .clickLoginButton();
        assertNotEquals(expected, loginPage.getProfilrLinkText(), "Произошла авторизация");
    }

    @DisplayName("Вход с существующим Password и неверным Email")
    @Test
    @Story("Авторизация")
    public void loginWithExistPasswordAndInvalidEmail() {
        String expected = "Profile";
        String invalidEmail = "scs" + email;
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .inputЕmailField(invalidEmail)
                .inputPasswordField(password)
                .clickLoginButton();
        assertNotEquals(expected, loginPage.getProfilrLinkText(), "Произошла авторизация");
    }

    @DisplayName("Вход с существующим Password и пустым Email")
    @Test
    @Story("Авторизация")
    public void loginWithExistPasswordAndEmptyEmail() {
        String expected = "Profile";
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .inputPasswordField(password)
                .clickLoginButton();
        assertNotEquals(expected, loginPage.getProfilrLinkText(), "Произошла авторизация");
    }

    @DisplayName("Вход с несуществующими полями  Email и Password")
    @Test
    @Story("Авторизация")
    public void loginWithNonExistEmailAndPassword() {
        String expected = "Profile";
        String invalidEmail = "scs" + email;
        String invalidPassword = password + "scs";
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .inputЕmailField(invalidEmail)
                .inputPasswordField(invalidPassword)
                .clickLoginButton();
        assertNotEquals(expected, loginPage.getProfilrLinkText(), "Произошла авторизация");
    }

}
