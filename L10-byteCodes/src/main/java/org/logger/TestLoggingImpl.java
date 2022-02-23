package org.logger;

public class TestLoggingImpl implements TestLogging {

    @Log
    @Override
    public void calculation(int param) {
        System.out.println("Должен быть залогирован");
    }

    @Override
    public void calculation(int param, int param2) {
        System.out.println("Не должен логироваться");
    }

    @Override
    public void calculation(int param, int param2, int param3) {
        System.out.println("Не должен логироваться");
    }
}
