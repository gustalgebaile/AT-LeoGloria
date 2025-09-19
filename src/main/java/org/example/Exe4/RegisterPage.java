package org.example.Exe4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage extends BasePage {

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    private final By nameField = By.name("name");
    private final By emailField = By.xpath("//input[@data-qa='signup-email']");
    private final By signupButton = By.xpath("//button[@data-qa='signup-button']");

    // Informações da conta (após clicar em Signup)
    private final By titleMrRadio = By.id("id_gender1");
    private final By titleMrsRadio = By.id("id_gender2");
    private final By passwordField = By.id("password");
    private final By daysDropdown = By.id("days");
    private final By monthsDropdown = By.id("months");
    private final By yearsDropdown = By.id("years");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By offersCheckbox = By.id("optin");

    // Endereço
    private final By firstNameField = By.id("first_name");
    private final By lastNameField = By.id("last_name");
    private final By companyField = By.id("company");
    private final By address1Field = By.id("address1");
    private final By address2Field = By.id("address2");
    private final By countryDropdown = By.id("country");
    private final By stateField = By.id("state");
    private final By cityField = By.id("city");
    private final By zipcodeField = By.id("zipcode");
    private final By mobileNumberField = By.id("mobile_number");

    private final By createAccountButton = By.xpath("//button[@data-qa='create-account']");
    private final By continueButton = By.xpath("//a[@data-qa='continue-button']");

    public void startRegistration(String name, String email) {
        type(nameField, name);
        type(emailField, email);
        click(signupButton);
    }

    public void fillAccountInformation(String title, String password, String day, String month, String year,
                                       boolean subscribeNewsletter, boolean receiveOffers) {
        if ("Mr".equalsIgnoreCase(title)) {
            click(titleMrRadio);
        } else if ("Mrs".equalsIgnoreCase(title)) {
            click(titleMrsRadio);
        }
        type(passwordField, password);

        new Select(driver.findElement(daysDropdown)).selectByVisibleText(day);
        new Select(driver.findElement(monthsDropdown)).selectByVisibleText(month);
        new Select(driver.findElement(yearsDropdown)).selectByVisibleText(year);

        if (subscribeNewsletter) {
            click(newsletterCheckbox);
        }
        if (receiveOffers) {
            click(offersCheckbox);
        }
    }
    public void fillAddressInformation(String firstName, String lastName, String company, String address1, String address2,
                                       String country, String state, String city, String zipcode, String mobileNumber) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(companyField, company);
        type(address1Field, address1);
        type(address2Field, address2);
        new Select(driver.findElement(countryDropdown)).selectByVisibleText(country);
        type(stateField, state);
        type(cityField, city);
        type(zipcodeField, zipcode);
        type(mobileNumberField, mobileNumber);
    }
    public void submitRegistration() {
        click(createAccountButton);
    }
    public void continueRegistration() {
        click(continueButton);
    }
}


