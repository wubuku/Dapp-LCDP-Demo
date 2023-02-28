package org.dddml.suidemocontracts.specialization;

/**
 * Created by Yang on 2016/7/19.
 */
public class DomainError extends RuntimeException {

    private String name;

    public String getName() {
        return name;
    }

    public DomainError() {
    }

    public DomainError(String message) {
        super(message);
    }

    public DomainError(String format, Object... args) {
        super(String.format(format, args));
    }

    public DomainError(String message, Throwable cause) {
        super(message, cause);
    }

    public static DomainError named(String name, String format, Object... args) {
        String message = "[" + name + "] " + String.format(format, args);
        return named(name, message);
    }

    public static DomainError named(String name, String message) {
        DomainError error = new DomainError(message);
        error.name = name;
        return error;
    }

}


