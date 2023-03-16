package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.Command;
import org.dddml.suidemocontracts.specialization.DomainError;

public interface OrderItemShipGroupAssocSubitemCommand extends Command
{

    Day getOrderItemShipGroupAssocSubitemDay();

    void setOrderItemShipGroupAssocSubitemDay(Day orderItemShipGroupAssocSubitemDay);

}

