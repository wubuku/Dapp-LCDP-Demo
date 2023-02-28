package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface OrderStateRepository
{
    OrderState get(String id, boolean nullAllowed);

    void save(OrderState state);

}

