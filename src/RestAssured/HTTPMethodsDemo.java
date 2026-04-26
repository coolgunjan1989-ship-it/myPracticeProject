package RestAssured;

import io.restassured.RestAssured;
import org.testng.annotations.Test;
import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.when;
import static io.restassured.path.json.JsonPath.given;
import static org.hamcrest.Matchers.*;


public class HTTPMethodsDemo {
    String userId;
    @Test(priority = 1, enabled = false)
    void getUsers() {
        when().get("http://localhost:3000/users")
                .then().contentType("application/json").statusCode(200).log().all();
    }

    @Test(priority = 2, enabled = false)
    void users() {
        when().get("http://localhost:3000/users")
                .then().body("id[0]", equalTo("1")).log().all();
    }

    @Test(priority = 3, enabled = false)
    void validations() {
        when().get("http://localhost:3000/users")
                .then().body("email[0]", containsString("john.doe"))
                .body(containsString("email"))
                .header("Content-Type", equalTo("application/json"))
                .time(lessThan(2000L)).log().all();
    }

    @Test(priority = 1)
            public void postData() {
        Map<String, Object> address = new HashMap<String, Object>();
        address.put("city", "Delhi");
        address.put("zip", "10001");

        Map<String, Object> users = new HashMap<>();
        users.put("name", "Nikhil Sukhija");
        users.put("email", "sukhija.Nikhil@gmail.com");
        users.put("role", "admin");
        users.put("address", address);
        userId = RestAssured.given().contentType("application/json").body(users)
                .when().post("http://localhost:3000/users")
                .then().statusCode(201)
                        .header("Content-Type", "application/json")
                                .body("name", equalTo("Nikhil Sukhija"))
                .body(containsString("id"))
                .time(lessThan(2000L))
                .log().all()
                .extract().jsonPath().get("id");

    }



    @Test(priority = 2,dependsOnMethods = {"postData"})
    public void putData() {
        System.out.println("http://localhost:3000/users/" + userId);
        Map<String, Object> address = new HashMap<String, Object>();
        address.put("city", "Faridabad");
        address.put("zip", "10001");

        Map<String, Object> users = new HashMap<>();
        users.put("name", "Nikhil Sukhija");
        users.put("email", "sukhija.Nikhil@gmail.com");
        users.put("role", "admin");
        users.put("address", address);

        RestAssured.given().contentType("application/json").body(users)
                .when().put("http://localhost:3000/users/" + userId)
                .then().statusCode(200)
                .header("Content-Type", "application/json")
                .body("name", equalTo("Nikhil Sukhija"))
                .body(containsString("id"))
                .log().all();
    }

    @Test(priority = 3,dependsOnMethods = {"postData", "putData"})
    public void deleteData() {
        System.out.println("http://localhost:3000/users/" + userId);
        when().delete("http://localhost:3000/users/" + userId)
                .then().statusCode(200)
                .time(lessThan(2000L))
                .header("Content-Type", "application/json")
                .body("name", equalTo("Nikhil Sukhija"))
                .body(containsString("id"))
                .log().all();
    }
}