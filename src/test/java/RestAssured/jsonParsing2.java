package RestAssured;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class jsonParsing2 {
    jsonParsing jsonParsing = new jsonParsing();

    String userId;
    @Test(priority = 1, enabled = false)
    void getUsers() {
        ResponseBody responseBody =
        when().get("http://localhost:3000/users")
                .then().contentType("application/json").statusCode(200).log().all()
                .extract().response().body();

        JsonPath jsonPath = new JsonPath(responseBody.asString());

        //Get number of users
        int noOfUsers = jsonPath.getInt("size()");

        //Extract all users
        for(int i=0; i<noOfUsers; i++){
            System.out.println(jsonPath.getString("["+i+"]"));
        }

        String user = "Gunjan Sharma";
        int flag =0;
        //search for user Gunjan Sharma in the list
        for(int i=0; i<noOfUsers; i++){
           if(jsonPath.getString("["+i+"].name").equals(user)){
               flag =1;
               break;
            }
        }
        if(flag==1){
            System.out.println("The user "+ user + " is found in the User List.");
        }
        else{
            System.out.println("The user "+ user + " is not found in the User List.");
        }

        assertThat(flag, equalTo(1));

    }

    @Test
    public void ecommerce(){
        String path = "src/RestAssured/marketplace.json";
        JsonPath jsonPath = new JsonPath(jsonParsing.getJsonResponse(path));

       System.out.println( jsonPath.getString("api_response.marketplace.orders[0].customer.email"));

        //Collection Filtering: Write a query to find the email of all customers whose status is currently "VIP".
        int noOfOrders = jsonPath.getInt("api_response.marketplace.orders.size()");

        for(int i=0; i<noOfOrders; i++){
           if( jsonPath.getString("api_response.marketplace.orders["+i+"].customer.status").equalsIgnoreCase("regular")){
               System.out.println("The email of Regular customer is " + jsonPath.getString( "api_response.marketplace.orders["+i+"].customer.email"));
           }
        }

        //Discount Code for first item in first order
        //  Direct Path: What is the path to get the discount_code for the first item in the first order?
        System.out.println("The discount code for first item in first order is " +  jsonPath.get("api_response.marketplace.orders[0].items[0].promotion.discount_code"));


        //Storage Condition for product Organic Honey
        //Deep Nesting: How would you access the storage_condition for the product "Organic Honey" (Product ID P-402)?
        int noOfProducts = jsonPath.getInt("api_response.marketplace.products.size()");

        for(int i=0; i<noOfProducts; i++){
            if( jsonPath.getString("api_response.marketplace.products["+i+"].name").equalsIgnoreCase("Organic Honey")){
                System.out.println("The storage condition required for Organic Honey is " + jsonPath.getString( "api_response.marketplace.products["+i+"].specifications.safety.storage_condition"));
            }
        }

        //Flattening Arrays: Write an expression to get a unique list of all courier names across every shipment in the entire dataset.
        List<String> listOfCouriers = jsonPath.getList("api_response.marketplace.orders.logistics.shipments.flatten().courier");

        for(String courier: listOfCouriers){
            System.out.println("the order is " + courier);
        }




        //Aggregations: How would you calculate the total number of items ordered by "Kashvi Sukhija" by looking at the quantity fields in her order?

        //Conditional Search: Find the order_id where the shipping_address city is "Tokyo".

        //Index Logic: In the product list, what is the value of products[2].inventory.warehouses[0].location?

        //Boolean Check: Which expression would return true if any product in the list has a stock_level of less than 10?

        //Attribute Extraction: Get the last_login_ip for the user with id: 1002.

        //Complex Map: Access the USD value from the currency_rates object within the metadata.

    }
}
