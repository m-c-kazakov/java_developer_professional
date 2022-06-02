package ru.otus.listener.homework;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HistoryListener implements Listener, HistoryReader {
    Map<Long, Message> map = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        map.put(msg.getId(), msg.toBuilder().build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(map.get(id));
    }
}
