package pages;

import dto.Computer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class EditComputerPage extends NewComputerPage {

    public EditComputerPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "input.btn.danger")
    private WebElement deleteButton;

    @Override
    public void addComputer(Computer computer){
        throw new Error("This method is not implemented in the EditComputerPage");
    }

    public EditComputerPage editComputerName(String newComputerName) {
        nameField.clear();
        nameField.sendKeys(newComputerName);
        return this;
    }

    public EditComputerPage editComputerDateIntroduced(String newDateIntroduced) {
        introducedField.clear();
        introducedField.sendKeys(newDateIntroduced);
        return this;
    }

    public EditComputerPage editComputerDateDiscontinued(String newDateDiscontinued) {
        discontinuedField.clear();
        discontinuedField.sendKeys(newDateDiscontinued);
        return this;
    }

    public EditComputerPage editComputerCompany(Computer.Company newCompany){
        new Select(companySelect).selectByVisibleText(newCompany.name);
        return this;
    }

    public String getComputerID(){
        String[] splitUrl = driver.getCurrentUrl().split("/");
        return splitUrl[splitUrl.length - 1];
    }

    public void updateComputer(){
        createComputerButton.click();
    }

    public Computer getDisplayedComputerData(){
        Computer computer = new Computer();

        computer.name = nameField.getAttribute("value");
        computer.dateIntroduced = introducedField.getAttribute("value");
        computer.dateDiscontinued = discontinuedField.getAttribute("value");
        computer.company = getSelectedCompany();
        return computer;
    }

    private Computer.Company getSelectedCompany(){
        return Computer.Company.parseName(
                new Select(companySelect).getFirstSelectedOption().getText()
        );
    }

    public HomePage deleteThisComputer() {
        deleteButton.click();
        return PageFactory.initElements(driver, HomePage.class);
    }
}
