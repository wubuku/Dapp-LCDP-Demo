package org.dddml.roochdemocontracts.restful.resource;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by yangjiefeng on 2018/8/22.
 */
public class SecurityContextUtil {


    public static String getRequesterId() {
        if (SecurityContextHolder.getContext() != null
                && SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return null;
    }

}
