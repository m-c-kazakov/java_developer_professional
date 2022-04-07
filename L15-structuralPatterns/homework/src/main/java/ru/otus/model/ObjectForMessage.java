package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ObjectForMessage {
    private List<String> data;

    public ObjectForMessage(List<String> data) {
        this.data = new ArrayList<>(data);
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = new ArrayList<>(data);
    }
}
