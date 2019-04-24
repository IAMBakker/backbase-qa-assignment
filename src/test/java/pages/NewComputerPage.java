package pages;

import dto.Computer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class NewComputerPage extends PageBase {

    @FindBy(id = "name")
    protected WebElement nameField;

    @FindBy(id = "introduced")
    protected WebElement introducedField;

    @FindBy(id = "discontinued")
    protected WebElement discontinuedField;

    @FindBy(id = "company")
    protected WebElement companySelect;

    @FindBy(css = "input[type='submit']")
    protected WebElement createComputerButton;

    public NewComputerPage(WebDriver driver) {
        super(driver);
    }

    public void addComputer(Computer computer){
        nameField.sendKeys(computer.name);
        introducedField.sendKeys(computer.dateIntroduced);
        discontinuedField.sendKeys(computer.dateDiscontinued);
        if(computer.company != Computer.Company.DEFAULT){
            new Select(companySelect).selectByVisibleText(computer.company.name);
        }
        createComputerButton.click();
    }

    public boolean nameFieldHasError() {
        return nameField.findElement(By.xpath("../.."))
                .getAttribute("class")
                .contains("error");
    }

    public boolean dateIntroducedFieldHasError() {
        return introducedField.findElement(By.xpath("../.."))
                .getAttribute("class")
                .contains("error");
    }

    public boolean dateDiscontinuedFieldHasError() {
        return discontinuedField.findElement(By.xpath("../.."))
                .getAttribute("class")
                .contains("error");
    }
}
