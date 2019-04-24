package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserFactory {

    private String browserString;

    public BrowserFactory(String browserString){
        this.browserString = browserString;
    }

    /**
     * Very basic browser initiation.
     * @return WebDriver (Either a Chrome or FirefoxDriver)
     */
    public WebDriver initBrowser(){
        switch (browserString.toUpperCase()){
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "CHROME":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            default:
                throw new Error("browserString not recognized: " + browserString);
        }

    }
}
