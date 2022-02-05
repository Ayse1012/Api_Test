package day3;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SpartanTestWithJsonPath {
    @BeforeClass
    public void beforeClass(){

        baseURI = ConfigurationReader.get("spartan_url");

    }
    @Test
    public void test1(){
        /*
          Given accept type is json
          And path param spartan id is 11
          When user sends a get request to /spartans/{id}
         Then status code is 200
         And content type is Json
         And   "id": 11,
               "name": "Nona",
              "gender": "Female"
              "phone": 7959094216
    */
        Response response=given().accept(ContentType.JSON).pathParam("id",11)
                .when().get("/spartans/{id}");
       assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        //verify id and name
        int id=response.path("id");
        String name=response.path("name");
        assertEquals(id,11);
        assertEquals(name,"Nona");

        //body i verify etmenın bir diğer yolu
        JsonPath jsonPath=response.jsonPath();
        //burada response u alıp jsonPath e assing(response daki bilgileriaktarıyoruz)ediyoruz
        int idjson=jsonPath.getInt("id");
        System.out.println("idjson = " + idjson);

        String jsonName=jsonPath.get("name");
        System.out.println("jsonName = " + jsonName);

        String jsonGender=jsonPath.getString("gender");
        System.out.println("jsonGender = " + jsonGender);

        long phone=jsonPath.getLong("phone");
        System.out.println("phone = " + phone);

        //verify all information
        assertEquals(idjson,11);
        assertEquals(jsonName,"Nona");
        assertEquals(jsonGender,"Female");
        assertEquals(phone,7959094216l);


    }


}
