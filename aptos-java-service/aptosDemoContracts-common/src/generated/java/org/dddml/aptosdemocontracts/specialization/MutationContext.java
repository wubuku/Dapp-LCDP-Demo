package org.dddml.aptosdemocontracts.specialization;

import java.util.function.Function;

public interface MutationContext<T, TM> {

    Event getEvent();

    TM createMutableState(T state);

    static <T, TM> MutationContext forEvent(Event e, Function<T, TM> mutableStateFactory) {
        return new MutationContext<T, TM>() {
            @Override
            public Event getEvent() {
                return e;
            }

            @Override
            public TM createMutableState(T state) {
                return mutableStateFactory.apply(state);
            }
        };
    }

}
