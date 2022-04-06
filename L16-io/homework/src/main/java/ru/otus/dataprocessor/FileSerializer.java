package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileSerializer implements Serializer {
    private final ObjectMapper mapper = new ObjectMapper();

    String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // TODO формирует результирующий json и сохраняет его в файл
        try (var bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            StringBuilder stringBuilder = new StringBuilder().append("{");
            data.entrySet().stream()
                    .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                    .forEach(entry -> {
                        stringBuilder.append("\"");
                        stringBuilder.append(entry.getKey());
                        stringBuilder.append("\"");
                        stringBuilder.append(":");
                        stringBuilder.append(entry.getValue());
                        stringBuilder.append(",");
                    });
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "}");

            String value = stringBuilder.toString();

            bufferedWriter.write(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
