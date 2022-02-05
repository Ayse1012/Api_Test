package day5_POJO;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.*;
import java.util.HashMap;
import java.util.Map;

public class PostRequestDemo {
    @BeforeClass
    public void beforeClass(){

        baseURI = ConfigurationReader.get("spartan_url");

    }

    @Test
    public void postNewSpartan(){
/*
    Given accept type and Content type is JSON
    And request json body is:
    {
      "gender":"Male",
      "name":"MikeEU",
      "phone":8877445596
   }
    When user sends POST request to '/api/spartans'
    Then status code 201
    And content type should be application/json
    And json payload/response/body should contain:
    "A Spartan is Born!" message
    and same data what is posted
 */
        String jsonBody = "{\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"name\": \"Elizabeth\",\n" +
                "  \"phone\": 1234567895\n" +
                "}";

        //post-put-patch request methodlarını body otomatik olarak json a çevirir
        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(jsonBody).when().post("/spartans");

        response.prettyPrint();
        //verify status code
        assertEquals(response.statusCode(),201);
        //verify content Type
        assertEquals(response.contentType(),"application/json");

        //verify successful message //1.yol
        assertTrue(response.body().asString().contains("A Spartan is"));

        //verify successful message //2.yol kısa
        assertEquals(response.path("success"),"A Spartan is Born!");

         //verify successful message //2.yol uzun
        String expectedMessage="A Spartan is Born!";
        String actualMessage=response.path("success");
        assertEquals(actualMessage,expectedMessage);

        //verify successful message //3.yol
        JsonPath jsonPath=response.jsonPath();
        assertEquals(jsonPath.getString("success"),"A Spartan is Born!");

        String name=jsonPath.getString("data.name");
        String gender=jsonPath.getString("data.gender");
        long phone=jsonPath.getLong("data.phone");

        assertEquals(name,"Elizabeth");
        assertEquals(gender,"Female");
        assertEquals(phone,1234567895);

    }

    @Test
    public void postNewSpartan2(){
        //post yapmak istediğimiz json body için map oluşturuyoruz
        Map<String,Object> bodyMap=new HashMap<>();
        bodyMap.put("name","Philips");
        bodyMap.put("gender", "Male");
        bodyMap.put("phone", 1234567890);

        //hamcrest mather ile body verify etme
       given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .and().body(bodyMap)//map i otomatik olarak json a çevirdi //serialization
               .when().post("/spartans").then().log().all()
               .statusCode(201).and().contentType("application/json").and()
               .body("success",equalTo("A Spartan is Born!"),
                       "data.name",equalTo("Philips"),
                       "data.gender",equalTo("Male"),
                       "data.phone",equalTo(1234567890));
    }

    @Test
    public void postNewSpartan3(){

        Spartan2 spartan2=new Spartan2();

        spartan2.setName("Kate");
        spartan2.setGender("Female");
        spartan2.setPhone(1234567894);

       Response response= given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and()
                .body(spartan2).when().post("/spartans");

        assertEquals(response.statusCode(),201);
        System.out.println("spartan2.getName() = " + spartan2.getName());
        assertEquals(spartan2.getName(),"Kate");
    }
    @Test
    public void çalışma(){

      String mybody="{\n" +
              "        \"name\": \"Betül\",\n" +
              "        \"gender\": \"Female\",\n" +
              "        \"phone\": 4357208765\n" +
              "}";

      Response response=given().accept(ContentType.JSON)
              .and().contentType(ContentType.JSON)
              .and().body(mybody).when().post("/spartans");

      response.prettyPrint();
      assertEquals(response.statusCode(),201);
      assertEquals(response.contentType(),"application/json");
      assertEquals(response.path("success"),"A Spartan is Born!");

      JsonPath jsonPath=response.jsonPath();
      assertEquals(jsonPath.getString("success"),"A Spartan is Born!");

      long phone=response.path("data.phone");
      assertEquals(phone,4357208765l);
      assertEquals(response.path("data.name"),"Betül");
      assertEquals(response.path("data.gender"),"Female");


    }
    @Test
    public void çalışma2(){

        //map kullanarak post etme
        Map<String,Object> mapBody=new HashMap<>();
        mapBody.put("name","Betül");
        mapBody.put("gender","Female");
        mapBody.put("phone",4357208765l);

        given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .and().body(mapBody).when()
                .post("/spartans").then().log().all().statusCode(201)
                .and().contentType("application/json").and()
                .body("data.name",equalTo("Betül"),"data.gender",equalTo("Female"),
                        "data.phone",equalTo(4357208765l),
                        "success",equalTo("A Spartan is Born!")).log().all();

        
    }
    @Test
    public void postNewSpartan4(){
        Spartan2 spartan2=new Spartan2();

        spartan2.setName("Kate");
        spartan2.setGender("Female");
        spartan2.setPhone(1234567894);

        Response response= given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and()
                .body(spartan2).when().post("/spartans");

        assertEquals(response.statusCode(),201);

        //creat ettiğimiz data yi verify etmek istiyoruz,swagger bize id ile sorgulamamıza izin veriyor.biz de id yi
        //almak istiyoruz.ve id yi dinamik olarak almak için aşağidaki satırı yazıyoruz.
        //id yi aldıktan sonra id ile tekrar get yapıp data yi verify ediyoruz
        int ıdSpartan=response.path("data.id");

        Response response1=given().accept(ContentType.JSON).and()
                .pathParam("id",ıdSpartan)
                .and().when().get("/spartans/{id}");
        response1.prettyPrint();
        assertEquals(response1.path("name"),"Kate");





    }

}
