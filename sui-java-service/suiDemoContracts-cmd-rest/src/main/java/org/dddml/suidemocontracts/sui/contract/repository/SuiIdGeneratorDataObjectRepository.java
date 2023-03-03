package org.dddml.suidemocontracts.sui.contract.repository;

import org.dddml.suidemocontracts.sui.contract.SuiIdGeneratorDataObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuiIdGeneratorDataObjectRepository extends JpaRepository<SuiIdGeneratorDataObject, String> {
}
