import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;

public class task3 {
    private static Map<Integer, String> parseValues(String jsonValues) {
        Map<Integer, String> result = new HashMap<>();
        
        int currentPosition = jsonValues.indexOf("\"id\"");
        
        while (currentPosition != -1) {
            int colonInd = jsonValues.indexOf(":", currentPosition);
            int commaInd = jsonValues.indexOf(",", colonInd);
            
            int id = Integer.parseInt(
                jsonValues.substring(colonInd + 1, commaInd).trim()
            );
            
            int startInd = jsonValues.indexOf("\"value\"", commaInd);
            int secondColonInd = jsonValues.indexOf(":", startInd);
            int firstMark = jsonValues.indexOf("\"", secondColonInd);
            int secondMark = jsonValues.indexOf("\"", firstMark + 1);
            
            String value = jsonValues.substring(firstMark + 1, secondMark);
            
            result.put(id, value);
            
            currentPosition = jsonValues.indexOf("\"id\"", secondMark + 1);
        }
        
        return result;
    }

private static String updateJson(String json, Map<Integer, String> valuesMap) {
    StringBuilder result = new StringBuilder(json);
    
    int currentPosition = result.indexOf("\"id\"");
    
    while (currentPosition != -1) {
	int colonInd = result.indexOf(":", currentPosition);
	int commaInd = result.indexOf(",", colonInd);
	
	int id = Integer.parseInt(result.substring(colonInd + 1, commaInd).trim());
	String value = valuesMap.get(id);
	
	if (value == null) {
		currentPosition = result.indexOf("\"id\"", commaInd);
		continue;
	}
	
	int valueKeyInd = result.indexOf("\"value\"", commaInd + 1);
	int secondColonInd = result.indexOf(":", valueKeyInd);
	int quoteMarkInd = result.indexOf("\"", secondColonInd);
	result.insert(quoteMarkInd + 1, value);
	currentPosition = result.indexOf("\"id\"", quoteMarkInd + value.length() + 1);
    }
	return result.toString();
}

    public static void main(String[] args) {

        Path pathValues = Paths.get(args[0]);
	Path pathTests = Paths.get(args[1]);
	Path pathReport = Paths.get(args[2]);

        String tests = null, values = null;

        try {
            tests = Files.lines(pathTests, StandardCharsets.UTF_8).collect(Collectors.joining()); // Уточняю кодировку для JSON-ов
            values = Files.lines(pathValues, StandardCharsets.UTF_8).collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }

        Map<Integer, String> idsValues = parseValues(values);
        String result = updateJson(tests, idsValues);

        try {
            Files.write(pathReport,
                    result.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }
}