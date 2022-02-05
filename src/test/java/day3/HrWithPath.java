package day3;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class HrWithPath {

    @BeforeClass
    public void beforeClass(){

        baseURI = ConfigurationReader.get("hr_url");

    }
    @Test
    public void getCountries(){
        Response response=given().accept(ContentType.JSON).and().queryParam("q","{\"region_id\":3}")
                .when().get("/countries");
        response.prettyPrint();
        assertEquals(response.statusCode(),200);//Assert u yukarı import ettik

        //print limit value-
        System.out.println("response.path(\"limit\") = " + response.path("limit"));//limit items in içinde değil
        //get hasmore
        System.out.println("response.path(\"hasmore\") = " + response.path("hasMore"));
        //get China
        System.out.println("response.path(\"items.country_name[1]\") = " + response.path("items.country_name[1]"));
        //get japon
        System.out.println("response.path(\"items.country_id[3]\") = " + response.path("items.country_id[3]"));

        //get all country_name-items deki bütün ulkelerin adı
        List<String> allCountries = response.path("items.country_name");
        System.out.println("allCountries = " + allCountries);
        System.out.println(allCountries);

        //bütün region id lerin 3 olduğunu verify et
        List<Integer> allIds= response.path("items.region_id");//burda bütün id leri aldık

        for (int allId : allIds) {
            System.out.println("allId = " + allId);
            assertEquals(allId,3);

        }

    }

    @Test
    public void tesr2(){
        //odev
        //manual olarak Postman de aynisini yapalim.
        //test kismindan snipped kullarak;
        // 1) status code 2)body contains "IT_PROG" 3) header date oldugunu verify edelim
        // bu http request i collection a kaydedelim.

        // job_id si "IT_PROG" olan tum calisanlari al
        //status code 200, content type application json
        // butun job idlerin "IT_PROG" oldgunu verify et

        Response response=given().accept(ContentType.JSON)
                .and().queryParam("q","{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");

        response.prettyPrint();

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        List<String> allJob_ids= response.path("items.job_id");
        System.out.println("allJob_ids = " + allJob_ids);
        for (String allIds : allJob_ids) {
            assertEquals(allIds,"IT_PROG");

        }

        //2.yol
        //bu yol date bays de sorun old için çalışmadı,sintex doğru
     /*   Response response1=get("/employees");
        JsonPath jsonPath=response1.jsonPath();
        List<String> allJob_id=jsonPath.getList("items.findAll{it.job_id==IT_PROG}.first_name");
        System.out.println("allJob_id = " + allJob_id);*/

    }
}
