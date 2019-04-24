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

public class DeleteComputerTest extends TestBase {

    private List<Computer> computers;
    private HomePage homePage;
    private int randomNumber = new Random().nextInt(999);

    @Before
    public void setUp(){
        computers = new ArrayList<>();
        computers.add(new Computer("dtc"+ randomNumber +"01comp-veryveryverylongnamecomputer",
                "",
                "",
                Computer.Company.RCA
        ));
        computers.add(new Computer("dtc"+ randomNumber +"02",
                "1754-12-16",
                "",
                Computer.Company.DEFAULT
        ));
        computers.add(new Computer("dtc"+ randomNumber +"03",
                "",
                "2518-01-31",
                Computer.Company.DEFAULT
        ));
        computers.add(new Computer("dtc"+ randomNumber +"04-Å¡Ã",
                "2518-01-31",
                "2007-02-11",
                Computer.Company.DEFAULT
        ));
        computers.add(new Computer("dtc"+ randomNumber +"05",
                "2004-02-29",
                "2016-02-29",
                Computer.Company.DEFAULT
        ));
        createComputers(computers);
    }

    @Test
    public void deleteComputerWithCompanyTest(){
        homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.filterComputers("dtc"+ randomNumber)
        .clickFirstFullyMatchingComputer(computers.get(0).withParsedDates())
        .deleteThisComputer();

        assertThat(homePage.getAlertMessageText())
                .as("Alert message should display that the computer was deleted")
                .containsIgnoringCase("Done! Computer has been deleted");

        List<Computer> computersAfterDeletion = homePage.filterComputers("dtc"+ randomNumber)
                .getListOfComputersDisplayed();

        assertThat(computersAfterDeletion)
                .as("Computer should no longer show up after filtering, since it was deleted")
                .doesNotContain(computers.get(0).withParsedDates());
    }

    // Not entirely sure why I want to perform this test, but it also checks if we are able
    // to delete computers that have introduced and discontinued dates.
    @Test
    public void deleteComputerWithStrangeCharactersInNameTest(){
        homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.filterComputers("dtc"+ randomNumber)
                .clickFirstFullyMatchingComputer(computers.get(3).withParsedDates())
                .deleteThisComputer();

        assertThat(homePage.getAlertMessageText())
                .as("Alert message should display that the computer was deleted")
                .containsIgnoringCase("Done! Computer has been deleted");

        List<Computer> computersAfterDeletion = homePage.filterComputers("dtc"+ randomNumber)
                .getListOfComputersDisplayed();

        assertThat(computersAfterDeletion)
                .as("Computer should no longer show up after filtering, since it was deleted")
                .doesNotContain(computers.get(3).withParsedDates());
    }
}
