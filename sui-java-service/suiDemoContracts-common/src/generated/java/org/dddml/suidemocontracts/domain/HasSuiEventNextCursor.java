package org.dddml.suidemocontracts.domain;

import java.math.*;
import java.util.*;

public interface HasSuiEventNextCursor {

    SuiEventId getNextCursor();

    interface MutableHasSuiEventNextCursor {

        void setNextCursor(SuiEventId p);

    }

}
