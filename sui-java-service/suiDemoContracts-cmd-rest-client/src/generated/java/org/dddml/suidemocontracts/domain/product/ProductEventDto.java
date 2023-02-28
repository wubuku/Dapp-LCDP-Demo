package org.dddml.suidemocontracts.domain.product;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public class ProductEventDto extends AbstractEvent implements ProductEvent.ProductStateEvent {
    public static class ProductStateEventDto extends ProductEventDto {
    }

    private ProductEventId productEventId;

    ProductEventId getProductEventId() {
        if (productEventId == null) { productEventId = new ProductEventId(); }
        return productEventId;
    }

    void setProductEventId(ProductEventId eventId) {
        this.productEventId = eventId;
    }

    public String getProductId() {
        return getProductEventId().getProductId();
    }

    public void setProductId(String productId) {
        getProductEventId().setProductId(productId);
    }

    public Long getVersion() {
        return getProductEventId().getVersion();
    }
    
    public void setVersion(Long version) {
        getProductEventId().setVersion(version);
    }

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private BigInteger unitPrice;

    public BigInteger getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(BigInteger unitPrice) {
        this.unitPrice = unitPrice;
    }

    private Boolean active;

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    private String createdBy;

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    private Date createdAt;

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    private Boolean isPropertyNameRemoved;

    public Boolean getIsPropertyNameRemoved() {
        return this.isPropertyNameRemoved;
    }

    public void setIsPropertyNameRemoved(Boolean removed) {
        this.isPropertyNameRemoved = removed;
    }

    private Boolean isPropertyUnitPriceRemoved;

    public Boolean getIsPropertyUnitPriceRemoved() {
        return this.isPropertyUnitPriceRemoved;
    }

    public void setIsPropertyUnitPriceRemoved(Boolean removed) {
        this.isPropertyUnitPriceRemoved = removed;
    }

    private Boolean isPropertyActiveRemoved;

    public Boolean getIsPropertyActiveRemoved() {
        return this.isPropertyActiveRemoved;
    }

    public void setIsPropertyActiveRemoved(Boolean removed) {
        this.isPropertyActiveRemoved = removed;
    }

    public ProductEventDto toSubclass() {
        if (STATE_EVENT_TYPE_CREATED.equals(getEventType())) {
            ProductStateCreatedDto e = new ProductStateCreatedDto();
            copyTo(e);
            return e;
        }

        throw new UnsupportedOperationException("Unknown event type:" + getEventType());
    }

    public void copyTo(ProductEventDto e) {
        e.setProductId(this.getProductId());
        e.setVersion(this.getVersion());
        e.setName(this.getName());
        e.setUnitPrice(this.getUnitPrice());
        e.setActive(this.getActive());
        e.setCreatedBy(this.getCreatedBy());
        e.setCreatedAt(this.getCreatedAt());
        e.setIsPropertyNameRemoved(this.getIsPropertyNameRemoved());
        e.setIsPropertyUnitPriceRemoved(this.getIsPropertyUnitPriceRemoved());
        e.setIsPropertyActiveRemoved(this.getIsPropertyActiveRemoved());
    }

	public static class ProductStateCreatedDto extends ProductEventDto implements ProductEvent.ProductStateCreated {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_CREATED;
        }

	}


}

