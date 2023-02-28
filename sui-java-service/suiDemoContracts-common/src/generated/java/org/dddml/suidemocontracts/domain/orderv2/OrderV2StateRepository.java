package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;

public interface OrderV2StateRepository
{
    OrderV2State get(String id, boolean nullAllowed);

    void save(OrderV2State state);

}

