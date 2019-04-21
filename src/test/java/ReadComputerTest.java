import dto.Computer;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static helper.CreateComputers.createRandomComputer;

public class ReadComputerTest {
    private Computer computer;

    @Before
    public void setUp(){
        computer = createRandomComputer();
    }

    @Test
    public void readComputer(){
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();

        driver.get("http://computer-database.herokuapp.com/computers");
    }
}
