// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.order;

import java.util.*;
import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.domain.Command;
import org.dddml.roochdemocontracts.specialization.DomainError;

public interface OrderItemShipGroupAssociationCommand extends Command {

    String getProductObjId();

    void setProductObjId(String productObjId);

}

