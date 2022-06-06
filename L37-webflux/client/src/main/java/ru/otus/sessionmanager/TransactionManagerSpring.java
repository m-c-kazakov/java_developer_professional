package ru.otus.sessionmanager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionManagerSpring implements TransactionManager {


    @Override
    @Transactional
    public void doInTransaction(TransactionAction action) {
        action.run();
    }
}
