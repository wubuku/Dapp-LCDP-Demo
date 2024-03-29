// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;

public abstract class AbstractDomainNameAggregate extends AbstractAggregate implements DomainNameAggregate {
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
        public void register(BigInteger registrationPeriod, Long offChainVersion, String commandId, String requesterId, DomainNameCommands.Register c) {
            java.util.function.Supplier<DomainNameEvent.Registered> eventFactory = () -> newRegistered(registrationPeriod, offChainVersion, commandId, requesterId);
            DomainNameEvent.Registered e;
            try {
                e = verifyRegister(eventFactory, registrationPeriod, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            apply(e);
        }

        @Override
        public void renew(BigInteger renewPeriod, Long offChainVersion, String commandId, String requesterId, DomainNameCommands.Renew c) {
            java.util.function.Supplier<DomainNameEvent.Renewed> eventFactory = () -> newRenewed(renewPeriod, offChainVersion, commandId, requesterId);
            DomainNameEvent.Renewed e;
            try {
                e = verifyRenew(eventFactory, renewPeriod, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            apply(e);
        }

        protected DomainNameEvent.Registered verifyRegister(java.util.function.Supplier<DomainNameEvent.Registered> eventFactory, BigInteger registrationPeriod, DomainNameCommands.Register c) {
            BigInteger RegistrationPeriod = registrationPeriod;

            DomainNameEvent.Registered e = (DomainNameEvent.Registered) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.domainname.RegisterLogic",
                    "verify",
                    new Class[]{java.util.function.Supplier.class, DomainNameState.class, BigInteger.class, VerificationContext.class},
                    new Object[]{eventFactory, getState(), registrationPeriod, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.domainname;
//
//public class RegisterLogic {
//    public static DomainNameEvent.Registered verify(java.util.function.Supplier<DomainNameEvent.Registered> eventFactory, DomainNameState domainNameState, BigInteger registrationPeriod, VerificationContext verificationContext) {
//    }
//}

            return e;
        }
           

        protected DomainNameEvent.Renewed verifyRenew(java.util.function.Supplier<DomainNameEvent.Renewed> eventFactory, BigInteger renewPeriod, DomainNameCommands.Renew c) {
            BigInteger RenewPeriod = renewPeriod;

            DomainNameEvent.Renewed e = (DomainNameEvent.Renewed) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.domainname.RenewLogic",
                    "verify",
                    new Class[]{java.util.function.Supplier.class, DomainNameState.class, BigInteger.class, VerificationContext.class},
                    new Object[]{eventFactory, getState(), renewPeriod, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.domainname;
//
//public class RenewLogic {
//    public static DomainNameEvent.Renewed verify(java.util.function.Supplier<DomainNameEvent.Renewed> eventFactory, DomainNameState domainNameState, BigInteger renewPeriod, VerificationContext verificationContext) {
//    }
//}

            return e;
        }
           

        protected AbstractDomainNameEvent.Registered newRegistered(BigInteger registrationPeriod, Long offChainVersion, String commandId, String requesterId) {
            DomainNameEventId eventId = new DomainNameEventId(getState().getDomainNameId(), null);
            AbstractDomainNameEvent.Registered e = new AbstractDomainNameEvent.Registered();

            e.setRegistrationPeriod(registrationPeriod);
            e.setOwner(null);
            e.setSuiTimestamp(null);
            e.setSuiTxDigest(null);
            e.setSuiEventSeq(null);
            e.setSuiPackageId(null);
            e.setSuiTransactionModule(null);
            e.setSuiSender(null);
            e.setSuiType(null);
            e.setStatus(null);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setDomainNameEventId(eventId);
            return e;
        }

        protected AbstractDomainNameEvent.Renewed newRenewed(BigInteger renewPeriod, Long offChainVersion, String commandId, String requesterId) {
            DomainNameEventId eventId = new DomainNameEventId(getState().getDomainNameId(), null);
            AbstractDomainNameEvent.Renewed e = new AbstractDomainNameEvent.Renewed();

            e.setRenewPeriod(renewPeriod);
            e.setAccount(null);
            e.setSuiTimestamp(null);
            e.setSuiTxDigest(null);
            e.setSuiEventSeq(null);
            e.setSuiPackageId(null);
            e.setSuiTransactionModule(null);
            e.setSuiSender(null);
            e.setSuiType(null);
            e.setStatus(null);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setDomainNameEventId(eventId);
            return e;
        }

    }

}

