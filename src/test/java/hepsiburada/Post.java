package hepsiburada;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;
import  java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class Post {

    @BeforeClass()
    public void beforeClass(){
        baseURI= ConfigurationReader.get("hepsibUrl");
    }
    @Test
    public void postUser(){

        String mybody="{\n" +
                "\"id\": 1,\n" +
                "\"username\": \"ftm\",\n" +
                "\"firstName\": \"fatma\",\n" +
                "\"lastName\": \"yılamz\",\n" +
                "\"email\": \"ftmy@jgkk\",\n" +
                "\"password\": \"ylm123\",\n" +
                "\"phone\": \"1234567890\",\n" +
                "\"userStatus\": 5\n" +
                "}";

        Response response=given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .and().body(mybody).when().post("/user");

        response.prettyPrint();
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        JsonPath jsonPath=response.jsonPath();
        assertEquals(jsonPath.getString("username"),"ftm");

        assertEquals(response.path("lastName"),"yılmaz");



    }

}
