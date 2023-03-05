package org.dddml.suidemocontracts.domain;

import java.math.*;
import java.util.*;

public interface VersionedSuiMoveObject {

    BigInteger getVersion();

    interface MutableVersionedSuiMoveObject {

        void setVersion(BigInteger p);

    }

}
