package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.SuiEvent;
import com.github.wubuku.sui.bean.SuiTransactionResponse;
import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.sui.contract.ContractConstants;
import org.dddml.suidemocontracts.sui.contract.SuiIdGeneratorDataObject;
import org.dddml.suidemocontracts.sui.contract.repository.SuiIdGeneratorDataObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SuiIdGeneratorDataObjectService {
    @Autowired
    private SuiIdGeneratorDataObjectRepository suiIdGeneratorDataObjectRepository;

    @Autowired
    private SuiJsonRpcClient suiJsonRpcClient;

    @Value("${sui.contract.package-publish-transaction}")
    private String packagePublishTransaction;

    public void initSuiIdGeneratorDataObjects() {
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
        });
        //System.out.println("package Id: " + packageIdRef.get());
        String packageId = packageIdRef.get();

        String[] idGeneratorDataObjTypes = ContractConstants.getIdGeneratorDataObjectTypes(packageId);
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
                    saveSuiIdGeneratorDataObjectIfNotExists(
                            newObject.getNewObject().getObjectType(), newObject.getNewObject().getObjectId());
                }
            }
        });
    }

    private void saveSuiIdGeneratorDataObjectIfNotExists(String suiObjectType, String suiObjectId) {
        int idx = suiObjectType.indexOf("::");
        if (idx < 0) {
            throw new IllegalArgumentException("Invalid sui object type: " + suiObjectType);
        }
        String objectName = suiObjectType.substring(idx + 2);
        if (suiIdGeneratorDataObjectRepository.findById(objectName).orElse(null) != null) {
            return;
        }
        SuiIdGeneratorDataObject suiIdGeneratorDataObject = new SuiIdGeneratorDataObject();
        suiIdGeneratorDataObject.setObjectName(objectName);
        suiIdGeneratorDataObject.setSuiObjectType(suiObjectType);
        suiIdGeneratorDataObject.setSuiObjectId(suiObjectId);
        suiIdGeneratorDataObjectRepository.save(suiIdGeneratorDataObject);
    }
}
