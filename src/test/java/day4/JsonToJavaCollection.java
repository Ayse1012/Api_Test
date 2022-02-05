package day4;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

 public class JsonToJavaCollection {

    /*  @BeforeClass
         public void beforeClass(){

             baseURI = ConfigurationReader.get("spartan_url");

        } */
    @Test
    public void spartanToMap(){

        Response response = given().accept(ContentType.JSON).and().pathParam("id", 46)
                .when().get("/spartans/{id}");

        assertEquals(response.statusCode(), 200);
           response.prettyPrint();

        // 6  yol
        //contains method ( tavsiye edilmiyor)
        //               -->   (response.body).contains("string")
        //response path
        //               -->    response.path("name")
        //jsonpath
        //               -->   response u json path a assign ettik ve jsonPath methodlari ile body i aldik (jsonPath.getString("id)
        //de-serialization
        //               -->  Json formatinda olan body yi java ya cevirdik (map<String,Object> ya da List<Map<String,Object> olabilir
        //serialization
        // hamcrest matcher ile body i alip verify etme --> assertThat() .body("id",equalTo(46))
        //                                          --> assertThat() .body("id",is(46))
        response.path("id"); // 46


        // json formatinda olan body yi java collections a ceviriyoruz

        Map<String, Object> jsonMap = response.body().as(Map.class);
        //bu satırdan sonra java başlıyor
            System.out.println("jsonMap = " + jsonMap);
           //burada aldığımız her sonuç
        int id = (int) jsonMap.get("id");//burada istediğimiz key ın sonucunun türünü (int mi,stringmi vs.)otamatik creet etmek gerekiyor
        Object name = jsonMap.get("name");//veya buradaki gibi hepsini kapsayan object kullanabiliriz
        String gender = (String) jsonMap.get("gender");
        Object phone = jsonMap.get("phone");

        //    System.out.println("jsonMap.containsValue(\"Derya\") = " + jsonMap.containsValue("Derya"));

//        assertEquals(id, 46);
//        assertEquals(name, "Derya");
//        assertEquals(gender, "Female");
//        assertEquals(phone, 1765238743);

        assertEquals(jsonMap.get("name"),"Derya");
        assertEquals(jsonMap.get("id"),46);
        assertEquals(jsonMap.get("gender"),"Female");
        assertEquals(jsonMap.get("phone"),1765238743);

    }
    @Test
    public void test2(){
        // Odev
        //spartans end pointi ile hepsini al ve List<Map<String,Object> at
        //3. spartanin bilgileri yazdir
        //3. spartani yeni bir Map<String,Object> e ekle ismi de spartan3 olsun
        //{
        //        "id": 18,
        //        "name": "Allen",
        //        "gender": "Male",
        //        "phone": 8716150217
        //    },  ve bu bilgileri verify et

        Response response = given().accept(ContentType.JSON)
                .when().get("/spartans");

        assertEquals(response.statusCode(), 200);
           response.prettyPrint();

       List<Map<String, Object>> allSpartansList = response.body().as(List.class);
        System.out.println("jsonMap1 = " + allSpartansList);
        System.out.println("allSpartansList.size() = " + allSpartansList.size());

        System.out.println("allSpartansList.get(0).get(\"name\") = " + allSpartansList.get(0).get("name"));
        System.out.println("allSpartansList.get(allSpartansList.size()-2).get(\"name\") = " + allSpartansList.get(allSpartansList.size() - 2).get("name"));

        System.out.println("allSpartansList.get(2).get(\"name\") = " + allSpartansList.get(2).get("name"));

        Object id =allSpartansList.get(5).get("id"); //5.indexteki id

        Map<String,Object> spartan3=allSpartansList.get(2);
        System.out.println("spartan3 = " + spartan3);

        assertEquals(spartan3.get("id"),18);
        assertEquals(spartan3.get("name"),"Allen");
        assertEquals(spartan3.get("gender"),"Male");
        assertEquals(spartan3.get("phone"),8716150217l);
    }

    @Test
    public void region(){
        //body karışık old zaman;list,map(key and value yapısı) bir arado old zaman aşağıdaki yontemi uygulayabiliriz
        Response response=given().accept(ContentType.JSON).when().get("http://54.237.100.89:1000/ords/hr/regions");
        assertEquals(response.statusCode(),200);

        //json dan map e atalım
        Map<String,Object> regionMap=response.body().as(Map.class);
        System.out.println("regionMap = " + regionMap);
        System.out.println("regionMap.get(\"count\") = " + regionMap.get("count"));
        System.out.println("regionMap.get(\"hasMore\") = " + regionMap.get("hasMore"));

        // body icerisinde items in icinde bulunan degerler list oldugu icin items in icini de list<map> olarak
        // atmamiz gerekiyor. "regionMap.get("items") bize map donuyor ve onun icin casting yapiyoruz.

        List<Map<String,Object>> itemsList = (List<Map<String, Object>>) regionMap.get("items");

       //  List<Map<String,Object>> itemsList =regionMap.get("items");
         //yukarıdaki satırın sağ tarafı List map tutuyor, bizde sağ tarafa atamak için casting yapıyoruz

                 //   System.out.println("itemsList = " + itemsList);
        /* for (Map<String, Object> stringObjectMap : itemsList) {
             System.out.println("stringObjectMap = " + stringObjectMap);

         } */

         System.out.println("itemsList.get(0).get(\"region_name\") = " + itemsList.get(0).get("region_name"));

        assertEquals(itemsList.get(0).get("region_name"),"Cybertek Europe");
        //cybertek turkey i verify et
        assertEquals(itemsList.get(2).get("region_name"),"CybertekTurkey");

    }

    @Test
    public void benzerÖrnek(){

        Response response=given().accept(ContentType.JSON).when().get("http://54.237.100.89:1000/ords/hr/employees");
        assertEquals(response.statusCode(),200);

        // response.prettyPrint();

        Map<String,Object> employeeMap=response.body().as(Map.class);
        System.out.println("employeeMap.get(\"hasMore\") = " + employeeMap.get("hasMore"));
        System.out.println("employeeMap.get(\"limit\") = " + employeeMap.get("limit"));
        System.out.println("employeeMap = " + employeeMap);
        System.out.println("employeeMap.get(\"items\") = " + employeeMap.get("items"));
        System.out.println("employeeMap.get(\"links\") = " + employeeMap.get("links"));

        System.out.println("employeeMap.get(\"items.employee_id\") = " + employeeMap.get("items.employee_id"));//bu şekilde items list inin içini okuyamaz
        // okuyabilmesi için aşağıdaki gibi items i list<map> e assign etmeliyiz

        List<Map<String,Object>> itemsMapList= (List<Map<String, Object>>) employeeMap.get("items");
        System.out.println("itemsMapList.get(1).get(\"employee_id\") = " + itemsMapList.get(1).get("employee_id"));
        System.out.println("itemsMapList.get(5).get(\"phone_number\") = " + itemsMapList.get(5).get("phone_number"));

        assertEquals(itemsMapList.get(1).get("employee_id"),101);
        // assertEquals(itemsMapList.get(5).get("phone_number"),590.423.4569l);


    }
    @Test
    public void benzerÖrnek2(){
        Response response=given().accept(ContentType.JSON).when().get("http://54.237.100.89:1000/ords/hr/countries");
        assertEquals(response.statusCode(),200);

        //  response.prettyPrint();

        Map<String,Object> countryMap=response.body().as(Map.class);
        System.out.println("countryMap = " + countryMap);
        System.out.println("countryMap.get(\"count\") = " + countryMap.get("count"));
        System.out.println("countryMap.get(\"links\") = " + countryMap.get("links"));

        //items deki elemanların değerlerini görebilmek için countryMap.get("items") ı list<Map> e assign etmeliyiz

        List<Map<String,Object>> countryListMap= (List<Map<String, Object>>) countryMap.get("items");

        System.out.println("countryListMap.get(4).get(\"country_name\") = " + countryListMap.get(4).get("country_name"));
        System.out.println("countryListMap.get(3).get(\"region_id\") = " + countryListMap.get(3).get("region_id"));

        assertEquals(countryListMap.get(4).get("country_name"),"Canada");
        assertEquals(countryListMap.get(3).get("region_id"),2);


    }



}
