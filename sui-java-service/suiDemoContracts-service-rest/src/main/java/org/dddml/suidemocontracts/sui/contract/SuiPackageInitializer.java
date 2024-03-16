package org.dddml.suidemocontracts.sui.contract;

import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.sui.contract.repository.MoveObjectIdGeneratorObjectRepository;
import org.dddml.suidemocontracts.sui.contract.repository.SuiPackageRepository;
import org.dddml.suidemocontracts.sui.contract.service.SuiPackageInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SuiPackageInitializer {

    private final SuiPackageInitializationService defaultPackageInitializationService;

    @Autowired
    public SuiPackageInitializer(
            MoveObjectIdGeneratorObjectRepository moveObjectIdGeneratorObjectRepository,
            SuiPackageRepository suiPackageRepository,
            SuiJsonRpcClient suiJsonRpcClient,
            @Value("#{'${sui.contract.package-publish-transactions.default:}'?:'${sui.contract.package-publish-transaction:}'}")
            String defaultPackagePublishTransactionDigest
    ) {
        if (defaultPackagePublishTransactionDigest != null && !defaultPackagePublishTransactionDigest.trim().isEmpty()) {
            defaultPackageInitializationService = new SuiPackageInitializationService(
                    moveObjectIdGeneratorObjectRepository,
                    suiPackageRepository,
                    suiJsonRpcClient,
                    defaultPackagePublishTransactionDigest,
                    ContractConstants.DEFAULT_SUI_PACKAGE_NAME,
                    ContractConstants::getDefaultPackageIdGeneratorObjectTypes
            );
        } else {
            //throw new IllegalArgumentException("defaultPackagePublishTransactionDigest is null");
            defaultPackageInitializationService = null;
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        if (defaultPackageInitializationService != null) {
            defaultPackageInitializationService.init();
        }
    }
}
