package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.SuiEvent;
import com.github.wubuku.sui.bean.SuiTransactionResponse;
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
        SuiTransactionResponse suiTransactionResponse = suiJsonRpcClient.getTransaction(
                packagePublishTransaction
        );
        //System.out.println(suiTransactionResponse);
        AtomicReference<String> packageIdRef = new AtomicReference<>();
        Arrays.stream(suiTransactionResponse.getEffects().getEvents()).filter(
                event -> event instanceof SuiEvent.Publish
        ).findFirst().ifPresent(event -> {
            SuiEvent.Publish publish = (SuiEvent.Publish) event;
            //System.out.println(publish);
            packageIdRef.set(publish.getPublish().getPackageId());
            saveDefaultSuiPackageIfNotExists(
                    publish.getPublish().getPackageId(),
                    publish.getPublish().getSender()
            );
        });
        //System.out.println("package Id: " + packageIdRef.get());
        String packageId = packageIdRef.get();

        String[] idGeneratorDataObjTypes = ContractConstants.getMoveObjectIdGeneratorObjectTypes(packageId);
        //System.out.println(idGeneratorDataObjTypes.length);
        Arrays.stream(suiTransactionResponse.getEffects().getEvents()).filter(
                event -> event instanceof SuiEvent.NewObject
        ).forEach(event -> {
            SuiEvent.NewObject newObject = (SuiEvent.NewObject) event;
            //System.out.println(newObject);
            if (newObject.getNewObject().getPackageId().equals(packageId)) {
                //System.out.println(newObject.getNewObject().getObjectId());
                if (Arrays.stream(idGeneratorDataObjTypes).anyMatch(t ->
                        t.equals(newObject.getNewObject().getObjectType()))) {
                    //System.out.println("new object Id: " + newObject.getNewObject().getObjectId() + ", type: " + newObject.getNewObject().getObjectType());
                    saveMoveObjectIdGeneratorObjectIfNotExists(
                            newObject.getNewObject().getObjectType(), newObject.getNewObject().getObjectId());
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
