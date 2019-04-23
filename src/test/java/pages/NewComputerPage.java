package pages;

import dto.Computer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class NewComputerPage extends PageBase {

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(id = "introduced")
    private WebElement introducedField;

    @FindBy(id = "discontinued")
    private WebElement discontinuedField;

    @FindBy(id = "company")
    private WebElement companySelect;

    @FindBy(css = "input.button[type='submit']")
    private WebElement createComputerButton;

    public NewComputerPage(WebDriver driver) {
        super(driver);
    }

    public void AddComputer(Computer computer){
        nameField.sendKeys(computer.name);
        introducedField.sendKeys(computer.dateIntroduced);
        discontinuedField.sendKeys(computer.dateDiscontinued);
        if(computer.company != Computer.Company.DEFAULT){
            new Select(companySelect).selectByVisibleText(computer.company.name);
        }
        createComputerButton.click();
    }
}
