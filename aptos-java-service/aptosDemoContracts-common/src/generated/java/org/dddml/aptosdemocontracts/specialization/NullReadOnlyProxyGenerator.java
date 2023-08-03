package org.dddml.aptosdemocontracts.specialization;

import java.util.Set;

/**
 * Created by Yang on 2016/12/27.
 */
public class NullReadOnlyProxyGenerator implements ReadOnlyProxyGenerator {
    @Override
    public Object createProxy(Object target, Class[] interfaces, String readOnlyGetterName, Set<String> readOnlyPropertyPascalCaseNames) {
        return target;
    }

    @Override
    public Object getTarget(Object proxy) {
        return proxy;
    }
}
