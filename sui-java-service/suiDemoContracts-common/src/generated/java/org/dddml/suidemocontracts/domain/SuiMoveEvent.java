package org.dddml.suidemocontracts.domain;

import java.math.*;
import java.util.*;

public interface SuiMoveEvent {

    String getSuiPackageId();

    String getSuiTransactionModule();

    String getSuiSender();

    String getSuiType();

    interface MutableSuiMoveEvent {

        void setSuiPackageId(String p);

        void setSuiTransactionModule(String p);

        void setSuiSender(String p);

        void setSuiType(String p);

    }

}
