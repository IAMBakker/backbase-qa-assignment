package pages;

import dto.Computer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class HomePage extends PageBase {

    @FindBy(id = "searchbox")
    private WebElement filterInput;

    @FindBy(id = "searchsubmit")
    private WebElement filterButton;

    @FindBy(id = "add")
    private WebElement addButton;

    @FindBy(css = "table.computers")
    private WebElement table;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage filterComputers(String name) {
        filterInput.clear();
        filterInput.sendKeys(name);
        filterButton.click();

        return this;
    }

    public NewComputerPage addComputer(){
        addButton.click();
        return new NewComputerPage(driver);
    }

    public List<Computer> getListOfComputersDisplayed(){
        List<Computer> computers = new ArrayList<>();
        table.findElements(By.cssSelector("tbody tr")).forEach((row)->{
            computers.add(getComputerFromTableRow(row));
        });
        return computers;
    }

    private Computer getComputerFromTableRow(WebElement row){
        Computer computer = new Computer();

        List<WebElement> cells = row.findElements(By.cssSelector("td"));

        computer.id = getComputerIdFromTableCell(cells.get(0));
        computer.name = cells.get(0).getText();
        computer.dateIntroduced = getTextFromTableCell(cells.get(1));
        computer.dateDiscontinued = getTextFromTableCell(cells.get(2));
        computer.company = getCompanyEnumFromTableCell(cells.get(3));

        return computer;
    }

    /**
     * Splits the href value and returns the last string in the array of the computer name cell
     * to retrieve the computer ID.
     * @param cell - The Cell containing the computer name
     * @return computer ID
     */
    private int getComputerIdFromTableCell(WebElement cell){
        String[] splitHref = cell.findElement(By.cssSelector("a"))
                .getAttribute("href")
                .split("/");
        return Integer.valueOf(splitHref[splitHref.length-1]);
    }

    private String getTextFromTableCell(WebElement cell){
        try{
            cell.findElement(By.cssSelector("em"));
            return null;
        } catch (NoSuchElementException e){
            return cell.getText();
        }
    }

    private Computer.Company getCompanyEnumFromTableCell(WebElement cell){
        String text = getTextFromTableCell(cell);
        if(text == null){
            return Computer.Company.DEFAULT;
        } else {
            return Computer.Company.valueOf(text);
        }
    }
}
