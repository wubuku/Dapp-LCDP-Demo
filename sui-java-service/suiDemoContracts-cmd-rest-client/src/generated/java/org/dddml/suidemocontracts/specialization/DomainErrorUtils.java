package org.dddml.suidemocontracts.specialization;

import java.util.UUID;

public class DomainErrorUtils {

    /**
     * 将可以认为是 Domain Error 的异常转换为 DomainError，
     * 其他的转换为 RuntimeException。
     * @param e
     * @return
     */
    public static RuntimeException convertException(Exception rawException) {
        Exception e = tryGetDomainError(rawException);
        if (shouldBeRegardedAsDomainError(e)) {
            DomainError domainError = e instanceof DomainError
                    ? (DomainError) e
                    : DomainError.named("[" + e.getClass().getName() + "]", e.getMessage());
            //onDomainError.accept(domainError);
            return domainError;//throw domainError;
        } else {
            String msg = "[" + UUID.randomUUID().toString() + "] Exception caught.";
            RuntimeException runtimeException = new RuntimeException(msg, e);
            //onOtherException.accept(runtimeException);
            return runtimeException;//throw runtimeException;
        }
    }

    /**
     * 判断一个异常是否可以认为是 Domain Error。
     * @param e Exception
     * @return 如果可以认为是 Domain Error，返回 true，否则返回 false。
     */
    private static boolean shouldBeRegardedAsDomainError(Exception e) {
        if (e instanceof DomainError) {
            return true;
        }
        final String[][] exceptionAndStackTraceClassNamesArray = new String[][]{
                new String[]{DomainError.class.getName()},
                // todo: 在 Domain 层抛出的 java.lang 包的异常，算作 Domain Error？
                new String[]{"java.lang.",
                        "org.dddml.suidemocontracts.domain."},
                // 动态代理反射的异常都算领域错误吗？也许动态代理的异常应该“去包装”？
                //new String[]{"java.lang.reflect.UndeclaredThrowableException",
                //        "com.sun.proxy.", "org.dddml.suidemocontracts.domain."}
        };
        boolean sbde = false;
        for (int i = 0; i < exceptionAndStackTraceClassNamesArray.length; i++) {
            String[] exceptionAndStackTraceClassNames = exceptionAndStackTraceClassNamesArray[i];
            if (e.getClass().getName().startsWith(exceptionAndStackTraceClassNames[0])) {
                if (exceptionAndStackTraceClassNames.length == 1) {
                    // 只需要匹配异常名称，不匹配 stack track 元素
                    sbde = true;
                    break;
                }
                if (e.getStackTrace().length >= exceptionAndStackTraceClassNames.length - 1) {
                    for (int j = 1; j < exceptionAndStackTraceClassNames.length; j++) {
                        if (!e.getStackTrace()[j - 1].getClassName().startsWith(exceptionAndStackTraceClassNames[j])) {
                            break;
                        }
                        if (j == exceptionAndStackTraceClassNames.length - 1) {
                            // 已经把需要匹配 stack trace 元素都匹配完了
                            sbde = true;
                        }
                    }
                }
            }
            if (sbde) {
                break;
            }
        }
        return sbde;
    }

    private static Exception tryGetDomainError(Exception e) {
        if (e instanceof java.lang.reflect.UndeclaredThrowableException){
            java.lang.reflect.UndeclaredThrowableException undeclaredThrowableException =
                    (java.lang.reflect.UndeclaredThrowableException)e;
            Throwable undeclaredThrowable = undeclaredThrowableException.getUndeclaredThrowable();
            if (undeclaredThrowable instanceof java.lang.reflect.InvocationTargetException) {
                java.lang.reflect.InvocationTargetException invocationTargetException =
                        (java.lang.reflect.InvocationTargetException) undeclaredThrowable;
                Throwable targetException = invocationTargetException.getTargetException();
                if (targetException != null && targetException instanceof DomainError) {
                    return (DomainError)targetException;
                }
            }
        }
        return e;
    }
}
