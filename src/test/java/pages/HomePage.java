package pages;

import dto.Computer;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends PageBase {

    @FindBy(id = "searchbox")
    private WebElement filterInput;

    @FindBy(id = "searchsubmit")
    private WebElement filterButton;

    @FindBy(id = "add")
    private WebElement addButton;

    @FindBy(css = "table.computers")
    private WebElement table;

    @FindBy(css = "div.alert-message")
    private WebElement alertMessage;

    @FindBy(css = "table.computers thead th:nth-child(2)")
    private WebElement dateIntroducedHeader;

    @FindBy(css = "div.pagination li.next a")
    private WebElement nextButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage filterComputers(String name) {
        filterInput.clear();
        filterInput.sendKeys(name);
        filterButton.click();

        return this;
    }

    public NewComputerPage clickAddComputer(){
        addButton.click();
        return PageFactory.initElements(driver, NewComputerPage.class);
    }

    public String getAlertMessageText(){
        try{
            return alertMessage.getText();
        } catch (NoSuchElementException ex){
            throw new Error("Unable to locate Alert Message.\n" + ex);
        }
    }

    /**
     * Clicks a computer with a name matching the passed String
     * @param name
     * @return EditComputerPage
     */
    public EditComputerPage clickFirstComputerMatchingNameString(String name){
        getMapOfComputersDisplayed().forEach((k,v)->{
            if(k.name.equalsIgnoreCase(name)){
                v.findElement(By.cssSelector("td a[href^='/computers/']")).click();
            }
        });
        return PageFactory.initElements(driver, EditComputerPage.class);
    }

    /**
     * Clicks a computer that matches the computerDTO passed to this function,
     * name, dateIntroduced, dateDiscontinued and Company have to match. Be
     * careful when using this function, the dateStrings displayed on the table
     * are in a different format than the ones supplied when creating a computer.
     * See: {@link dto.Computer.Company}.withParsedDates() function.
     * @param computer
     * @return EditComputerPage
     */
    public EditComputerPage clickFirstFullyMatchingComputer(Computer computer){
        getMapOfComputersDisplayed().get(computer)
                .findElement(By.cssSelector("td a[href^='/computers/']"))
                .click();
        return PageFactory.initElements(driver, EditComputerPage.class);
    }

    public List<Computer> getListOfComputersDisplayed(){
        List<Computer> computers = new ArrayList<>();
        table.findElements(By.cssSelector("tbody tr")).forEach((row)->{
            computers.add(getComputerFromTableRow(row));
        });
        return computers;
    }

    public Map<Computer, WebElement> getMapOfComputersDisplayed(){
        Map<Computer, WebElement> computers = new HashMap<>();
        table.findElements(By.cssSelector("tbody tr")).forEach((row)->{
            computers.put(getComputerFromTableRow(row), row);
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
            return "";
        } catch (NoSuchElementException ex){
            return cell.getText();
        }
    }

    private Computer.Company getCompanyEnumFromTableCell(WebElement cell){
        String text = getTextFromTableCell(cell);
        if(text == ""){
            return Computer.Company.DEFAULT;
        } else {
            return Computer.Company.parseName(text);
        }
    }

    public HomePage sortComputersByDateIntroducedAscending() {
        do{
            WebElement headerName = dateIntroducedHeader.findElement(By.cssSelector("a"));
            headerName.click();
        } while (!dateIntroducedHeader.getAttribute("class").contains("headerSortDown"));
        return this;
    }

    public HomePage clickNext() {
        nextButton.click();
        return this;
    }
}
