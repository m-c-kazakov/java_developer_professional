package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ObjectForMessage {
    private final List<String> data = new ArrayList<>();

    public List<String> getData() {
        return new ArrayList<>(data);
    }

    public void setData(List<String> data) {
        this.data.addAll(data);
    }
}
