package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {
    @FindBy(id = "first-name")
    private WebElement firstNameField;
    @FindBy(id = "last-name")
    private WebElement lastNameField;
    @FindBy(id = "postal-code")
    private WebElement postalCodeField;
    @FindBy(id = "continue")
    private WebElement continueButton;
    @FindBy(id = "finish")
    private WebElement finishButton;
    @FindBy(css = ".complete-header")
    private WebElement successMessage;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage fillInformation(String first, String last, String postal) {
        waitAndType(firstNameField, first);
        waitAndType(lastNameField, last);
        waitAndType(postalCodeField, postal);
        waitAndClick(continueButton);
        return this;
    }

    public CheckoutPage finishCheckout() {
        waitAndClick(finishButton);
        return this;
    }

    public boolean isCheckoutComplete() {
        return getText(successMessage).contains("THANK YOU");
    }
}