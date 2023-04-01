package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.ObjectChange;
import com.github.wubuku.sui.bean.SuiEvent;
import com.github.wubuku.sui.bean.SuiTransactionBlockResponse;
import com.github.wubuku.sui.bean.SuiTransactionBlockResponseOptions;
import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.sui.contract.ContractConstants;
import org.dddml.suidemocontracts.sui.contract.MoveObjectIdGeneratorObject;
import org.dddml.suidemocontracts.sui.contract.SuiPackage;
import org.dddml.suidemocontracts.sui.contract.repository.MoveObjectIdGeneratorObjectRepository;
import org.dddml.suidemocontracts.sui.contract.repository.SuiPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MoveObjectIdGeneratorObjectService {
    @Autowired
    private MoveObjectIdGeneratorObjectRepository moveObjectIdGeneratorObjectRepository;

    @Autowired
    private SuiPackageRepository suiPackageRepository;

    @Autowired
    private SuiJsonRpcClient suiJsonRpcClient;

    @Value("${sui.contract.package-publish-transaction}")
    private String packagePublishTransaction;

    @Transactional
    public void initMoveObjectIdGeneratorObjects() {
        SuiTransactionBlockResponse suiTransactionBlockResponse = suiJsonRpcClient.getTransactionBlock(
                packagePublishTransaction,
                new SuiTransactionBlockResponseOptions(true, true, true, true, true, true)
        );

        AtomicReference<String> packageIdRef = new AtomicReference<>();
        ObjectChange[] objectChanges = suiTransactionBlockResponse.getObjectChanges();
        Arrays.stream(objectChanges).filter(
                event -> event instanceof ObjectChange.Published
        ).findFirst().ifPresent(event -> {
            ObjectChange.Published published = (ObjectChange.Published) event;
            //System.out.println(published);
            packageIdRef.set(published.getPackageId());
            saveDefaultSuiPackageIfNotExists(
                    published.getPackageId(),
                    null //todo publish.getDigest(),
            );
        });

        //System.out.println("package Id: " + packageIdRef.get());
        String packageId = packageIdRef.get();
        String[] idGeneratorDataObjTypes = ContractConstants.getMoveObjectIdGeneratorObjectTypes(packageId);
        Arrays.stream(objectChanges).filter(
                event -> event instanceof ObjectChange.Created
        ).forEach(event -> {
            ObjectChange.Created objectCreated = (ObjectChange.Created) event;
            int idx = objectCreated.getObjectType().indexOf("::");
            if (objectCreated.getObjectType().substring(0, idx).equals(packageId)) {
                if (Arrays.stream(idGeneratorDataObjTypes).anyMatch(t -> t.equals(objectCreated.getObjectType()))) {
                    saveMoveObjectIdGeneratorObjectIfNotExists(
                            objectCreated.getObjectType(), objectCreated.getObjectId());
                }
            }
        });
    }

    private void saveDefaultSuiPackageIfNotExists(String packageId, String publisher) {
        if (suiPackageRepository.findById(ContractConstants.DEFAULT_SUI_PACKAGE_NAME).isPresent()) {
            return;
        }
        SuiPackage suiPackage = new SuiPackage();
        suiPackage.setName(ContractConstants.DEFAULT_SUI_PACKAGE_NAME);
        suiPackage.setObjectId(packageId);
        suiPackage.setPublisher(publisher);

        suiPackageRepository.save(suiPackage);
    }

    private void saveMoveObjectIdGeneratorObjectIfNotExists(String suiObjectType, String suiObjectId) {
        int idx = suiObjectType.indexOf("::");
        if (idx < 0) {
            throw new IllegalArgumentException("Invalid sui object type: " + suiObjectType);
        }
        String objectName = suiObjectType.substring(idx + 2);
        if (moveObjectIdGeneratorObjectRepository.findById(objectName).isPresent()) {
            return;
        }
        MoveObjectIdGeneratorObject moveObjectIdGeneratorObject = new MoveObjectIdGeneratorObject();
        moveObjectIdGeneratorObject.setName(objectName);
        moveObjectIdGeneratorObject.setObjectType(suiObjectType);
        moveObjectIdGeneratorObject.setObjectId(suiObjectId);
        moveObjectIdGeneratorObjectRepository.save(moveObjectIdGeneratorObject);
    }
}
