package org.dddml.aptosdemocontracts.specialization;

public interface VerificationContext {

    Object getCommand();

    static VerificationContext forCommand(Object cmd) {
        return new VerificationContext() {
            @Override
            public Object getCommand() {
                return cmd;
            }
        };
    }
}
