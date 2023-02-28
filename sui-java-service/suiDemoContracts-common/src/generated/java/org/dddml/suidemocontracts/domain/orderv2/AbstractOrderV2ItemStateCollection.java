package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.ApplicationContext;
import org.dddml.suidemocontracts.specialization.Saveable;

public abstract class AbstractOrderV2ItemStateCollection implements EntityStateCollection.ModifiableEntityStateCollection<String, OrderV2ItemState>, Saveable
{
    protected OrderV2ItemStateDao getOrderV2ItemStateDao()
    {
        return (OrderV2ItemStateDao)ApplicationContext.current.get("orderV2ItemStateDao");
    }

    private OrderV2State orderV2State;

    private Map<OrderV2ItemId, OrderV2ItemState> loadedOrderV2ItemStates = new HashMap<OrderV2ItemId, OrderV2ItemState>();

    private Map<OrderV2ItemId, OrderV2ItemState> removedOrderV2ItemStates = new HashMap<OrderV2ItemId, OrderV2ItemState>();

    protected Iterable<OrderV2ItemState> getLoadedOrderV2ItemStates() {
        return this.loadedOrderV2ItemStates.values();
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

    protected Iterable<OrderV2ItemState> getInnerIterable() {
        if (!getForReapplying()) {
            //if (!getStateCollectionReadOnly()) {
            //    throw new UnsupportedOperationException("State collection is NOT ReadOnly.");
            //}
            assureAllLoaded();
            return this.loadedOrderV2ItemStates.values();
        } else {
            List<OrderV2ItemState> ss = new ArrayList<OrderV2ItemState>();
            for (OrderV2ItemState s : loadedOrderV2ItemStates.values()) {
                if (!(removedOrderV2ItemStates.containsKey(((OrderV2ItemState.SqlOrderV2ItemState)s).getOrderV2ItemId()) && s.getDeleted())) {
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
            Iterable<OrderV2ItemState> ss = getOrderV2ItemStateDao().findByOrderV2OrderId(orderV2State.getOrderId(), orderV2State);
            for (OrderV2ItemState s : ss) {
                if (!this.loadedOrderV2ItemStates.containsKey(((OrderV2ItemState.SqlOrderV2ItemState)s).getOrderV2ItemId()) 
                    && !this.removedOrderV2ItemStates.containsKey(((OrderV2ItemState.SqlOrderV2ItemState)s).getOrderV2ItemId())) {
                    this.loadedOrderV2ItemStates.put(((OrderV2ItemState.SqlOrderV2ItemState)s).getOrderV2ItemId(), s);
                }
            }
            allLoaded = true;
        }
    }

    public AbstractOrderV2ItemStateCollection(OrderV2State outerState) {
        this.orderV2State = outerState;
        this.setForReapplying(((OrderV2State.SqlOrderV2State)outerState).getForReapplying());
    }

    @Override
    public Iterator<OrderV2ItemState> iterator() {
        return getInnerIterable().iterator();
    }

    public OrderV2ItemState get(String productId) {
        return get(productId, true, false);
    }

    public OrderV2ItemState getOrAdd(String productId) {
        return get(productId, false, false);
    }

    protected OrderV2ItemState get(String productId, boolean nullAllowed, boolean forCreation) {
        OrderV2ItemId globalId = new OrderV2ItemId(orderV2State.getOrderId(), productId);
        if (loadedOrderV2ItemStates.containsKey(globalId)) {
            OrderV2ItemState state = loadedOrderV2ItemStates.get(globalId);
            if (state instanceof AbstractOrderV2ItemState) {
                ((AbstractOrderV2ItemState)state).setStateReadOnly(getStateCollectionReadOnly());
            }
            return state;
        }
        boolean justNewIfNotLoaded = forCreation || getForReapplying();
        if (justNewIfNotLoaded) {
            if (getStateCollectionReadOnly()) {
                throw new UnsupportedOperationException("State collection is ReadOnly.");
            }
            OrderV2ItemState state = AbstractOrderV2ItemState.SimpleOrderV2ItemState.newForReapplying();
            ((OrderV2ItemState.SqlOrderV2ItemState)state).setOrderV2ItemId(globalId);
            loadedOrderV2ItemStates.put(globalId, state);
            return state;
        } else {
            OrderV2ItemState state = getOrderV2ItemStateDao().get(globalId, nullAllowed, orderV2State);
            if (state != null) {
                if (state instanceof AbstractOrderV2ItemState) {
                    ((AbstractOrderV2ItemState)state).setStateReadOnly(getStateCollectionReadOnly());
                }
                if (((OrderV2ItemState.SqlOrderV2ItemState)state).isStateUnsaved() && getStateCollectionReadOnly()) {
                    return state;//throw new UnsupportedOperationException("State collection is ReadOnly.");
                }
                loadedOrderV2ItemStates.put(globalId, state);
            }
            return state;
        }

    }

    public boolean remove(OrderV2ItemState state) {
        if (getStateCollectionReadOnly()) {
            throw new UnsupportedOperationException("State collection is ReadOnly.");
        }
        this.loadedOrderV2ItemStates.remove(((OrderV2ItemState.SqlOrderV2ItemState)state).getOrderV2ItemId());
        if (this.removedOrderV2ItemStates.containsKey(((OrderV2ItemState.SqlOrderV2ItemState)state).getOrderV2ItemId())) {
            return false;
        }
        this.removedOrderV2ItemStates.put(((OrderV2ItemState.SqlOrderV2ItemState)state).getOrderV2ItemId(), state);
        return true;
    }

    public boolean add(OrderV2ItemState state) {
        if (getStateCollectionReadOnly()) {
            throw new UnsupportedOperationException("State collection is ReadOnly.");
        }
        this.removedOrderV2ItemStates.remove(((OrderV2ItemState.SqlOrderV2ItemState)state).getOrderV2ItemId());
        if (this.loadedOrderV2ItemStates.containsKey(((OrderV2ItemState.SqlOrderV2ItemState)state).getOrderV2ItemId())) {
            return false;
        }
        this.loadedOrderV2ItemStates.put(((OrderV2ItemState.SqlOrderV2ItemState)state).getOrderV2ItemId(), state);
        return true;
    }

    public Collection<OrderV2ItemState> getLoadedStates() {
        return Collections.unmodifiableCollection(this.loadedOrderV2ItemStates.values());
    }

    public Collection<OrderV2ItemState> getRemovedStates() {
        return Collections.unmodifiableCollection(this.removedOrderV2ItemStates.values());
    }

    public int size() {
        assureAllLoaded();
        return this.loadedOrderV2ItemStates.size();
    }

    public boolean isEmpty() {
        assureAllLoaded();
        return this.loadedOrderV2ItemStates.isEmpty();
    }

    public boolean contains(Object o) {
        if (loadedOrderV2ItemStates.values().contains(o)) {
            return true;
        }
        assureAllLoaded();
        return this.loadedOrderV2ItemStates.containsValue(o);
    }

    public Object[] toArray() {
        assureAllLoaded();
        return this.loadedOrderV2ItemStates.values().toArray();
    }

    public <T> T[] toArray(T[] a) {
        assureAllLoaded();
        return this.loadedOrderV2ItemStates.values().toArray(a);
    }

    public boolean containsAll(Collection<?> c) {
        assureAllLoaded();
        return this.loadedOrderV2ItemStates.values().containsAll(c);
    }

    public boolean addAll(Collection<? extends OrderV2ItemState> c) {
        boolean b = false;
        for (OrderV2ItemState s : c) {
            if (add(s)) {
                b = true;
            }
        }
        return b;
    }

    public boolean remove(Object o) {
        return remove((OrderV2ItemState)o);
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
        this.loadedOrderV2ItemStates.values().forEach(s -> this.removedOrderV2ItemStates.put(((OrderV2ItemState.SqlOrderV2ItemState)s).getOrderV2ItemId(), s));
        this.loadedOrderV2ItemStates.clear();
    }

    //region Saveable Implements

    public void save ()
    {
        for (OrderV2ItemState s : this.getLoadedOrderV2ItemStates()) {
            getOrderV2ItemStateDao().save(s);
        }
        for (OrderV2ItemState s : this.removedOrderV2ItemStates.values()) {
            getOrderV2ItemStateDao().delete(s);
        }
    }

    //endregion

}


