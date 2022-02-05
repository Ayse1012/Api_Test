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

public class HrWithJsonPath {

    @BeforeClass
    public void beforeClass(){

        baseURI = ConfigurationReader.get("hr_url");

    }
    @Test
    public void test1(){
      //Response response=  given().accept(ContentType.JSON).when().get("/countries"); veya sadece end point varsa
       Response response=get("/countries");

        assertEquals(response.statusCode(),200);;
        response.prettyPrint();

        JsonPath jsonPath=response.jsonPath();
         //body deki üçüncü ülkenin ismini yazdır
       String thirdCountryName= jsonPath.getString("items.country_name[2]");
        System.out.println(thirdCountryName);

        //bütün ülkelerin country id sini yazdır
        //burada birden fazla veri alacağımız için "getList" metot unu kullandık ve List e attık
        List<String> countryIds=jsonPath.getList("items.country_id");
        System.out.println("countryIds = " + countryIds);

        //region id si 4 olan bütün ülkelerin ismini yazdır
       List<String> countryId4= jsonPath.getList("items.findAll {it.region_id==4}.country_name");
                          //body deki items içerisinde,region_id i 4 olan ülkelerin country_name(country_id) ini getir

    }
    @Test
    public void test2(){
        Response response=get("/employees");
        JsonPath jsonPath=response.jsonPath();
        jsonPath.prettyPrint();

        //salary 24000 üzerindeki çalışanların ismini bul
        List<String> namesSalary=jsonPath.getList("items.findAll{it.salary<24000}.first_name");
        System.out.println("namesSalary = " + namesSalary);

        //salary en düşük olanı getir
       String fakir=jsonPath.getString("items.min{it.salary}.first_name");
        System.out.println("fakir = " + fakir);

        //salary en zengin olanı getir
        String zengin=jsonPath.getString("items.max{it.salary}.first_name");
        System.out.println("zengin = " + zengin);

        //employe id i 121 olanı yazdır
       String lastName= jsonPath.getString("items.find{it.employee_id==121}.last_name");
        System.out.println("lastName = " + lastName);
    }
    @Test
    public void testÖdev(){
        //odev
        // manager_id si 114 olan butun calisanlarin emailini yazdir
        //department_id si 30 olanlarin ad ve soyadlarini yazdir..
        // https://www.james-willett.com/rest-assured-gpath-json/ (gpath ile ilgili detayli bilgi)

        Response response=get("/employees");
        response.prettyPrint();

        JsonPath jsonPath=response.jsonPath();

       List<String> email114= jsonPath.getList("items.findAll{it.manager_id==114}.email");
        System.out.println("email114 = " + email114);

       List<String> lastName= jsonPath.getList("items.findAll{it.department_id==30}.last_name");
        System.out.println("LastName = " + lastName);

        List<String> firstName= jsonPath.getList("items.findAll{it.department_id==30}.first_name");
        System.out.println("firstName = " + firstName);


    }

}
