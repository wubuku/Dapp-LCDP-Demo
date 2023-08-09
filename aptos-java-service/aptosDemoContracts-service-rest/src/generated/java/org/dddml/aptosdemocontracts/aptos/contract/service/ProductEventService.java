// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.aptos.contract.service;

import com.github.wubuku.aptos.bean.Event;
import com.github.wubuku.aptos.utils.NodeApiClient;

import org.dddml.aptosdemocontracts.domain.product.AbstractProductEvent;
import org.dddml.aptosdemocontracts.aptos.contract.ContractConstants;
import org.dddml.aptosdemocontracts.aptos.contract.DomainBeanUtils;
import org.dddml.aptosdemocontracts.aptos.contract.AptosAccount;

import org.dddml.aptosdemocontracts.aptos.contract.product.ProductCreated;
import org.dddml.aptosdemocontracts.aptos.contract.product.ProductUpdated;
import org.dddml.aptosdemocontracts.aptos.contract.product.ProductDeleted;
import org.dddml.aptosdemocontracts.aptos.contract.repository.ProductEventRepository;
import org.dddml.aptosdemocontracts.aptos.contract.repository.AptosAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.*;
import java.util.*;


@Service
public class ProductEventService {

    public static final java.util.Set<String> DELETION_COMMAND_EVENTS = new java.util.HashSet<>(java.util.Arrays.asList("ProductDeleted"));

    public static boolean isDeletionCommand(String eventType) {
        return DELETION_COMMAND_EVENTS.contains(eventType);
    }

    @Value("${aptos.contract.address}")
    private String aptosContractAddress;

    @Autowired
    private AptosAccountRepository aptosAccountRepository;

    @Autowired
    private NodeApiClient aptosNodeApiClient;

    @Autowired
    private ProductEventRepository productEventRepository;

    @Transactional
    public void updateStatusToProcessed(AbstractProductEvent event) {
        event.setStatus("D");
        productEventRepository.save(event);
    }

    @Transactional
    public void pullProductCreatedEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getProductCreatedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<ProductCreated>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.PRODUCT_MODULE_EVENTS,
                        ContractConstants.PRODUCT_MODULE_PRODUCT_CREATED_HANDLE_FIELD,
                        ProductCreated.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<ProductCreated> eventEnvelope : eventPage) {
                    saveProductCreated(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getProductCreatedEventNextCursor() {
        AbstractProductEvent.ProductCreated lastEvent = productEventRepository.findFirstProductCreatedByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveProductCreated(Event<ProductCreated> eventEnvelope) {
        AbstractProductEvent.ProductCreated productCreated = DomainBeanUtils.toProductCreated(eventEnvelope);
        if (productEventRepository.findById(productCreated.getProductEventId()).isPresent()) {
            return;
        }
        productEventRepository.save(productCreated);
    }

    @Transactional
    public void pullProductUpdatedEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getProductUpdatedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<ProductUpdated>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.PRODUCT_MODULE_EVENTS,
                        ContractConstants.PRODUCT_MODULE_PRODUCT_UPDATED_HANDLE_FIELD,
                        ProductUpdated.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<ProductUpdated> eventEnvelope : eventPage) {
                    saveProductUpdated(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getProductUpdatedEventNextCursor() {
        AbstractProductEvent.ProductUpdated lastEvent = productEventRepository.findFirstProductUpdatedByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveProductUpdated(Event<ProductUpdated> eventEnvelope) {
        AbstractProductEvent.ProductUpdated productUpdated = DomainBeanUtils.toProductUpdated(eventEnvelope);
        if (productEventRepository.findById(productUpdated.getProductEventId()).isPresent()) {
            return;
        }
        productEventRepository.save(productUpdated);
    }

    @Transactional
    public void pullProductDeletedEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getProductDeletedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<ProductDeleted>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.PRODUCT_MODULE_EVENTS,
                        ContractConstants.PRODUCT_MODULE_PRODUCT_DELETED_HANDLE_FIELD,
                        ProductDeleted.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<ProductDeleted> eventEnvelope : eventPage) {
                    saveProductDeleted(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getProductDeletedEventNextCursor() {
        AbstractProductEvent.ProductDeleted lastEvent = productEventRepository.findFirstProductDeletedByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveProductDeleted(Event<ProductDeleted> eventEnvelope) {
        AbstractProductEvent.ProductDeleted productDeleted = DomainBeanUtils.toProductDeleted(eventEnvelope);
        if (productEventRepository.findById(productDeleted.getProductEventId()).isPresent()) {
            return;
        }
        productEventRepository.save(productDeleted);
    }

    private String getResourceAccountAddress() {
        return aptosAccountRepository.findById(ContractConstants.RESOURCE_ACCOUNT_ADDRESS)
                .map(AptosAccount::getAddress).orElse(null);
    }
}