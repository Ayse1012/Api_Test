package hepsiburada;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;

public class Gets {

    @BeforeClass()
    public void beforeClass(){
     baseURI= ConfigurationReader.get("hepsibUrl");
    }
    @Test
    public void getStore(){
     Response response= (Response) given().accept(ContentType.JSON)
             .when().get("/store/inventory");

     response.prettyPrint();

        assertEquals(response.statusCode(),200);
       assertEquals(response.header("Access-Control-Allow-Methods"),"GET, POST, DELETE, PUT");

        JsonPath jsonPath=response.jsonPath();
       assertEquals(jsonPath.getInt("booked"),2);
       assertTrue(response.headers().hasHeaderWithName("Content-Type"));
       assertEquals(response.contentType(),"application/json");
     //  assertEquals(response.body().asString(),"freshly updated pet");

    }
    @Test
    public void getpet(){

        Response response=given().accept(ContentType.JSON).and()
                .pathParam("petId",5)
                .when().get("/pet/{petId}");

        response.prettyPrint();

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        System.out.println("response.path(\"category\") = " + response.path("category"));

      //  assertEquals(response.path("category.id"),"0");
        assertEquals(response.path("name"),"teste00002");

    }
    @Test
    public void findByStatus(){
        Response response= given().accept(ContentType.JSON).and().queryParam("status","available")
                .when().get("/pet/findByStatus");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        response.prettyPrint();

        assertTrue(response.body().asString().contains("doggie"));



        JsonPath jsonPath= response.jsonPath();
        System.out.println("jsonPath.getString(\"name[0]\") = " + jsonPath.getString("name[0]"));
        //  assertEquals(jsonPath.getString("id.status"),"available");
        //System.out.println("jsonPath.getInt(\"id[5]\") = " + jsonPath.getString("id[5]"));
        // System.out.println("jsonPath.getString(\"status\") = " + jsonPath.getString("id[5].name"));
        List<Object> id = jsonPath.getList("id");
        System.out.println("names = " +id);
        System.out.println( jsonPath.getString("find{it.id==9223372000001102860}.status"));
       /* Map<String,Object> jsonMap= response.body().as(Map.class);
        System.out.println("jsonMap = " + jsonMap);*/
    }





}
