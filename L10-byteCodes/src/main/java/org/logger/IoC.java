package org.logger;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class IoC {

    private static List<Method> logMethods;

    static TestLogging createTestLogging(TestLogging testLoggingImpl) {
        logMethods = Arrays.stream(testLoggingImpl.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Log.class)).collect(Collectors.toList());
        InvocationHandler handler = new InvocationHandlerImpl(testLoggingImpl);
        return (TestLogging) Proxy.newProxyInstance(IoC.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    record InvocationHandlerImpl(TestLogging myClass) implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            boolean isEqualsMethod = logMethods.stream()
                    .filter(m -> m.getName().equals(method.getName()))
                    .anyMatch(m -> Arrays.equals(m.getParameterTypes(), method.getParameterTypes()));
            if (isEqualsMethod) {
                System.out.println("Method args: " + Arrays.stream(args).map(String::valueOf).collect(Collectors.joining("; ")));
            }
            return method.invoke(myClass, args);
        }
    }
}
