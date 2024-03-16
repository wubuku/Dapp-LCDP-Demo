package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.ObjectChange;
import com.github.wubuku.sui.bean.SuiTransactionBlockResponse;
import com.github.wubuku.sui.bean.SuiTransactionBlockResponseOptions;
import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.sui.contract.MoveObjectIdGeneratorObject;
import org.dddml.suidemocontracts.sui.contract.SuiPackage;
import org.dddml.suidemocontracts.sui.contract.repository.MoveObjectIdGeneratorObjectRepository;
import org.dddml.suidemocontracts.sui.contract.repository.SuiPackageRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class SuiPackageInitializationService {

    private MoveObjectIdGeneratorObjectRepository moveObjectIdGeneratorObjectRepository;

    private SuiPackageRepository suiPackageRepository;

    private SuiJsonRpcClient suiJsonRpcClient;

    private String packagePublishTransactionDigest;

    private String suiPackageName;

    private Function<String, String[]> moveObjectIdGeneratorObjectTypesGetter;

    public SuiPackageInitializationService(
            MoveObjectIdGeneratorObjectRepository moveObjectIdGeneratorObjectRepository,
            SuiPackageRepository suiPackageRepository,
            SuiJsonRpcClient suiJsonRpcClient,
            String packagePublishTransactionDigest,
            String suiPackageName,
            Function<String, String[]> moveObjectIdGeneratorObjectTypesGetter
    ) {
        this.moveObjectIdGeneratorObjectRepository = moveObjectIdGeneratorObjectRepository;
        this.suiPackageRepository = suiPackageRepository;
        this.suiJsonRpcClient = suiJsonRpcClient;
        this.packagePublishTransactionDigest = packagePublishTransactionDigest;
        this.suiPackageName = suiPackageName;
        this.moveObjectIdGeneratorObjectTypesGetter = moveObjectIdGeneratorObjectTypesGetter;
    }

    @Transactional
    public void init() {
        boolean showInput = false;
        boolean showRawInput = false;
        boolean showEffects = false;
        boolean showEvents = true;
        boolean showObjectChanges = true;
        boolean showBalanceChanges = false;
        SuiTransactionBlockResponse suiTransactionBlockResponse = suiJsonRpcClient.getTransactionBlock(
                packagePublishTransactionDigest,
                new SuiTransactionBlockResponseOptions(showInput, showRawInput, showEffects, showEvents, showObjectChanges, showBalanceChanges)
        );

        AtomicReference<String> packageIdRef = new AtomicReference<>();
        ObjectChange[] objectChanges = suiTransactionBlockResponse.getObjectChanges();
        if (objectChanges == null) {
            System.out.println("No object changes found in SuiTransactionBlockResponse");
            return;
        }
        Arrays.stream(objectChanges).filter(
                c -> c instanceof ObjectChange.Published
        ).findFirst().ifPresent(c -> {
            ObjectChange.Published published = (ObjectChange.Published) c;
            //System.out.println(published);
            packageIdRef.set(published.getPackageId());
            saveDefaultSuiPackageIfNotExists(
                    published.getPackageId(),
                    published.getDigest()
            );
        });

        //System.out.println("package Id: " + packageIdRef.get());
        String packageId = packageIdRef.get();
        Arrays.stream(objectChanges).filter(
                c -> c instanceof ObjectChange.Created
        ).forEach(c -> {
            ObjectChange.Created objectCreated = (ObjectChange.Created) c;
            int idx = objectCreated.getObjectType().indexOf("::");
            if (objectCreated.getObjectType().substring(0, idx).equals(packageId)) {
                if (Arrays.stream(moveObjectIdGeneratorObjectTypesGetter.apply(packageId)).anyMatch(t -> t.equals(objectCreated.getObjectType()))) {
                    saveMoveObjectIdGeneratorObjectIfNotExists(
                            objectCreated.getObjectType(), objectCreated.getObjectId());
                }
            }
        });
    }

    private void saveDefaultSuiPackageIfNotExists(String packageId, String publisher) {
        if (suiPackageRepository.findById(suiPackageName).isPresent()) {
            return;
        }
        SuiPackage suiPackage = new SuiPackage();
        suiPackage.setName(suiPackageName);
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
