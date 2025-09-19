package org.example.Exe4;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TestWatcherExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private RegisterPage registerPage;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://automationexercise.com");
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
    }

    @Test
    public void registroELoginValido() {
        driver.findElement(By.linkText("Signup / Login")).click();

        String email = "qa" + System.currentTimeMillis() + "@mail.com";
        String password = "senhaSegura123";

        registerPage.startRegistration("Teste QA", email);
        registerPage.fillAccountInformation("Mr", password, "10", "April", "1990", true, true);
        registerPage.fillAddressInformation("Teste", "QA", "Empresa",
                "Rua Teste, 123", "Ap 1", "Canada", "Ontario", "12345", "11999999999", "999999");
        registerPage.submitRegistration();
        registerPage.continueRegistration();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loggedUserElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Logged in as')]")));
        String loggedUser = loggedUserElement.getText();
        assertTrue(loggedUser.contains("Teste QA"));

        driver.findElement(By.linkText("Logout")).click();

        loginPage.login(email, password);

        loggedUserElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Logged in as')]")));
        loggedUser = loggedUserElement.getText();
        assertTrue(loggedUser.contains("Teste QA"));
    }

    @Test
    public void loginInvalido() {
        driver.findElement(By.linkText("Signup / Login")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='login-email']")));

        loginPage.login("usuario@invalido.com", "senhaErrada123");

        WebElement errorElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@style,'color: red')]")));

        assertEquals("Your email or password is incorrect!", errorElem.getText());
    }
    @AfterAll
    public void cleanUp() {
        if (driver != null) {
            driver.quit();
        }
    }

}
