package org.dddml.suidemocontracts.domain;

import java.math.*;
import java.util.*;

public interface HasStatus {

    String getStatus();

    interface MutableHasStatus {

        void setStatus(String p);

    }

}
