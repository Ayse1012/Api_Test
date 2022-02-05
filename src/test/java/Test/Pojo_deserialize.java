package Test;

import com.google.gson.Gson;
import day5_POJO.Spartan2;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;

public class Pojo_deserialize {

    String spartanUrl="http://54.237.100.89:8000/api/spartans";
    @Test
    public void spartanPojo(){
        Response response= RestAssured.given().accept(ContentType.JSON).pathParam("id",63)
                .when().get(spartanUrl+"/{id}");
        assertEquals(response.statusCode(),200);
        response.prettyPrint();

        //JSON to Pojo
        //json to Spartan Class
        // Map<String,Object> map=response.body().as(map.class) gibi
        Spartan2 spartan63 = response.body().as(Spartan2.class);

        //yukarıdaki satırdan sonra artık Json body spartan classına assign edildi
        System.out.println("spartan63.getName() = " + spartan63.getName());
        System.out.println("spartan63.getId() = " + spartan63.getId());
        System.out.println("spartan63.getGender() = " + spartan63.getGender());
        System.out.println("spartan63.getPhone() = " + spartan63.getPhone());

        //asset
        assertEquals(spartan63.getId(),63);
        assertEquals(spartan63.getName(),"Clayton");
        assertEquals(spartan63.getGender(),"Male");
        assertEquals(spartan63.getPhone(),1782167106);


    }
    @Test
    public void gsonExample(){

        //json and gson
        //gson classını kullanarak elimizdeki veriyi json'dan java'ya yada java dan json a konvert(değiştire) edebiliyoruz
        Gson gson =new Gson();

        // Json to Java ( deserialization )-json dan java ya

        //myjson u kendimiz oluşturduğumuz için url almadık
        String myjson= "{\n"+
                "\"id\": 2003,\n"+
                "\"name\": \"Holley\",\n"+
                "\"gender\": \"Female\",\n"+
             "\"phone\": 2885654523\n"+
            "}";
        System.out.println("myjson = " + myjson);
        //json formatındaki myjson ı java formatına çevirme (key-value şeklinde old için map e atıyoruz)
        Map<String,Object> javaSpartan=gson.fromJson(myjson,Map.class);

        System.out.println("spartan = " + javaSpartan);

        //istersek myjson u bir class a atıp fromJson metodunu kullanarakta yapabiliriz
       // Spartan spartan2003=gson.fromJson(myjson,Spartan.class);
       // System.out.println("spartan1 = " + spartan2003);

        //serialization--java dan json a çevirme
        //Spartan class ımız bir java class dır,
        // aşağıda Spartan class'a değerler atayıp daha sonra json formatına çevirdik,
        // json string tuttuğu için stringe attık

        Spartan spartan1=new Spartan(11,"Alex","Male",1234567891);
         String jsonSp1 =gson.toJson(spartan1);
        System.out.println("jsonSp1 = " + jsonSp1);

        Spartan spartan2=new Spartan(12,"Diego","Male",234567890);
        String jsonSp2 = gson.toJson(spartan2);
        System.out.println("jsonSp2 = " + jsonSp2);





    }




}
