package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.ApplicationContext;
import org.dddml.suidemocontracts.specialization.Saveable;

public abstract class AbstractOrderShipGroupStateCollection implements EntityStateCollection.ModifiableEntityStateCollection<Integer, OrderShipGroupState>, Saveable
{
    protected OrderShipGroupStateDao getOrderShipGroupStateDao()
    {
        return (OrderShipGroupStateDao)ApplicationContext.current.get("orderShipGroupStateDao");
    }

    private OrderV2State orderV2State;

    private Map<OrderV2OrderShipGroupId, OrderShipGroupState> loadedOrderShipGroupStates = new HashMap<OrderV2OrderShipGroupId, OrderShipGroupState>();

    private Map<OrderV2OrderShipGroupId, OrderShipGroupState> removedOrderShipGroupStates = new HashMap<OrderV2OrderShipGroupId, OrderShipGroupState>();

    protected Iterable<OrderShipGroupState> getLoadedOrderShipGroupStates() {
        return this.loadedOrderShipGroupStates.values();
    }

    private boolean forReapplying;

    public boolean getForReapplying() {
        return forReapplying;
    }

    public void setForReapplying(boolean forReapplying) {
        this.forReapplying = forReapplying;
    }

    private Boolean stateCollectionReadOnly;

    public Boolean getStateCollectionReadOnly() {
        if (this.orderV2State instanceof AbstractOrderV2State) {
            if (((AbstractOrderV2State)this.orderV2State).getStateReadOnly() != null && ((AbstractOrderV2State)this.orderV2State).getStateReadOnly()) 
            { return true; }
        }
        if (this.stateCollectionReadOnly == null) {
            return false;
        }
        return this.stateCollectionReadOnly;
    }

    public void setStateCollectionReadOnly(Boolean readOnly) {
        this.stateCollectionReadOnly = readOnly;
    }

    private boolean allLoaded;

    public boolean isAllLoaded() {
        return this.allLoaded;
    }

    protected Iterable<OrderShipGroupState> getInnerIterable() {
        if (!getForReapplying()) {
            //if (!getStateCollectionReadOnly()) {
            //    throw new UnsupportedOperationException("State collection is NOT ReadOnly.");
            //}
            assureAllLoaded();
            return this.loadedOrderShipGroupStates.values();
        } else {
            List<OrderShipGroupState> ss = new ArrayList<OrderShipGroupState>();
            for (OrderShipGroupState s : loadedOrderShipGroupStates.values()) {
                if (!(removedOrderShipGroupStates.containsKey(((OrderShipGroupState.SqlOrderShipGroupState)s).getOrderV2OrderShipGroupId()) && s.getDeleted())) {
                    ss.add(s);
                }
            }
            return ss;
        }
    }

    public boolean isLazy() {
        return true;
    }

    protected void assureAllLoaded() {
        if (!allLoaded) {
            Iterable<OrderShipGroupState> ss = getOrderShipGroupStateDao().findByOrderV2OrderId(orderV2State.getOrderId(), orderV2State);
            for (OrderShipGroupState s : ss) {
                if (!this.loadedOrderShipGroupStates.containsKey(((OrderShipGroupState.SqlOrderShipGroupState)s).getOrderV2OrderShipGroupId()) 
                    && !this.removedOrderShipGroupStates.containsKey(((OrderShipGroupState.SqlOrderShipGroupState)s).getOrderV2OrderShipGroupId())) {
                    this.loadedOrderShipGroupStates.put(((OrderShipGroupState.SqlOrderShipGroupState)s).getOrderV2OrderShipGroupId(), s);
                }
            }
            allLoaded = true;
        }
    }

    public AbstractOrderShipGroupStateCollection(OrderV2State outerState) {
        this.orderV2State = outerState;
        this.setForReapplying(((OrderV2State.SqlOrderV2State)outerState).getForReapplying());
    }

    @Override
    public Iterator<OrderShipGroupState> iterator() {
        return getInnerIterable().iterator();
    }

    public OrderShipGroupState get(Integer shipGroupSeqId) {
        return get(shipGroupSeqId, true, false);
    }

    public OrderShipGroupState getOrAdd(Integer shipGroupSeqId) {
        return get(shipGroupSeqId, false, false);
    }

    protected OrderShipGroupState get(Integer shipGroupSeqId, boolean nullAllowed, boolean forCreation) {
        OrderV2OrderShipGroupId globalId = new OrderV2OrderShipGroupId(orderV2State.getOrderId(), shipGroupSeqId);
        if (loadedOrderShipGroupStates.containsKey(globalId)) {
            OrderShipGroupState state = loadedOrderShipGroupStates.get(globalId);
            if (state instanceof AbstractOrderShipGroupState) {
                ((AbstractOrderShipGroupState)state).setStateReadOnly(getStateCollectionReadOnly());
            }
            return state;
        }
        boolean justNewIfNotLoaded = forCreation || getForReapplying();
        if (justNewIfNotLoaded) {
            if (getStateCollectionReadOnly()) {
                throw new UnsupportedOperationException("State collection is ReadOnly.");
            }
            OrderShipGroupState state = AbstractOrderShipGroupState.SimpleOrderShipGroupState.newForReapplying();
            ((OrderShipGroupState.SqlOrderShipGroupState)state).setOrderV2OrderShipGroupId(globalId);
            loadedOrderShipGroupStates.put(globalId, state);
            return state;
        } else {
            OrderShipGroupState state = getOrderShipGroupStateDao().get(globalId, nullAllowed, orderV2State);
            if (state != null) {
                if (state instanceof AbstractOrderShipGroupState) {
                    ((AbstractOrderShipGroupState)state).setStateReadOnly(getStateCollectionReadOnly());
                }
                if (((OrderShipGroupState.SqlOrderShipGroupState)state).isStateUnsaved() && getStateCollectionReadOnly()) {
                    return state;//throw new UnsupportedOperationException("State collection is ReadOnly.");
                }
                loadedOrderShipGroupStates.put(globalId, state);
            }
            return state;
        }

    }

    public boolean remove(OrderShipGroupState state) {
        if (getStateCollectionReadOnly()) {
            throw new UnsupportedOperationException("State collection is ReadOnly.");
        }
        this.loadedOrderShipGroupStates.remove(((OrderShipGroupState.SqlOrderShipGroupState)state).getOrderV2OrderShipGroupId());
        if (this.removedOrderShipGroupStates.containsKey(((OrderShipGroupState.SqlOrderShipGroupState)state).getOrderV2OrderShipGroupId())) {
            return false;
        }
        this.removedOrderShipGroupStates.put(((OrderShipGroupState.SqlOrderShipGroupState)state).getOrderV2OrderShipGroupId(), state);
        return true;
    }

    public boolean add(OrderShipGroupState state) {
        if (getStateCollectionReadOnly()) {
            throw new UnsupportedOperationException("State collection is ReadOnly.");
        }
        this.removedOrderShipGroupStates.remove(((OrderShipGroupState.SqlOrderShipGroupState)state).getOrderV2OrderShipGroupId());
        if (this.loadedOrderShipGroupStates.containsKey(((OrderShipGroupState.SqlOrderShipGroupState)state).getOrderV2OrderShipGroupId())) {
            return false;
        }
        this.loadedOrderShipGroupStates.put(((OrderShipGroupState.SqlOrderShipGroupState)state).getOrderV2OrderShipGroupId(), state);
        return true;
    }

    public Collection<OrderShipGroupState> getLoadedStates() {
        return Collections.unmodifiableCollection(this.loadedOrderShipGroupStates.values());
    }

    public Collection<OrderShipGroupState> getRemovedStates() {
        return Collections.unmodifiableCollection(this.removedOrderShipGroupStates.values());
    }

    public int size() {
        assureAllLoaded();
        return this.loadedOrderShipGroupStates.size();
    }

    public boolean isEmpty() {
        assureAllLoaded();
        return this.loadedOrderShipGroupStates.isEmpty();
    }

    public boolean contains(Object o) {
        if (loadedOrderShipGroupStates.values().contains(o)) {
            return true;
        }
        assureAllLoaded();
        return this.loadedOrderShipGroupStates.containsValue(o);
    }

    public Object[] toArray() {
        assureAllLoaded();
        return this.loadedOrderShipGroupStates.values().toArray();
    }

    public <T> T[] toArray(T[] a) {
        assureAllLoaded();
        return this.loadedOrderShipGroupStates.values().toArray(a);
    }

    public boolean containsAll(Collection<?> c) {
        assureAllLoaded();
        return this.loadedOrderShipGroupStates.values().containsAll(c);
    }

    public boolean addAll(Collection<? extends OrderShipGroupState> c) {
        boolean b = false;
        for (OrderShipGroupState s : c) {
            if (add(s)) {
                b = true;
            }
        }
        return b;
    }

    public boolean remove(Object o) {
        return remove((OrderShipGroupState)o);
    }

    public boolean removeAll(Collection<?> c) {
        boolean b = false;
        for (Object s : c) {
            if (remove(s)) {
                b = true;
            }
        }
        return b;
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        assureAllLoaded();
        this.loadedOrderShipGroupStates.values().forEach(s -> this.removedOrderShipGroupStates.put(((OrderShipGroupState.SqlOrderShipGroupState)s).getOrderV2OrderShipGroupId(), s));
        this.loadedOrderShipGroupStates.clear();
    }

    //region Saveable Implements

    public void save ()
    {
        for (OrderShipGroupState s : this.getLoadedOrderShipGroupStates()) {
            getOrderShipGroupStateDao().save(s);
        }
        for (OrderShipGroupState s : this.removedOrderShipGroupStates.values()) {
            getOrderShipGroupStateDao().delete(s);
        }
    }

    //endregion

}


