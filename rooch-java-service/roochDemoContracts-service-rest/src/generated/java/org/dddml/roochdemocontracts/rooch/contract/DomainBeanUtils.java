package org.dddml.roochdemocontracts.rooch.contract;

import com.github.wubuku.rooch.bean.AnnotatedEventView;
import com.github.wubuku.rooch.bean.EventID;
import org.dddml.roochdemocontracts.domain.RoochEvent;
import org.dddml.roochdemocontracts.domain.RoochEventId;
import org.dddml.roochdemocontracts.domain.order.AbstractOrderEvent;
import org.dddml.roochdemocontracts.rooch.contract.order.OrderCreated;
import org.dddml.roochdemocontracts.rooch.contract.order.OrderItemRemoved;

import java.math.BigInteger;

public class DomainBeanUtils {
    private DomainBeanUtils() {
    }

    public static org.dddml.roochdemocontracts.domain.Day toDay(Day contractDay) {
        if (contractDay == null) {
            return null;
        }
        org.dddml.roochdemocontracts.domain.Day day = new org.dddml.roochdemocontracts.domain.Day();
        day.setMonth(toMonth(contractDay.getValue().getMonth()));
        day.setNumber(contractDay.getValue().getNumber());
        day.setTimeZone(contractDay.getValue().getTimeZone());
        return day;
    }

    private static org.dddml.roochdemocontracts.domain.Month toMonth(Month contractMonth) {
        //todo convert contract Month to domain Month
        return null;
    }


    public static AbstractOrderEvent.OrderCreated toOrderCreated(AnnotatedEventView<OrderCreated> eventEnvelope) {
        OrderCreated contractEvent = eventEnvelope.getParsedEventData().getValue();

        AbstractOrderEvent.OrderCreated orderCreated = new AbstractOrderEvent.OrderCreated();
        orderCreated.setOrderId(contractEvent.getOrderId());
        orderCreated.setId_(contractEvent.getId().getValue().getVec()[0]);
        orderCreated.setProductObjId(contractEvent.getProductObjId());
        orderCreated.setQuantity(contractEvent.getQuantity());
        orderCreated.setUnitPrice(contractEvent.getUnitPrice());
        orderCreated.setTotalAmount(contractEvent.getTotalAmount());
        orderCreated.setOwner(contractEvent.getOwner());
        orderCreated.setVersion(BigInteger.valueOf(-1));

        setRoochEventProperties(orderCreated, eventEnvelope);

        return orderCreated;
    }

    public static AbstractOrderEvent.OrderItemRemoved toOrderItemRemoved(AnnotatedEventView<OrderItemRemoved> eventEnvelope) {
        OrderItemRemoved contractEvent = eventEnvelope.getParsedEventData().getValue();

        AbstractOrderEvent.OrderItemRemoved orderItemRemoved = new AbstractOrderEvent.OrderItemRemoved();
        orderItemRemoved.setOrderId(contractEvent.getOrderId());
        orderItemRemoved.setId_(contractEvent.getId());
        orderItemRemoved.setProductObjId(contractEvent.getProductObjId());
        orderItemRemoved.setVersion(contractEvent.getVersion());

        setRoochEventProperties(orderItemRemoved, eventEnvelope);

        return orderItemRemoved;
    }

    private static void setRoochEventProperties(RoochEvent.MutableRoochEvent domainRoochEvent, AnnotatedEventView<?> eventEnvelope) {
        domainRoochEvent.setRoochEventId(toRoochEventId(eventEnvelope.getEvent().getEventId()));
        domainRoochEvent.setRoochTypeTag(eventEnvelope.getEvent().getTypeTag());
        domainRoochEvent.setRoochEventIndex(eventEnvelope.getEvent().getEventIndex());
        domainRoochEvent.setRoochTxHash(eventEnvelope.getTxHash());
        domainRoochEvent.setRoochSender(eventEnvelope.getSender());
        domainRoochEvent.setRoochTimestampMs(eventEnvelope.getTimestampMs());
    }

    private static RoochEventId toRoochEventId(EventID eventId) {
        return new RoochEventId(eventId.getEventHandleId(), eventId.getEventSeq());
    }
}
