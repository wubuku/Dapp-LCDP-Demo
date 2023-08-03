package org.dddml.aptosdemocontracts.specialization;

import java.util.Set;

/**
 * Created by Yang on 2016/12/27.
 */
public interface ReadOnlyProxyGenerator {
    Object createProxy(Object target, Class[] interfaces, String readOnlyGetterName, Set<String> readOnlyPropertyPascalCaseNames);

    Object getTarget(Object proxy);
}
