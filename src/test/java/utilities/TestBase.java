package utilities;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

public class TestBase {

    protected static WebDriver driver;
    protected static String baseURL = PropertiesReader.getProperty("url");
    protected static String browser = PropertiesReader.getProperty("browser");

    @BeforeClass
    public static void driverSetup(){
        driver = new BrowserFactory(browser).initBrowser();
    }

    @Before
    public void openHomePage() {
        driver.get(baseURL);
        driver.manage().window().maximize();
    }

    @AfterClass
    public static void tearDown(){
        driver.close();
    }
}
