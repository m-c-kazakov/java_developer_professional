package org.logger;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class IoC {

    static TestLogging createTestLogging() {
        InvocationHandler handler = new InvocationHandlerImpl(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(IoC.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    record InvocationHandlerImpl(TestLogging myClass) implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            boolean isEqualsInterface = Arrays.stream(myClass.getClass().getInterfaces()).anyMatch(aClass -> aClass.equals(method.getDeclaringClass()));
            boolean isEqualsMethod = Arrays.stream(myClass.getClass().getMethods())
                    .filter(m -> m.isAnnotationPresent(Log.class))
                    .filter(m -> m.getName().equals(method.getName()))
                    .anyMatch(m -> Arrays.equals(m.getParameterTypes(), method.getParameterTypes()));
            if (isEqualsInterface && isEqualsMethod) {
                System.out.println("Method args: " + Arrays.stream(args).map(String::valueOf).collect(Collectors.joining("; ")));
            }
            return method.invoke(myClass, args);
        }
    }
}
