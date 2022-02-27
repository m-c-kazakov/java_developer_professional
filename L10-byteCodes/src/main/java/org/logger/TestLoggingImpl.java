package org.logger;

public class TestLoggingImpl implements TestLogging {

    @Log
    @Override
    public void calculation() {
        System.out.println("Должен быть залогирован 0");
    }

    @Override
    public void calculation(int param) {
        System.out.println("Не должен логироваться 1");
    }

    @Override
    public void calculation(int param, int param2) {
        System.out.println("Не должен логироваться 2");
    }

    @Override
    public void calculation(int param, int param2, int param3) {
        System.out.println("Не должен логироваться 3");
    }
}
