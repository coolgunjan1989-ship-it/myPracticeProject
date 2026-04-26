package RestAssured;

import Utils.CommonMethods;
import Utils.YamlReader;
import Utils.appendUserToFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class generateDynamicDBJson {

    ObjectMapper mapper = new ObjectMapper();

    public Map<String,String> updateJson() throws JsonProcessingException {
        Map<String,String> returnData = new HashMap<>();
        Object object;
        Map<String,String> newData = new HashMap<>();
        CommonMethods commonMethods = new CommonMethods();
        int count = commonMethods.generateRandomInt(11);
        int length = commonMethods.generateRangeInt(1, 11);
        String name = commonMethods.generateRandomString(length);
        String email = name+"@gmail.com";
        String[] arrayRole = {"admin", "user"};
        String role = commonMethods.getRandomValue(arrayRole);
        Map<String,String> address = commonMethods.getRandomIndianAddress();
        String city = address.get("city");
        String pincode = address.get("pincode");

        newData.put("email", email);
        newData.put("role", role);
        newData.put("city", city);
        newData.put("zip", pincode);
        newData.put("name", name);

        UserPOJO userPojo = null;

        for (int i = 0; i <= count; i++) {
            userPojo = new UserPOJO();
            userPojo.setEmail(email);
            userPojo.setRole(role);
            userPojo.setName(name);
            UserPOJO.Address add = new UserPOJO.Address();
            add.setCity(city);
            add.setZip(pincode);
            userPojo.setAddress(add);
        }
        object = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userPojo);
        returnData.put("body", (String) object);
        return returnData;
    }

    public void appendData() throws FileNotFoundException, JsonProcessingException {
        //TODO needs to be corrected , finding the users arrray in json and then appending
        Object userPOJO =  updateJson();
        appendUserToFile newUser = new appendUserToFile(userPOJO);
        File file = new File("src/RestAssured/db.json");
        FileReader fileReader = new FileReader(file);
        JSONTokener jsonTokener = new JSONTokener(fileReader);
        JSONObject jsonObject = new JSONObject(jsonTokener);
        System.out.println("The updated json is " + jsonObject.toString());

    }

    public void postRequest(String url, String body){
        RestAssured.given().body(body)
                .when().post(url)
                .then().log().all();
    }

    public String postRequest(String url, String body, String toBeExtracted){

       String value =  RestAssured.given().body(body)
                .when().post(url)
                .then().extract().response().jsonPath().get(toBeExtracted);
        return value;
    }

    public void getRequest(String url){
        RestAssured.when().get(url)
                .then().log().all();
    }

    public void main(String[] args) throws IOException {
        YamlReader reader = new YamlReader("config/YmlFiles/local.yml");
        String usersURI = reader.getParamValue("usersRequest");
        Map<String,String> returnedData =  updateJson();
        String body  = returnedData.get("body");
        String id = postRequest(usersURI, body, "users[].id");
        System.out.println("The id is " + id);
        getRequest(usersURI);

        System.out.println("Getting list of existing users");
        FileReader fileReader = new FileReader("config/JSONFiles/db.json");
        JSONTokener dbTokener = new JSONTokener(fileReader);
        JSONObject object = new JSONObject(dbTokener);

        String prettyJson = object.toString();
        System.out.println("Existing users are :- \n" + prettyJson);

        JsonPath jsonPath = new JsonPath(prettyJson);
        UserPOJO[] users = jsonPath.getObject("users", UserPOJO[].class);

        for (UserPOJO user : users) {
            System.out.println("Processing: " + user.getName());
            // Access nested address without casting
            System.out.println("City: " + user.getAddress().getCity());
        }



        }





        
    }


    
