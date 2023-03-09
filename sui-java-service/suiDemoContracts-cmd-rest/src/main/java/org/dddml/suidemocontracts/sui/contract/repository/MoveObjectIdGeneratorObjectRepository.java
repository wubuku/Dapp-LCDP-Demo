package org.dddml.suidemocontracts.sui.contract.repository;

import org.dddml.suidemocontracts.sui.contract.MoveObjectIdGeneratorObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveObjectIdGeneratorObjectRepository extends JpaRepository<MoveObjectIdGeneratorObject, String> {
}
