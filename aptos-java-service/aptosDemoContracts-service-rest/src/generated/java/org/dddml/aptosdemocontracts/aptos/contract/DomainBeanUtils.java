// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.aptos.contract;

import java.math.*;

import com.github.wubuku.aptos.bean.Event;
import com.github.wubuku.aptos.bean.Option;
import org.dddml.aptosdemocontracts.aptos.contract.product.ProductEvent;
import org.dddml.aptosdemocontracts.domain.AptosEvent;
import org.dddml.aptosdemocontracts.domain.AptosEventGuid;
import org.dddml.aptosdemocontracts.domain.order.AbstractOrderEvent;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderCreated;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderItemRemoved;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderItemQuantityUpdated;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderEstimatedShipDateUpdated;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderShipGroupAdded;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderShipGroupQuantityCanceled;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderShipGroupItemRemoved;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderShipGroupRemoved;
import org.dddml.aptosdemocontracts.domain.product.AbstractProductEvent;
import org.dddml.aptosdemocontracts.aptos.contract.product.ProductCreated;
import org.dddml.aptosdemocontracts.aptos.contract.product.ProductUpdated;
import org.dddml.aptosdemocontracts.aptos.contract.product.ProductDeleted;
import org.dddml.aptosdemocontracts.domain.daysummary.AbstractDaySummaryEvent;
import org.dddml.aptosdemocontracts.aptos.contract.daysummary.DaySummaryCreated;

/**
 * Utils that convert beans in the contract package to domain beans.
 */
public class DomainBeanUtils {
    private DomainBeanUtils() {
    }

    public static org.dddml.aptosdemocontracts.domain.Day toDay(Day contractDay) {
        if (contractDay == null) {
            return null;
        }
        org.dddml.aptosdemocontracts.domain.Day day = new org.dddml.aptosdemocontracts.domain.Day();
        day.setMonth(toMonth(contractDay.getMonth()));
        day.setNumber(contractDay.getNumber());
        day.setTimeZone(contractDay.getTimeZone());
        return day;
    }

    public static org.dddml.aptosdemocontracts.domain.Month toMonth(Month contractMonth) {
        if (contractMonth == null) {
            return null;
        }
        org.dddml.aptosdemocontracts.domain.Month month = new org.dddml.aptosdemocontracts.domain.Month();
        month.setYear(toYear(contractMonth.getYear()));
        month.setNumber(contractMonth.getNumber());
        month.setIsLeap(contractMonth.getIsLeap());
        return month;
    }

    public static org.dddml.aptosdemocontracts.domain.Year toYear(Year contractYear) {
        if (contractYear == null) {
            return null;
        }
        org.dddml.aptosdemocontracts.domain.Year year = new org.dddml.aptosdemocontracts.domain.Year();
        year.setNumber(contractYear.getNumber());
        year.setCalendar(contractYear.getCalendar());
        return year;
    }


    public static AbstractOrderEvent.OrderCreated toOrderCreated(Event<OrderCreated> eventEnvelope) {
        OrderCreated contractEvent = eventEnvelope.getData();

        AbstractOrderEvent.OrderCreated orderCreated = new AbstractOrderEvent.OrderCreated();
        orderCreated.setOrderId(contractEvent.getOrderId());
        orderCreated.setProductId(contractEvent.getProductId());
        orderCreated.setQuantity(contractEvent.getQuantity());
        orderCreated.setUnitPrice(contractEvent.getUnitPrice());
        orderCreated.setTotalAmount(contractEvent.getTotalAmount());
        orderCreated.setOwner(contractEvent.getOwner());
        orderCreated.setVersion(BigInteger.valueOf(-1));

        setAptosEventProperties(orderCreated, eventEnvelope);

        return orderCreated;
    }

    public static AbstractOrderEvent.OrderItemRemoved toOrderItemRemoved(Event<OrderItemRemoved> eventEnvelope) {
        OrderItemRemoved contractEvent = eventEnvelope.getData();

        AbstractOrderEvent.OrderItemRemoved orderItemRemoved = new AbstractOrderEvent.OrderItemRemoved();
        orderItemRemoved.setOrderId(contractEvent.getOrderId());
        orderItemRemoved.setProductId(contractEvent.getProductId());
        orderItemRemoved.setVersion(contractEvent.getVersion());

        setAptosEventProperties(orderItemRemoved, eventEnvelope);

        return orderItemRemoved;
    }

    public static AbstractOrderEvent.OrderItemQuantityUpdated toOrderItemQuantityUpdated(Event<OrderItemQuantityUpdated> eventEnvelope) {
        OrderItemQuantityUpdated contractEvent = eventEnvelope.getData();

        AbstractOrderEvent.OrderItemQuantityUpdated orderItemQuantityUpdated = new AbstractOrderEvent.OrderItemQuantityUpdated();
        orderItemQuantityUpdated.setOrderId(contractEvent.getOrderId());
        orderItemQuantityUpdated.setProductId(contractEvent.getProductId());
        orderItemQuantityUpdated.setQuantity(contractEvent.getQuantity());
        orderItemQuantityUpdated.setVersion(contractEvent.getVersion());

        setAptosEventProperties(orderItemQuantityUpdated, eventEnvelope);

        return orderItemQuantityUpdated;
    }

    public static AbstractOrderEvent.OrderEstimatedShipDateUpdated toOrderEstimatedShipDateUpdated(Event<OrderEstimatedShipDateUpdated> eventEnvelope) {
        OrderEstimatedShipDateUpdated contractEvent = eventEnvelope.getData();

        AbstractOrderEvent.OrderEstimatedShipDateUpdated orderEstimatedShipDateUpdated = new AbstractOrderEvent.OrderEstimatedShipDateUpdated();
        orderEstimatedShipDateUpdated.setOrderId(contractEvent.getOrderId());
        orderEstimatedShipDateUpdated.setEstimatedShipDate(DomainBeanUtils.toDay(contractEvent.getEstimatedShipDate()));
        orderEstimatedShipDateUpdated.setVersion(contractEvent.getVersion());

        setAptosEventProperties(orderEstimatedShipDateUpdated, eventEnvelope);

        return orderEstimatedShipDateUpdated;
    }

    public static AbstractOrderEvent.OrderShipGroupAdded toOrderShipGroupAdded(Event<OrderShipGroupAdded> eventEnvelope) {
        OrderShipGroupAdded contractEvent = eventEnvelope.getData();

        AbstractOrderEvent.OrderShipGroupAdded orderShipGroupAdded = new AbstractOrderEvent.OrderShipGroupAdded();
        orderShipGroupAdded.setOrderId(contractEvent.getOrderId());
        orderShipGroupAdded.setShipGroupSeqId(contractEvent.getShipGroupSeqId());
        orderShipGroupAdded.setShipmentMethod(contractEvent.getShipmentMethod());
        orderShipGroupAdded.setProductId(contractEvent.getProductId());
        orderShipGroupAdded.setQuantity(contractEvent.getQuantity());
        orderShipGroupAdded.setVersion(contractEvent.getVersion());

        setAptosEventProperties(orderShipGroupAdded, eventEnvelope);

        return orderShipGroupAdded;
    }

    public static AbstractOrderEvent.OrderShipGroupQuantityCanceled toOrderShipGroupQuantityCanceled(Event<OrderShipGroupQuantityCanceled> eventEnvelope) {
        OrderShipGroupQuantityCanceled contractEvent = eventEnvelope.getData();

        AbstractOrderEvent.OrderShipGroupQuantityCanceled orderShipGroupQuantityCanceled = new AbstractOrderEvent.OrderShipGroupQuantityCanceled();
        orderShipGroupQuantityCanceled.setOrderId(contractEvent.getOrderId());
        orderShipGroupQuantityCanceled.setShipGroupSeqId(contractEvent.getShipGroupSeqId());
        orderShipGroupQuantityCanceled.setProductId(contractEvent.getProductId());
        orderShipGroupQuantityCanceled.setCancelQuantity(contractEvent.getCancelQuantity());
        orderShipGroupQuantityCanceled.setVersion(contractEvent.getVersion());

        setAptosEventProperties(orderShipGroupQuantityCanceled, eventEnvelope);

        return orderShipGroupQuantityCanceled;
    }

    public static AbstractOrderEvent.OrderShipGroupItemRemoved toOrderShipGroupItemRemoved(Event<OrderShipGroupItemRemoved> eventEnvelope) {
        OrderShipGroupItemRemoved contractEvent = eventEnvelope.getData();

        AbstractOrderEvent.OrderShipGroupItemRemoved orderShipGroupItemRemoved = new AbstractOrderEvent.OrderShipGroupItemRemoved();
        orderShipGroupItemRemoved.setOrderId(contractEvent.getOrderId());
        orderShipGroupItemRemoved.setShipGroupSeqId(contractEvent.getShipGroupSeqId());
        orderShipGroupItemRemoved.setProductId(contractEvent.getProductId());
        orderShipGroupItemRemoved.setVersion(contractEvent.getVersion());

        setAptosEventProperties(orderShipGroupItemRemoved, eventEnvelope);

        return orderShipGroupItemRemoved;
    }

    public static AbstractOrderEvent.OrderShipGroupRemoved toOrderShipGroupRemoved(Event<OrderShipGroupRemoved> eventEnvelope) {
        OrderShipGroupRemoved contractEvent = eventEnvelope.getData();

        AbstractOrderEvent.OrderShipGroupRemoved orderShipGroupRemoved = new AbstractOrderEvent.OrderShipGroupRemoved();
        orderShipGroupRemoved.setOrderId(contractEvent.getOrderId());
        orderShipGroupRemoved.setShipGroupSeqId(contractEvent.getShipGroupSeqId());
        orderShipGroupRemoved.setVersion(contractEvent.getVersion());

        setAptosEventProperties(orderShipGroupRemoved, eventEnvelope);

        return orderShipGroupRemoved;
    }
//
//    public static AbstractProductEvent.ProductCreated toProductCreated(Event<ProductCreated> eventEnvelope) {
//        ProductCreated contractEvent = eventEnvelope.getData();
//
//        AbstractProductEvent.ProductCreated productCreated = new AbstractProductEvent.ProductCreated();
//        productCreated.setProductId(contractEvent.getProductId());
//        productCreated.setName(contractEvent.getName());
//        productCreated.setUnitPrice(contractEvent.getUnitPrice());
//        productCreated.setVersion(BigInteger.valueOf(-1));
//
//        setAptosEventProperties(productCreated, eventEnvelope);
//
//        return productCreated;
//    }
//
//    public static AbstractProductEvent.ProductUpdated toProductUpdated(Event<ProductUpdated> eventEnvelope) {
//        ProductUpdated contractEvent = eventEnvelope.getData();
//
//        AbstractProductEvent.ProductUpdated productUpdated = new AbstractProductEvent.ProductUpdated();
//        productUpdated.setProductId(contractEvent.getProductId());
//        productUpdated.setName(contractEvent.getName());
//        productUpdated.setUnitPrice(contractEvent.getUnitPrice());
//        productUpdated.setVersion(contractEvent.getVersion());
//
//        setAptosEventProperties(productUpdated, eventEnvelope);
//
//        return productUpdated;
//    }
//
//    public static AbstractProductEvent.ProductDeleted toProductDeleted(Event<ProductDeleted> eventEnvelope) {
//        ProductDeleted contractEvent = eventEnvelope.getData();
//
//        AbstractProductEvent.ProductDeleted productDeleted = new AbstractProductEvent.ProductDeleted();
//        productDeleted.setProductId(contractEvent.getProductId());
//        productDeleted.setVersion(contractEvent.getVersion());
//
//        setAptosEventProperties(productDeleted, eventEnvelope);
//
//        return productDeleted;
//    }

    public static AbstractProductEvent.ProductEvent toProductEvent(Event<ProductEvent> eventEnvelope) {
        return null;//todo
    }

    public static AbstractDaySummaryEvent.DaySummaryCreated toDaySummaryCreated(Event<DaySummaryCreated> eventEnvelope) {
        DaySummaryCreated contractEvent = eventEnvelope.getData();

        AbstractDaySummaryEvent.DaySummaryCreated daySummaryCreated = new AbstractDaySummaryEvent.DaySummaryCreated();
        daySummaryCreated.setDay(DomainBeanUtils.toDay(contractEvent.getDay()));
        daySummaryCreated.setDescription(contractEvent.getDescription());
        daySummaryCreated.setMetaData(contractEvent.getMetaData());
        daySummaryCreated.setArrayData(contractEvent.getArrayData());
        daySummaryCreated.setOptionalData(extractOptionalValue(contractEvent.getOptionalData()));
        daySummaryCreated.setU16ArrayData(contractEvent.getU16ArrayData());
        daySummaryCreated.setU32ArrayData(contractEvent.getU32ArrayData());
        daySummaryCreated.setU64ArrayData(contractEvent.getU64ArrayData());
        daySummaryCreated.setU128ArrayData(contractEvent.getU128ArrayData());
        daySummaryCreated.setU256ArrayData(contractEvent.getU256ArrayData());
        daySummaryCreated.setVersion(BigInteger.valueOf(-1));

        setAptosEventProperties(daySummaryCreated, eventEnvelope);

        return daySummaryCreated;
    }

    public static org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemTableItemAdded toPersistenceOrderItemTableItemAdded(Event<OrderItemTableItemAdded> eventEnvelope) {
        OrderItemTableItemAdded contractEvent = eventEnvelope.getData();
        org.dddml.aptosdemocontracts.domain.order.OrderItemId id = new org.dddml.aptosdemocontracts.domain.order.OrderItemId(contractEvent.getOrderId(), contractEvent.getProductId());
        org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemTableItemAdded e = new org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemTableItemAdded();
        e.setOrderItemId(id);
        setAptosEventProperties(e, eventEnvelope);
        return e;
    }

    public static org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderShipGroupTableItemAdded toPersistenceOrderShipGroupTableItemAdded(Event<OrderShipGroupTableItemAdded> eventEnvelope) {
        OrderShipGroupTableItemAdded contractEvent = eventEnvelope.getData();
        org.dddml.aptosdemocontracts.domain.order.OrderShipGroupId id = new org.dddml.aptosdemocontracts.domain.order.OrderShipGroupId(contractEvent.getOrderId(), contractEvent.getShipGroupSeqId());
        org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderShipGroupTableItemAdded e = new org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderShipGroupTableItemAdded();
        e.setOrderShipGroupId(id);
        setAptosEventProperties(e, eventEnvelope);
        return e;
    }

    public static org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemShipGroupAssociationTableItemAdded toPersistenceOrderItemShipGroupAssociationTableItemAdded(Event<OrderItemShipGroupAssociationTableItemAdded> eventEnvelope) {
        OrderItemShipGroupAssociationTableItemAdded contractEvent = eventEnvelope.getData();
        org.dddml.aptosdemocontracts.domain.order.OrderItemShipGroupAssociationId id = new org.dddml.aptosdemocontracts.domain.order.OrderItemShipGroupAssociationId(contractEvent.getOrderId(), contractEvent.getOrderShipGroupShipGroupSeqId(), contractEvent.getProductId());
        org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemShipGroupAssociationTableItemAdded e = new org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemShipGroupAssociationTableItemAdded();
        e.setOrderItemShipGroupAssociationId(id);
        setAptosEventProperties(e, eventEnvelope);
        return e;
    }

    public static org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemShipGroupAssocSubitemTableItemAdded toPersistenceOrderItemShipGroupAssocSubitemTableItemAdded(Event<OrderItemShipGroupAssocSubitemTableItemAdded> eventEnvelope) {
        OrderItemShipGroupAssocSubitemTableItemAdded contractEvent = eventEnvelope.getData();
        org.dddml.aptosdemocontracts.domain.order.OrderItemShipGroupAssocSubitemId id = new org.dddml.aptosdemocontracts.domain.order.OrderItemShipGroupAssocSubitemId(contractEvent.getOrderId(), contractEvent.getOrderShipGroupShipGroupSeqId(), contractEvent.getOrderItemShipGroupAssociationProductId(), toDay(contractEvent.getOrderItemShipGroupAssocSubitemDay()));
        org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemShipGroupAssocSubitemTableItemAdded e = new org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemShipGroupAssocSubitemTableItemAdded();
        e.setOrderItemShipGroupAssocSubitemId(id);
        setAptosEventProperties(e, eventEnvelope);
        return e;
    }

    public static void setAptosEventProperties(AptosEvent.MutableAptosEvent domainAptosEvent, Event<?> eventEnvelope) {
        domainAptosEvent.setAptosEventGuid(toAptosEventGuid(eventEnvelope.getGuid()));
        domainAptosEvent.setAptosEventType(eventEnvelope.getType());
        domainAptosEvent.setAptosEventSequenceNumber(new BigInteger(eventEnvelope.getSequenceNumber()));
        domainAptosEvent.setAptosEventVersion(new BigInteger(eventEnvelope.getVersion()));
    }

    public static AptosEventGuid toAptosEventGuid(com.github.wubuku.aptos.bean.Event.Guid eventGuid) {
        return new AptosEventGuid(new BigInteger(eventGuid.getCreationNumber()),eventGuid.getAccountAddress());
    }

    private static <T> T extractOptionalValue(Option<T> optionView) {
        return optionView == null ? null
                : (optionView.getVec() == null || optionView.getVec().size() == 0) ? null
                : optionView.getVec().get(0);
    }
}
