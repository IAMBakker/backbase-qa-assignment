import dto.Computer;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import pages.EditComputerPage;
import pages.HomePage;
import utilities.TestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static helper.CreateComputers.createComputers;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateComputerTest extends TestBase {

    private List<Computer> computers;
    private HomePage homePage;
    private int randomNumber = new Random().nextInt(999);

    @Before
    public void setUp(){
        computers = new ArrayList<>();
        computers.add(new Computer("utc"+ randomNumber +"01comp-veryveryverylongnamecomputer",
                "",
                "",
                Computer.Company.NETRONICS
        ));
        computers.add(new Computer("utc"+ randomNumber +"02",
                "1754-12-16",
                "",
                Computer.Company.DEFAULT
        ));
        computers.add(new Computer("utc"+ randomNumber +"03_Ãï†",
                "",
                "2518-01-31",
                Computer.Company.DEFAULT
        ));
        computers.add(new Computer("utc"+ randomNumber +"04",
                "2518-01-31",
                "2007-02-11",
                Computer.Company.DEFAULT
        ));
        computers.add(new Computer("utc"+ randomNumber +"05",
                "2004-02-29",
                "2016-02-29",
                Computer.Company.DEFAULT
        ));
        createComputers(computers);
    }

    @Test
    public void updateComputerNameTest(){
        homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.filterComputers("utc" + randomNumber)
        .clickFirstFullyMatchingComputer(computers.get(0).withParsedDates());

        EditComputerPage editPage = PageFactory.initElements(driver, EditComputerPage.class);

        String newComputerName = "utc" + randomNumber + "01NEWNAME";
        String computerID = editPage.editComputerName(newComputerName)
                .getComputerID();
        editPage.updateComputer();

        boolean returnedToHomePage = homePage.isOnHomePage();
        assertThat(returnedToHomePage).isTrue();

        assertThat(homePage.getAlertMessageText())
                .as("Alert Message should be displayed displaying Done! and the computer name")
                .containsIgnoringCase(newComputerName)
                .containsIgnoringCase("Done!")
                .containsIgnoringCase("has been updated");

        driver.get(baseURL + "/" + computerID);
        assertThat(editPage.getDisplayedComputerData().name)
                .as("After opening the edit screen again for this computer we can" +
                        "check whether the new name was edited correctly.")
                .isEqualTo(newComputerName);
    }

    @Test
    public void updateComputerIntroducedDateTest(){
        homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.filterComputers("utc" + randomNumber)
                .clickFirstFullyMatchingComputer(computers.get(1).withParsedDates());

        EditComputerPage editPage = PageFactory.initElements(driver, EditComputerPage.class);

        String newDateIntroduced = "2015-01-01";
        String computerID = editPage.editComputerDateIntroduced(newDateIntroduced)
                .getComputerID();
        editPage.updateComputer();

        boolean returnedToHomePage = homePage.isOnHomePage();
        assertThat(returnedToHomePage).isTrue();

        assertThat(homePage.getAlertMessageText())
                .as("Alert Message should be displayed displaying Done! and the computer name")
                .containsIgnoringCase(computers.get(1).name)
                .containsIgnoringCase("Done!")
                .containsIgnoringCase("has been updated");

        driver.get(baseURL + "/" + computerID);
        assertThat(editPage.getDisplayedComputerData().dateIntroduced)
                .as("After opening the edit screen again for this computer we can" +
                        "check whether the date introduced was edited correctly.")
                .isEqualTo(newDateIntroduced);
    }

    @Test
    public void updateComputerDiscontinuedDateTest(){
        homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.filterComputers("utc" + randomNumber)
                .clickFirstFullyMatchingComputer(computers.get(2).withParsedDates());

        EditComputerPage editPage = PageFactory.initElements(driver, EditComputerPage.class);

        String newDateDiscontinued = "2016-02-02";
        String computerID = editPage.editComputerDateDiscontinued(newDateDiscontinued)
                .getComputerID();
        editPage.updateComputer();

        boolean returnedToHomePage = homePage.isOnHomePage();
        assertThat(returnedToHomePage).isTrue();

        assertThat(homePage.getAlertMessageText())
                .as("Alert Message should be displayed displaying Done! and the computer name")
                .containsIgnoringCase(computers.get(2).name)
                .containsIgnoringCase("Done!")
                .containsIgnoringCase("has been updated");

        driver.get(baseURL + "/" + computerID);
        assertThat(editPage.getDisplayedComputerData().dateDiscontinued)
                .as("After opening the edit screen again for this computer we can" +
                        "check whether the date introduced was edited correctly.")
                .isEqualTo(newDateDiscontinued);
    }

    @Test
    public void updateComputerCompanyTest(){
        homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.filterComputers("utc" + randomNumber)
                .clickFirstFullyMatchingComputer(computers.get(3).withParsedDates());

        EditComputerPage editPage = PageFactory.initElements(driver, EditComputerPage.class);

        Computer.Company newCompany = Computer.Company.THINKING_MACHINES;
        String computerID = editPage.editComputerCompany(newCompany)
                .getComputerID();
        editPage.updateComputer();

        boolean returnedToHomePage = homePage.isOnHomePage();
        assertThat(returnedToHomePage).isTrue();

        assertThat(homePage.getAlertMessageText())
                .as("Alert Message should be displayed displaying Done! and the computer name")
                .containsIgnoringCase(computers.get(3).name)
                .containsIgnoringCase("Done!")
                .containsIgnoringCase("has been updated");

        driver.get(baseURL + "/" + computerID);
        assertThat(editPage.getDisplayedComputerData().company)
                .as("After opening the edit screen again for this computer we can" +
                        "check whether the date introduced was edited correctly.")
                .isEqualTo(newCompany);
    }
}
