package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class PageBase {
    protected WebDriver driver;

    public PageBase(WebDriver driver){
        this.driver = driver;
    }

    /**
     * Checks if the <b>N computers found</b> message is displayed
     * @return boolean, true if the function is called when on the home page
     */
    public boolean isOnHomePage(){
        try{
            return driver.findElement(By.cssSelector("section#main h1"))
                    .getText().contains("computers found");
        } catch (NoSuchElementException ex){
            return false;
        }
    }
}
