package org.dddml.suidemocontracts.domain;

import java.math.*;
import java.util.*;

public interface SuiEventEnvelope {

    Long getSuiTimestamp();

    String getSuiTxDigest();

    Long getSuiEventSeq();

    interface MutableSuiEventEnvelope {

        void setSuiTimestamp(Long p);

        void setSuiTxDigest(String p);

        void setSuiEventSeq(Long p);

    }

}
