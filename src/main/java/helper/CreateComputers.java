package helper;

import dto.Computer;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Random;

import static io.restassured.RestAssured.*;


public class CreateComputers {

    public static void createComputer(final Computer computer){
        Response response = given()
//                .config(RestAssuredConfig.config().paramConfig(ParamConfig.paramConfig().formParamsUpdateStrategy()))
                .contentType(ContentType.URLENC)
                .baseUri("http://computer-database.herokuapp.com/computers")
                .params(
                        new HashMap<String, String>(){{
                            put("name", computer.name);
                            put("introduced", computer.dateIntroduced);
                            put("discontinued", computer.dateDiscontinued);
                            if(computer.company != null){
                                put("company", computer.company.name);
                            }
                        }}
                )
                .post();
        System.out.println(response.statusCode());
    }

    public static Computer createRandomComputer(){
        Computer computer = new Computer();
        computer.name = "test_computer_"+new Random().nextInt(500);
        createComputer(computer);
        return computer;
    }
}
