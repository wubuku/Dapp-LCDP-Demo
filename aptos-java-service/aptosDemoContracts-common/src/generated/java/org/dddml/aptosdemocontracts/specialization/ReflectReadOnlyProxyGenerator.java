package org.dddml.aptosdemocontracts.specialization;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

public class ReflectReadOnlyProxyGenerator implements ReadOnlyProxyGenerator {

    @Override
    public Object createProxy(Object target, Class[] interfaces, String readOnlyGetterName, Set<String> readOnlyPropertyPascalCaseNames) {
        setReadOnly(target, readOnlyGetterName);
        ReadOnlyInvocationHandler ih = new ReadOnlyInvocationHandler(target, readOnlyGetterName, readOnlyPropertyPascalCaseNames);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), interfaces, ih);
    }

    private void setReadOnly(Object target, String readOnlyGetterName) {
        String readOnlySetterName = null;
        if (readOnlyGetterName.startsWith("get")) {
            readOnlySetterName = "set" + readOnlyGetterName.substring(3);
        } else if (readOnlyGetterName.startsWith("is")) {
            readOnlySetterName = "set" + readOnlyGetterName.substring(2);
        }
        if (readOnlySetterName != null) {
            Method rdSetter = getDeclaredMethod(target, readOnlySetterName, Boolean.class);
            if (rdSetter != null) {
                try {
                    rdSetter.invoke(target, Boolean.TRUE);
                } catch (IllegalAccessException e) {
                    //e.printStackTrace();
                } catch (InvocationTargetException e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    private static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (NoSuchMethodException e) {
                //e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Object getTarget(Object proxy) {
        if (proxy == null) {
            throw new NullPointerException();
        }
        if (Proxy.isProxyClass(proxy.getClass())) {
            ReadOnlyInvocationHandler ih = (ReadOnlyInvocationHandler) Proxy.getInvocationHandler(proxy);
            return ih.getTarget();
        }
        return proxy;
    }

    static class ReadOnlyInvocationHandler implements InvocationHandler {

        private Set<String> readOnlyPropertyPascalCaseNames;

        private String readOnlyGetterName;

        private Object target;

        Object getTarget() {
            return this.target;
        }

        ReadOnlyInvocationHandler(Object target, String readOnlyGetterName, Set<String> readOnlyPropertyPascalCaseNames) {
            this.target = target;
            this.readOnlyGetterName = readOnlyGetterName;
            this.readOnlyPropertyPascalCaseNames = readOnlyPropertyPascalCaseNames;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().startsWith("set")) {
                String pn = method.getName().substring(3);
                if (readOnlyPropertyPascalCaseNames.contains(pn)) {
                    boolean readOnly = isReadOnly();
                    if (readOnly) {
                        throw new UnsupportedOperationException(String.format("%1$s.%2$s is readOnly.", method.getDeclaringClass().getName(), pn));
                    }
                }
            }
            return method.invoke(target, args);
        }

        private boolean isReadOnly() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            boolean readOnly = false;
            Method rdGetter = getDeclaredMethod(target, this.readOnlyGetterName);
            if (rdGetter != null) {
                readOnly = (Boolean) rdGetter.invoke(target);
            }
            return readOnly;
        }
    }
}
