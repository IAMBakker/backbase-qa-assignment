import dto.Computer;
import helper.CreateComputers;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import pages.HomePage;
import pages.NewComputerPage;
import utilities.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateComputerTest extends TestBase {

    @Test
    public void createComputerLongName(){
        Computer computer = new Computer();
        computer.name = "sorryicouldntthinkofashortercomputername";
        computer.company = Computer.Company.RCA;

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.clickAddComputer()
        .addComputer(computer);

        assertThat(homePage.getAlertMessageText())
                .containsIgnoringCase(
                        computer.name
        ).containsIgnoringCase(
                "Done!"
        ).as("Alert Message should be displayed displaying Done! and the computer name");
    }

    @Test
    public void createComputerDateIntroducedInPastEdgeCase(){
        Computer computer = new Computer();
        computer.name = "ctc2computer";
        computer.dateIntroduced = "0-12-16";

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.clickAddComputer()
                .addComputer(computer);

        assertThat(homePage.getAlertMessageText())
                .containsIgnoringCase(computer.name)
                .containsIgnoringCase("Done!")
                .containsIgnoringCase("has been created")
                .as("Alert Message should be displayed displaying Done! and the computer name");
    }

    @Test
    public void createComputerDateDiscontinuedInFutureEdgeCase(){
        Computer computer = new Computer();
        computer.name = "ctc3computer";
        computer.dateDiscontinued = "10000-1-16";

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.clickAddComputer()
                .addComputer(computer);

        assertThat(homePage.getAlertMessageText())
                .containsIgnoringCase(computer.name)
                .containsIgnoringCase("Done!")
                .containsIgnoringCase("has been created")
                .as("Alert Message should be displayed displaying Done! and the computer name");
    }

    @Test
    public void createComputerDateIntroducedAfterDateDiscontinued(){
        Computer computer = new Computer();
        computer.name = "ctc4computer";
        computer.dateIntroduced = "2500-12-16";
        computer.dateDiscontinued = "1642-05-30";

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.clickAddComputer()
                .addComputer(computer);

        assertThat(homePage.getAlertMessageText())
                .containsIgnoringCase(computer.name)
                .containsIgnoringCase("Done!")
                .containsIgnoringCase("has been created")
                .as("Alert Message should be displayed displaying Done! and the computer name");
    }

    @Test
    public void createComputerLeapDaysHappyFlow(){
        Computer computer = new Computer();
        computer.name = "ctc5computer";
        computer.dateIntroduced = "2000-02-29";
        computer.dateDiscontinued = "2016-02-29";

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.clickAddComputer()
                .addComputer(computer);

        assertThat(homePage.getAlertMessageText())
                .containsIgnoringCase(computer.name)
                .containsIgnoringCase("Done!")
                .containsIgnoringCase("has been created")
                .as("Alert Message should be displayed displaying Done! and the computer name");
    }

    @Test
    public void createComputerSpecialCharactersInName(){
        Computer computer = new Computer();
        computer.name = "ƒÆ’Ã‚Â¢ÃƒÂ¢Ã¢â ¬Å¡Ã‚Â¬ÃƒÂ¯Ã¢â¬ÂÃï†";

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.clickAddComputer()
                .addComputer(computer);

        assertThat(homePage.getAlertMessageText())
                .containsIgnoringCase(computer.name)
                .containsIgnoringCase("Done!")
                .containsIgnoringCase("has been created")
                .as("Alert Message should be displayed displaying Done! and the computer name");
    }

    @Test
    public void createComputerWithoutName(){
        Computer computer = new Computer();
        computer.name = "";

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.clickAddComputer()
                .addComputer(computer);

        NewComputerPage newComputerPage = PageFactory.initElements(driver, NewComputerPage.class);

        assertThat(newComputerPage.isOnHomePage()).isFalse();

        assertThat(newComputerPage.nameFieldHasError()).isEqualTo(true);
    }

    @Test
    public void createComputerWrongDateIntroducedFormat(){
        Computer computer = new Computer();
        computer.name = "ctc9computer";
        computer.dateIntroduced = "22-01-2012";
        computer.dateDiscontinued = "2012-06-12";

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.clickAddComputer()
                .addComputer(computer);

        NewComputerPage newComputerPage = PageFactory.initElements(driver, NewComputerPage.class);

        assertThat(newComputerPage.isOnHomePage()).isFalse();

        assertThat(newComputerPage.dateIntroducedFieldHasError()).isEqualTo(true);
    }

    @Test
    public void createComputerWrongDateDiscontinuedFormat(){
        Computer computer = new Computer();
        computer.name = "ctc9computer";
        computer.dateIntroduced = "2012-02-11";
        computer.dateDiscontinued = "2014/01/22";

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.clickAddComputer()
                .addComputer(computer);

        NewComputerPage newComputerPage = PageFactory.initElements(driver, NewComputerPage.class);

        assertThat(newComputerPage.isOnHomePage()).isFalse();

        assertThat(newComputerPage.dateDiscontinuedFieldHasError()).isEqualTo(true);
    }

    @Test
    public void createComputerDuplicateName(){
        // Making the computer by sending a http post.
        Computer oldComputer = CreateComputers.createRandomComputer();

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);

        // This will make the computer a second time using the ui
        homePage.clickAddComputer()
                .addComputer(oldComputer);

        // Asserting that we are able to do so, because computer names do not have to be unique
        assertThat(homePage.getAlertMessageText())
                .containsIgnoringCase(oldComputer.name)
                .containsIgnoringCase("Done!")
                .containsIgnoringCase("has been created")
                .as("Alert Message should be displayed displaying Done! and the computer name");
    }
}
