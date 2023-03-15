package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.ApplicationContext;
import org.dddml.suidemocontracts.specialization.Saveable;

public abstract class AbstractOrderItemShipGroupAssocSubitemStateCollection implements EntityStateCollection.ModifiableEntityStateCollection<Integer, OrderItemShipGroupAssocSubitemState>, Saveable
{
    protected OrderItemShipGroupAssocSubitemStateDao getOrderItemShipGroupAssocSubitemStateDao()
    {
        return (OrderItemShipGroupAssocSubitemStateDao)ApplicationContext.current.get("orderItemShipGroupAssocSubitemStateDao");
    }

    private OrderItemShipGroupAssociationState orderItemShipGroupAssociationState;

    private Map<OrderV2OrderItemShipGroupAssocSubitemId, OrderItemShipGroupAssocSubitemState> loadedOrderItemShipGroupAssocSubitemStates = new HashMap<OrderV2OrderItemShipGroupAssocSubitemId, OrderItemShipGroupAssocSubitemState>();

    private Map<OrderV2OrderItemShipGroupAssocSubitemId, OrderItemShipGroupAssocSubitemState> removedOrderItemShipGroupAssocSubitemStates = new HashMap<OrderV2OrderItemShipGroupAssocSubitemId, OrderItemShipGroupAssocSubitemState>();

    protected Iterable<OrderItemShipGroupAssocSubitemState> getLoadedOrderItemShipGroupAssocSubitemStates() {
        return this.loadedOrderItemShipGroupAssocSubitemStates.values();
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
        if (this.orderItemShipGroupAssociationState instanceof AbstractOrderItemShipGroupAssociationState) {
            if (((AbstractOrderItemShipGroupAssociationState)this.orderItemShipGroupAssociationState).getStateReadOnly() != null && ((AbstractOrderItemShipGroupAssociationState)this.orderItemShipGroupAssociationState).getStateReadOnly()) 
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

    protected Iterable<OrderItemShipGroupAssocSubitemState> getInnerIterable() {
        if (!getForReapplying()) {
            //if (!getStateCollectionReadOnly()) {
            //    throw new UnsupportedOperationException("State collection is NOT ReadOnly.");
            //}
            assureAllLoaded();
            return this.loadedOrderItemShipGroupAssocSubitemStates.values();
        } else {
            List<OrderItemShipGroupAssocSubitemState> ss = new ArrayList<OrderItemShipGroupAssocSubitemState>();
            for (OrderItemShipGroupAssocSubitemState s : loadedOrderItemShipGroupAssocSubitemStates.values()) {
                if (!(removedOrderItemShipGroupAssocSubitemStates.containsKey(((OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState)s).getOrderV2OrderItemShipGroupAssocSubitemId()) && s.getDeleted())) {
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
            Iterable<OrderItemShipGroupAssocSubitemState> ss = getOrderItemShipGroupAssocSubitemStateDao().findByOrderV2OrderIdAndOrderShipGroupShipGroupSeqIdAndOrderItemShipGroupAssociationProductId(((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)orderItemShipGroupAssociationState).getOrderV2OrderItemShipGroupAssociationId().getOrderV2OrderId(), ((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)orderItemShipGroupAssociationState).getOrderV2OrderItemShipGroupAssociationId().getOrderShipGroupShipGroupSeqId(), ((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)orderItemShipGroupAssociationState).getOrderV2OrderItemShipGroupAssociationId().getProductId(), ((AbstractOrderItemShipGroupAssociationState)orderItemShipGroupAssociationState).getOrderV2State());
            for (OrderItemShipGroupAssocSubitemState s : ss) {
                if (!this.loadedOrderItemShipGroupAssocSubitemStates.containsKey(((OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState)s).getOrderV2OrderItemShipGroupAssocSubitemId()) 
                    && !this.removedOrderItemShipGroupAssocSubitemStates.containsKey(((OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState)s).getOrderV2OrderItemShipGroupAssocSubitemId())) {
                    this.loadedOrderItemShipGroupAssocSubitemStates.put(((OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState)s).getOrderV2OrderItemShipGroupAssocSubitemId(), s);
                }
            }
            allLoaded = true;
        }
    }

    public AbstractOrderItemShipGroupAssocSubitemStateCollection(OrderItemShipGroupAssociationState outerState) {
        this.orderItemShipGroupAssociationState = outerState;
        this.setForReapplying(((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)outerState).getForReapplying());
    }

    @Override
    public Iterator<OrderItemShipGroupAssocSubitemState> iterator() {
        return getInnerIterable().iterator();
    }

    public OrderItemShipGroupAssocSubitemState get(Integer orderItemShipGroupAssocSubitemSeqId) {
        return get(orderItemShipGroupAssocSubitemSeqId, true, false);
    }

    public OrderItemShipGroupAssocSubitemState getOrAdd(Integer orderItemShipGroupAssocSubitemSeqId) {
        return get(orderItemShipGroupAssocSubitemSeqId, false, false);
    }

    protected OrderItemShipGroupAssocSubitemState get(Integer orderItemShipGroupAssocSubitemSeqId, boolean nullAllowed, boolean forCreation) {
        OrderV2OrderItemShipGroupAssocSubitemId globalId = new OrderV2OrderItemShipGroupAssocSubitemId(((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)orderItemShipGroupAssociationState).getOrderV2OrderItemShipGroupAssociationId().getOrderV2OrderId(), ((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)orderItemShipGroupAssociationState).getOrderV2OrderItemShipGroupAssociationId().getOrderShipGroupShipGroupSeqId(), ((OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState)orderItemShipGroupAssociationState).getOrderV2OrderItemShipGroupAssociationId().getProductId(), orderItemShipGroupAssocSubitemSeqId);
        if (loadedOrderItemShipGroupAssocSubitemStates.containsKey(globalId)) {
            OrderItemShipGroupAssocSubitemState state = loadedOrderItemShipGroupAssocSubitemStates.get(globalId);
            if (state instanceof AbstractOrderItemShipGroupAssocSubitemState) {
                ((AbstractOrderItemShipGroupAssocSubitemState)state).setStateReadOnly(getStateCollectionReadOnly());
            }
            return state;
        }
        boolean justNewIfNotLoaded = forCreation || getForReapplying();
        if (justNewIfNotLoaded) {
            if (getStateCollectionReadOnly()) {
                throw new UnsupportedOperationException("State collection is ReadOnly.");
            }
            OrderItemShipGroupAssocSubitemState state = AbstractOrderItemShipGroupAssocSubitemState.SimpleOrderItemShipGroupAssocSubitemState.newForReapplying();
            ((OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState)state).setOrderV2OrderItemShipGroupAssocSubitemId(globalId);
            loadedOrderItemShipGroupAssocSubitemStates.put(globalId, state);
            return state;
        } else {
            OrderItemShipGroupAssocSubitemState state = getOrderItemShipGroupAssocSubitemStateDao().get(globalId, nullAllowed, ((AbstractOrderItemShipGroupAssociationState)orderItemShipGroupAssociationState).getOrderV2State());
            if (state != null) {
                if (state instanceof AbstractOrderItemShipGroupAssocSubitemState) {
                    ((AbstractOrderItemShipGroupAssocSubitemState)state).setStateReadOnly(getStateCollectionReadOnly());
                }
                if (((OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState)state).isStateUnsaved() && getStateCollectionReadOnly()) {
                    return state;//throw new UnsupportedOperationException("State collection is ReadOnly.");
                }
                loadedOrderItemShipGroupAssocSubitemStates.put(globalId, state);
            }
            return state;
        }

    }

    public boolean remove(OrderItemShipGroupAssocSubitemState state) {
        if (getStateCollectionReadOnly()) {
            throw new UnsupportedOperationException("State collection is ReadOnly.");
        }
        this.loadedOrderItemShipGroupAssocSubitemStates.remove(((OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState)state).getOrderV2OrderItemShipGroupAssocSubitemId());
        if (this.removedOrderItemShipGroupAssocSubitemStates.containsKey(((OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState)state).getOrderV2OrderItemShipGroupAssocSubitemId())) {
            return false;
        }
        this.removedOrderItemShipGroupAssocSubitemStates.put(((OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState)state).getOrderV2OrderItemShipGroupAssocSubitemId(), state);
        return true;
    }

    public boolean add(OrderItemShipGroupAssocSubitemState state) {
        if (getStateCollectionReadOnly()) {
            throw new UnsupportedOperationException("State collection is ReadOnly.");
        }
        this.removedOrderItemShipGroupAssocSubitemStates.remove(((OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState)state).getOrderV2OrderItemShipGroupAssocSubitemId());
        if (this.loadedOrderItemShipGroupAssocSubitemStates.containsKey(((OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState)state).getOrderV2OrderItemShipGroupAssocSubitemId())) {
            return false;
        }
        this.loadedOrderItemShipGroupAssocSubitemStates.put(((OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState)state).getOrderV2OrderItemShipGroupAssocSubitemId(), state);
        return true;
    }

    public Collection<OrderItemShipGroupAssocSubitemState> getLoadedStates() {
        return Collections.unmodifiableCollection(this.loadedOrderItemShipGroupAssocSubitemStates.values());
    }

    public Collection<OrderItemShipGroupAssocSubitemState> getRemovedStates() {
        return Collections.unmodifiableCollection(this.removedOrderItemShipGroupAssocSubitemStates.values());
    }

    public int size() {
        assureAllLoaded();
        return this.loadedOrderItemShipGroupAssocSubitemStates.size();
    }

    public boolean isEmpty() {
        assureAllLoaded();
        return this.loadedOrderItemShipGroupAssocSubitemStates.isEmpty();
    }

    public boolean contains(Object o) {
        if (loadedOrderItemShipGroupAssocSubitemStates.values().contains(o)) {
            return true;
        }
        assureAllLoaded();
        return this.loadedOrderItemShipGroupAssocSubitemStates.containsValue(o);
    }

    public Object[] toArray() {
        assureAllLoaded();
        return this.loadedOrderItemShipGroupAssocSubitemStates.values().toArray();
    }

    public <T> T[] toArray(T[] a) {
        assureAllLoaded();
        return this.loadedOrderItemShipGroupAssocSubitemStates.values().toArray(a);
    }

    public boolean containsAll(Collection<?> c) {
        assureAllLoaded();
        return this.loadedOrderItemShipGroupAssocSubitemStates.values().containsAll(c);
    }

    public boolean addAll(Collection<? extends OrderItemShipGroupAssocSubitemState> c) {
        boolean b = false;
        for (OrderItemShipGroupAssocSubitemState s : c) {
            if (add(s)) {
                b = true;
            }
        }
        return b;
    }

    public boolean remove(Object o) {
        return remove((OrderItemShipGroupAssocSubitemState)o);
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
        this.loadedOrderItemShipGroupAssocSubitemStates.values().forEach(s -> this.removedOrderItemShipGroupAssocSubitemStates.put(((OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState)s).getOrderV2OrderItemShipGroupAssocSubitemId(), s));
        this.loadedOrderItemShipGroupAssocSubitemStates.clear();
    }

    //region Saveable Implements

    public void save ()
    {
        for (OrderItemShipGroupAssocSubitemState s : this.getLoadedOrderItemShipGroupAssocSubitemStates()) {
            getOrderItemShipGroupAssocSubitemStateDao().save(s);
        }
        for (OrderItemShipGroupAssocSubitemState s : this.removedOrderItemShipGroupAssocSubitemStates.values()) {
            getOrderItemShipGroupAssocSubitemStateDao().delete(s);
        }
    }

    //endregion

}


