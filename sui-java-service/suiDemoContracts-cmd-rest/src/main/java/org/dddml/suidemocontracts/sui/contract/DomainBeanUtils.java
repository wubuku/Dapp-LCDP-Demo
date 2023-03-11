// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract;

import java.math.*;

import com.github.wubuku.sui.bean.MoveEvent;
import com.github.wubuku.sui.bean.SuiMoveEventEnvelope;
import org.dddml.suidemocontracts.domain.domainname.AbstractDomainNameEvent;
import org.dddml.suidemocontracts.sui.contract.domainname.Registered;
import org.dddml.suidemocontracts.sui.contract.domainname.Renewed;
import org.dddml.suidemocontracts.domain.order.AbstractOrderEvent;
import org.dddml.suidemocontracts.sui.contract.order.OrderCreated;
import org.dddml.suidemocontracts.sui.contract.order.OrderItemRemoved;
import org.dddml.suidemocontracts.sui.contract.order.OrderItemQuantityUpdated;
import org.dddml.suidemocontracts.domain.product.AbstractProductEvent;
import org.dddml.suidemocontracts.sui.contract.product.ProductCreated;
import org.dddml.suidemocontracts.domain.orderv2.AbstractOrderV2Event;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderV2Created;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderV2ItemRemoved;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderV2ItemQuantityUpdated;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderV2EstimatedShipDateUpdated;
import org.dddml.suidemocontracts.domain.daysummary.AbstractDaySummaryEvent;
import org.dddml.suidemocontracts.sui.contract.daysummary.DaySummaryCreated;

public class DomainBeanUtils {
    private DomainBeanUtils() {
    }

    public static org.dddml.suidemocontracts.domain.Day toDay(Day contractDay) {
        if (contractDay == null) {
            return null;
        }
        org.dddml.suidemocontracts.domain.Day day = new org.dddml.suidemocontracts.domain.Day();
        day.setMonth(toMonth(contractDay.getFields().getMonth()));
        day.setNumber(contractDay.getFields().getNumber());
        day.setTimeZone(contractDay.getFields().getTimeZone());
        return day;
    }

    public static org.dddml.suidemocontracts.domain.domainname.DomainNameId toDomainNameId(DomainNameId contractDomainNameId) {
        if (contractDomainNameId == null) {
            return null;
        }
        org.dddml.suidemocontracts.domain.domainname.DomainNameId domainNameId = new org.dddml.suidemocontracts.domain.domainname.DomainNameId();
        domainNameId.setTopLevelDomain(contractDomainNameId.getFields().getTopLevelDomain());
        domainNameId.setSecondLevelDomain(contractDomainNameId.getFields().getSecondLevelDomain());
        return domainNameId;
    }

    public static org.dddml.suidemocontracts.domain.Month toMonth(Month contractMonth) {
        if (contractMonth == null) {
            return null;
        }
        org.dddml.suidemocontracts.domain.Month month = new org.dddml.suidemocontracts.domain.Month();
        month.setYear(toYear(contractMonth.getFields().getYear()));
        month.setNumber(contractMonth.getFields().getNumber());
        month.setIsLeap(contractMonth.getFields().getIsLeap());
        return month;
    }

    public static org.dddml.suidemocontracts.domain.Year toYear(Year contractYear) {
        if (contractYear == null) {
            return null;
        }
        org.dddml.suidemocontracts.domain.Year year = new org.dddml.suidemocontracts.domain.Year();
        year.setNumber(contractYear.getFields().getNumber());
        year.setCalendar(contractYear.getFields().getCalendar());
        return year;
    }


    public static AbstractDomainNameEvent.Registered toRegistered(SuiMoveEventEnvelope<Registered> eventEnvelope) {
        MoveEvent<Registered> moveEvent = eventEnvelope.getEvent().getMoveEvent();
        Registered contractEvent = moveEvent.getFields();

        AbstractDomainNameEvent.Registered registered = new AbstractDomainNameEvent.Registered();
        registered.setDomainNameId(DomainBeanUtils.toDomainNameId(contractEvent.getDomainNameId()));
        registered.setId_(contractEvent.getId());
        registered.setRegistrationPeriod(contractEvent.getRegistrationPeriod());
        registered.setOwner(contractEvent.getOwner());
        registered.setVersion(BigInteger.valueOf(-1));

        registered.setSuiTimestamp(eventEnvelope.getTimestamp());
        registered.setSuiTxDigest(eventEnvelope.getTxDigest());
        registered.setSuiEventSeq(eventEnvelope.getId().getEventSeq());

        registered.setSuiPackageId(moveEvent.getPackageId());
        registered.setSuiTransactionModule(moveEvent.getTransactionModule());
        registered.setSuiSender(moveEvent.getSender());

        return registered;
    }

    public static AbstractDomainNameEvent.Renewed toRenewed(SuiMoveEventEnvelope<Renewed> eventEnvelope) {
        MoveEvent<Renewed> moveEvent = eventEnvelope.getEvent().getMoveEvent();
        Renewed contractEvent = moveEvent.getFields();

        AbstractDomainNameEvent.Renewed renewed = new AbstractDomainNameEvent.Renewed();
        renewed.setDomainNameId(DomainBeanUtils.toDomainNameId(contractEvent.getDomainNameId()));
        renewed.setId_(contractEvent.getId());
        renewed.setRenewPeriod(contractEvent.getRenewPeriod());
        renewed.setAccount(contractEvent.getAccount());
        renewed.setVersion(contractEvent.getVersion());

        renewed.setSuiTimestamp(eventEnvelope.getTimestamp());
        renewed.setSuiTxDigest(eventEnvelope.getTxDigest());
        renewed.setSuiEventSeq(eventEnvelope.getId().getEventSeq());

        renewed.setSuiPackageId(moveEvent.getPackageId());
        renewed.setSuiTransactionModule(moveEvent.getTransactionModule());
        renewed.setSuiSender(moveEvent.getSender());

        return renewed;
    }

    public static AbstractOrderEvent.OrderCreated toOrderCreated(SuiMoveEventEnvelope<OrderCreated> eventEnvelope) {
        MoveEvent<OrderCreated> moveEvent = eventEnvelope.getEvent().getMoveEvent();
        OrderCreated contractEvent = moveEvent.getFields();

        AbstractOrderEvent.OrderCreated orderCreated = new AbstractOrderEvent.OrderCreated();
        orderCreated.setId(contractEvent.getId());
        orderCreated.setProduct(contractEvent.getProduct());
        orderCreated.setQuantity(contractEvent.getQuantity());
        orderCreated.setUnitPrice(contractEvent.getUnitPrice());
        orderCreated.setTotalAmount(contractEvent.getTotalAmount());
        orderCreated.setOwner(contractEvent.getOwner());
        orderCreated.setVersion(BigInteger.valueOf(-1));

        orderCreated.setSuiTimestamp(eventEnvelope.getTimestamp());
        orderCreated.setSuiTxDigest(eventEnvelope.getTxDigest());
        orderCreated.setSuiEventSeq(eventEnvelope.getId().getEventSeq());

        orderCreated.setSuiPackageId(moveEvent.getPackageId());
        orderCreated.setSuiTransactionModule(moveEvent.getTransactionModule());
        orderCreated.setSuiSender(moveEvent.getSender());

        return orderCreated;
    }

    public static AbstractOrderEvent.OrderItemRemoved toOrderItemRemoved(SuiMoveEventEnvelope<OrderItemRemoved> eventEnvelope) {
        MoveEvent<OrderItemRemoved> moveEvent = eventEnvelope.getEvent().getMoveEvent();
        OrderItemRemoved contractEvent = moveEvent.getFields();

        AbstractOrderEvent.OrderItemRemoved orderItemRemoved = new AbstractOrderEvent.OrderItemRemoved();
        orderItemRemoved.setId(contractEvent.getId());
        orderItemRemoved.setProductId(contractEvent.getProductId());
        orderItemRemoved.setVersion(contractEvent.getVersion());

        orderItemRemoved.setSuiTimestamp(eventEnvelope.getTimestamp());
        orderItemRemoved.setSuiTxDigest(eventEnvelope.getTxDigest());
        orderItemRemoved.setSuiEventSeq(eventEnvelope.getId().getEventSeq());

        orderItemRemoved.setSuiPackageId(moveEvent.getPackageId());
        orderItemRemoved.setSuiTransactionModule(moveEvent.getTransactionModule());
        orderItemRemoved.setSuiSender(moveEvent.getSender());

        return orderItemRemoved;
    }

    public static AbstractOrderEvent.OrderItemQuantityUpdated toOrderItemQuantityUpdated(SuiMoveEventEnvelope<OrderItemQuantityUpdated> eventEnvelope) {
        MoveEvent<OrderItemQuantityUpdated> moveEvent = eventEnvelope.getEvent().getMoveEvent();
        OrderItemQuantityUpdated contractEvent = moveEvent.getFields();

        AbstractOrderEvent.OrderItemQuantityUpdated orderItemQuantityUpdated = new AbstractOrderEvent.OrderItemQuantityUpdated();
        orderItemQuantityUpdated.setId(contractEvent.getId());
        orderItemQuantityUpdated.setProductId(contractEvent.getProductId());
        orderItemQuantityUpdated.setQuantity(contractEvent.getQuantity());
        orderItemQuantityUpdated.setVersion(contractEvent.getVersion());

        orderItemQuantityUpdated.setSuiTimestamp(eventEnvelope.getTimestamp());
        orderItemQuantityUpdated.setSuiTxDigest(eventEnvelope.getTxDigest());
        orderItemQuantityUpdated.setSuiEventSeq(eventEnvelope.getId().getEventSeq());

        orderItemQuantityUpdated.setSuiPackageId(moveEvent.getPackageId());
        orderItemQuantityUpdated.setSuiTransactionModule(moveEvent.getTransactionModule());
        orderItemQuantityUpdated.setSuiSender(moveEvent.getSender());

        return orderItemQuantityUpdated;
    }

    public static AbstractProductEvent.ProductCreated toProductCreated(SuiMoveEventEnvelope<ProductCreated> eventEnvelope) {
        MoveEvent<ProductCreated> moveEvent = eventEnvelope.getEvent().getMoveEvent();
        ProductCreated contractEvent = moveEvent.getFields();

        AbstractProductEvent.ProductCreated productCreated = new AbstractProductEvent.ProductCreated();
        productCreated.setProductId(contractEvent.getProductId());
        productCreated.setId_(contractEvent.getId());
        productCreated.setName(contractEvent.getName());
        productCreated.setUnitPrice(contractEvent.getUnitPrice());
        productCreated.setVersion(BigInteger.valueOf(-1));

        productCreated.setSuiTimestamp(eventEnvelope.getTimestamp());
        productCreated.setSuiTxDigest(eventEnvelope.getTxDigest());
        productCreated.setSuiEventSeq(eventEnvelope.getId().getEventSeq());

        productCreated.setSuiPackageId(moveEvent.getPackageId());
        productCreated.setSuiTransactionModule(moveEvent.getTransactionModule());
        productCreated.setSuiSender(moveEvent.getSender());

        return productCreated;
    }

    public static AbstractOrderV2Event.OrderV2Created toOrderV2Created(SuiMoveEventEnvelope<OrderV2Created> eventEnvelope) {
        MoveEvent<OrderV2Created> moveEvent = eventEnvelope.getEvent().getMoveEvent();
        OrderV2Created contractEvent = moveEvent.getFields();

        AbstractOrderV2Event.OrderV2Created orderV2Created = new AbstractOrderV2Event.OrderV2Created();
        orderV2Created.setOrderId(contractEvent.getOrderId());
        orderV2Created.setId_(contractEvent.getId());
        orderV2Created.setProduct(contractEvent.getProduct());
        orderV2Created.setQuantity(contractEvent.getQuantity());
        orderV2Created.setUnitPrice(contractEvent.getUnitPrice());
        orderV2Created.setTotalAmount(contractEvent.getTotalAmount());
        orderV2Created.setOwner(contractEvent.getOwner());
        orderV2Created.setVersion(BigInteger.valueOf(-1));

        orderV2Created.setSuiTimestamp(eventEnvelope.getTimestamp());
        orderV2Created.setSuiTxDigest(eventEnvelope.getTxDigest());
        orderV2Created.setSuiEventSeq(eventEnvelope.getId().getEventSeq());

        orderV2Created.setSuiPackageId(moveEvent.getPackageId());
        orderV2Created.setSuiTransactionModule(moveEvent.getTransactionModule());
        orderV2Created.setSuiSender(moveEvent.getSender());

        return orderV2Created;
    }

    public static AbstractOrderV2Event.OrderV2ItemRemoved toOrderV2ItemRemoved(SuiMoveEventEnvelope<OrderV2ItemRemoved> eventEnvelope) {
        MoveEvent<OrderV2ItemRemoved> moveEvent = eventEnvelope.getEvent().getMoveEvent();
        OrderV2ItemRemoved contractEvent = moveEvent.getFields();

        AbstractOrderV2Event.OrderV2ItemRemoved orderV2ItemRemoved = new AbstractOrderV2Event.OrderV2ItemRemoved();
        orderV2ItemRemoved.setOrderId(contractEvent.getOrderId());
        orderV2ItemRemoved.setId_(contractEvent.getId());
        orderV2ItemRemoved.setProductId(contractEvent.getProductId());
        orderV2ItemRemoved.setVersion(contractEvent.getVersion());

        orderV2ItemRemoved.setSuiTimestamp(eventEnvelope.getTimestamp());
        orderV2ItemRemoved.setSuiTxDigest(eventEnvelope.getTxDigest());
        orderV2ItemRemoved.setSuiEventSeq(eventEnvelope.getId().getEventSeq());

        orderV2ItemRemoved.setSuiPackageId(moveEvent.getPackageId());
        orderV2ItemRemoved.setSuiTransactionModule(moveEvent.getTransactionModule());
        orderV2ItemRemoved.setSuiSender(moveEvent.getSender());

        return orderV2ItemRemoved;
    }

    public static AbstractOrderV2Event.OrderV2ItemQuantityUpdated toOrderV2ItemQuantityUpdated(SuiMoveEventEnvelope<OrderV2ItemQuantityUpdated> eventEnvelope) {
        MoveEvent<OrderV2ItemQuantityUpdated> moveEvent = eventEnvelope.getEvent().getMoveEvent();
        OrderV2ItemQuantityUpdated contractEvent = moveEvent.getFields();

        AbstractOrderV2Event.OrderV2ItemQuantityUpdated orderV2ItemQuantityUpdated = new AbstractOrderV2Event.OrderV2ItemQuantityUpdated();
        orderV2ItemQuantityUpdated.setOrderId(contractEvent.getOrderId());
        orderV2ItemQuantityUpdated.setId_(contractEvent.getId());
        orderV2ItemQuantityUpdated.setProductId(contractEvent.getProductId());
        orderV2ItemQuantityUpdated.setQuantity(contractEvent.getQuantity());
        orderV2ItemQuantityUpdated.setVersion(contractEvent.getVersion());

        orderV2ItemQuantityUpdated.setSuiTimestamp(eventEnvelope.getTimestamp());
        orderV2ItemQuantityUpdated.setSuiTxDigest(eventEnvelope.getTxDigest());
        orderV2ItemQuantityUpdated.setSuiEventSeq(eventEnvelope.getId().getEventSeq());

        orderV2ItemQuantityUpdated.setSuiPackageId(moveEvent.getPackageId());
        orderV2ItemQuantityUpdated.setSuiTransactionModule(moveEvent.getTransactionModule());
        orderV2ItemQuantityUpdated.setSuiSender(moveEvent.getSender());

        return orderV2ItemQuantityUpdated;
    }

    public static AbstractOrderV2Event.OrderV2EstimatedShipDateUpdated toOrderV2EstimatedShipDateUpdated(SuiMoveEventEnvelope<OrderV2EstimatedShipDateUpdated> eventEnvelope) {
        MoveEvent<OrderV2EstimatedShipDateUpdated> moveEvent = eventEnvelope.getEvent().getMoveEvent();
        OrderV2EstimatedShipDateUpdated contractEvent = moveEvent.getFields();

        AbstractOrderV2Event.OrderV2EstimatedShipDateUpdated orderV2EstimatedShipDateUpdated = new AbstractOrderV2Event.OrderV2EstimatedShipDateUpdated();
        orderV2EstimatedShipDateUpdated.setOrderId(contractEvent.getOrderId());
        orderV2EstimatedShipDateUpdated.setId_(contractEvent.getId());
        orderV2EstimatedShipDateUpdated.setEstimatedShipDate(DomainBeanUtils.toDay(contractEvent.getEstimatedShipDate()));
        orderV2EstimatedShipDateUpdated.setVersion(contractEvent.getVersion());

        orderV2EstimatedShipDateUpdated.setSuiTimestamp(eventEnvelope.getTimestamp());
        orderV2EstimatedShipDateUpdated.setSuiTxDigest(eventEnvelope.getTxDigest());
        orderV2EstimatedShipDateUpdated.setSuiEventSeq(eventEnvelope.getId().getEventSeq());

        orderV2EstimatedShipDateUpdated.setSuiPackageId(moveEvent.getPackageId());
        orderV2EstimatedShipDateUpdated.setSuiTransactionModule(moveEvent.getTransactionModule());
        orderV2EstimatedShipDateUpdated.setSuiSender(moveEvent.getSender());

        return orderV2EstimatedShipDateUpdated;
    }

    public static AbstractDaySummaryEvent.DaySummaryCreated toDaySummaryCreated(SuiMoveEventEnvelope<DaySummaryCreated> eventEnvelope) {
        MoveEvent<DaySummaryCreated> moveEvent = eventEnvelope.getEvent().getMoveEvent();
        DaySummaryCreated contractEvent = moveEvent.getFields();

        AbstractDaySummaryEvent.DaySummaryCreated daySummaryCreated = new AbstractDaySummaryEvent.DaySummaryCreated();
        daySummaryCreated.setDay(DomainBeanUtils.toDay(contractEvent.getDay()));
        daySummaryCreated.setId_(contractEvent.getId());
        daySummaryCreated.setDescription(contractEvent.getDescription());
        daySummaryCreated.setMetaData(contractEvent.getMetaData());
        daySummaryCreated.setArrayData(contractEvent.getArrayData());
        daySummaryCreated.setOptionalData(contractEvent.getOptionalData());
        daySummaryCreated.setVersion(BigInteger.valueOf(-1));

        daySummaryCreated.setSuiTimestamp(eventEnvelope.getTimestamp());
        daySummaryCreated.setSuiTxDigest(eventEnvelope.getTxDigest());
        daySummaryCreated.setSuiEventSeq(eventEnvelope.getId().getEventSeq());

        daySummaryCreated.setSuiPackageId(moveEvent.getPackageId());
        daySummaryCreated.setSuiTransactionModule(moveEvent.getTransactionModule());
        daySummaryCreated.setSuiSender(moveEvent.getSender());

        return daySummaryCreated;
    }

}
