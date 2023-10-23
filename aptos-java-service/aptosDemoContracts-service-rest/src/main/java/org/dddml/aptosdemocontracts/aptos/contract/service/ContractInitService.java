package org.dddml.aptosdemocontracts.aptos.contract.service;

import com.github.wubuku.aptos.bean.AccountResource;
import com.github.wubuku.aptos.bean.SignerCapability;
import com.github.wubuku.aptos.utils.NodeApiClient;
import org.dddml.aptosdemocontracts.aptos.contract.AptosAccount;
import org.dddml.aptosdemocontracts.aptos.contract.ContractConstants;
import org.dddml.aptosdemocontracts.aptos.contract.repository.AptosAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class ContractInitService {
    @Autowired
    private AptosAccountRepository aptosAccountRepository;

    @Autowired
    private NodeApiClient aptosNodeApiClient;

    @Value("${aptos.contract.address}")
    private String aptosContractAddress;

    @Transactional
    public void initAccountAddresses() {
        String resourceType = aptosContractAddress + "::" + ContractConstants.RESOURCE_ACCOUNT_MODULE_RESOURCE_ACCOUNT;
        try {
            AccountResource<ResourceAccount> resource = aptosNodeApiClient.getAccountResource(aptosContractAddress,
                    resourceType, ResourceAccount.class, null);
            if (resource != null) {
                saveResourceAccountIfNotExists(resource.getData().getCap().getAccount());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveResourceAccountIfNotExists(String address) {
        if (aptosAccountRepository.findById(ContractConstants.RESOURCE_ACCOUNT_ADDRESS).isPresent()) {
            return;
        }
        AptosAccount aptosAccount = new AptosAccount();
        aptosAccount.setName(ContractConstants.RESOURCE_ACCOUNT_ADDRESS);
        aptosAccount.setAddress(address);
        aptosAccountRepository.save(aptosAccount);
    }

    static class ResourceAccount {
        private SignerCapability cap;

        public SignerCapability getCap() {
            return cap;
        }

        public void setCap(SignerCapability cap) {
            this.cap = cap;
        }

        @Override
        public String toString() {
            return "ResourceAccount{" +
                    "cap=" + cap +
                    '}';
        }
    }
}
