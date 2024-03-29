// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain.order;

import java.util.List;
import java.math.BigInteger;
import org.dddml.aptosdemocontracts.domain.*;
import java.util.Date;
import org.dddml.aptosdemocontracts.specialization.Event;
import org.dddml.aptosdemocontracts.domain.Command;

public interface OrderAggregate {
    OrderState getState();

    List<Event> getChanges();

    void create(String productId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId, OrderCommands.Create c);

    void removeItem(String productId, Long offChainVersion, String commandId, String requesterId, OrderCommands.RemoveItem c);

    void updateItemQuantity(String productId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId, OrderCommands.UpdateItemQuantity c);

    void updateEstimatedShipDate(Day estimatedShipDate, Long offChainVersion, String commandId, String requesterId, OrderCommands.UpdateEstimatedShipDate c);

    void addOrderShipGroup(Integer shipGroupSeqId, String shipmentMethod, String productId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId, OrderCommands.AddOrderShipGroup c);

    void cancelOrderShipGroupQuantity(Integer shipGroupSeqId, String productId, BigInteger cancelQuantity, Long offChainVersion, String commandId, String requesterId, OrderCommands.CancelOrderShipGroupQuantity c);

    void removeOrderShipGroupItem(Integer shipGroupSeqId, String productId, Long offChainVersion, String commandId, String requesterId, OrderCommands.RemoveOrderShipGroupItem c);

    void removeOrderShipGroup(Integer shipGroupSeqId, Long offChainVersion, String commandId, String requesterId, OrderCommands.RemoveOrderShipGroup c);

    void throwOnInvalidStateTransition(Command c);
}

