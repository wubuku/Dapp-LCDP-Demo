// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.order;

import java.util.List;
import java.math.BigInteger;
import org.dddml.roochdemocontracts.domain.*;
import java.util.Date;
import org.dddml.roochdemocontracts.specialization.Event;
import org.dddml.roochdemocontracts.domain.Command;

public interface OrderAggregate {
    OrderState getState();

    List<Event> getChanges();

    void create(String productObjId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId, OrderCommands.Create c);

    void removeItem(String productObjId, Long offChainVersion, String commandId, String requesterId, OrderCommands.RemoveItem c);

    void updateItemQuantity(String productObjId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId, OrderCommands.UpdateItemQuantity c);

    void updateEstimatedShipDate(Day estimatedShipDate, Long offChainVersion, String commandId, String requesterId, OrderCommands.UpdateEstimatedShipDate c);

    void addOrderShipGroup(Integer shipGroupSeqId, String shipmentMethod, String productObjId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId, OrderCommands.AddOrderShipGroup c);

    void addOrderItemShipGroupAssocSubitem(Integer shipGroupSeqId, String productObjId, Day day, String description, Long offChainVersion, String commandId, String requesterId, OrderCommands.AddOrderItemShipGroupAssocSubitem c);

    void cancelOrderShipGroupQuantity(Integer shipGroupSeqId, String productObjId, BigInteger cancelQuantity, Long offChainVersion, String commandId, String requesterId, OrderCommands.CancelOrderShipGroupQuantity c);

    void removeOrderShipGroupItem(Integer shipGroupSeqId, String productObjId, Long offChainVersion, String commandId, String requesterId, OrderCommands.RemoveOrderShipGroupItem c);

    void throwOnInvalidStateTransition(Command c);
}

