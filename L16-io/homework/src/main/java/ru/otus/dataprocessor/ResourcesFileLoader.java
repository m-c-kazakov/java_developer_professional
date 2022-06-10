package ru.otus.dataprocessor;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.otus.model.Measurement;

import javax.json.*;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResourcesFileLoader implements Loader {

    String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    private static void navigateTree(JsonValue tree, List<Measurement> measurements) {
        switch (tree.getValueType()) {
            case OBJECT:
                System.out.println("OBJECT");
                var jsonObject = (JsonObject) tree;
                Measurement measurement = new Measurement(jsonObject.getString("name"), Double.parseDouble(jsonObject.get("value").toString()));
                measurements.add(measurement);
//                for (Map.Entry<String, JsonValue> entry : jsonObject.entrySet()) {
//                    navigateTree(jsonObject.get(entry.getKey()), measurements);
//
//                }
                break;
            case ARRAY:
                System.out.println("ARRAY");
                JsonArray array = (JsonArray) tree;
                for (JsonValue val : array) {
                    navigateTree(val, measurements);
                }
                break;
            case STRING:
                JsonString st = (JsonString) tree;
                System.out.println("STRING " + st.getString());
                break;
            case NUMBER:
                JsonNumber num = (JsonNumber) tree;
                System.out.println("NUMBER " + num.toString());
                break;
            case TRUE:
            case FALSE:
            case NULL:
                System.out.println(tree.getValueType().toString());
                break;
        }
    }

    @Override
    public List<Measurement> load() {
        // TODO читает файл, парсит и возвращает результат
        try (var jsonReader = Json.createReader(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName))) {

            JsonStructure jsonFromTheFile = jsonReader.read();
            List<Measurement> measurements = new ArrayList<>();
            navigateTree(jsonFromTheFile, measurements);
            return measurements;
        }
    }
}
