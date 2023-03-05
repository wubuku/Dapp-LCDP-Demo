package org.dddml.suidemocontracts.domain;

import java.math.*;
import java.util.*;

public interface VersionedSuiMoveEvent {

    BigInteger getVersion();

    interface MutableVersionedSuiMoveEvent {

        void setVersion(BigInteger p);

    }

}
