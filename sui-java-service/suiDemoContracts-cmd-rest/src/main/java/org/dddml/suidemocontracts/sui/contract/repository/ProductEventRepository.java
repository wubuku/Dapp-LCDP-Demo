package org.dddml.suidemocontracts.sui.contract.repository;

import org.dddml.suidemocontracts.domain.product.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductEventRepository extends JpaRepository<AbstractProductEvent, ProductEventId> {
    AbstractProductEvent.ProductCreated findFirstProductCreatedByOrderBySuiTimestampDesc();
}
