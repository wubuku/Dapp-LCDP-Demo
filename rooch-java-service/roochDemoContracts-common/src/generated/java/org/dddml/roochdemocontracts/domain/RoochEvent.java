// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain;

import java.math.*;
import java.util.*;

public interface RoochEvent {

    BigInteger getRoochEventVersion();

    BigInteger getRoochEventSequenceNumber();

    String getRoochEventType();

    RoochEventGuid getRoochEventGuid();

    interface MutableRoochEvent {

        void setRoochEventVersion(BigInteger p);

        void setRoochEventSequenceNumber(BigInteger p);

        void setRoochEventType(String p);

        void setRoochEventGuid(RoochEventGuid p);

    }

}
