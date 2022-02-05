package day4;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
public class CbTraningWithJsonPath {

    @BeforeClass
    public void beforeClass(){

        baseURI = ConfigurationReader.get("cbt_url");

    }
    @Test
    public void test1(){

        Response response=given().accept(ContentType.JSON)
                .when().get("/student/all");

        assertEquals(response.statusCode(),200);
        //Json
        JsonPath jsonPath=response.jsonPath();
        jsonPath.prettyPrint();

        //aşağılarda yazılan(yeşil renkli) sintex in adı; gpath
        String firstName= jsonPath.getString("students.firstName[0]");//burada kullanılan dile Gson dili denir
        System.out.println("firstName = " + firstName);

       String lastName= jsonPath.getString("students.lastName[0]");
        System.out.println("lastName = " + lastName);

       String emailAddress= jsonPath.getString("students.contact[4].emailAddress");
        System.out.println("email = " + emailAddress);


      String companyId=  jsonPath.getString("students.company[4].companyId");
        System.out.println("companyId = " + companyId);

        //body de students array içinde company altında address, adress altında da zipCode var
       String zipCoda = jsonPath.getString("students.company[25].address.zipCode");
        System.out.println("zipCoda = " + zipCoda);

        //butün öğrencilerin isimlerini getirme
      List<String> names=  jsonPath.getList("students.firstName");
        System.out.println("names = " + names);


    }


}
