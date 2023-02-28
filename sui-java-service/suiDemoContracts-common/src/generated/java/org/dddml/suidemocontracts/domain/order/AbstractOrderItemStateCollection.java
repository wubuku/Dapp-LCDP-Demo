package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.ApplicationContext;
import org.dddml.suidemocontracts.specialization.Saveable;

public abstract class AbstractOrderItemStateCollection implements EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemState>, Saveable
{
    protected OrderItemStateDao getOrderItemStateDao()
    {
        return (OrderItemStateDao)ApplicationContext.current.get("orderItemStateDao");
    }

    private OrderState orderState;

    private Map<OrderItemId, OrderItemState> loadedOrderItemStates = new HashMap<OrderItemId, OrderItemState>();

    private Map<OrderItemId, OrderItemState> removedOrderItemStates = new HashMap<OrderItemId, OrderItemState>();

    protected Iterable<OrderItemState> getLoadedOrderItemStates() {
        return this.loadedOrderItemStates.values();
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
        if (this.orderState instanceof AbstractOrderState) {
            if (((AbstractOrderState)this.orderState).getStateReadOnly() != null && ((AbstractOrderState)this.orderState).getStateReadOnly()) 
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

    protected Iterable<OrderItemState> getInnerIterable() {
        if (!getForReapplying()) {
            //if (!getStateCollectionReadOnly()) {
            //    throw new UnsupportedOperationException("State collection is NOT ReadOnly.");
            //}
            assureAllLoaded();
            return this.loadedOrderItemStates.values();
        } else {
            List<OrderItemState> ss = new ArrayList<OrderItemState>();
            for (OrderItemState s : loadedOrderItemStates.values()) {
                if (!(removedOrderItemStates.containsKey(((OrderItemState.SqlOrderItemState)s).getOrderItemId()) && s.getDeleted())) {
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
            Iterable<OrderItemState> ss = getOrderItemStateDao().findByOrderId(orderState.getId(), orderState);
            for (OrderItemState s : ss) {
                if (!this.loadedOrderItemStates.containsKey(((OrderItemState.SqlOrderItemState)s).getOrderItemId()) 
                    && !this.removedOrderItemStates.containsKey(((OrderItemState.SqlOrderItemState)s).getOrderItemId())) {
                    this.loadedOrderItemStates.put(((OrderItemState.SqlOrderItemState)s).getOrderItemId(), s);
                }
            }
            allLoaded = true;
        }
    }

    public AbstractOrderItemStateCollection(OrderState outerState) {
        this.orderState = outerState;
        this.setForReapplying(((OrderState.SqlOrderState)outerState).getForReapplying());
    }

    @Override
    public Iterator<OrderItemState> iterator() {
        return getInnerIterable().iterator();
    }

    public OrderItemState get(String productId) {
        return get(productId, true, false);
    }

    public OrderItemState getOrAdd(String productId) {
        return get(productId, false, false);
    }

    protected OrderItemState get(String productId, boolean nullAllowed, boolean forCreation) {
        OrderItemId globalId = new OrderItemId(orderState.getId(), productId);
        if (loadedOrderItemStates.containsKey(globalId)) {
            OrderItemState state = loadedOrderItemStates.get(globalId);
            if (state instanceof AbstractOrderItemState) {
                ((AbstractOrderItemState)state).setStateReadOnly(getStateCollectionReadOnly());
            }
            return state;
        }
        boolean justNewIfNotLoaded = forCreation || getForReapplying();
        if (justNewIfNotLoaded) {
            if (getStateCollectionReadOnly()) {
                throw new UnsupportedOperationException("State collection is ReadOnly.");
            }
            OrderItemState state = AbstractOrderItemState.SimpleOrderItemState.newForReapplying();
            ((OrderItemState.SqlOrderItemState)state).setOrderItemId(globalId);
            loadedOrderItemStates.put(globalId, state);
            return state;
        } else {
            OrderItemState state = getOrderItemStateDao().get(globalId, nullAllowed, orderState);
            if (state != null) {
                if (state instanceof AbstractOrderItemState) {
                    ((AbstractOrderItemState)state).setStateReadOnly(getStateCollectionReadOnly());
                }
                if (((OrderItemState.SqlOrderItemState)state).isStateUnsaved() && getStateCollectionReadOnly()) {
                    return state;//throw new UnsupportedOperationException("State collection is ReadOnly.");
                }
                loadedOrderItemStates.put(globalId, state);
            }
            return state;
        }

    }

    public boolean remove(OrderItemState state) {
        if (getStateCollectionReadOnly()) {
            throw new UnsupportedOperationException("State collection is ReadOnly.");
        }
        this.loadedOrderItemStates.remove(((OrderItemState.SqlOrderItemState)state).getOrderItemId());
        if (this.removedOrderItemStates.containsKey(((OrderItemState.SqlOrderItemState)state).getOrderItemId())) {
            return false;
        }
        this.removedOrderItemStates.put(((OrderItemState.SqlOrderItemState)state).getOrderItemId(), state);
        return true;
    }

    public boolean add(OrderItemState state) {
        if (getStateCollectionReadOnly()) {
            throw new UnsupportedOperationException("State collection is ReadOnly.");
        }
        this.removedOrderItemStates.remove(((OrderItemState.SqlOrderItemState)state).getOrderItemId());
        if (this.loadedOrderItemStates.containsKey(((OrderItemState.SqlOrderItemState)state).getOrderItemId())) {
            return false;
        }
        this.loadedOrderItemStates.put(((OrderItemState.SqlOrderItemState)state).getOrderItemId(), state);
        return true;
    }

    public Collection<OrderItemState> getLoadedStates() {
        return Collections.unmodifiableCollection(this.loadedOrderItemStates.values());
    }

    public Collection<OrderItemState> getRemovedStates() {
        return Collections.unmodifiableCollection(this.removedOrderItemStates.values());
    }

    public int size() {
        assureAllLoaded();
        return this.loadedOrderItemStates.size();
    }

    public boolean isEmpty() {
        assureAllLoaded();
        return this.loadedOrderItemStates.isEmpty();
    }

    public boolean contains(Object o) {
        if (loadedOrderItemStates.values().contains(o)) {
            return true;
        }
        assureAllLoaded();
        return this.loadedOrderItemStates.containsValue(o);
    }

    public Object[] toArray() {
        assureAllLoaded();
        return this.loadedOrderItemStates.values().toArray();
    }

    public <T> T[] toArray(T[] a) {
        assureAllLoaded();
        return this.loadedOrderItemStates.values().toArray(a);
    }

    public boolean containsAll(Collection<?> c) {
        assureAllLoaded();
        return this.loadedOrderItemStates.values().containsAll(c);
    }

    public boolean addAll(Collection<? extends OrderItemState> c) {
        boolean b = false;
        for (OrderItemState s : c) {
            if (add(s)) {
                b = true;
            }
        }
        return b;
    }

    public boolean remove(Object o) {
        return remove((OrderItemState)o);
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
        this.loadedOrderItemStates.values().forEach(s -> this.removedOrderItemStates.put(((OrderItemState.SqlOrderItemState)s).getOrderItemId(), s));
        this.loadedOrderItemStates.clear();
    }

    //region Saveable Implements

    public void save ()
    {
        for (OrderItemState s : this.getLoadedOrderItemStates()) {
            getOrderItemStateDao().save(s);
        }
        for (OrderItemState s : this.removedOrderItemStates.values()) {
            getOrderItemStateDao().delete(s);
        }
    }

    //endregion

}


