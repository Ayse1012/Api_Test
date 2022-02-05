package day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SpartanGetRequest {

    String spartanUrl = "http://54.237.100.89:8000/api";

    @Test
    public void Test1() {
/* TASK
    When users sends a get request to /api/spartans/3
    Then status code should be 200
    And content type should be application/json;charset=UFT-8
    and json body should contain Fidole
 */
        Response response = RestAssured.given().accept(ContentType.JSON).when().get(spartanUrl + "/spartans/3");
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json");
        Assert.assertTrue(response.body().asString().contains("Fidole"));

    }
    @Test
    public void Test2(){
        /*
        Given no headers provided
        When Users sends GET request to /api/hello
        Then response status code should be 200
        And Content type header should be "text/plain;charset=UTF-8"
        And header should contain date
        And Content-Length should be 17
        And body should be "Hello from Sparta"
        */
        Response response=RestAssured.given().when().get(spartanUrl+"/hello");//burada body miz text old için accept e gerekyok
        Assert.assertEquals(response.statusCode(),200);
        response.prettyPrint();

        System.out.println("response.header(\"Date\") = " + response.header("Date"));
        System.out.println("response.header(\"Content-Type\") = " + response.header("Content-Type"));

        //And Content type header should be "text/plain;charset=UTF-8"
          //header--> peylot header daki verilerin karşılığını(key-velue yapısı) almamızı sağlar
        Assert.assertEquals(response.header("Content-Type"),"text/plain;charset=UTF-8");
        Assert.assertEquals(response.contentType(),"text/plain;charset=UTF-8");

        //And header should contain date--- header da date var olduğu
        //hasHeaderWithName()
        Assert.assertTrue(response.headers().hasHeaderWithName("Date"));//headerin içinde date diye bir header varmı
                                                                                   //başlıkların içinde date başlığı varmı

        // And Content-Length should be 17
        Assert.assertEquals(response.header("Content-Length"),"17");

        //And body should be "Hello from Sparta"
        Assert.assertEquals(response.body().asString(),"Hello from Sparta");
        Assert.assertTrue(response.body().asString().contains("Hello"));


    }



}
