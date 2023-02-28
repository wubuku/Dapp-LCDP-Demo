package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface DomainNameStateRepository
{
    DomainNameState get(DomainNameId id, boolean nullAllowed);

    void save(DomainNameState state);

}

