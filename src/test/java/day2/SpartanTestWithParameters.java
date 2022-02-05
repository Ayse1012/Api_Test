package day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;


public class SpartanTestWithParameters {

    //
    @BeforeClass
    public void beforeClass(){
        baseURI = "http://54.237.100.89:8000/api";
    }
    @Test
    public void test1(){
        Response response=given().get("/spartans");
        response.prettyPrint();
    }
    @Test
    public void SpartanTestParam() {
        /*
          Given accept type is Json
          And Id parameter value is 5
          When user sends GET request to /api/spartans/{id}
          Then response status code should be 200
          And response content-type: application/json
          And "Blythe" should be in response payload
       */

        Response response = given().accept(ContentType.JSON).and().pathParam("id", 5)
                .when().get("/spartans/{id}");

        response.prettyPrint();

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json");

        Assert.assertTrue(response.body().asString().contains("Blythe"));

    }

    @Test
    public void testNegative(){
        Response response= RestAssured.given().accept(ContentType.JSON)
                .and().pathParam("id",551)
                .when().get("/spartans/{id}");
        response.prettyPrint();

        Assert.assertEquals(response.statusCode(),404);
        Assert.assertEquals(response.contentType(),"application/json");

        Assert.assertTrue(response.body().asString().contains("Not Found"));

    }
    @Test
    public void testWithQueryParams(){
        /*
        Given accept type is Json
        And query parameter values are :
        gender|Female
        nameContains|e
        When user sends GET request to /api/spartans/search
        Then response status code should be 200
        And response content-type: application/json;charset=UTF-8
        And "Female" should be in response payload
        And "Janette" should be in response payload
     */

        Response response=given().accept(ContentType.JSON)
                .and().queryParam("gender","Female")
                .and().queryParam("nameContains","e")
                .when().get("/spartans/search");
        response.prettyPrint();

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");

        //testin daha sağlam olması için bu assert leri yapmalıyız
        Assert.assertTrue(response.body().asString().contains("Female"));
        Assert.assertTrue(response.body().asString().contains("Janette"));
        Assert.assertFalse(response.body().asString().contains("Male"));

    }
    //yukarıdakı test case ın map versiyonu
    @Test
    public void testWithQueryWithMap(){
        Map<String,Object> queryMap=new HashMap<>();
        queryMap.put("gender","Female");//map e query param ekliyoruz
        queryMap.put("nameContains","e");

        Response response=given().accept(ContentType.JSON)
                .and().queryParams(queryMap).when().get("/spartans/search");

        response.prettyPrint();

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");

        //testin daha sağlam olması için bu assert leri yapmalıyız
        Assert.assertTrue(response.body().asString().contains("Female"));
        Assert.assertTrue(response.body().asString().contains("Janette"));
        Assert.assertFalse(response.body().asString().contains("Male"));


    }
    @Test
    public void test(){
        Response response=given().accept(ContentType.JSON)
                .and().pathParam("id",8)
                .when().get("/spartans/{id}");
        response.prettyPrint();

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");
        Assert.assertTrue(response.body().asString().contains("Rodolfo"));


    }
    @Test
    public void testQueryWithMap(){
        Map<String,Object> queryMap=new HashMap<>();
        queryMap.put("gender","Male");
        queryMap.put("nameContains","an");
        Response response=given().accept(ContentType.JSON)
                .and().queryParams(queryMap)
                .when().get("/spartans/search");

        response.prettyPrint();

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");
        Assert.assertFalse(response.body().asString().contains("Female"));


    }




}
