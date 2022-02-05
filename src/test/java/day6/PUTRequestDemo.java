package day6;

import org.testng.annotations.BeforeClass;
import utilities.ConfigurationReader;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.hamcrest.Matchers;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

public class PUTRequestDemo {
   /* Not=HTTP REQUEST

    GET --> Datalari getirme (cagirma)
    POST --> Yeni bir data olusturma (create data)
    PUT --> Olan bir data yi guncelleme (update)
(butun body yi yazmamiz gerekir)
    PATCH --> olan bir data nin parca olarak guncelleme
            (sadece guncelleme gereken data yi yazmamiz yeterli)
    DELETE --> data yi silmek.

            POST --> genellikle 201 doner (developer a bagli)
    PUT --> 200 ya da 204 doner(developer a bagli)

    Authentication --> sizin gercekten soylediginiz kisi oldugunuzu teyit eder.

// sirkete kimligin ile girebilirsiniz ancak server odasina giremezsiniz

Authorization --> sizin bazi islemleri yapamaya yetkili olup olmadiginizi teyit eder.

//yetkiniz var ise server odasina girebilirsiniz.

401 --> girdiginiz bilgiler yanlis. sistem sizi taniyamadi (kullanici adi veya sifresi yanlis olabilir)

403 --> girdiginiz bilgiler dogru ancak bu islemi gerceklestirmeye yetkiniz yok.

    */


    @BeforeClass
    public void beforeClass(){

        baseURI = ConfigurationReader.get("spartan_url");

    }
    @Test
    public void putMethod(){
        //sabit olan id deki bilgileri istediğimiz şekilde değiştirip database e kaydetme işlemi
        Map<String,Object> putMap=new HashMap<>();

        putMap.put("gender","Male");
        putMap.put("name","batuhan");
         putMap.put("phone",1234567890);

         //yaptığımız değişikliği aşağidaki kısım database e gönderiyoruz
         given().contentType(ContentType.JSON)
                 .and().pathParam("id",2066).and()
                 .body(putMap).when().put("/spartans/{id}").then().log().all()
                 .assertThat().statusCode(204);


    }

    @Test
    public void PatchMethod(){
        Map<String,Object> putMap=new HashMap<>();
        //patch de sadece yapmak istediğimiz değişikligi yapıyoruz
        // bu sebeple put dan daha kullanışlı
        putMap.put("name","Kamuran");


        given().contentType(ContentType.JSON)
                .and().pathParam("id",2066).and()
                .body(putMap).when().patch("/spartans/{id}").then().log().all()
                .assertThat().statusCode(204);

    }

    @Test
    public void Delete(){
        //id si xxxx olan body i silme
        given().pathParam("id",2002)
                .when().delete("/spartans/{id}")
                .then().statusCode(204).log().all();
    }

}

