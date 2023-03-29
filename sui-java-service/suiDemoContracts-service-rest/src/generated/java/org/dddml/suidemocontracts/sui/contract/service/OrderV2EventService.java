// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.EventId;
import com.github.wubuku.sui.bean.PaginatedMoveEvents;
import com.github.wubuku.sui.bean.SuiMoveEventEnvelope;
import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.orderv2.AbstractOrderV2Event;
import org.dddml.suidemocontracts.sui.contract.ContractConstants;
import org.dddml.suidemocontracts.sui.contract.DomainBeanUtils;
import org.dddml.suidemocontracts.sui.contract.SuiPackage;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderV2Created;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderV2ItemRemoved;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderV2ItemQuantityUpdated;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderV2EstimatedShipDateUpdated;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderShipGroupAdded;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderShipGroupQuantityCanceled;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderShipGroupItemRemoved;
import org.dddml.suidemocontracts.sui.contract.orderv2.OrderShipGroupRemoved;
import org.dddml.suidemocontracts.sui.contract.repository.OrderV2EventRepository;
import org.dddml.suidemocontracts.sui.contract.repository.SuiPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderV2EventService {

    @Autowired
    private SuiPackageRepository suiPackageRepository;

    @Autowired
    private SuiJsonRpcClient suiJsonRpcClient;

    @Autowired
    private OrderV2EventRepository orderV2EventRepository;

    @Transactional
    public void updateStatusToProcessed(AbstractOrderV2Event event) {
        event.setStatus("D");
        orderV2EventRepository.save(event);
    }

    @Transactional
    public void pullOrderV2CreatedEvents() {
        String packageId = getDefaultSuiPackageId();
        if (packageId == null) {
            return;
        }
        int limit = 1;
        EventId cursor = getOrderV2CreatedEventNextCursor();
        while (true) {
            PaginatedMoveEvents<OrderV2Created> eventPage = suiJsonRpcClient.getMoveEvents(
                    packageId + "::" + ContractConstants.ORDER_V2_MODULE_ORDER_V2_CREATED,
                    cursor, limit, false, OrderV2Created.class);

            if (eventPage.getData() != null && !eventPage.getData().isEmpty()) {
                cursor = eventPage.getNextCursor();
                for (SuiMoveEventEnvelope<OrderV2Created> eventEnvelope : eventPage.getData()) {
                    saveOrderV2Created(eventEnvelope);
                }
            } else {
                break;
            }
            if (cursor == null) {
                break;
            }
        }
    }

    private EventId getOrderV2CreatedEventNextCursor() {
        AbstractOrderV2Event lastEvent = orderV2EventRepository.findFirstOrderV2CreatedByOrderBySuiTimestampDesc();
        return lastEvent != null ? new EventId(lastEvent.getSuiTxDigest(), lastEvent.getSuiEventSeq()) : null;
    }

    private void saveOrderV2Created(SuiMoveEventEnvelope<OrderV2Created> eventEnvelope) {
        AbstractOrderV2Event.OrderV2Created orderV2Created = DomainBeanUtils.toOrderV2Created(eventEnvelope);
        if (orderV2EventRepository.findById(orderV2Created.getOrderV2EventId()).isPresent()) {
            return;
        }
        orderV2EventRepository.save(orderV2Created);
    }

    @Transactional
    public void pullOrderV2ItemRemovedEvents() {
        String packageId = getDefaultSuiPackageId();
        if (packageId == null) {
            return;
        }
        int limit = 1;
        EventId cursor = getOrderV2ItemRemovedEventNextCursor();
        while (true) {
            PaginatedMoveEvents<OrderV2ItemRemoved> eventPage = suiJsonRpcClient.getMoveEvents(
                    packageId + "::" + ContractConstants.ORDER_V2_MODULE_ORDER_V2_ITEM_REMOVED,
                    cursor, limit, false, OrderV2ItemRemoved.class);

            if (eventPage.getData() != null && !eventPage.getData().isEmpty()) {
                cursor = eventPage.getNextCursor();
                for (SuiMoveEventEnvelope<OrderV2ItemRemoved> eventEnvelope : eventPage.getData()) {
                    saveOrderV2ItemRemoved(eventEnvelope);
                }
            } else {
                break;
            }
            if (cursor == null) {
                break;
            }
        }
    }

    private EventId getOrderV2ItemRemovedEventNextCursor() {
        AbstractOrderV2Event lastEvent = orderV2EventRepository.findFirstOrderV2ItemRemovedByOrderBySuiTimestampDesc();
        return lastEvent != null ? new EventId(lastEvent.getSuiTxDigest(), lastEvent.getSuiEventSeq()) : null;
    }

    private void saveOrderV2ItemRemoved(SuiMoveEventEnvelope<OrderV2ItemRemoved> eventEnvelope) {
        AbstractOrderV2Event.OrderV2ItemRemoved orderV2ItemRemoved = DomainBeanUtils.toOrderV2ItemRemoved(eventEnvelope);
        if (orderV2EventRepository.findById(orderV2ItemRemoved.getOrderV2EventId()).isPresent()) {
            return;
        }
        orderV2EventRepository.save(orderV2ItemRemoved);
    }

    @Transactional
    public void pullOrderV2ItemQuantityUpdatedEvents() {
        String packageId = getDefaultSuiPackageId();
        if (packageId == null) {
            return;
        }
        int limit = 1;
        EventId cursor = getOrderV2ItemQuantityUpdatedEventNextCursor();
        while (true) {
            PaginatedMoveEvents<OrderV2ItemQuantityUpdated> eventPage = suiJsonRpcClient.getMoveEvents(
                    packageId + "::" + ContractConstants.ORDER_V2_MODULE_ORDER_V2_ITEM_QUANTITY_UPDATED,
                    cursor, limit, false, OrderV2ItemQuantityUpdated.class);

            if (eventPage.getData() != null && !eventPage.getData().isEmpty()) {
                cursor = eventPage.getNextCursor();
                for (SuiMoveEventEnvelope<OrderV2ItemQuantityUpdated> eventEnvelope : eventPage.getData()) {
                    saveOrderV2ItemQuantityUpdated(eventEnvelope);
                }
            } else {
                break;
            }
            if (cursor == null) {
                break;
            }
        }
    }

    private EventId getOrderV2ItemQuantityUpdatedEventNextCursor() {
        AbstractOrderV2Event lastEvent = orderV2EventRepository.findFirstOrderV2ItemQuantityUpdatedByOrderBySuiTimestampDesc();
        return lastEvent != null ? new EventId(lastEvent.getSuiTxDigest(), lastEvent.getSuiEventSeq()) : null;
    }

    private void saveOrderV2ItemQuantityUpdated(SuiMoveEventEnvelope<OrderV2ItemQuantityUpdated> eventEnvelope) {
        AbstractOrderV2Event.OrderV2ItemQuantityUpdated orderV2ItemQuantityUpdated = DomainBeanUtils.toOrderV2ItemQuantityUpdated(eventEnvelope);
        if (orderV2EventRepository.findById(orderV2ItemQuantityUpdated.getOrderV2EventId()).isPresent()) {
            return;
        }
        orderV2EventRepository.save(orderV2ItemQuantityUpdated);
    }

    @Transactional
    public void pullOrderV2EstimatedShipDateUpdatedEvents() {
        String packageId = getDefaultSuiPackageId();
        if (packageId == null) {
            return;
        }
        int limit = 1;
        EventId cursor = getOrderV2EstimatedShipDateUpdatedEventNextCursor();
        while (true) {
            PaginatedMoveEvents<OrderV2EstimatedShipDateUpdated> eventPage = suiJsonRpcClient.getMoveEvents(
                    packageId + "::" + ContractConstants.ORDER_V2_MODULE_ORDER_V2_ESTIMATED_SHIP_DATE_UPDATED,
                    cursor, limit, false, OrderV2EstimatedShipDateUpdated.class);

            if (eventPage.getData() != null && !eventPage.getData().isEmpty()) {
                cursor = eventPage.getNextCursor();
                for (SuiMoveEventEnvelope<OrderV2EstimatedShipDateUpdated> eventEnvelope : eventPage.getData()) {
                    saveOrderV2EstimatedShipDateUpdated(eventEnvelope);
                }
            } else {
                break;
            }
            if (cursor == null) {
                break;
            }
        }
    }

    private EventId getOrderV2EstimatedShipDateUpdatedEventNextCursor() {
        AbstractOrderV2Event lastEvent = orderV2EventRepository.findFirstOrderV2EstimatedShipDateUpdatedByOrderBySuiTimestampDesc();
        return lastEvent != null ? new EventId(lastEvent.getSuiTxDigest(), lastEvent.getSuiEventSeq()) : null;
    }

    private void saveOrderV2EstimatedShipDateUpdated(SuiMoveEventEnvelope<OrderV2EstimatedShipDateUpdated> eventEnvelope) {
        AbstractOrderV2Event.OrderV2EstimatedShipDateUpdated orderV2EstimatedShipDateUpdated = DomainBeanUtils.toOrderV2EstimatedShipDateUpdated(eventEnvelope);
        if (orderV2EventRepository.findById(orderV2EstimatedShipDateUpdated.getOrderV2EventId()).isPresent()) {
            return;
        }
        orderV2EventRepository.save(orderV2EstimatedShipDateUpdated);
    }

    @Transactional
    public void pullOrderShipGroupAddedEvents() {
        String packageId = getDefaultSuiPackageId();
        if (packageId == null) {
            return;
        }
        int limit = 1;
        EventId cursor = getOrderShipGroupAddedEventNextCursor();
        while (true) {
            PaginatedMoveEvents<OrderShipGroupAdded> eventPage = suiJsonRpcClient.getMoveEvents(
                    packageId + "::" + ContractConstants.ORDER_V2_MODULE_ORDER_SHIP_GROUP_ADDED,
                    cursor, limit, false, OrderShipGroupAdded.class);

            if (eventPage.getData() != null && !eventPage.getData().isEmpty()) {
                cursor = eventPage.getNextCursor();
                for (SuiMoveEventEnvelope<OrderShipGroupAdded> eventEnvelope : eventPage.getData()) {
                    saveOrderShipGroupAdded(eventEnvelope);
                }
            } else {
                break;
            }
            if (cursor == null) {
                break;
            }
        }
    }

    private EventId getOrderShipGroupAddedEventNextCursor() {
        AbstractOrderV2Event lastEvent = orderV2EventRepository.findFirstOrderShipGroupAddedByOrderBySuiTimestampDesc();
        return lastEvent != null ? new EventId(lastEvent.getSuiTxDigest(), lastEvent.getSuiEventSeq()) : null;
    }

    private void saveOrderShipGroupAdded(SuiMoveEventEnvelope<OrderShipGroupAdded> eventEnvelope) {
        AbstractOrderV2Event.OrderShipGroupAdded orderShipGroupAdded = DomainBeanUtils.toOrderShipGroupAdded(eventEnvelope);
        if (orderV2EventRepository.findById(orderShipGroupAdded.getOrderV2EventId()).isPresent()) {
            return;
        }
        orderV2EventRepository.save(orderShipGroupAdded);
    }

    @Transactional
    public void pullOrderShipGroupQuantityCanceledEvents() {
        String packageId = getDefaultSuiPackageId();
        if (packageId == null) {
            return;
        }
        int limit = 1;
        EventId cursor = getOrderShipGroupQuantityCanceledEventNextCursor();
        while (true) {
            PaginatedMoveEvents<OrderShipGroupQuantityCanceled> eventPage = suiJsonRpcClient.getMoveEvents(
                    packageId + "::" + ContractConstants.ORDER_V2_MODULE_ORDER_SHIP_GROUP_QUANTITY_CANCELED,
                    cursor, limit, false, OrderShipGroupQuantityCanceled.class);

            if (eventPage.getData() != null && !eventPage.getData().isEmpty()) {
                cursor = eventPage.getNextCursor();
                for (SuiMoveEventEnvelope<OrderShipGroupQuantityCanceled> eventEnvelope : eventPage.getData()) {
                    saveOrderShipGroupQuantityCanceled(eventEnvelope);
                }
            } else {
                break;
            }
            if (cursor == null) {
                break;
            }
        }
    }

    private EventId getOrderShipGroupQuantityCanceledEventNextCursor() {
        AbstractOrderV2Event lastEvent = orderV2EventRepository.findFirstOrderShipGroupQuantityCanceledByOrderBySuiTimestampDesc();
        return lastEvent != null ? new EventId(lastEvent.getSuiTxDigest(), lastEvent.getSuiEventSeq()) : null;
    }

    private void saveOrderShipGroupQuantityCanceled(SuiMoveEventEnvelope<OrderShipGroupQuantityCanceled> eventEnvelope) {
        AbstractOrderV2Event.OrderShipGroupQuantityCanceled orderShipGroupQuantityCanceled = DomainBeanUtils.toOrderShipGroupQuantityCanceled(eventEnvelope);
        if (orderV2EventRepository.findById(orderShipGroupQuantityCanceled.getOrderV2EventId()).isPresent()) {
            return;
        }
        orderV2EventRepository.save(orderShipGroupQuantityCanceled);
    }

    @Transactional
    public void pullOrderShipGroupItemRemovedEvents() {
        String packageId = getDefaultSuiPackageId();
        if (packageId == null) {
            return;
        }
        int limit = 1;
        EventId cursor = getOrderShipGroupItemRemovedEventNextCursor();
        while (true) {
            PaginatedMoveEvents<OrderShipGroupItemRemoved> eventPage = suiJsonRpcClient.getMoveEvents(
                    packageId + "::" + ContractConstants.ORDER_V2_MODULE_ORDER_SHIP_GROUP_ITEM_REMOVED,
                    cursor, limit, false, OrderShipGroupItemRemoved.class);

            if (eventPage.getData() != null && !eventPage.getData().isEmpty()) {
                cursor = eventPage.getNextCursor();
                for (SuiMoveEventEnvelope<OrderShipGroupItemRemoved> eventEnvelope : eventPage.getData()) {
                    saveOrderShipGroupItemRemoved(eventEnvelope);
                }
            } else {
                break;
            }
            if (cursor == null) {
                break;
            }
        }
    }

    private EventId getOrderShipGroupItemRemovedEventNextCursor() {
        AbstractOrderV2Event lastEvent = orderV2EventRepository.findFirstOrderShipGroupItemRemovedByOrderBySuiTimestampDesc();
        return lastEvent != null ? new EventId(lastEvent.getSuiTxDigest(), lastEvent.getSuiEventSeq()) : null;
    }

    private void saveOrderShipGroupItemRemoved(SuiMoveEventEnvelope<OrderShipGroupItemRemoved> eventEnvelope) {
        AbstractOrderV2Event.OrderShipGroupItemRemoved orderShipGroupItemRemoved = DomainBeanUtils.toOrderShipGroupItemRemoved(eventEnvelope);
        if (orderV2EventRepository.findById(orderShipGroupItemRemoved.getOrderV2EventId()).isPresent()) {
            return;
        }
        orderV2EventRepository.save(orderShipGroupItemRemoved);
    }

    @Transactional
    public void pullOrderShipGroupRemovedEvents() {
        String packageId = getDefaultSuiPackageId();
        if (packageId == null) {
            return;
        }
        int limit = 1;
        EventId cursor = getOrderShipGroupRemovedEventNextCursor();
        while (true) {
            PaginatedMoveEvents<OrderShipGroupRemoved> eventPage = suiJsonRpcClient.getMoveEvents(
                    packageId + "::" + ContractConstants.ORDER_V2_MODULE_ORDER_SHIP_GROUP_REMOVED,
                    cursor, limit, false, OrderShipGroupRemoved.class);

            if (eventPage.getData() != null && !eventPage.getData().isEmpty()) {
                cursor = eventPage.getNextCursor();
                for (SuiMoveEventEnvelope<OrderShipGroupRemoved> eventEnvelope : eventPage.getData()) {
                    saveOrderShipGroupRemoved(eventEnvelope);
                }
            } else {
                break;
            }
            if (cursor == null) {
                break;
            }
        }
    }

    private EventId getOrderShipGroupRemovedEventNextCursor() {
        AbstractOrderV2Event lastEvent = orderV2EventRepository.findFirstOrderShipGroupRemovedByOrderBySuiTimestampDesc();
        return lastEvent != null ? new EventId(lastEvent.getSuiTxDigest(), lastEvent.getSuiEventSeq()) : null;
    }

    private void saveOrderShipGroupRemoved(SuiMoveEventEnvelope<OrderShipGroupRemoved> eventEnvelope) {
        AbstractOrderV2Event.OrderShipGroupRemoved orderShipGroupRemoved = DomainBeanUtils.toOrderShipGroupRemoved(eventEnvelope);
        if (orderV2EventRepository.findById(orderShipGroupRemoved.getOrderV2EventId()).isPresent()) {
            return;
        }
        orderV2EventRepository.save(orderShipGroupRemoved);
    }


    private String getDefaultSuiPackageId() {
        return suiPackageRepository.findById(ContractConstants.DEFAULT_SUI_PACKAGE_NAME)
                .map(SuiPackage::getObjectId).orElse(null);
    }
}