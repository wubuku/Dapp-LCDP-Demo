package org.dddml.suidemocontracts.specialization;

public interface IdGenerator<TId, TCommand, TState> {

    /* Generate Id form command info. */
    TId generateId(TCommand command);

    /* Get next (Arbitrary)Id. */
    TId getNextId();

    /* Would the Id generated frorm command be equals the state? */
    boolean equals(TCommand command, TState state);

    /* Is Arbitrary Id enabled. */
    boolean isArbitraryIdEnabled();


}