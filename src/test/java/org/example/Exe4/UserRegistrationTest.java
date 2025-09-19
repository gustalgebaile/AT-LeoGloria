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

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TestWatcherExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRegistrationTest {
    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://automationexercise.com/");
        LoginPage loginPage = new LoginPage(driver);
    }

    @Test
    public void shouldRegisterAndLoginSuccessfully() {
        driver.findElement(By.linkText("Signup / Login")).click();

        String email = "gualgebaile1" + System.currentTimeMillis() + "@gmail.com";
        RegisterPage registerPage = new RegisterPage(driver);

        registerPage.startRegistration("Nome Teste", email);

        registerPage.fillAccountInformation("Mr", "senhaSegura123", "10", "April", "1990", true, true);

        registerPage.fillAddressInformation("Nome Teste", "Sobrenome", "Empresa Exemplo",
                "Rua Exemplo, 123", "Complemento", "Canada", "Estado Exemplo", "Cidade Exemplo", "12345-678", "11999999999");

        registerPage.submitRegistration();
        registerPage.continueRegistration();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loggedTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Logged in as')]")));

        assertTrue(loggedTextElement.getText().contains("Nome Teste"));
    }
    @AfterAll
    public void cleanUp() {
        if (driver != null) {
            driver.quit();
        }
    }

}
