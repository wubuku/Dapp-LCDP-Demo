package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.Command;
import org.dddml.suidemocontracts.specialization.DomainError;

public interface OrderItemShipGroupAssocSubitemCommand extends Command
{

    Integer getOrderItemShipGroupAssocSubitemSeqId();

    void setOrderItemShipGroupAssocSubitemSeqId(Integer orderItemShipGroupAssocSubitemSeqId);

}

