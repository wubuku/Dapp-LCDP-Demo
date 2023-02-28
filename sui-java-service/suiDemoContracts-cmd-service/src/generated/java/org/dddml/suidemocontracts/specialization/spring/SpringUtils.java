package org.dddml.suidemocontracts.specialization.spring;

import org.springframework.aop.framework.Advised;

/**
 * Created by yangjiefeng on 2018/2/1.
 */
public class SpringUtils {

    public static Object getAopTarget(Object obj) {
        if (obj instanceof Advised) {
            Object target = null;
            try {
                target = ((Advised) obj).getTargetSource().getTarget();
            } catch (Exception e) {
                return null;//e.printStackTrace();
            }
            return target;
        }
        return null;//throw new IllegalArgumentException("The argument is NOT Advised object.");
    }

}
