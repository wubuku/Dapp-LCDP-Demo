// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.product;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.domain.AbstractCommand;

public abstract class AbstractProductCommandDto extends AbstractCommand {

    /**
     * Product Id
     */
    private String productId;

    public String getProductId()
    {
        return this.productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    /**
     * Id
     */
    private String id_;

    public String getId_()
    {
        return this.id_;
    }

    public void setId_(String id)
    {
        this.id_ = id;
    }

    /**
     * Off Chain Version
     */
    private Long offChainVersion;

    public Long getOffChainVersion()
    {
        return this.offChainVersion;
    }

    public void setOffChainVersion(Long offChainVersion)
    {
        this.offChainVersion = offChainVersion;
    }


    public void copyTo(ProductCommand command) {
        command.setProductId(this.getProductId());
        command.setId_(this.getId_());
        command.setOffChainVersion(this.getOffChainVersion());
        
        command.setRequesterId(this.getRequesterId());
        command.setCommandId(this.getCommandId());
    }

}
