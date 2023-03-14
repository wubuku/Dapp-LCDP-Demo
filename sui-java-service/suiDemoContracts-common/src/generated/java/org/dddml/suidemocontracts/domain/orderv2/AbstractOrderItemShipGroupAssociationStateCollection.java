package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.ApplicationContext;
import org.dddml.suidemocontracts.specialization.Saveable;

public abstract class AbstractOrderItemShipGroupAssociationStateCollection implements EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemShipGroupAssociationState>, Saveable
{
    protected OrderItemShipGroupAssociationStateDao getOrderItemShipGroupAssociationStateDao()
    {
        return (OrderItemShipGroupAssociationStateDao)ApplicationContext.current.get("orderItemShipGroupAssociationStateDao");
    }

    private OrderShipGroupState orderShipGroupState;

    private Map<OrderV2OrderItemShipGroupAssociationId, OrderItemShipGroupAssociationState> loadedOrderItemShipGroupAssociationStates = new HashMap<OrderV2OrderItemShipGroupAssociationId, OrderItemShipGroupAssociationState>();

    private Map<OrderV2OrderItemShipGroupAssociationId, OrderItemShipGroupAssociationState> removedOrderItemShipGroupAssociationStates = new HashMap<OrderV2OrderItemShipGroupAssociationId, OrderItemShipGroupAssociationState>();

    protected Iterable<OrderItemShipGroupAssociationState> getLoadedOrderItemShipGroupAssociationStates() {
        return this.loadedOrderItemShipGroupAssociationStates.values();
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
        if (this.orderShipGroupState instanceof AbstractOrderShipGroupState) {
            if (((AbstractOrderShipGroupState)this.orderShipGroupState).getStateReadOnly() != null && ((AbstractOrderShipGroupState)this.orderShipGroupState).getStateReadOnly()) 
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

    protected Iterable<OrderItemShipGroupAssociationState> getInnerIterable() {
        if (!getForReapplying()) {
            //if (!getStateCollectionReadOnly()) {
            //    throw new UnsupportedOperationException("State collection is NOT ReadOnly.");
            //}
            assureAllLoaded();
            return this.loadedOrderItemShipGroupAssociationStates.values();
        } else {
            List<OrderItemShipGroupAssociationState> ss = new ArrayList<OrderItemShipGroupAssociationState>();
            for (OrderItemShipGroupAssociationState s : loadedOrderItemShipGroupAssociationStates.values()) {
                if (!(removedOrderItemShipGroupAssociationStates.containsKey(((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)s).getOrderV2OrderItemShipGroupAssociationId()) && s.getDeleted())) {
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
            Iterable<OrderItemShipGroupAssociationState> ss = getOrderItemShipGroupAssociationStateDao().findByOrderV2OrderIdAndOrderShipGroupShipGroupSeqId(((OrderShipGroupState.SqlOrderShipGroupState)orderShipGroupState).getOrderV2OrderShipGroupId().getOrderV2OrderId(), ((OrderShipGroupState.SqlOrderShipGroupState)orderShipGroupState).getOrderV2OrderShipGroupId().getShipGroupSeqId(), ((AbstractOrderShipGroupState)orderShipGroupState).getOrderV2State());
            for (OrderItemShipGroupAssociationState s : ss) {
                if (!this.loadedOrderItemShipGroupAssociationStates.containsKey(((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)s).getOrderV2OrderItemShipGroupAssociationId()) 
                    && !this.removedOrderItemShipGroupAssociationStates.containsKey(((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)s).getOrderV2OrderItemShipGroupAssociationId())) {
                    this.loadedOrderItemShipGroupAssociationStates.put(((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)s).getOrderV2OrderItemShipGroupAssociationId(), s);
                }
            }
            allLoaded = true;
        }
    }

    public AbstractOrderItemShipGroupAssociationStateCollection(OrderShipGroupState outerState) {
        this.orderShipGroupState = outerState;
        this.setForReapplying(((OrderShipGroupState.SqlOrderShipGroupState)outerState).getForReapplying());
    }

    @Override
    public Iterator<OrderItemShipGroupAssociationState> iterator() {
        return getInnerIterable().iterator();
    }

    public OrderItemShipGroupAssociationState get(String productId) {
        return get(productId, true, false);
    }

    public OrderItemShipGroupAssociationState getOrAdd(String productId) {
        return get(productId, false, false);
    }

    protected OrderItemShipGroupAssociationState get(String productId, boolean nullAllowed, boolean forCreation) {
        OrderV2OrderItemShipGroupAssociationId globalId = new OrderV2OrderItemShipGroupAssociationId(((OrderShipGroupState.SqlOrderShipGroupState)orderShipGroupState).getOrderV2OrderShipGroupId().getOrderV2OrderId(), ((OrderShipGroupState.SqlOrderShipGroupState)orderShipGroupState).getOrderV2OrderShipGroupId().getShipGroupSeqId(), productId);
        if (loadedOrderItemShipGroupAssociationStates.containsKey(globalId)) {
            OrderItemShipGroupAssociationState state = loadedOrderItemShipGroupAssociationStates.get(globalId);
            if (state instanceof AbstractOrderItemShipGroupAssociationState) {
                ((AbstractOrderItemShipGroupAssociationState)state).setStateReadOnly(getStateCollectionReadOnly());
            }
            return state;
        }
        boolean justNewIfNotLoaded = forCreation || getForReapplying();
        if (justNewIfNotLoaded) {
            if (getStateCollectionReadOnly()) {
                throw new UnsupportedOperationException("State collection is ReadOnly.");
            }
            OrderItemShipGroupAssociationState state = AbstractOrderItemShipGroupAssociationState.SimpleOrderItemShipGroupAssociationState.newForReapplying();
            ((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)state).setOrderV2OrderItemShipGroupAssociationId(globalId);
            loadedOrderItemShipGroupAssociationStates.put(globalId, state);
            return state;
        } else {
            OrderItemShipGroupAssociationState state = getOrderItemShipGroupAssociationStateDao().get(globalId, nullAllowed, ((AbstractOrderShipGroupState)orderShipGroupState).getOrderV2State());
            if (state != null) {
                if (state instanceof AbstractOrderItemShipGroupAssociationState) {
                    ((AbstractOrderItemShipGroupAssociationState)state).setStateReadOnly(getStateCollectionReadOnly());
                }
                if (((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)state).isStateUnsaved() && getStateCollectionReadOnly()) {
                    return state;//throw new UnsupportedOperationException("State collection is ReadOnly.");
                }
                loadedOrderItemShipGroupAssociationStates.put(globalId, state);
            }
            return state;
        }

    }

    public boolean remove(OrderItemShipGroupAssociationState state) {
        if (getStateCollectionReadOnly()) {
            throw new UnsupportedOperationException("State collection is ReadOnly.");
        }
        this.loadedOrderItemShipGroupAssociationStates.remove(((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)state).getOrderV2OrderItemShipGroupAssociationId());
        if (this.removedOrderItemShipGroupAssociationStates.containsKey(((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)state).getOrderV2OrderItemShipGroupAssociationId())) {
            return false;
        }
        this.removedOrderItemShipGroupAssociationStates.put(((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)state).getOrderV2OrderItemShipGroupAssociationId(), state);
        return true;
    }

    public boolean add(OrderItemShipGroupAssociationState state) {
        if (getStateCollectionReadOnly()) {
            throw new UnsupportedOperationException("State collection is ReadOnly.");
        }
        this.removedOrderItemShipGroupAssociationStates.remove(((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)state).getOrderV2OrderItemShipGroupAssociationId());
        if (this.loadedOrderItemShipGroupAssociationStates.containsKey(((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)state).getOrderV2OrderItemShipGroupAssociationId())) {
            return false;
        }
        this.loadedOrderItemShipGroupAssociationStates.put(((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)state).getOrderV2OrderItemShipGroupAssociationId(), state);
        return true;
    }

    public Collection<OrderItemShipGroupAssociationState> getLoadedStates() {
        return Collections.unmodifiableCollection(this.loadedOrderItemShipGroupAssociationStates.values());
    }

    public Collection<OrderItemShipGroupAssociationState> getRemovedStates() {
        return Collections.unmodifiableCollection(this.removedOrderItemShipGroupAssociationStates.values());
    }

    public int size() {
        assureAllLoaded();
        return this.loadedOrderItemShipGroupAssociationStates.size();
    }

    public boolean isEmpty() {
        assureAllLoaded();
        return this.loadedOrderItemShipGroupAssociationStates.isEmpty();
    }

    public boolean contains(Object o) {
        if (loadedOrderItemShipGroupAssociationStates.values().contains(o)) {
            return true;
        }
        assureAllLoaded();
        return this.loadedOrderItemShipGroupAssociationStates.containsValue(o);
    }

    public Object[] toArray() {
        assureAllLoaded();
        return this.loadedOrderItemShipGroupAssociationStates.values().toArray();
    }

    public <T> T[] toArray(T[] a) {
        assureAllLoaded();
        return this.loadedOrderItemShipGroupAssociationStates.values().toArray(a);
    }

    public boolean containsAll(Collection<?> c) {
        assureAllLoaded();
        return this.loadedOrderItemShipGroupAssociationStates.values().containsAll(c);
    }

    public boolean addAll(Collection<? extends OrderItemShipGroupAssociationState> c) {
        boolean b = false;
        for (OrderItemShipGroupAssociationState s : c) {
            if (add(s)) {
                b = true;
            }
        }
        return b;
    }

    public boolean remove(Object o) {
        return remove((OrderItemShipGroupAssociationState)o);
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
        this.loadedOrderItemShipGroupAssociationStates.values().forEach(s -> this.removedOrderItemShipGroupAssociationStates.put(((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)s).getOrderV2OrderItemShipGroupAssociationId(), s));
        this.loadedOrderItemShipGroupAssociationStates.clear();
    }

    //region Saveable Implements

    public void save ()
    {
        for (OrderItemShipGroupAssociationState s : this.getLoadedOrderItemShipGroupAssociationStates()) {
            getOrderItemShipGroupAssociationStateDao().save(s);
        }
        for (OrderItemShipGroupAssociationState s : this.removedOrderItemShipGroupAssociationStates.values()) {
            getOrderItemShipGroupAssociationStateDao().delete(s);
        }
    }

    //endregion

}


