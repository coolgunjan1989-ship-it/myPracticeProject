package RestAssured;

import io.restassured.RestAssured;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class dataFromExcel {
    String userId;

    @DataProvider(name = "getUser")
    public Object[][] getUser() {
        return new Object[][]{
                {"Gunjan", "gunjan.sharma@gmail.com"},
                {"Vihaan", "vihaan.sukhija@gmail.com"}

        };
    }



    @Test(priority = 1, dataProvider = "getUser")
    public void postData(String name, String email) {
        Map<String, Object> address = new HashMap<String, Object>();
        address.put("city", "Delhi");
        address.put("zip", "10001");

        Map<String, Object> users = new HashMap<>();
        users.put("name", name);
        users.put("email", email);
        users.put("role", "admin");
        users.put("address", address);
        userId = RestAssured.given().contentType("application/json").body(users)
                .when().post("http://localhost:3000/users")
                .then().statusCode(201)
                .header("Content-Type", "application/json")
                .body("name", equalTo(name))
                .body(containsString("id"))
                .time(lessThan(2000L))
                .log().all()
                .extract().jsonPath().get("id");

    }

}
