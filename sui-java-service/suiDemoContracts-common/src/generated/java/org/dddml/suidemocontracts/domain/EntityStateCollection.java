package org.dddml.suidemocontracts.domain;

import java.util.Collection;

public interface EntityStateCollection<TId, TState> extends Collection<TState> {

    /**
     * Get entity state.
     * @param entityId entity Id.
     * @return If not found, return null.
     */
    TState get(TId entityId);

    boolean isLazy();

    boolean isAllLoaded();

    Collection<TState> getLoadedStates();

    interface ModifiableEntityStateCollection<TId, TState> extends EntityStateCollection<TId, TState> {
        Collection<TState> getRemovedStates();

        /**
         * Get existed entity state or add new entity state.
         * @param entityId entity Id.
         * @return entity state which is not null.
         */
        TState getOrAdd(TId entityId);
    }

}


