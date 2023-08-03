package org.dddml.aptosdemocontracts.specialization;

public class DomainErrorFailure {

    private String name;
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DomainErrorFailure() {
    }

    public DomainErrorFailure(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public static DomainErrorFailure ofDomainErrorOrThrow(Exception e) {
        RuntimeException runtimeException = DomainErrorUtils.convertException(e);
        if (runtimeException instanceof DomainError) {
            DomainError de = (DomainError) runtimeException;
            DomainErrorFailure domainErrorFailure = new DomainErrorFailure();
            domainErrorFailure.setName(de.getName());
            domainErrorFailure.setMessage(de.getMessage());
            return domainErrorFailure;
        } else {
            throw runtimeException;
        }
    }

}
