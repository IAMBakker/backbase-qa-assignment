import dto.Computer;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import pages.HomePage;
import utilities.TestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static helper.CreateComputers.createComputers;
import static org.assertj.core.api.Assertions.assertThat;

public class ReadComputerTest extends TestBase {
    private List<Computer> computers;
    private HomePage homePage;
    private int randomNumber = new Random().nextInt(999);

    @Before
    public void setUp(){
        computers = new ArrayList<>();
        computers.add(new Computer("rtc"+ randomNumber +"01veryveryverylongnamecomputer",
                "",
                "",
                Computer.Company.APPLE
        ));
        computers.add(new Computer("rtc"+ randomNumber +"02",
                "1754-12-16",
                "",
                Computer.Company.DEFAULT
        ));
        computers.add(new Computer("rtc"+ randomNumber +"03",
                "",
                "2518-01-31",
                Computer.Company.DEFAULT
        ));
        computers.add(new Computer("rtc"+ randomNumber +"04",
                "2600-01-01",
                "2007-02-21",
                Computer.Company.DEFAULT
        ));
        computers.add(new Computer("rtc"+ randomNumber +"05:ƒÆ’Ã¢",
                "1996-02-29",
                "2000-02-29",
                Computer.Company.DEFAULT
        ));
        createComputers(computers);
    }

    @Test
    public void readComputerDataFromTable(){
        homePage = PageFactory.initElements(driver, HomePage.class);
        List<Computer> preparedComputers = homePage.filterComputers("rtc" + randomNumber)
        .getListOfComputersDisplayed();

        computers.forEach(c -> {
            assertThat(preparedComputers)
                    .as("The filtered list does not contain the computer created in our setup with " +
                            "dateStrings correctly displayed in format dd MMM yyyy")
                    .contains(c.withParsedDates());
        });
    }

    @Test
    public void readComputerDataFromEditScreen(){
        homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.filterComputers("rtc" + randomNumber);

        // retrieving the computer we are matching from the computers list created in the setup.
        Computer computer = computers.get(4);

        Computer readComputer = homePage.clickFirstComputerMatchingNameString(computer.name)
                .getDisplayedComputerData();

        assertThat(readComputer).isEqualTo(computer);
    }

    /**
     * I only implemented a single sorting function to showcase the way I
     * would test this functionality, since the functions do not work.
     */
    @Test
    public void sortByDateIntroducedAscending(){
        homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.filterComputers("rtc" + randomNumber);

        List<Computer> listBeforeSorting = homePage.getListOfComputersDisplayed();

        homePage.sortComputersByDateIntroducedAscending();

        List<Computer> listAfterSorting = homePage.getListOfComputersDisplayed();

        assertThat(listAfterSorting)
                .as("The order of the computers in the list should be changed")
                .doesNotContainSequence(listBeforeSorting);
        assertThat(listAfterSorting.get(0))
                .as("The first line of the table should match computer #2")
                .isEqualTo(computers.get(1));
    }

    @Test
    public void clickingNextDisplaysTenDifferentComputers(){
        homePage = PageFactory.initElements(driver, HomePage.class);

        List<Computer> computersOnPageOne = homePage.getListOfComputersDisplayed();

        homePage.clickNext();

        List<Computer> computersOnPageTwo = homePage.getListOfComputersDisplayed();

        // This assert will fail if duplicate computers exist with different computer IDs
        // a weakness of the equals method of the Computers DTO.
        // I could extend the Computer DTO in a new class where I override the equals
        // method with one that does match IDs. But it would make this test a bit messy
        // and the chance for it occuring is low enough that I have skipped out on this.
        assertThat(computersOnPageTwo)
                .as("Moving to the next page should display 10 new computers")
                .doesNotContainAnyElementsOf(computersOnPageOne);
    }
}
