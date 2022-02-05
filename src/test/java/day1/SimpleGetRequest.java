package day1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import org.testng.annotations.Test;

public class SimpleGetRequest {

   String hrUrl="http://54.237.100.89:1000/ords/hr";
   String spartanUrl="http://54.237.100.89:8000/api/spartans";
   String ctUrl="http://api.cybertektraining.com";

    @Test
    public void test1(){

        Response response= RestAssured.get(hrUrl);
        System.out.println("response.statusCode() = " + response.statusCode());
        Assert.assertEquals(response.statusCode(),200);

    }
    @Test
    public void  test2(){
/*
        Given accept type is json
        When user sends get request to regions endpoint
        Then response status code must be 200
        and body is json format
     */

        Response response =RestAssured.given().accept(ContentType.JSON).when().get(hrUrl);
       response.prettyPrint();
        Assert.assertEquals(response.statusCode(),200);

    }
    @Test
    public void  Test3(){
        //lütfen bize content type xml olarak ver
        Response response=RestAssured.given().accept(ContentType.XML).when().get(spartanUrl);
        System.out.println("response.statusCode() = " + response.statusCode());
        response.prettyPrint();

    }
    @Test
    public void  Test4(){
         /*
        Given accept type is json
        When user sends get request to regions/2
        Then response status code must be 200
        and body is json format
        and response body contains Americas
     */
        Response response =RestAssured.given().accept(ContentType.JSON).when().get(hrUrl+"/2");

        // status code u verify et
        Assert.assertEquals(response.statusCode(),200);

        //content type json olduğunu verify et
        Assert.assertEquals(response.contentType(),"application/json");

        String body=response.body().asString();//body i string olarak getiriyor
        System.out.println("body = " + body);//body i görmek için

        Assert.assertTrue(response.body().asString().contains("Americas"));
        //body de Americas old verify et
       // Assert.assertTrue(body.contains("Americas"));

    }
    @Test
    public void Test5(){
        Response response=RestAssured.given().accept(ContentType.JSON).when().get(ctUrl+"/student/all");
         response.prettyPrint();
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json;charset=UTF-8");

        //System.out.println("response.body().asString() = " + response.body().asString());

        Assert.assertTrue(response.body().asString().contains("Chicago"));

    }
    @Test
    public void Test6(){
        Response response=RestAssured.when().get(ctUrl+"/teacher/all");
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),200);
        //Assert.assertTrue(response.body().asString().contains("10422"));

       Response response1= RestAssured.when().get(ctUrl+"/teacher/10422");
      Assert.assertTrue(response1.body().asString().contains("Alexander"));

    }
    @Test
    public void Test7() {
        Response response=RestAssured.when().get(ctUrl+"/teacher/department/Consulting");
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json;charset=UTF-8");
        response.prettyPrint();

    }
    @Test
    public void Test8(){
      Response response=RestAssured.given().accept(ContentType.XML).when().get(spartanUrl);
      response.prettyPrint();
      Assert.assertEquals(response.statusCode(),200);
      Assert.assertEquals(response.contentType(),"application/xml");

        System.out.println("response.body().htmlPath() = " + response.body().htmlPath());
        Assert.assertTrue(response.body().asString().contains("Tedmund"));

    }
    @Test
    public void Test9() {

    Response response=RestAssured.given().accept(ContentType.JSON).when().get(spartanUrl+"/10");
    response.prettyPrint();
    Assert.assertEquals(response.statusCode(),200);
    Assert.assertTrue(response.body().asString().contains("Lorenza"));
    }
    @Test
    public void Test10(){
        Response response=RestAssured.given().accept(ContentType.JSON).when().get(hrUrl+"/employees/100");
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");

        String body=response.body().asString();
        Assert.assertTrue(body.contains("AD_PRES"));
        Assert.assertFalse(body.contains("SVOLLMAN"));
    }
    @Test
    public void Test11(){
        Response response=RestAssured.given().accept(ContentType.JSON).when().get(spartanUrl+"/search?gender=Female&nameContains=li");
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");
        String body=response.body().asString();
        Assert.assertTrue(body.contains("Elita"));
    }



}
