package RestAssured;

import io.restassured.http.Cookie;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class FileUploadCookiesHeaders {

    @Test(enabled = false)
    public void fileUpload(){
        File file = new File("C:\\Users\\Akshay-Gunjan\\OneDrive\\Desktop\\demo.txt");
        File newFile = new File("C:\\Users\\Akshay-Gunjan\\OneDrive\\Desktop\\vdiIssue.txt");

        given().multiPart(file).multiPart(newFile).contentType("multipart/form-data")
                .when().post("https://postman-echo.com/post")
                .then().statusCode(200)
                .body("files", hasKey("demo.txt"))
                .body("files", hasKey("vdiIssue.txt"))
                .log().all();
    }

    @Test(enabled = false)
    public void fileDownload(){
        //Check available file on https://the-internet.herokuapp.com/download
        String fileName = "test.txt";
        given().pathParam("fileName", fileName).
        when().get("https://the-internet.herokuapp.com/download/{fileName}")
                .then().statusCode(200)
                .log().all();
    }


    @Test(enabled = false)
    public void cookies(){
        Response response =
        when().get("http://www.google.com")
                .then().statusCode(200)
                .cookie("AEC", notNullValue())
               // .log().cookies()
                .extract().response();

        String AECCookieValue = response.getCookie("AEC");
        System.out.println("The value of AEC Cookie value is " + AECCookieValue);

        Map<String, String> cookies = response.getCookies();

        //Printing cookies in form of Key,Value Pair
        for(String key : cookies.keySet()){
            System.out.println("Key: " + key + "   Value: " + cookies.get(key));
        }

        Cookie AEC = response.getDetailedCookie("AEC");
        System.out.println("cookie AEC has Expiry Date? " + AEC.hasExpiryDate());
        System.out.println("cookie AEC - Expiry Date = " + AEC.getExpiryDate());
        System.out.println("cookie AEC has value? " + AEC.hasValue());
        System.out.println("cookie AEC's value = " + AEC.getValue());
        System.out.println("cookie AEC is secured? " + AEC.isSecured());


    }

    @Test(enabled = true)
    public void headers(){
        Response response =
                when().get("http://www.google.com")
                        .then().statusCode(200)
                        // .log().headers()
                        .header("content-Encoding", equalTo("gzip"))
                        .header("Server" , equalTo("gws"))
                        .header("Content-Type", containsString("text/html"))
                        .extract().response();

        String contentEncodingValue = response.getHeader("Content-Encoding");
        System.out.println("The value of content encoding header value is " + contentEncodingValue);

        Headers headers = response.getHeaders();

        //Printing cookies in form of Key,Value Pair
        for(Header h : headers){
            System.out.println("Key: " + h.getName() + "   Value: " + h.getValue());
        }

    }
}
