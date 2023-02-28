package org.dddml.suidemocontracts.specialization;

import java.io.Serializable;

/**
 * Created by Yang on 2016/7/19.
 */
public interface EventStoreAggregateId {
    Serializable getId();

    class SimpleEventStoreAggregateId implements EventStoreAggregateId {

        private Serializable id;

        @Override
        public Serializable getId() {
            return this.id;
        }

        public SimpleEventStoreAggregateId(Serializable id) {
            this.id = id;
        }
    }
}
