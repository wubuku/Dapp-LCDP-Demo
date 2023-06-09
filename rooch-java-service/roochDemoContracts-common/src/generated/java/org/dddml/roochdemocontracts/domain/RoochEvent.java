// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain;

import java.math.*;
import java.util.*;

public interface RoochEvent {

    RoochEventId getRoochEventId();

    String getRoochSender();

    String getRoochTxHash();

    String getRoochTypeTag();

    Long getRoochTimestampMs();

    BigInteger getRoochBlockHeight();

    Long getRoochEventIndex();

    interface MutableRoochEvent {

        void setRoochEventId(RoochEventId p);

        void setRoochSender(String p);

        void setRoochTxHash(String p);

        void setRoochTypeTag(String p);

        void setRoochTimestampMs(Long p);

        void setRoochBlockHeight(BigInteger p);

        void setRoochEventIndex(Long p);

    }

}
