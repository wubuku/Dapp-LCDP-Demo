package org.dddml.aptosdemocontracts.aptos.contract.repository;

import org.dddml.aptosdemocontracts.aptos.contract.AptosAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AptosAccountRepository extends JpaRepository<AptosAccount, String> {
    
}
