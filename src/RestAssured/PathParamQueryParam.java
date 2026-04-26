package RestAssured;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class PathParamQueryParam {

    @Test(enabled=false)
    public void pathParam(){
        given().pathParam("id", "1")
                .when().get("http://localhost:3000/users/{id}")
                .then().statusCode(200)
                .log().all();
    }

    @Test(enabled=false)
    public void queryParam(){
                given().queryParam("role", "admin").queryParam("address.city", "Delhi")
                        .when().get("http://localhost:3000/users")
                .then().statusCode(200)
                .log().all();
    }

    @Test(enabled=true)
    public void queryParam1(){

                when().get("http://localhost:3000/users?address.city=Delhi&role=admin")
                .then().statusCode(200)
                .log().all();
    }
}
