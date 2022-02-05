package day6;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

public class HomeWork {


    int idPost;
    @BeforeClass
    public void beforeClass(){

        baseURI = ConfigurationReader.get("spartan_url");

    }

    @Test  (priority = 1)
    public void PostNew(){

        HomeWorkClass ödevClass=new HomeWorkClass("Ayşe","Male",1234567890);

        Response response=given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .and().body(ödevClass).when().post("/spartans");

        assertEquals(response.statusCode(),201);
         idPost=response.path("data.id");
        System.out.println("idPost = " + idPost);

        Response response1=  given().contentType(ContentType.JSON).pathParam("id",idPost)
                        .and().when().get("/spartans/{id}");
        assertEquals(response1.contentType(),"application/json");
        assertEquals(response1.path("name"),"Ayşe");

    }
    @Test(priority = 2)
    public void putSpartan(){
        Map<String,Object> putMap=new HashMap<>();

        putMap.put("name","Ahmet");
        putMap.put("gender","Male");
        putMap.put("phone",1234567890);

        given().contentType(ContentType.JSON).pathParam("id",idPost)
                        .and().body(putMap)
                .when().put("/spartans/{id}").then().log().all()
                .assertThat().statusCode(204);


    }
    @Test(priority = 3)
    public void patchSpartan(){
        Map<String,Object> putMap=new HashMap<>();
        putMap.put("gender","Female");
        given().contentType(ContentType.JSON).pathParam("id",idPost)
                .and().body(putMap).when().patch("/spartans/{id}");

    }
    @Test(priority = 4)
    public void getSpartan(){

        Response response=given().accept(ContentType.JSON).pathParam("id",idPost)
                .when().get("/spartans/{id}");

        response.prettyPrint();

        assertEquals(response.statusCode(),200);
        assertEquals(response.path("name"),"Ahmet");


    }
    @Test(priority = 5)
    public void deleteSpartan(){
        //say goodbye
        given().pathParam("id",idPost).when().delete("/spartans/{id}")
                .then().statusCode(204).log().all();

        System.out.println( "say goodbye ");
    }

}
