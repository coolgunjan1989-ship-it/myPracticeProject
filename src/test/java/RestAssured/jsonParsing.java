package RestAssured;

import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class jsonParsing {

    public String getJsonResponse(String path) {
        File file = new File(path);
        FileReader fileReader = null;
        try {
             fileReader = new FileReader(file);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        JSONTokener jsonTokener = new JSONTokener(fileReader);
        JSONObject jsonObject = new JSONObject(jsonTokener);
        return jsonObject.toString();
    }

    @Test(enabled = false)
    public void parseJson() throws FileNotFoundException {
        System.out.println("The JSON is "   + getJsonResponse("src/RestAssured/response.json"));

        JsonPath jsonPath = new JsonPath(getJsonResponse("src/RestAssured/response.json"));
        System.out.println("The first user's email id is " + jsonPath.get("users[0].personal_info.email"));
        System.out.println("All cities are " + jsonPath.get("users.address.city"));

        String status = jsonPath.getString("status");
        assertThat(status, is("success"));

        List<Integer> ids = jsonPath.get("users.id");
        int index = 0;
        for(int i=0; i<ids.size(); i++){
            if(ids.get(i)==103){
                index = i;
                break;
            }
        }

        System.out.println("The role of the 103 user is " + jsonPath.get("users["+index+"].role"));

    }

    @Test
    public void marketplaceJson(){
        String path = "src/RestAssured/marketplace.json";
        JsonPath jsonPath = new JsonPath(getJsonResponse(path));
        System.out.println("The discount code of first item of first order is " + jsonPath.get("api_response.marketplace.orders[0].items[0].promotion.discount_code"));

        List<String> customerStatus = jsonPath.get("orders.customer.status");
         int index =0;
        for(int i = 0; i<customerStatus.size(); i++){
            jsonPath.get("orders.customer.status");
        }
    }
}
