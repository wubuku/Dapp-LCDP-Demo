// <autogenerated>
//   This file was generated by dddappp code generator.
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
import org.dddml.suidemocontracts.sui.contract.order.OrderDeleted;
import org.dddml.suidemocontracts.domain.product.AbstractProductEvent;
import org.dddml.suidemocontracts.sui.contract.product.ProductCreated;
import org.dddml.suidemocontracts.domain.orderv2.AbstractOrderV2Event;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderV2Created;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderV2ItemRemoved;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderV2ItemQuantityUpdated;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderV2EstimatedShipDateUpdated;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderShipGroupAdded;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderShipGroupQuantityCanceled;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderShipGroupItemRemoved;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderShipGroupRemoved;
import org.dddml.suidemocontracts.domain.daysummary.AbstractDaySummaryEvent;
import org.dddml.suidemocontracts.sui.contract.daysummary.DaySummaryCreated;

/**
 * Utils that convert beans in the contract package to domain beans.
 */
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

    public static org.dddml.suidemocontracts.domain.Day toDay(DayForEvent contractDay) {
        if (contractDay == null) {
            return null;
        }
        org.dddml.suidemocontracts.domain.Day day = new org.dddml.suidemocontracts.domain.Day();
        day.setMonth(toMonth(contractDay.getMonth()));
        day.setNumber(contractDay.getNumber());
        day.setTimeZone(contractDay.getTimeZone());
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

    public static org.dddml.suidemocontracts.domain.domainname.DomainNameId toDomainNameId(DomainNameIdForEvent contractDomainNameId) {
        if (contractDomainNameId == null) {
            return null;
        }
        org.dddml.suidemocontracts.domain.domainname.DomainNameId domainNameId = new org.dddml.suidemocontracts.domain.domainname.DomainNameId();
        domainNameId.setTopLevelDomain(contractDomainNameId.getTopLevelDomain());
        domainNameId.setSecondLevelDomain(contractDomainNameId.getSecondLevelDomain());
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

    public static org.dddml.suidemocontracts.domain.Month toMonth(MonthForEvent contractMonth) {
        if (contractMonth == null) {
            return null;
        }
        org.dddml.suidemocontracts.domain.Month month = new org.dddml.suidemocontracts.domain.Month();
        month.setYear(toYear(contractMonth.getYear()));
        month.setNumber(contractMonth.getNumber());
        month.setIsLeap(contractMonth.getIsLeap());
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

    public static org.dddml.suidemocontracts.domain.Year toYear(YearForEvent contractYear) {
        if (contractYear == null) {
            return null;
        }
        org.dddml.suidemocontracts.domain.Year year = new org.dddml.suidemocontracts.domain.Year();
        year.setNumber(contractYear.getNumber());
        year.setCalendar(contractYear.getCalendar());
        return year;
    }


    public static AbstractDomainNameEvent.Registered toRegistered(SuiMoveEventEnvelope<Registered> eventEnvelope) {
        Registered contractEvent = eventEnvelope.getParsedJson();

        AbstractDomainNameEvent.Registered registered = new AbstractDomainNameEvent.Registered();
        registered.setDomainNameId(DomainBeanUtils.toDomainNameId(contractEvent.getDomainNameId()));
        registered.setId_(contractEvent.getId());
        registered.setRegistrationPeriod(contractEvent.getRegistrationPeriod());
        registered.setOwner(contractEvent.getOwner());
        registered.setVersion(BigInteger.valueOf(-1));

        registered.setSuiTimestamp(eventEnvelope.getTimestampMs());
        registered.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        registered.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        registered.setSuiPackageId(eventEnvelope.getPackageId());
        registered.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        registered.setSuiSender(eventEnvelope.getSender());

        return registered;
    }

    public static AbstractDomainNameEvent.Renewed toRenewed(SuiMoveEventEnvelope<Renewed> eventEnvelope) {
        Renewed contractEvent = eventEnvelope.getParsedJson();

        AbstractDomainNameEvent.Renewed renewed = new AbstractDomainNameEvent.Renewed();
        renewed.setDomainNameId(DomainBeanUtils.toDomainNameId(contractEvent.getDomainNameId()));
        renewed.setId_(contractEvent.getId());
        renewed.setRenewPeriod(contractEvent.getRenewPeriod());
        renewed.setAccount(contractEvent.getAccount());
        renewed.setVersion(contractEvent.getVersion());

        renewed.setSuiTimestamp(eventEnvelope.getTimestampMs());
        renewed.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        renewed.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        renewed.setSuiPackageId(eventEnvelope.getPackageId());
        renewed.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        renewed.setSuiSender(eventEnvelope.getSender());

        return renewed;
    }

    public static AbstractOrderEvent.OrderCreated toOrderCreated(SuiMoveEventEnvelope<OrderCreated> eventEnvelope) {
        OrderCreated contractEvent = eventEnvelope.getParsedJson();

        AbstractOrderEvent.OrderCreated orderCreated = new AbstractOrderEvent.OrderCreated();
        orderCreated.setId(contractEvent.getId());
        orderCreated.setProduct(contractEvent.getProduct());
        orderCreated.setQuantity(contractEvent.getQuantity());
        orderCreated.setUnitPrice(contractEvent.getUnitPrice());
        orderCreated.setTotalAmount(contractEvent.getTotalAmount());
        orderCreated.setOwner(contractEvent.getOwner());
        orderCreated.setVersion(BigInteger.valueOf(-1));

        orderCreated.setSuiTimestamp(eventEnvelope.getTimestampMs());
        orderCreated.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        orderCreated.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        orderCreated.setSuiPackageId(eventEnvelope.getPackageId());
        orderCreated.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        orderCreated.setSuiSender(eventEnvelope.getSender());

        return orderCreated;
    }

    public static AbstractOrderEvent.OrderItemRemoved toOrderItemRemoved(SuiMoveEventEnvelope<OrderItemRemoved> eventEnvelope) {
        OrderItemRemoved contractEvent = eventEnvelope.getParsedJson();

        AbstractOrderEvent.OrderItemRemoved orderItemRemoved = new AbstractOrderEvent.OrderItemRemoved();
        orderItemRemoved.setId(contractEvent.getId());
        orderItemRemoved.setProductId(contractEvent.getProductId());
        orderItemRemoved.setVersion(contractEvent.getVersion());

        orderItemRemoved.setSuiTimestamp(eventEnvelope.getTimestampMs());
        orderItemRemoved.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        orderItemRemoved.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        orderItemRemoved.setSuiPackageId(eventEnvelope.getPackageId());
        orderItemRemoved.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        orderItemRemoved.setSuiSender(eventEnvelope.getSender());

        return orderItemRemoved;
    }

    public static AbstractOrderEvent.OrderItemQuantityUpdated toOrderItemQuantityUpdated(SuiMoveEventEnvelope<OrderItemQuantityUpdated> eventEnvelope) {
        OrderItemQuantityUpdated contractEvent = eventEnvelope.getParsedJson();

        AbstractOrderEvent.OrderItemQuantityUpdated orderItemQuantityUpdated = new AbstractOrderEvent.OrderItemQuantityUpdated();
        orderItemQuantityUpdated.setId(contractEvent.getId());
        orderItemQuantityUpdated.setProductId(contractEvent.getProductId());
        orderItemQuantityUpdated.setQuantity(contractEvent.getQuantity());
        orderItemQuantityUpdated.setVersion(contractEvent.getVersion());

        orderItemQuantityUpdated.setSuiTimestamp(eventEnvelope.getTimestampMs());
        orderItemQuantityUpdated.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        orderItemQuantityUpdated.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        orderItemQuantityUpdated.setSuiPackageId(eventEnvelope.getPackageId());
        orderItemQuantityUpdated.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        orderItemQuantityUpdated.setSuiSender(eventEnvelope.getSender());

        return orderItemQuantityUpdated;
    }

    public static AbstractOrderEvent.OrderDeleted toOrderDeleted(SuiMoveEventEnvelope<OrderDeleted> eventEnvelope) {
        OrderDeleted contractEvent = eventEnvelope.getParsedJson();

        AbstractOrderEvent.OrderDeleted orderDeleted = new AbstractOrderEvent.OrderDeleted();
        orderDeleted.setId(contractEvent.getId());
        orderDeleted.setVersion(contractEvent.getVersion());

        orderDeleted.setSuiTimestamp(eventEnvelope.getTimestampMs());
        orderDeleted.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        orderDeleted.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        orderDeleted.setSuiPackageId(eventEnvelope.getPackageId());
        orderDeleted.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        orderDeleted.setSuiSender(eventEnvelope.getSender());

        return orderDeleted;
    }

    public static AbstractProductEvent.ProductCreated toProductCreated(SuiMoveEventEnvelope<ProductCreated> eventEnvelope) {
        ProductCreated contractEvent = eventEnvelope.getParsedJson();

        AbstractProductEvent.ProductCreated productCreated = new AbstractProductEvent.ProductCreated();
        productCreated.setProductId(contractEvent.getProductId());
        productCreated.setId_(contractEvent.getId());
        productCreated.setName(contractEvent.getName());
        productCreated.setUnitPrice(contractEvent.getUnitPrice());
        productCreated.setVersion(BigInteger.valueOf(-1));

        productCreated.setSuiTimestamp(eventEnvelope.getTimestampMs());
        productCreated.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        productCreated.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        productCreated.setSuiPackageId(eventEnvelope.getPackageId());
        productCreated.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        productCreated.setSuiSender(eventEnvelope.getSender());

        return productCreated;
    }

    public static AbstractOrderV2Event.OrderV2Created toOrderV2Created(SuiMoveEventEnvelope<OrderV2Created> eventEnvelope) {
        OrderV2Created contractEvent = eventEnvelope.getParsedJson();

        AbstractOrderV2Event.OrderV2Created orderV2Created = new AbstractOrderV2Event.OrderV2Created();
        orderV2Created.setOrderId(contractEvent.getOrderId());
        orderV2Created.setId_(contractEvent.getId());
        orderV2Created.setProduct(contractEvent.getProduct());
        orderV2Created.setQuantity(contractEvent.getQuantity());
        orderV2Created.setUnitPrice(contractEvent.getUnitPrice());
        orderV2Created.setTotalAmount(contractEvent.getTotalAmount());
        orderV2Created.setOwner(contractEvent.getOwner());
        orderV2Created.setVersion(BigInteger.valueOf(-1));

        orderV2Created.setSuiTimestamp(eventEnvelope.getTimestampMs());
        orderV2Created.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        orderV2Created.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        orderV2Created.setSuiPackageId(eventEnvelope.getPackageId());
        orderV2Created.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        orderV2Created.setSuiSender(eventEnvelope.getSender());

        return orderV2Created;
    }

    public static AbstractOrderV2Event.OrderV2ItemRemoved toOrderV2ItemRemoved(SuiMoveEventEnvelope<OrderV2ItemRemoved> eventEnvelope) {
        OrderV2ItemRemoved contractEvent = eventEnvelope.getParsedJson();

        AbstractOrderV2Event.OrderV2ItemRemoved orderV2ItemRemoved = new AbstractOrderV2Event.OrderV2ItemRemoved();
        orderV2ItemRemoved.setOrderId(contractEvent.getOrderId());
        orderV2ItemRemoved.setId_(contractEvent.getId());
        orderV2ItemRemoved.setProductId(contractEvent.getProductId());
        orderV2ItemRemoved.setVersion(contractEvent.getVersion());

        orderV2ItemRemoved.setSuiTimestamp(eventEnvelope.getTimestampMs());
        orderV2ItemRemoved.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        orderV2ItemRemoved.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        orderV2ItemRemoved.setSuiPackageId(eventEnvelope.getPackageId());
        orderV2ItemRemoved.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        orderV2ItemRemoved.setSuiSender(eventEnvelope.getSender());

        return orderV2ItemRemoved;
    }

    public static AbstractOrderV2Event.OrderV2ItemQuantityUpdated toOrderV2ItemQuantityUpdated(SuiMoveEventEnvelope<OrderV2ItemQuantityUpdated> eventEnvelope) {
        OrderV2ItemQuantityUpdated contractEvent = eventEnvelope.getParsedJson();

        AbstractOrderV2Event.OrderV2ItemQuantityUpdated orderV2ItemQuantityUpdated = new AbstractOrderV2Event.OrderV2ItemQuantityUpdated();
        orderV2ItemQuantityUpdated.setOrderId(contractEvent.getOrderId());
        orderV2ItemQuantityUpdated.setId_(contractEvent.getId());
        orderV2ItemQuantityUpdated.setProductId(contractEvent.getProductId());
        orderV2ItemQuantityUpdated.setQuantity(contractEvent.getQuantity());
        orderV2ItemQuantityUpdated.setVersion(contractEvent.getVersion());

        orderV2ItemQuantityUpdated.setSuiTimestamp(eventEnvelope.getTimestampMs());
        orderV2ItemQuantityUpdated.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        orderV2ItemQuantityUpdated.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        orderV2ItemQuantityUpdated.setSuiPackageId(eventEnvelope.getPackageId());
        orderV2ItemQuantityUpdated.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        orderV2ItemQuantityUpdated.setSuiSender(eventEnvelope.getSender());

        return orderV2ItemQuantityUpdated;
    }

    public static AbstractOrderV2Event.OrderV2EstimatedShipDateUpdated toOrderV2EstimatedShipDateUpdated(SuiMoveEventEnvelope<OrderV2EstimatedShipDateUpdated> eventEnvelope) {
        OrderV2EstimatedShipDateUpdated contractEvent = eventEnvelope.getParsedJson();

        AbstractOrderV2Event.OrderV2EstimatedShipDateUpdated orderV2EstimatedShipDateUpdated = new AbstractOrderV2Event.OrderV2EstimatedShipDateUpdated();
        orderV2EstimatedShipDateUpdated.setOrderId(contractEvent.getOrderId());
        orderV2EstimatedShipDateUpdated.setId_(contractEvent.getId());
        orderV2EstimatedShipDateUpdated.setEstimatedShipDate(DomainBeanUtils.toDay(contractEvent.getEstimatedShipDate()));
        orderV2EstimatedShipDateUpdated.setVersion(contractEvent.getVersion());

        orderV2EstimatedShipDateUpdated.setSuiTimestamp(eventEnvelope.getTimestampMs());
        orderV2EstimatedShipDateUpdated.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        orderV2EstimatedShipDateUpdated.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        orderV2EstimatedShipDateUpdated.setSuiPackageId(eventEnvelope.getPackageId());
        orderV2EstimatedShipDateUpdated.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        orderV2EstimatedShipDateUpdated.setSuiSender(eventEnvelope.getSender());

        return orderV2EstimatedShipDateUpdated;
    }

    public static AbstractOrderV2Event.OrderShipGroupAdded toOrderShipGroupAdded(SuiMoveEventEnvelope<OrderShipGroupAdded> eventEnvelope) {
        OrderShipGroupAdded contractEvent = eventEnvelope.getParsedJson();

        AbstractOrderV2Event.OrderShipGroupAdded orderShipGroupAdded = new AbstractOrderV2Event.OrderShipGroupAdded();
        orderShipGroupAdded.setOrderId(contractEvent.getOrderId());
        orderShipGroupAdded.setId_(contractEvent.getId());
        orderShipGroupAdded.setShipGroupSeqId(contractEvent.getShipGroupSeqId());
        orderShipGroupAdded.setShipmentMethod(contractEvent.getShipmentMethod());
        orderShipGroupAdded.setProductId(contractEvent.getProductId());
        orderShipGroupAdded.setQuantity(contractEvent.getQuantity());
        orderShipGroupAdded.setVersion(contractEvent.getVersion());

        orderShipGroupAdded.setSuiTimestamp(eventEnvelope.getTimestampMs());
        orderShipGroupAdded.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        orderShipGroupAdded.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        orderShipGroupAdded.setSuiPackageId(eventEnvelope.getPackageId());
        orderShipGroupAdded.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        orderShipGroupAdded.setSuiSender(eventEnvelope.getSender());

        return orderShipGroupAdded;
    }

    public static AbstractOrderV2Event.OrderShipGroupQuantityCanceled toOrderShipGroupQuantityCanceled(SuiMoveEventEnvelope<OrderShipGroupQuantityCanceled> eventEnvelope) {
        OrderShipGroupQuantityCanceled contractEvent = eventEnvelope.getParsedJson();

        AbstractOrderV2Event.OrderShipGroupQuantityCanceled orderShipGroupQuantityCanceled = new AbstractOrderV2Event.OrderShipGroupQuantityCanceled();
        orderShipGroupQuantityCanceled.setOrderId(contractEvent.getOrderId());
        orderShipGroupQuantityCanceled.setId_(contractEvent.getId());
        orderShipGroupQuantityCanceled.setShipGroupSeqId(contractEvent.getShipGroupSeqId());
        orderShipGroupQuantityCanceled.setProductId(contractEvent.getProductId());
        orderShipGroupQuantityCanceled.setCancelQuantity(contractEvent.getCancelQuantity());
        orderShipGroupQuantityCanceled.setVersion(contractEvent.getVersion());

        orderShipGroupQuantityCanceled.setSuiTimestamp(eventEnvelope.getTimestampMs());
        orderShipGroupQuantityCanceled.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        orderShipGroupQuantityCanceled.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        orderShipGroupQuantityCanceled.setSuiPackageId(eventEnvelope.getPackageId());
        orderShipGroupQuantityCanceled.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        orderShipGroupQuantityCanceled.setSuiSender(eventEnvelope.getSender());

        return orderShipGroupQuantityCanceled;
    }

    public static AbstractOrderV2Event.OrderShipGroupItemRemoved toOrderShipGroupItemRemoved(SuiMoveEventEnvelope<OrderShipGroupItemRemoved> eventEnvelope) {
        OrderShipGroupItemRemoved contractEvent = eventEnvelope.getParsedJson();

        AbstractOrderV2Event.OrderShipGroupItemRemoved orderShipGroupItemRemoved = new AbstractOrderV2Event.OrderShipGroupItemRemoved();
        orderShipGroupItemRemoved.setOrderId(contractEvent.getOrderId());
        orderShipGroupItemRemoved.setId_(contractEvent.getId());
        orderShipGroupItemRemoved.setShipGroupSeqId(contractEvent.getShipGroupSeqId());
        orderShipGroupItemRemoved.setProductId(contractEvent.getProductId());
        orderShipGroupItemRemoved.setVersion(contractEvent.getVersion());

        orderShipGroupItemRemoved.setSuiTimestamp(eventEnvelope.getTimestampMs());
        orderShipGroupItemRemoved.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        orderShipGroupItemRemoved.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        orderShipGroupItemRemoved.setSuiPackageId(eventEnvelope.getPackageId());
        orderShipGroupItemRemoved.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        orderShipGroupItemRemoved.setSuiSender(eventEnvelope.getSender());

        return orderShipGroupItemRemoved;
    }

    public static AbstractOrderV2Event.OrderShipGroupRemoved toOrderShipGroupRemoved(SuiMoveEventEnvelope<OrderShipGroupRemoved> eventEnvelope) {
        OrderShipGroupRemoved contractEvent = eventEnvelope.getParsedJson();

        AbstractOrderV2Event.OrderShipGroupRemoved orderShipGroupRemoved = new AbstractOrderV2Event.OrderShipGroupRemoved();
        orderShipGroupRemoved.setOrderId(contractEvent.getOrderId());
        orderShipGroupRemoved.setId_(contractEvent.getId());
        orderShipGroupRemoved.setShipGroupSeqId(contractEvent.getShipGroupSeqId());
        orderShipGroupRemoved.setVersion(contractEvent.getVersion());

        orderShipGroupRemoved.setSuiTimestamp(eventEnvelope.getTimestampMs());
        orderShipGroupRemoved.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        orderShipGroupRemoved.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        orderShipGroupRemoved.setSuiPackageId(eventEnvelope.getPackageId());
        orderShipGroupRemoved.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        orderShipGroupRemoved.setSuiSender(eventEnvelope.getSender());

        return orderShipGroupRemoved;
    }

    public static AbstractDaySummaryEvent.DaySummaryCreated toDaySummaryCreated(SuiMoveEventEnvelope<DaySummaryCreated> eventEnvelope) {
        DaySummaryCreated contractEvent = eventEnvelope.getParsedJson();

        AbstractDaySummaryEvent.DaySummaryCreated daySummaryCreated = new AbstractDaySummaryEvent.DaySummaryCreated();
        daySummaryCreated.setDay(DomainBeanUtils.toDay(contractEvent.getDay()));
        daySummaryCreated.setId_(contractEvent.getId());
        daySummaryCreated.setDescription(contractEvent.getDescription());
        daySummaryCreated.setMetaData(contractEvent.getMetaData());
        daySummaryCreated.setArrayData(contractEvent.getArrayData());
        daySummaryCreated.setOptionalData(contractEvent.getOptionalData());
        daySummaryCreated.setVersion(BigInteger.valueOf(-1));

        daySummaryCreated.setSuiTimestamp(eventEnvelope.getTimestampMs());
        daySummaryCreated.setSuiTxDigest(eventEnvelope.getId().getTxDigest());
        daySummaryCreated.setSuiEventSeq(new BigInteger(eventEnvelope.getId().getEventSeq()));

        daySummaryCreated.setSuiPackageId(eventEnvelope.getPackageId());
        daySummaryCreated.setSuiTransactionModule(eventEnvelope.getTransactionModule());
        daySummaryCreated.setSuiSender(eventEnvelope.getSender());

        return daySummaryCreated;
    }

}
