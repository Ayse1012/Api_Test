package day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.baseURI;

public class HrApiParameters {

    @BeforeClass
    public void beforeClass(){

        baseURI = ConfigurationReader.get("hr_url");

    }

    @Test
    public void test1(){
        /*
        Given accept type is Json
        And parameters: q = "region_id":2
        When users sends a GET request to "/countries"
        Then status code is 200
        And Content type is application/json
        And Payload should contain "United States of America"
        {"region_id":2}
     */
        Response response= RestAssured.given().accept(ContentType.JSON)
                .and().queryParams("q","{\"region_id\":2}")//iki "" iç içe olursa tırnaklar arasına ters \ koyulur
                .when().get("/countries");
        response.prettyPrint();

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.body().asString().contains("United States of America"));

         //body içinde items arrey var, items arrey deki bir elementim değerini alma
        System.out.println("response.path(\"items.country_id[0]\") = " + response.path("items.country_id[0]"));

        System.out.println("response.path(\"items.country_name[0]\") = " + response.path("items.country_name[0]"));
        System.out.println("response.path(\"items.links[0].href\") = " + response.path("items.links[0].href"));

        //items deki bütün country_id leri alma
        List<String> allCountryId=response.path("items.country_id");
        System.out.println("allCountryId = " + allCountryId);


    }


}
