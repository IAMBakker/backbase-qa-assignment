package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {

    protected static WebDriver driver;

    @BeforeClass
    public static void driverSetup(){
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @Before
    public void openHomePage() {
        driver.get(PropertiesReader.getProperty("url"));
        driver.manage().window().maximize();
    }
}
