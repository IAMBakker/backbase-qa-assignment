import dto.Computer;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import pages.HomePage;
import utilities.TestBase;

import java.util.List;

import static helper.CreateComputers.createRandomComputer;
import static org.assertj.core.api.Assertions.assertThat;

public class ReadComputerTests extends TestBase {
    private Computer computer;
    private HomePage homePage;

    @Before
    public void setUp(){
        computer = createRandomComputer();
    }

    @Test
    public void readComputer(){
        homePage = PageFactory.initElements(driver, HomePage.class);
        List<Computer> computers = homePage.filterComputers(computer.name)
        .getListOfComputersDisplayed();

        assertThat(computers).contains(computer).as("The filtered list should contain " +
                "the computer created in our setup");
    }
}
