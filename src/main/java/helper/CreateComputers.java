package helper;

import dto.Computer;
import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.config.ParamConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utilities.PropertiesReader;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;


public class CreateComputers {

    public static void createComputers(final List<Computer> computers){
        computers.forEach(c -> createComputer(c));
    }

    public static void createComputer(final Computer computer){
        Response response = given()
                .config(RestAssured.config()
                .encoderConfig(EncoderConfig.encoderConfig()
                        .encodeContentTypeAs("x-www-form-urlencoded",
                                ContentType.URLENC)))
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .baseUri(PropertiesReader.getProperty("url"))
                .formParams(
                        new HashMap<String, String>(){{
                            put("name", computer.name);
                            put("introduced", computer.dateIntroduced);
                            put("discontinued", computer.dateDiscontinued);
                            if(computer.company != Computer.Company.DEFAULT){
                                put("company", computer.company.index);
                            }
                        }}
                )

                .post();
        assertThat(response.getStatusCode()).isEqualTo(200).as(String.join("Unable to create computer: ",
                computer.toString()));
    }

    public static Computer createRandomComputer(){
        Computer computer = new Computer();
        computer.name = "test_computer_"+new Random().nextInt(500);
        createComputer(computer);
        return computer;
    }
}
