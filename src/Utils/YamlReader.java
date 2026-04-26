package Utils;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.Map;

/**
 * A simplified YAML utility to read properties from a configuration file.
 */
public class YamlReader {

    private Map<String, Object> configData;

    /**
     * Constructor that loads a specific YAML file.
     * @param filePath Path to the .yml or .yaml file
     */
    public YamlReader(String filePath) {
        try (InputStream inputStream = new FileInputStream(new File(filePath))) {
            Yaml yaml = new Yaml();
            this.configData = yaml.load(inputStream);
        } catch (Exception e) {
            System.err.println("Error loading YAML file: " + e.getMessage());
        }
    }

    /**
     * Gets a value from the "Params" section (common in your project structure).
     * @param key The key to look for
     * @return The value as a String, or null if not found
     */
    public String getParamValue(String key) {
        if (configData == null || !configData.containsKey("Params")) {
            return null;
        }

        Map<String, Object> params = (Map<String, Object>) configData.get("Params");
        Object value = params.get(key);

        return value != null ? value.toString() : null;
    }

    /**
     * Standard usage example:
     * YamlReader reader = new YamlReader("src/test/resources/config/env.yml");
     * String browser = reader.getParamValue("browserName");
     */
}