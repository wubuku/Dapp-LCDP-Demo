package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.domainname.DomainNameEvent.*;

public abstract class AbstractDomainNameState implements DomainNameState.SqlDomainNameState {

    private DomainNameId domainNameId;

    public DomainNameId getDomainNameId() {
        return this.domainNameId;
    }

    public void setDomainNameId(DomainNameId domainNameId) {
        this.domainNameId = domainNameId;
    }

    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private BigInteger expirationDate;

    public BigInteger getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(BigInteger expirationDate) {
        this.expirationDate = expirationDate;
    }

    private Long version;

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    private String createdBy;

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    private Date createdAt;

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    private String updatedBy;

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    private Date updatedAt;

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    private Boolean active;

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    private Boolean deleted;

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isStateUnsaved() {
        return this.getVersion() == null;
    }

    private Boolean stateReadOnly;

    public Boolean getStateReadOnly() { return this.stateReadOnly; }

    public void setStateReadOnly(Boolean readOnly) { this.stateReadOnly = readOnly; }

    private boolean forReapplying;

    public boolean getForReapplying() {
        return forReapplying;
    }

    public void setForReapplying(boolean forReapplying) {
        this.forReapplying = forReapplying;
    }

    public AbstractDomainNameState(List<Event> events) {
        initializeForReapplying();
        if (events != null && events.size() > 0) {
            this.setDomainNameId(((DomainNameEvent.SqlDomainNameEvent) events.get(0)).getDomainNameEventId().getDomainNameId());
            for (Event e : events) {
                mutate(e);
                this.setVersion((this.getVersion() == null ? DomainNameState.VERSION_NULL : this.getVersion()) + 1);
            }
        }
    }


    public AbstractDomainNameState() {
        initializeProperties();
    }

    protected void initializeForReapplying() {
        this.forReapplying = true;

        initializeProperties();
    }
    
    protected void initializeProperties() {
    }

    @Override
    public int hashCode() {
        return getDomainNameId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj instanceof DomainNameState) {
            return Objects.equals(this.getDomainNameId(), ((DomainNameState)obj).getDomainNameId());
        }
        return false;
    }


    public void mutate(Event e) {
        setStateReadOnly(false);
        if (false) { 
            ;
        } else if (e instanceof AbstractDomainNameEvent.Registered) {
            when((AbstractDomainNameEvent.Registered)e);
        } else if (e instanceof AbstractDomainNameEvent.Renewed) {
            when((AbstractDomainNameEvent.Renewed)e);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
        }
    }

    protected void merge(DomainNameState s) {
        if (s == this) {
            return;
        }
        this.setExpirationDate(s.getExpirationDate());
        this.setActive(s.getActive());
    }

    public void when(AbstractDomainNameEvent.Registered e) {
        throwOnWrongEvent(e);

        BigInteger registrationPeriod = e.getRegistrationPeriod();
        BigInteger RegistrationPeriod = registrationPeriod;
        String owner = e.getOwner();
        String Owner = owner;

        if (this.getCreatedBy() == null){
            this.setCreatedBy(e.getCreatedBy());
        }
        if (this.getCreatedAt() == null){
            this.setCreatedAt(e.getCreatedAt());
        }
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

        DomainNameState updatedDomainNameState = (DomainNameState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.domainname.RegisterLogic",
                    "mutate",
                    new Class[]{DomainNameState.class, BigInteger.class, String.class, MutationContext.class},
                    new Object[]{this, registrationPeriod, owner, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.domainname;
//
//public class RegisterLogic {
//    public static DomainNameState mutate(DomainNameState domainNameState, BigInteger registrationPeriod, String owner, MutationContext<DomainNameState, DomainNameState.MutableDomainNameState> mutationContext) {
//    }
//}

        if (this != updatedDomainNameState) { merge(updatedDomainNameState); } //else do nothing

    }

    public void when(AbstractDomainNameEvent.Renewed e) {
        throwOnWrongEvent(e);

        BigInteger renewPeriod = e.getRenewPeriod();
        BigInteger RenewPeriod = renewPeriod;
        String account = e.getAccount();
        String Account = account;

        if (this.getCreatedBy() == null){
            this.setCreatedBy(e.getCreatedBy());
        }
        if (this.getCreatedAt() == null){
            this.setCreatedAt(e.getCreatedAt());
        }
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

        DomainNameState updatedDomainNameState = (DomainNameState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.domainname.RenewLogic",
                    "mutate",
                    new Class[]{DomainNameState.class, BigInteger.class, String.class, MutationContext.class},
                    new Object[]{this, renewPeriod, account, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.domainname;
//
//public class RenewLogic {
//    public static DomainNameState mutate(DomainNameState domainNameState, BigInteger renewPeriod, String account, MutationContext<DomainNameState, DomainNameState.MutableDomainNameState> mutationContext) {
//    }
//}

        if (this != updatedDomainNameState) { merge(updatedDomainNameState); } //else do nothing

    }

    public void save() {
    }

    protected void throwOnWrongEvent(DomainNameEvent event) {
        DomainNameId stateEntityId = this.getDomainNameId(); // Aggregate Id
        DomainNameId eventEntityId = ((DomainNameEvent.SqlDomainNameEvent)event).getDomainNameEventId().getDomainNameId(); // EntityBase.Aggregate.GetEventIdPropertyIdName();
        if (!stateEntityId.equals(eventEntityId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id %1$s in state but entity id %2$s in event", stateEntityId, eventEntityId);
        }


        Long stateVersion = this.getVersion();
        Long eventVersion = ((DomainNameEvent.SqlDomainNameEvent)event).getDomainNameEventId().getVersion();// Aggregate Version
        if (eventVersion == null) {
            throw new NullPointerException("event.getDomainNameEventId().getVersion() == null");
        }
        if (!(stateVersion == null && eventVersion.equals(DomainNameState.VERSION_NULL)) && !eventVersion.equals(stateVersion)) {
            throw DomainError.named("concurrencyConflict", "Conflict between state version (%1$s) and event version (%2$s)", stateVersion, eventVersion);
        }

    }


    public static class SimpleDomainNameState extends AbstractDomainNameState {

        public SimpleDomainNameState() {
        }

        public SimpleDomainNameState(List<Event> events) {
            super(events);
        }

        public static SimpleDomainNameState newForReapplying() {
            SimpleDomainNameState s = new SimpleDomainNameState();
            s.initializeForReapplying();
            return s;
        }

    }



}

