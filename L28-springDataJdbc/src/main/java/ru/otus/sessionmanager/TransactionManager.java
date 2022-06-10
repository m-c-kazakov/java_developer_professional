package ru.otus.sessionmanager;

public interface TransactionManager {

    void doInTransaction(TransactionAction action);
}
