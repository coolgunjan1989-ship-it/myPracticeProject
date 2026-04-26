package RestAssured;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.AuthorizationValue;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This utility connects directly to a live Swagger URL to extract
 * specific test data dynamically, ensuring the code is always in sync with the UI.
 */
public class SwaggerExampleExtractor {

    public static void main(String[] args) {
        // --- 1. CONFIGURATION ---
        // Ensure this is the RAW YAML/JSON URL (ends in .yaml or .json)
        // Example for SwaggerHub: https://api.swaggerhub.com/apis/OWNER/API_NAME/1.0.0/swagger.yaml
        String swaggerUrl = "https://app.swaggerhub.com/apiproxy/registry/coolgunjan1989organi/my-local-json-server-api/1.0.0?resolved=true&flatten=true&pretty=true";

        // --- PRIVATE vs PUBLIC TOGGLE ---
        // If you changed your API to PUBLIC in SwaggerHub settings, leave this empty ("").
        // If it is still PRIVATE, you must paste your API Key here.
        String swaggerHubApiKey = "affa7a85-a934-4287-976e-226ad47079c3";

        // --- 2. PARSING LOGIC WITH AUTHENTICATION ---
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        options.setResolveFully(true);

        List<AuthorizationValue> auths = null;
        if (swaggerHubApiKey != null && !swaggerHubApiKey.isEmpty()) {
            // SwaggerHub requires the key in the "Authorization" header
            AuthorizationValue apiKeyAuth = new AuthorizationValue("Authorization", swaggerHubApiKey, "header");
            auths = Collections.singletonList(apiKeyAuth);
            System.out.println("Attempting connection using provided API Key...");
        } else {
            System.out.println("Attempting connection to PUBLIC API...");
        }

        // Using readLocation instead of read to get detailed error messages
        SwaggerParseResult result = new OpenAPIV3Parser().readLocation(swaggerUrl, auths, options);
        OpenAPI openAPI = result.getOpenAPI();

        if (openAPI == null) {
            System.err.println("--- CONNECTION ERROR ---");
            System.err.println("Target URL: " + swaggerUrl);
            System.err.println("Messages from Parser: " + result.getMessages());
            System.err.println("\nPOSSIBLE FIXES:");
            System.err.println("1. Verify the URL is 'Raw' (should look like text/YAML, not the UI webpage).");
            System.err.println("2. If the API is still Private, ensure you provided a valid API Key.");
            System.err.println("3. If you just changed to Public, wait 30 seconds for SwaggerHub cache to update.");
            return;
        }

        System.out.println("--- Successfully Connected to LIVE Swagger: " + openAPI.getInfo().getTitle() + " ---");

        // --- 3. DATA EXTRACTION ---
        try {
            // Extract Request Example: PositiveTestCase for POST /users
            Map<String, Example> requestExamples = openAPI.getPaths().get("/users")
                    .getPost()
                    .getRequestBody()
                    .getContent()
                    .get("application/json")
                    .getExamples();

            Object positiveRequest = requestExamples.get("PositiveTestCase").getValue();
            System.out.println("\n[LIVE EXPECTED REQUEST]:\n" + positiveRequest);

            // Extract Response Example: SuccessResponse for 201 status
            Map<String, Example> responseExamples = openAPI.getPaths().get("/users")
                    .getPost()
                    .getResponses()
                    .get("201")
                    .getContent()
                    .get("application/json")
                    .getExamples();

            Object expectedResponse = responseExamples.get("SuccessResponse").getValue();
            System.out.println("\n[LIVE EXPECTED RESPONSE - 201]:\n" + expectedResponse);

        } catch (NullPointerException e) {
            System.err.println("ERROR: Path '/users' or specific examples not found in this version of the Swagger.");
        }
    }
}