package org.dddml.suidemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;

public abstract class AbstractDaySummaryAggregate extends AbstractAggregate implements DaySummaryAggregate
{
    private DaySummaryState.MutableDaySummaryState state;

    private List<Event> changes = new ArrayList<Event>();

    public AbstractDaySummaryAggregate(DaySummaryState state) {
        this.state = (DaySummaryState.MutableDaySummaryState)state;
    }

    public DaySummaryState getState() {
        return this.state;
    }

    public List<Event> getChanges() {
        return this.changes;
    }

    public void throwOnInvalidStateTransition(Command c) {
        DaySummaryCommand.throwOnInvalidStateTransition(this.state, c);
    }

    protected void apply(Event e) {
        onApplying(e);
        state.mutate(e);
        changes.add(e);
    }


    ////////////////////////

    public static class SimpleDaySummaryAggregate extends AbstractDaySummaryAggregate {
        public SimpleDaySummaryAggregate(DaySummaryState state) {
            super(state);
        }

        @Override
        public void create(String description, int[] metaData, String[] arrayData, int[] optionalData, Long version, String commandId, String requesterId, DaySummaryCommands.Create c) {
            try {
                verifyCreate(description, metaData, arrayData, optionalData, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            Event e = newDaySummaryCreated(description, metaData, arrayData, optionalData, version, commandId, requesterId);
            apply(e);
        }

        protected void verifyCreate(String description, int[] metaData, String[] arrayData, int[] optionalData, DaySummaryCommands.Create c) {
            String Description = description;
            int[] MetaData = metaData;
            String[] ArrayData = arrayData;
            int[] OptionalData = optionalData;

            ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.daysummary.CreateLogic",
                    "verify",
                    new Class[]{DaySummaryState.class, String.class, int[].class, String[].class, int[].class, VerificationContext.class},
                    new Object[]{getState(), description, metaData, arrayData, optionalData, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.daysummary;
//
//public class CreateLogic {
//    public static void verify(DaySummaryState daySummaryState, String description, int[] metaData, String[] arrayData, int[] optionalData, VerificationContext verificationContext) {
//    }
//}

        }
           

        protected AbstractDaySummaryEvent.DaySummaryCreated newDaySummaryCreated(String description, int[] metaData, String[] arrayData, int[] optionalData, Long version, String commandId, String requesterId) {
            DaySummaryEventId eventId = new DaySummaryEventId(getState().getDay(), version);
            AbstractDaySummaryEvent.DaySummaryCreated e = new AbstractDaySummaryEvent.DaySummaryCreated();

            e.setDescription(description);
            e.setMetaData(metaData);
            e.setArrayData(arrayData);
            e.setOptionalData(optionalData);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setDaySummaryEventId(eventId);
            return e;
        }

    }

}
