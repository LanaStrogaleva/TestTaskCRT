import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pom.LoginPage;
import pom.MainPage;
import pom.SignUpPage;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SignUpTest {
    WebDriver driver;

    static Stream<Arguments> positiveEmailData() {
        return Stream.of(
                arguments( "example@mail.ru", "Локальная часть содержит строчные буквы"),
                arguments( "Example@mail.ru", "Локальная часть содержит заглавные буквы"),
                arguments( "exa55mple@mail.ru", "Локальная часть содержит цифры"),
                arguments( "55example@mail.ru", "Локальная часть начинается с цифры"),
                arguments( "example55@mail.ru", "Локальная часть заканчивается цифрой"),
                arguments( "exam_ple@mail.ru", "Локальная часть содержит нижнее подчеркивание"),
                arguments( "exam.ple@mail.ru", "Локальная часть содержит точку"),
                arguments( "exam-ple@mail.ru", "Локальная часть содержит дефис"),
                arguments( "example@ma-il.ru", "Email с дефисом в доменной части email"),
                arguments( "example@ma_il.ru", "Email с нижним подчеркиванием в доменной части email"),
                arguments( "exampleexampleexampleexampleexampleexampleexampleexampleexamplee@xaminationxaminationxaminationxaminationxaminationxaminationxam.xaminationxaminationxaminationxaminationxaminationxaminationxam.xaminationxaminationxaminationxaminationxaminationxaminationxam", "Ошибка, если Длинный Email (локальная часть 64 символа, доменная состоит из 3 участков по 63\n" +
                        "символа, разделенных точками)")
        );
    }

    static Stream<Arguments> negativeEmailData() {
        return Stream.of(
                arguments("examplemail.ru", "Отсутствие @ в email"),
                arguments("@mail.ru", "Отсутствие локальной части"),
                arguments("example@", "Отсутствие доменной части"),
                arguments("@", "Отсутствие локальной и доменной части"),
                arguments("example@@mail.ru", "Два знака @@ подряд"),
                arguments("example@ex@mail.ru", "Два знака @@ через символы"),
                arguments("example@mailru", "Доменная часть не содержит точку"),
                arguments("exam#$ple@mail.ru", "Локальная часть содержит спецсимволы"),
                arguments("exam ple@mail.ru", "Локальная часть содержит пробелы"),
                arguments(".example@mail.ru", "Локальная часть начинается с точки"),
                arguments("_example@mail.ru", "Локальная часть начинается с нижнего подчеркивания"),
                arguments("-example@mail.ru", "Локальная часть начинается с дефиса"),
                arguments("example.@mail.ru", "Локальная часть заканчивается точкой"),
                arguments("example_@mail.ru", "Локальная часть заканчивается нижним подчеркиванием"),
                arguments("example-@mail.ru", "Локальная часть заканчивается дефисом"),
                arguments("exam..ple@mail.ru", "Локальная часть содержит точки подряд"),
                arguments("exam--ple@mail.ru", "Локальная часть содержит дефисы подряд"),
                arguments("exam__ple@mail.ru", "Локальная часть содержит нижние подчеркивания подряд"),
                arguments("example@.mail.ru", "Доменная часть начинается с точки"),
                arguments("example@_mail.ru", "Доменная часть начинается с нижнего подчеркивания"),
                arguments("example@-mail.ru", "Доменная часть начинается с дефиса"),
                arguments("example@mail.ru", "Доменная часть заканчивается точкой"),
                arguments("example@mail.ru", "Доменная часть заканчивается нижним подчеркиванием"),
                arguments("example@mail.ru-", "Доменная часть заканчивается дефисом"),
                arguments("example@mail..ru", "Доменная часть содержит точки подряд"),
                arguments("example@ma--il.ru", "Доменная часть содержит дефисы подряд"),
                arguments("example@ma__il.ru", "Доменная часть содержит нижние подчеркивания подряд"),
                arguments("exampleexampleexampleexampleexampleexampleexampleexampleexampleexam@mail.ru", "Превышение длины локальной части (максимальная допустимая 64 символа)"),
                arguments("exampleexampleexampleexampleexampleexampleexampleexampleexampl@xaminationxaminationxaminationxaminationxaminationxaminationxam.xaminationxaminationxaminationxaminationxaminationxaminationxam.xaminationxaminationxaminationxaminationxaminationxaminationxamination", "Превышение длины доменного имени (максимальная допустимая 255 символов)"),
                arguments("example@xaminationxaminationxaminationxaminationxaminationxaminationxamenation.ru", "Превышение длины участка доменного имени между точками (максимальная допустимая\n" +
                        "63 символа)"),
                arguments("     ", "только пробелы")
        );
    }

    static Stream<Arguments> positivePasswordData() {
        return Stream.of(
                arguments("Ббб", "Русские буквы"),
                arguments("Bbb", "Латинские буквы"),
                arguments("Bbb-cc", "Дефис"),
                arguments("Bbb555", "Цифры"),
                arguments("Bb:b", "Двоеточие"),
                arguments("Bb#b", "Спецсимволы"),
                arguments("b", "Длина  1 символ"),
                arguments("Bbbbbbbbbb", "Длина  10 символов")
        );
    }


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
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }


    @DisplayName("Переход на страницу регистрации по ссылке Sign Up")
    @Test
    @Story("Регистрация")
    public void switchToSignUpPage() {
        String expected = "Sign Up";
        SignUpPage signUpPage = new SignUpPage(driver);
        assertEquals(expected, signUpPage.getSignUpHeaderText(), "Не происходит переход на страницу регистрации");
    }
    @DisplayName("Регистрация с  валидными Email и Password")
    @Test
    @Story("Регистрация")
    public void registrationWithEmailAndPassword() {
        Faker faker = new Faker();
        String expected = "Login";
        SignUpPage signUpPage = new SignUpPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        signUpPage
                .inputЕmailField(faker.internet().emailAddress())
                .inputPasswordField(faker.internet().password())
                .clickSignUpButton();
        assertEquals(expected, loginPage.getLoginHeaderText(), "Не происходит переход на страницу авторизации");

    }
    @DisplayName("Регистрация с валидными Email, Password  и Name")
    @Test
    @Story("Регистрация")
    public void registrationWithEmailPasswordName() {
        Faker faker = new Faker();
        String expected = "Login";
        SignUpPage signUpPage = new SignUpPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        signUpPage
                .inputЕmailField(faker.internet().emailAddress())
                .inputPasswordField(faker.internet().password())
                .inputNameField(faker.internet().domainName())
                .clickSignUpButton();
        assertEquals(expected, loginPage.getLoginHeaderText(), "Не происходит переход на страницу авторизации");
    }
    @DisplayName("Регистрация с незаполненными полями  Email и Password")
    @Test
    @Story("Регистрация")
    public void registrationWithoutEmailAndPassword() {
        String expected = "Login";
        SignUpPage signUpPage = new SignUpPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        signUpPage
                .clickSignUpButton();
        assertNotEquals(expected, loginPage.getLoginHeaderText(), "Сообщение об ошибке нет, происходит переход на страницу авторизации");
    }
    @DisplayName("Регистрация с незаполненным полем  Email")
    @Test
    @Story("Регистрация")
    public void registrationWithoutEmail() {
        Faker faker = new Faker();
        String expected = "Login";
        SignUpPage signUpPage = new SignUpPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        signUpPage
                .inputPasswordField(faker.internet().password())
                .clickSignUpButton();
        assertNotEquals(expected, loginPage.getLoginHeaderText(), "Сообщение об ошибке нет, происходит переход на страницу авторизации");

    }
    @DisplayName("Регистрация с незаполненным полем  Password ")
    @Test
    @Story("Регистрация")
    public void registrationWithoutPassword() {
        Faker faker = new Faker();
        String expected = "Login";
        SignUpPage signUpPage = new SignUpPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        signUpPage
                .inputЕmailField(faker.internet().emailAddress())
                .clickSignUpButton();
        assertNotEquals(expected, loginPage.getLoginHeaderText(), "Сообщение об ошибке нет, происходит переход на страницу авторизации");

    }
    @DisplayName("Регистрация с существующим Email ")
    @Test
    @Story("Регистрация")
    public void registrationWithExistEmail() {
        Faker faker = new Faker();

        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String expected = "Email address already exists";
        SignUpPage signUpPage = new SignUpPage(driver);

        signUpPage
                .inputЕmailField(email)
                .inputPasswordField(password)
                .clickSignUpButton()
                .clickSignUpLink()
                .inputNameField(email)
                .inputPasswordField(password)
                .clickSignUpButton();
        assertEquals(expected, signUpPage.getErrorExistEmailMessage(), "Сообщение об ошибке нет, происходит переход на страницу авторизации");

    }
    @DisplayName("Позитивные проверки. Валидация поля Email")
    @ParameterizedTest
    @Story("Регистрация")
    @MethodSource("positiveEmailData")
    public void checkPositiveEmailValidation(String validEmail, String description) {
        String expected = "Login";
        String password = "wwwww";
        SignUpPage signUpPage = new SignUpPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        signUpPage
                .inputЕmailField(validEmail)
                .inputPasswordField(password)
                .clickSignUpButton();
        assertEquals(expected, loginPage.getLoginHeaderText(), "Не происходит переход на страницу авторизации, если "+description);
    }
    @DisplayName("Негативные проверки. Валидация поля Email")
    @ParameterizedTest
    @Story("Регистрация")
    @MethodSource("negativeEmailData")
    public void checkNegativeEmailValidation(String invalidEmail, String description) {
        String expected = "Login";
        String password = "wwwww";
        SignUpPage signUpPage = new SignUpPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        signUpPage
                .inputЕmailField(invalidEmail)
                .inputPasswordField(password)
                .clickSignUpButton();
        assertNotEquals(expected, loginPage.getLoginHeaderText(), "Происходит переход на страницу авторизации, если "+ description);

    }

    @DisplayName("Позитивные проверки. Валидация поля Password")
    @ParameterizedTest
    @Story("Регистрация")
    @MethodSource("positivePasswordData")
    public void checkPositivePasswordValidation(String validPassword, String description) {
        Faker faker = new Faker();
        String expected = "Login";
        String email = faker.internet().emailAddress();
        SignUpPage signUpPage = new SignUpPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        signUpPage
                .inputЕmailField(email)
                .inputPasswordField(validPassword)
                .clickSignUpButton();
        assertEquals(expected, loginPage.getLoginHeaderText(), "Не происходит переход на страницу авторизации, если пароль содержит "+ description);
    }

    @DisplayName("Негативные проверки. Валидация поля Password")
    @Test
    public void checkNegativePasswordValidation() {


    }

}
