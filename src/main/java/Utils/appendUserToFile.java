package Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.File;

public class appendUserToFile {

    public appendUserToFile(Object newUser) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File("src/RestAssured/db.json");

            // 1. Read existing JSON structure
            ObjectNode root = (ObjectNode) mapper.readTree(jsonFile);

            // 2. Get the "users" array
            ArrayNode usersArray = (ArrayNode) root.get("users");

            // 3. Convert POJO to JSON node and add to array
            usersArray.add(mapper.valueToTree(newUser));

            // 4. Save back to disk
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, root);

            System.out.println("User appended directly to db.json file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}