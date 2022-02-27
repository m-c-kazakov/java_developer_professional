package org.logger;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class IoC {

    static TestLogging createTestLogging(TestLogging testLoggingImpl) {
        Set<Method> logMethods = Arrays.stream(testLoggingImpl.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Log.class)).collect(Collectors.toSet());
        InvocationHandler handler = new TestLoggingInvocationHandler(testLoggingImpl, logMethods);
        TestLogging testLogging = (TestLogging) Proxy.newProxyInstance(IoC.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
        return testLogging;
    }
}
