package org.logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

record TestLoggingInvocationHandler(TestLogging myClass, Set<Method> logMethods) implements InvocationHandler {


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        boolean isEqualsMethod = logMethods.stream()
                .filter(m -> m.getName().equals(method.getName()))
                .anyMatch(m -> Arrays.equals(m.getParameterTypes(), method.getParameterTypes()));
        if (isEqualsMethod) {
            System.out.println("Method args: " + Optional.ofNullable(args)
                    .map(Arrays::stream).stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("; ")));
        }
        return method.invoke(myClass, args);
    }
}