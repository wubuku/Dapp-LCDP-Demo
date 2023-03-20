package org.dddml.suidemocontracts.specialization;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

public class ReflectUtils {
    private ReflectUtils() {
    }

    public static Object getPropertyValue(String propertyName, Object obj) {
        Object val = null;
        String pn = toPascalCase(propertyName);
        try {
            val = obj.getClass().getMethod("get" + pn, new Class[0]).invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);// e.printStackTrace();
        }
        return val;
    }

    public static Class<?> getPropertyType(String propertyName, Object obj) {
        BeanInfo info = null;
        try {
            info = Introspector.getBeanInfo(obj.getClass());
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);//e.printStackTrace();
        }
        Optional<PropertyDescriptor> pd =
                Arrays.stream(info.getPropertyDescriptors())
                        .filter(p -> Objects.equals(p.getName().toLowerCase(), propertyName.toLowerCase()))
                        .findFirst();
        if (pd.isPresent()) {
            return pd.get().getPropertyType();
        }
        throw new RuntimeException(String.format("Property %1$s is NOT present.", propertyName));
    }

    public static boolean isPropertyPresent(String propertyName, Object obj) {
        BeanInfo info = null;
        try {
            info = Introspector.getBeanInfo(obj.getClass());
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);//e.printStackTrace();
        }
        Optional<PropertyDescriptor> pd =
                Arrays.stream(info.getPropertyDescriptors())
                        .filter(p -> Objects.equals(p.getName().toLowerCase(), propertyName.toLowerCase()))
                        .findFirst();
        return pd.isPresent();
    }

    public static boolean trySetPropertyValue(String propertyName, Object obj, Object value,
                                              BiFunction<Object, Class<?>, Object> convert) {
        boolean throwOnError = false;
        return trySetPropertyValue(propertyName, obj, value, convert, throwOnError);
    }

    public static void setPropertyValue(String propertyName, Object obj, Object value) {
        boolean throwOnError = true;
        BiFunction<Object, Class<?>, Object> convert = (o, c) -> {
            //if (throwOnError) {
            //    throw new UnsupportedOperationException("Convert function is NOT definded.");
            //} else
            return o;
        };
        trySetPropertyValue(propertyName, obj, value, convert, throwOnError);
    }

    private static boolean trySetPropertyValue(String propertyName, Object obj, Object value,
                                               BiFunction<Object, Class<?>, Object> convert, boolean throwOnError) {
        String pascalName = toPascalCase(propertyName);
        Class propCls = value == null ? Object.class : value.getClass();
        Object targetPropVal = value;
        Method m = null;
        try {
            m = obj.getClass().getMethod("set" + pascalName, propCls);
        } catch (NoSuchMethodException e) {
            if (throwOnError) {
                throw new RuntimeException(e);// e.printStackTrace();
            } else {
                try {
                    Class<?> pt = getPropertyType(propertyName, obj);
                    m = obj.getClass().getMethod("set" + pascalName, pt);
                    targetPropVal = convert.apply(value, pt);
                } catch (NoSuchMethodException e1) {
                    if (throwOnError) {
                        throw new RuntimeException(e);// e.printStackTrace();
                    } else {
                        return false;
                    }
                } catch (Exception ex) {
                    //ex.printStackTrace();
                    //System.out.println("Convert value error. Value: " + value
                    //        + "(" + (value != null ?  value.getClass() : "") + "), set " + propertyName + " of object: " + obj);
                    if (throwOnError) {
                        throw new RuntimeException(e);
                    } // esle {}
                }
            }
        }
        if (m == null && throwOnError) {
            throw new RuntimeException(new NoSuchMethodException("set" + pascalName));
        }
        try {
            //todo m is null???
            if (m == null) {
                return false;
            }
            m.invoke(obj, targetPropVal);
            return true;
        } catch (IllegalAccessException | InvocationTargetException e) {
            if (throwOnError) {
                throw new RuntimeException(e);// e.printStackTrace();
            }
        } catch (IllegalArgumentException | ClassCastException e) {// Runtime Exception.
            //e.printStackTrace();
            if (throwOnError) {
                //System.out.println("Set value error. Value: " + value
                //        + "(" + (value != null ?  value.getClass() : "") + "), set " + propertyName + " of object: " + obj);
                throw e;
            }
        }
        return false;
    }

    private static String toPascalCase(String propertyName) {
        String pn = propertyName;
        if (Character.isLowerCase(propertyName.charAt(0))) {
            pn = Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
        }
        return pn;
    }


    public static Object invokeStaticMethod(String className, String methodName, Class<?>[] parameterTypes, Object[] args) {
        try {
            Class clazz = Class.forName(className);
            Method method = clazz.getDeclaredMethod(
                    methodName,
                    parameterTypes
            );
            return method.invoke(null, args);
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static Method assertStaticMethodIfClassExists(String className, String methodName, Class<?>[] parameterTypes,
                                                         String[] parameterNames) {
        return assertStaticMethod(className, methodName, parameterTypes, parameterNames, false);
    }

    private static Method assertStaticMethod(String className, String methodName, Class<?>[] parameterTypes,
                                             String[] parameterNames, boolean throwOnClassNotFound) {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            if (throwOnClassNotFound) {
                throw new RuntimeException(e);
            }
        }
        try {
            if (clazz != null) {
                return assertStaticMethod(clazz, methodName, parameterTypes, parameterNames);
            } else {
                return null;
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Method assertStaticMethod(Class clazz, String methodName, Class<?>[] parameterTypes,
                                             String[] parameterNames) throws NoSuchMethodException {
        Method method = clazz.getDeclaredMethod(
                methodName,
                parameterTypes
        );
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new RuntimeException(String.format("Method: %1$s.%2$s, MUST be static.",
                    clazz.getName(), methodName));
        }
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                if ("_".equals(parameterNames[i])) {
                    continue;
                }
                if (i < method.getParameters().length) {
                    if (!parameterNames[i].equals(method.getParameters()[i].getName())) {
                        throw new RuntimeException(String.format("Method: %1$s.%2$s, parameters[%3$s] MUST be named as \"%4$s\".",
                                clazz.getName(), methodName, i, parameterNames[i]));
                    }
                }
            }
        }
        return method;
    }

}
