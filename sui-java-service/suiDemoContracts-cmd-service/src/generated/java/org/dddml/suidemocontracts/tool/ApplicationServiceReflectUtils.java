package org.dddml.suidemocontracts.tool;

import org.dddml.suidemocontracts.domain.meta.M.BoundedContextMetadata;
import org.dddml.suidemocontracts.specialization.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ApplicationServiceReflectUtils {

    private ApplicationServiceReflectUtils() {
    }

    public static Object invokeApplicationServiceGetMethod(String entityName, Object id) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        String aggregateName = BoundedContextMetadata.TYPE_NAME_TO_AGGREGATE_NAME_MAP.get(entityName);
        Object appSvr = getApplicationService(aggregateName);
        Class appSrvClass = appSvr.getClass();
        Method m = null;
        m = appSrvClass.getMethod("get", id.getClass());
        return m.invoke(appSvr, id);
    }

    public static void invokeApplicationServiceInitializeMethod(String entityName, Object e) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        String aggregateName = BoundedContextMetadata.TYPE_NAME_TO_AGGREGATE_NAME_MAP.get(entityName);
        Object appSvr = getApplicationService(aggregateName);
        Class appSrvClass = appSvr.getClass();
        Method m = null;
        Object arg = e;
        try {
            m = appSrvClass.getMethod("initialize", getApplicationServiceInitializeMethodParameterType(aggregateName, entityName));
            if (!m.getParameterTypes()[0].isAssignableFrom(arg.getClass())) {
                throw new NoSuchMethodException("No exact initialize() method.");
            }
        }catch (NoSuchMethodException | ClassNotFoundException exM){
            //exM.printStackTrace();
            m = appSrvClass.getMethod("when", getApplicationServiceCreateMethodParameterType(aggregateName, entityName));
            if (m.getParameters()[0].getClass().isAssignableFrom(e.getClass())) {
                Method convMethod = e.getClass().getMethod(String.format("toCreate%1$s", entityName));
                arg = convMethod.invoke(e);
            }
        }
        m.invoke(appSvr, arg);
    }

    private static Object getApplicationService(String aggregateName) {
        String appSrvName = aggregateName.substring(0, 1).toLowerCase() + aggregateName.substring(1) + "ApplicationService";
        Object appSrv = ApplicationContext.current.get(appSrvName);
        return appSrv;
    }

    private static Class getApplicationServiceInitializeMethodParameterType(String aggregateName, String typeName) throws ClassNotFoundException {
        String paramTypeName = String.format("%1$s.domain.%2$s.%3$sStateEvent$%4$sStateCreated",
                getBoundedContextPackageName(), aggregateName.toLowerCase(), typeName, typeName);
        return Class.forName(paramTypeName);
    }

    private static Class getApplicationServiceCreateMethodParameterType(String aggregateName, String typeName) throws ClassNotFoundException {
        String paramTypeName = String.format("%1$s.domain.%2$s.%3$sCommand$Create%4$s",
                getBoundedContextPackageName(), aggregateName.toLowerCase(), typeName, typeName);
        return Class.forName(paramTypeName);
    }

    private static String getBoundedContextPackageName() {
        String[] thisClassNames = ApplicationServiceReflectUtils.class.getName().split("\\.");
        return Arrays.stream(thisClassNames).limit(thisClassNames.length - 2).reduce((x, y) -> x + "." + y).get();
    }

}
