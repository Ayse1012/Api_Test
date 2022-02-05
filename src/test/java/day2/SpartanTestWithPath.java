package day2;
import groovy.lang.DelegatesTo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SpartanTestWithPath {
    @BeforeClass
    public void beforeClass(){

        baseURI = ConfigurationReader.get("spartan_url");

    }
    @Test
    public void test1(){

      given().get(baseURI+"/spartans").then().statusCode(200);
      //burada hem url alıp hemde statuscode u verify etmiş  olduk

    }
    @Test
    public void hetOneSpartan(){
        //body nin içindeki bilgileri verify etme
        /*
   Given accept type is json
   And path param id is 10
   When user sends a get request to "api/spartans/{id}"
   Then status code is 200
   And content-type is "application/json;charset=UTF-8"
   And response payload values match the following:
           id is 10,
           name is "Lorenza",
           gender is "Female",
           phone is 3312820936

           payload=body
    */

        Response response=given().accept(ContentType.JSON).pathParam("id",10)
                .when().get("/spartans/{id}");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");

        response.prettyPrint();

        System.out.println("response.path(\"id\") = " + response.path("id"));
        System.out.println("response.path(\"name\") = " + response.path("name"));
        System.out.println("response.path(\"gender\") = " + response.path("gender"));
        System.out.println("response.path(\"phone\") = " + response.path("phone"));

        // body almanın bir diğer yolu
       // System.out.println("response.body().path(\"id\") = " + response.body().path("id"));

        // id int dır,phone ise long dur
        int actualId=response.path("id");
        String actualName=response.path("name");
        String actualGender=response.path("gender");
        int actualPhone=response.path("phone");

        Assert.assertEquals(actualId,10);
        Assert.assertEquals(actualName,"Lorenza");
        Assert.assertEquals(actualGender,"Female");
        Assert.assertEquals(actualPhone,1234554321);



    }
    @Test
    public void allSpartansWithPath(){
        Response response= RestAssured.given().accept(ContentType.JSON).when().get("/spartans");
        Assert.assertEquals(response.statusCode(),200);

        response.prettyPrint();

        int firstId=response.path("id[0]");
        System.out.println("firstId = " + firstId);

        String firstName=response.path("name[0]");
        System.out.println("firstName = " + firstName);

        String firstGender=response.path("gender[0]");
        System.out.println("gender = " + firstGender);

        long firstPhone=response.path("phone[0]");
        System.out.println("phone = " + firstPhone);

        //bütün name leri alma
        List<String> allNames=response.path("name");
        System.out.println("allNames = " + allNames);
         //veya
        for (String allName : allNames) {
            System.out.println("allName = " + allName);

        }
        //bütün phone lar
        List<Object> allPhone=response.path("phone");
        System.out.println("allPhone = " + allPhone);


    }


}
