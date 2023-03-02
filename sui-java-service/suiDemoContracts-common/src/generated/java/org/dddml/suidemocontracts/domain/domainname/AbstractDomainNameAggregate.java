package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;

public abstract class AbstractDomainNameAggregate extends AbstractAggregate implements DomainNameAggregate
{
    private DomainNameState.MutableDomainNameState state;

    private List<Event> changes = new ArrayList<Event>();

    public AbstractDomainNameAggregate(DomainNameState state) {
        this.state = (DomainNameState.MutableDomainNameState)state;
    }

    public DomainNameState getState() {
        return this.state;
    }

    public List<Event> getChanges() {
        return this.changes;
    }

    public void throwOnInvalidStateTransition(Command c) {
        DomainNameCommand.throwOnInvalidStateTransition(this.state, c);
    }

    protected void apply(Event e) {
        onApplying(e);
        state.mutate(e);
        changes.add(e);
    }


    ////////////////////////

    public static class SimpleDomainNameAggregate extends AbstractDomainNameAggregate {
        public SimpleDomainNameAggregate(DomainNameState state) {
            super(state);
        }

        @Override
        public void register(BigInteger registrationPeriod, Long version, String commandId, String requesterId, DomainNameCommands.Register c) {
            try {
                verifyRegister(registrationPeriod, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            Event e = newRegistered(registrationPeriod, version, commandId, requesterId);
            apply(e);
        }

        @Override
        public void renew(BigInteger renewPeriod, Long version, String commandId, String requesterId, DomainNameCommands.Renew c) {
            try {
                verifyRenew(renewPeriod, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            Event e = newRenewed(renewPeriod, version, commandId, requesterId);
            apply(e);
        }

        protected void verifyRegister(BigInteger registrationPeriod, DomainNameCommands.Register c) {
            BigInteger RegistrationPeriod = registrationPeriod;

            ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.domainname.RegisterLogic",
                    "verify",
                    new Class[]{DomainNameState.class, BigInteger.class, VerificationContext.class},
                    new Object[]{getState(), registrationPeriod, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.domainname;
//
//public class RegisterLogic {
//    public static void verify(DomainNameState domainNameState, BigInteger registrationPeriod, VerificationContext verificationContext) {
//    }
//}

        }
           

        protected void verifyRenew(BigInteger renewPeriod, DomainNameCommands.Renew c) {
            BigInteger RenewPeriod = renewPeriod;

            ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.domainname.RenewLogic",
                    "verify",
                    new Class[]{DomainNameState.class, BigInteger.class, VerificationContext.class},
                    new Object[]{getState(), renewPeriod, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.domainname;
//
//public class RenewLogic {
//    public static void verify(DomainNameState domainNameState, BigInteger renewPeriod, VerificationContext verificationContext) {
//    }
//}

        }
           

        protected AbstractDomainNameEvent.Registered newRegistered(BigInteger registrationPeriod, Long version, String commandId, String requesterId) {
            DomainNameEventId eventId = new DomainNameEventId(getState().getDomainNameId(), version);
            AbstractDomainNameEvent.Registered e = new AbstractDomainNameEvent.Registered();

            e.setRegistrationPeriod(registrationPeriod);
            e.setOwner(null); // todo Need to update 'verify' method to return event properties.

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setDomainNameEventId(eventId);
            return e;
        }

        protected AbstractDomainNameEvent.Renewed newRenewed(BigInteger renewPeriod, Long version, String commandId, String requesterId) {
            DomainNameEventId eventId = new DomainNameEventId(getState().getDomainNameId(), version);
            AbstractDomainNameEvent.Renewed e = new AbstractDomainNameEvent.Renewed();

            e.setRenewPeriod(renewPeriod);
            e.setAccount(null); // todo Need to update 'verify' method to return event properties.

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setDomainNameEventId(eventId);
            return e;
        }

    }

}

