package org.example.test;

import org.example.annotations.After;
import org.example.annotations.Before;
import org.example.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

public class TestStarter {

    public void startTests(List<Class<?>> classes)  {
        List<TestResult> results = new ArrayList<>();

        for (Class<?> clazz : classes) {
            TestResult testResult = new TestResult(clazz.getCanonicalName());
            results.add(testResult);

            List<Method> beforeEach = new ArrayList<>();
            List<Method> afterEach = new ArrayList<>();
            List<Method> tests = new ArrayList<>();

            getTestMethods(clazz, beforeEach, afterEach, tests);
            executeTestMethods(clazz, testResult, beforeEach, afterEach, tests);
        }

        results.forEach(TestResult::printResult);

    }

    private void executeTestMethods(Class<?> clazz, TestResult testResult, List<Method> beforeEach, List<Method> afterEach, List<Method> tests) {
        for (Method method : tests) {
            try {
                Object o = clazz.getConstructor().newInstance();

                for (Method beforeMethod : beforeEach) {
                    beforeMethod.invoke(o);
                }
                method.invoke(o);
                for (Method afterMethod : afterEach) {
                    afterMethod.invoke(o);
                }
                testResult.success();

            } catch (Exception e) {
                testResult.exception();
            }
        }
    }

    private void getTestMethods(Class<?> clazz, List<Method> beforeEach, List<Method> afterEach, List<Method> tests) {
        Arrays.stream(clazz.getMethods())
                .forEach(method -> {
                    Arrays.stream(method.getAnnotations())
                            .forEach(annotation -> {
                                String canonicalAnnotationName = annotation.annotationType().getTypeName();
                                if (Test.class.getCanonicalName().equals(canonicalAnnotationName)) {
                                    tests.add(method);
                                } else if (Before.class.getCanonicalName().equals(canonicalAnnotationName)) {
                                    beforeEach.add(method);
                                } else if (After.class.getCanonicalName().equals(canonicalAnnotationName)) {
                                    afterEach.add(method);
                                }
                            });
                });
    }


    public static void main(String[] args) {
        TestStarter testStarter = new TestStarter();
        testStarter.startTests(List.of(HomeWorkTest.class));
    }


    public static class TestResult {
        private final String className;
        private Integer successResult = 0;
        private Integer exceptionResult = 0;

        public TestResult(String className) {
            this.className = className;
        }

        public void success() {
            successResult++;
        }

        public void exception() {
            exceptionResult++;
        }

        public void printResult() {
            System.out.printf("RESULT for class=%s: successResult=%d; exceptionResult=%d; number of tests =%d; %n", className, successResult, exceptionResult, exceptionResult+successResult);
        }
    }
}
