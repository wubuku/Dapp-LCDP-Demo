// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.EventId;
import com.github.wubuku.sui.bean.Page;
import com.github.wubuku.sui.bean.PaginatedMoveEvents;
import com.github.wubuku.sui.bean.SuiMoveEventEnvelope;
import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.product.AbstractProductEvent;
import org.dddml.suidemocontracts.sui.contract.ContractConstants;
import org.dddml.suidemocontracts.sui.contract.DomainBeanUtils;
import org.dddml.suidemocontracts.sui.contract.SuiPackage;
import org.dddml.suidemocontracts.sui.contract.product.ProductCreated;
import org.dddml.suidemocontracts.sui.contract.repository.ProductEventRepository;
import org.dddml.suidemocontracts.sui.contract.repository.SuiPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductEventService {

    @Autowired
    private SuiPackageRepository suiPackageRepository;

    @Autowired
    private SuiJsonRpcClient suiJsonRpcClient;

    @Autowired
    private ProductEventRepository productEventRepository;

    @Transactional
    public void updateStatusToProcessed(AbstractProductEvent event) {
        event.setStatus("D");
        productEventRepository.save(event);
    }

    @Transactional
    public void pullProductCreatedEvents() {
        String packageId = getDefaultSuiPackageId();
        if (packageId == null) {
            return;
        }
        int limit = 1;
        EventId cursor = getProductCreatedEventNextCursor();
        while (true) {
            PaginatedMoveEvents<ProductCreated> eventPage = suiJsonRpcClient.queryMoveEvents(
                    packageId + "::" + ContractConstants.PRODUCT_MODULE_PRODUCT_CREATED,
                    cursor, limit, false, ProductCreated.class);

            if (eventPage.getData() != null && !eventPage.getData().isEmpty()) {
                cursor = eventPage.getNextCursor();
                for (SuiMoveEventEnvelope<ProductCreated> eventEnvelope : eventPage.getData()) {
                    saveProductCreated(eventEnvelope);
                }
            } else {
                break;
            }
            if (!Page.hasNextPage(eventPage)) {
                break;
            }
        }
    }

    private EventId getProductCreatedEventNextCursor() {
        AbstractProductEvent lastEvent = productEventRepository.findFirstProductCreatedByOrderBySuiTimestampDesc();
        return lastEvent != null ? new EventId(lastEvent.getSuiTxDigest(), lastEvent.getSuiEventSeq()) : null;
    }

    private void saveProductCreated(SuiMoveEventEnvelope<ProductCreated> eventEnvelope) {
        AbstractProductEvent.ProductCreated productCreated = DomainBeanUtils.toProductCreated(eventEnvelope);
        if (productEventRepository.findById(productCreated.getProductEventId()).isPresent()) {
            return;
        }
        productEventRepository.save(productCreated);
    }


    private String getDefaultSuiPackageId() {
        return suiPackageRepository.findById(ContractConstants.DEFAULT_SUI_PACKAGE_NAME)
                .map(SuiPackage::getObjectId).orElse(null);
    }
}
