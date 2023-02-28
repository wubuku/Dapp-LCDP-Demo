package org.dddml.suidemocontracts.domain.product;

import java.util.*;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface ProductStateRepository
{
    ProductState get(String id, boolean nullAllowed);

    void save(ProductState state);

}

