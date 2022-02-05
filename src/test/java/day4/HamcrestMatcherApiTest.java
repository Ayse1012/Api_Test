package day4;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import org.hamcrest.Matchers;
import utilities.ConfigurationReader;

import static org.hamcrest.Matchers.*;


public class HamcrestMatcherApiTest {

    //body ve header i tek satırda asset etme


@Test
    public void spartanWithHamcrest(){
    /*
      given accept type is Json
      And path param id is 15
      When user sends a get request to spartans/{id}
      Then status code is 200
      And content type is Json
      And json data has following
          "id": 21,
          "name": "Mark",
          "gender": "Male",
          "phone": 1852873386
       */

    given().accept(ContentType.JSON)
            .and().pathParam("id",21)
            .when().get("http://54.237.100.89:8000/api/spartans/{id}")
            .then().statusCode(200).and().contentType("application/json")
            .and().assertThat().
            body("id",equalTo(21),
                    "name",equalTo("Mark"),
                     "gender",equalTo("Male"),
                    "phone",equalTo(1852873386));

    //is=equalTo aynı işi görüyor

}
@Test
    public void test2(){
    given().accept(ContentType.JSON)
            .and().pathParam("id",21)
            .when().get("http://54.237.100.89:8000/api/spartans/{id}")
            .then().statusCode(200).assertThat().contentType("application/json")
            .and().assertThat().
            body("id",is(21),
            "name",is("Mark"),
            "gender",is("Male"),
            "phone",is(1852873386));


}
@Test
    public void teacherData(){

    //header daki değerleri tek satırda assert etme

    given().accept(ContentType.JSON).pathParam("id",4593).log().all()
            .when().get("http://api.cybertektraining.com/teacher/{id}")
            .then().statusCode(200).and()
            .contentType("application/json").and()
            .header("connection",equalTo("Keep-Alive")).and()
            .header("Date",notNullValue()).and()
            .assertThat().body("teachers.firstName[0]",equalTo("Tina"),
                    "teachers.lastName[0]",is("Wonder"),
                    "teachers.gender[0]",is("Male")).log().all();

    //.log.all----ile body de konsolda görürüz

}
@Test
    public void teacherWithDepartment(){

    given().accept(ContentType.JSON).pathParam("name","Computer")
            .when().get(ConfigurationReader.get("cbt_url")+"/teacher/department/{name}")
            .then().statusCode(200)
            .and().contentType("application/json").and()
            .body("teachers.firstName",hasItems("Alexander","Marteen"));

 }

 

}
