// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.aptos.contract.repository;

import org.dddml.aptosdemocontracts.domain.order.OrderItemId;
import org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemTableItemAdded;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.*;
import java.util.List;

public interface OrderItemTableItemAddedRepository extends JpaRepository<OrderItemTableItemAdded, OrderItemId> {

    @Transactional(readOnly = true)
    List<OrderItemTableItemAdded> findByOrderByAptosEventSequenceNumber(Pageable pageable);

    @Transactional(readOnly = true)
    OrderItemTableItemAdded findFirstByOrderByAptosEventSequenceNumber();

    @Transactional(readOnly = true)
    List<OrderItemTableItemAdded> findByOrderItemId_OrderId(String orderId);

}
