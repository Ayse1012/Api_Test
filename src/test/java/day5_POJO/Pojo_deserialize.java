package day5_POJO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import  java.util.List;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;
public class Pojo_deserialize {


    @Test
    public void regions(){
        Response response=given().when().get("http://54.237.100.89:1000/ords/hr/regions");
        assertEquals(response.statusCode(),200);

        //json to java--body yı javaya çevirmiş olduk
        Regions regions=response.body().as(Regions.class);//response body yi Regions class ına assgin ettik
        System.out.println("regions.count = " + regions.count);
        System.out.println("regions.hasMore = " + regions.hasMore);

        //Region class daki İtemListin içeriğini alma
        List<Item> items=regions.items;
        System.out.println("items.get(4).region_name = " + items.get(4).region_name);
        System.out.println("items.get(4).region_id = " + items.get(4).region_id);
        for (Item item : items) {
            System.out.println("item.region_id = " + item.region_id);
            System.out.println("item.region_name = " + item.region_name);
        }

        System.out.println("regions.links.get(0) = " + regions.links.get(0).href);
        assertEquals(items.get(4).region_name,"Cybertek Germany");


    }





}
