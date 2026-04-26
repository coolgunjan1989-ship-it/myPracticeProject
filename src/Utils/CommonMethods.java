package Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CommonMethods {
    Random rand = new Random();

    public String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    public int generateRandomInt(int bound) {
        int random = rand.nextInt(bound);

        return random;
    }

    public int generateRangeInt(int min, int max){
        int randomInRange = rand.nextInt(max - min + 1) + min;
        return randomInRange;
    }

    public String getRandomValue(String[] array) {
        Random random = new Random();
        int index = random.nextInt(array.length); // Picks a number between 0 and array length - 1
        return array[index];
    }

    public Map<String, String> getRandomIndianAddress() {
        Map<String, String> cityPincodes = new HashMap<>();
        cityPincodes.put("Delhi", "110001");
        cityPincodes.put("Mumbai", "400001");
        cityPincodes.put("Bangalore", "560001");
        cityPincodes.put("Chennai", "600001");
        cityPincodes.put("Kolkata", "700001");
        cityPincodes.put("Pune", "411001");
        cityPincodes.put("Hyderabad", "500001");
        cityPincodes.put("Faridabad", "121001");

        // Get all city names as an array
        Object[] cities = cityPincodes.keySet().toArray();

        // Pick a random city
        Random random = new Random();
        String randomCity = (String) cities[random.nextInt(cities.length)];
        String matchingPincode = cityPincodes.get(randomCity);

        // Return as a mini-map
        Map<String, String> result = new HashMap<>();
        result.put("city", randomCity);
        result.put("pincode", matchingPincode);
        return result;
    }



}
