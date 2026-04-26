package RestAssured;

import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class PostRequestExample {
    String userId;
    String name = "defg";

    @Test(priority =1,enabled = false)
    public void usingHashMap() {

        HashMap<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", name+"@gmail.com");
        user.put("role", "admin");
        HashMap<String, Object> address = new HashMap<>();
        address.put("city", "Delhi");
        address.put("zip", "100001");
        user.put("address", address);

        userId= given().contentType("application/json").body(user)
                .when().post("http://localhost:3000/users")
                .then().statusCode(201)
                .header("Content-Type", equalTo("application/json"))
                .body("name",equalTo(name))
                .time(lessThan(2000L))
                .log().all()
                .extract().jsonPath().get("id");
    }

    @Test(priority =1,enabled = false)
    public void usingOrgJSON() {

        JSONObject user = new JSONObject();
        user.put("name", name);
        user.put("email", name+"@gmail.com");
        user.put("role", "admin");
        JSONObject address = new JSONObject();
        address.put("city", "Delhi");
        address.put("zip", "100001");
        user.put("address", address);

        userId= given().contentType("application/json").body(user.toString())
                .when().post("http://localhost:3000/users")
                .then().statusCode(201)
                .header("Content-Type", equalTo("application/json"))
                .body("name",equalTo(name))
                .time(lessThan(2000L))
                .log().all()
                .extract().jsonPath().get("id");
    }

    @Test(priority =1,enabled = false)
    public void usingPOJOClass() {

        UserPOJO user = new UserPOJO();
        user.setName(name);
        user.setEmail(name+"@gmail.com");
        user.setRole("admin");
        UserPOJO.Address address = new UserPOJO.Address();
        address.setCity("Delhi");
        address.setZip("110001");
        user.setAddress(address);

        userId= given().contentType("application/json").body(user)
                .when().post("http://localhost:3000/users")
                .then().statusCode(201)
                .header("Content-Type", equalTo("application/json"))
                .body("name",equalTo(name))
                .body("email",equalTo(user.getEmail()))
                .time(lessThan(2000L))
                .log().all()
                .extract().jsonPath().get("id");
    }

    @Test(priority =1,enabled = true)
    public void usingJSONFile() throws FileNotFoundException {
        File file = new File(".//src/RestAssured/body.json");
        FileReader fileReader = new FileReader(file);
        JSONTokener tokener = new JSONTokener(fileReader);
        JSONObject user = new JSONObject(tokener);

        userId= given().contentType("application/json").body(user.toString())
                .when().post("http://localhost:3000/users")
                .then().statusCode(201)
                .header("Content-Type", equalTo("application/json"))
                .body("name",equalTo(name))
                .body("email",equalTo(name+"@gmail.com"))
                .time(lessThan(2000L))
                .log().all()
                .extract().jsonPath().get("id");
    }

    @AfterMethod
    public void deleteUser(){
                when().delete("http://localhost:3000/users/" +userId)
                .then().statusCode(200)
                .header("Content-Type", equalTo("application/json"))
                .body("name",equalTo(name))
                .time(lessThan(2000L))
                .log().all();
    }
}
