package org.example.Exe4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By emailField = By.xpath("//input[@data-qa='login-email']");
    private final By passwordField = By.xpath("//input[@data-qa='login-password']");
    private final By loginButton = By.xpath("//button[@data-qa='login-button']");
    private final By errorMessage = By.xpath("//p[contains(@style,'color:red')]");

    public LoginPage(WebDriver driver) { super(driver); }

    public void login(String email, String password) {
        type(emailField, email);
        type(passwordField, password);
        click(loginButton);
    }

    public String getErrorMessage() {
        return find(errorMessage).getText();
    }
}


