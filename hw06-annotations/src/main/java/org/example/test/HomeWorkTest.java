package org.example.test;

import org.example.annotations.After;
import org.example.annotations.Before;
import org.example.annotations.Test;

public class HomeWorkTest {

    @Before
    public void beforeEach() {
        System.out.println("Подготовка к запуску теста");
    }

    @Test
    public void test1() {
        System.out.println("Запуск теста 1");
    }

    @Test
    public void test$Exception() {
        System.out.println("Запуск теста c ошибкой");
        throw new RuntimeException();
    }

    @After
    public void afterEach() {
        System.out.println("Запуск метода после завершения теста");
    }
}
