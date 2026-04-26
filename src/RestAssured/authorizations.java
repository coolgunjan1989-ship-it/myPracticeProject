package RestAssured;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class authorizations {

    //Username and Password are not encrypted and sent to server as is
    @Test(enabled = false)
    public void basicAuth(){
        given().auth().basic("postman", "password")
                .when().get("https://postman-echo.com/basic-auth")
                .then().body("authenticated" , equalTo(true))
                .statusCode(200)
                .log().all();
    }

    //UserName and Password are encrypted before sending
    @Test(enabled = false)
    public void basicPreemptiveAuth(){
        given().auth().preemptive().basic("postman", "password")
                .when().get("https://postman-echo.com/basic-auth")
                .then().body("authenticated" , equalTo(true))
                .statusCode(200)
                .log().all();
    }

    //UserName and Password are encrypted before sending
    @Test(enabled = false)
    public void digestAuth(){
        given().auth().digest("postman", "password")
                .when().get("https://postman-echo.com/basic-auth")
                .then().body("authenticated" , equalTo(true))
                .statusCode(200)
                .log().all();
    }

    //Bearer Token
    @Test(enabled = false)
    public void bearerTokenAuth(){
        String bearerToken = "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";
        given().header("Authorization", "Bearer " + bearerToken)
                .when().get("https://api.github.com/user/repos")
                .then().statusCode(200)
                .log().all();
    }

    @Test(enabled = false)
    public void apiKeyAuth(){
        String apiKey = "f048088804a444cab39fabc4f09a6c5e";
        String city = "Delhi";
        given().queryParam("city", city).queryParam("key", apiKey)
                .when().get("https://api.weatherbit.io/v2.0/current")
                .then().statusCode(200)
                .log().all();
    }


    @Test(enabled = true)
    public void oathAuth(){
        String clientId = "AQt3FEH0qsVisG9-HTU9RnWGlBZEow79O1K-Xzq4xjV3LKu5Pz2mP3XbsH-6s3ZA-2RoWHepVyPR6A38";
        String clientSecret = "EApyeeqk-57-n4Sh9enC3emxC8ZSFEAVSLD7vzu2yEH5q0DeB_Xvg0qd53s7VvsQUXqUfTZnkoM1xf65";
        String token = given().formParam("grant_type", "client_credentials")
                .auth().preemptive().basic(clientId, clientSecret) // Try preemptive
                .contentType("application/x-www-form-urlencoded")
                .when().post("https://api-m.sandbox.paypal.com/v1/oauth2/token")
                .then().statusCode(200)
                .log().all()
                .extract().jsonPath().get("access_token");

        System.out.println("The token is " + token);

        given().header("Authorization" , "Bearer " + token)
                .when().get("https://api-m.sandbox.paypal.com/v1/invoicing/invoices?page=3&page_size=4&total_count_required=true")
                .then().statusCode(200)
                .log().all();

        given().auth().oauth2(token)
                .when().get("https://api-m.sandbox.paypal.com/v1/invoicing/invoices?page=3&page_size=4&total_count_required=true")
                .then().statusCode(200)
                .log().all();
    }

}
