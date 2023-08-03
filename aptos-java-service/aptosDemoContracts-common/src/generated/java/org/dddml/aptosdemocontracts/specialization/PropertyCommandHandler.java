package org.dddml.aptosdemocontracts.specialization;

/**
 * Created by Yang on 2016/7/25.
 */
public interface PropertyCommandHandler<TContent, TState> {

    void execute(PropertyCommand<TContent, TState> command);

}
