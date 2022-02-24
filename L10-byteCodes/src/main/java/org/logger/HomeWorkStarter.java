package org.logger;

public class HomeWorkStarter {
    public static void main(String[] args) {
        TestLogging testLogging = IoC.createTestLogging(new TestLoggingImpl());
        testLogging.calculation(1);
        testLogging.calculation(2,3);
        testLogging.calculation(4,5,6);

    }
}
