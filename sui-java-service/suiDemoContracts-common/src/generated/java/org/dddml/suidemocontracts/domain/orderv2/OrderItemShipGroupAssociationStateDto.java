package org.dddml.suidemocontracts.domain.orderv2;

import java.math.BigInteger;
import java.util.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;


public class OrderItemShipGroupAssociationStateDto
{

    private String productId;

    public String getProductId()
    {
        return this.productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    private BigInteger quantity;

    public BigInteger getQuantity()
    {
        return this.quantity;
    }

    public void setQuantity(BigInteger quantity)
    {
        this.quantity = quantity;
    }

    private BigInteger cancelQuantity;

    public BigInteger getCancelQuantity()
    {
        return this.cancelQuantity;
    }

    public void setCancelQuantity(BigInteger cancelQuantity)
    {
        this.cancelQuantity = cancelQuantity;
    }

    private Boolean active;

    public Boolean getActive()
    {
        return this.active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    private Long offChainVersion;

    public Long getOffChainVersion()
    {
        return this.offChainVersion;
    }

    public void setOffChainVersion(Long offChainVersion)
    {
        this.offChainVersion = offChainVersion;
    }

    private String orderV2OrderId;

    public String getOrderV2OrderId()
    {
        return this.orderV2OrderId;
    }

    public void setOrderV2OrderId(String orderV2OrderId)
    {
        this.orderV2OrderId = orderV2OrderId;
    }

    private Integer orderShipGroupShipGroupSeqId;

    public Integer getOrderShipGroupShipGroupSeqId()
    {
        return this.orderShipGroupShipGroupSeqId;
    }

    public void setOrderShipGroupShipGroupSeqId(Integer orderShipGroupShipGroupSeqId)
    {
        this.orderShipGroupShipGroupSeqId = orderShipGroupShipGroupSeqId;
    }

    private String createdBy;

    public String getCreatedBy()
    {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    private Date createdAt;

    public Date getCreatedAt()
    {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    private String updatedBy;

    public String getUpdatedBy()
    {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    private Date updatedAt;

    public Date getUpdatedAt()
    {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }


    public static class DtoConverter extends AbstractStateDtoConverter
    {
        public static Collection<String> collectionFieldNames = Arrays.asList(new String[]{});

        @Override
        protected boolean isCollectionField(String fieldName) {
            return CollectionUtils.collectionContainsIgnoringCase(collectionFieldNames, fieldName);
        }

        public OrderItemShipGroupAssociationStateDto[] toOrderItemShipGroupAssociationStateDtoArray(Iterable<OrderItemShipGroupAssociationState> states) {
            return toOrderItemShipGroupAssociationStateDtoList(states).toArray(new OrderItemShipGroupAssociationStateDto[0]);
        }

        public List<OrderItemShipGroupAssociationStateDto> toOrderItemShipGroupAssociationStateDtoList(Iterable<OrderItemShipGroupAssociationState> states) {
            ArrayList<OrderItemShipGroupAssociationStateDto> stateDtos = new ArrayList();
            for (OrderItemShipGroupAssociationState s : states) {
                OrderItemShipGroupAssociationStateDto dto = toOrderItemShipGroupAssociationStateDto(s);
                stateDtos.add(dto);
            }
            return stateDtos;
        }

        public OrderItemShipGroupAssociationStateDto toOrderItemShipGroupAssociationStateDto(OrderItemShipGroupAssociationState state)
        {
            if(state == null) {
                return null;
            }
            OrderItemShipGroupAssociationStateDto dto = new OrderItemShipGroupAssociationStateDto();
            if (returnedFieldsContains("ProductId")) {
                dto.setProductId(state.getProductId());
            }
            if (returnedFieldsContains("Quantity")) {
                dto.setQuantity(state.getQuantity());
            }
            if (returnedFieldsContains("CancelQuantity")) {
                dto.setCancelQuantity(state.getCancelQuantity());
            }
            if (returnedFieldsContains("Active")) {
                dto.setActive(state.getActive());
            }
            if (returnedFieldsContains("OffChainVersion")) {
                dto.setOffChainVersion(state.getOffChainVersion());
            }
            if (returnedFieldsContains("OrderV2OrderId")) {
                dto.setOrderV2OrderId(state.getOrderV2OrderId());
            }
            if (returnedFieldsContains("OrderShipGroupShipGroupSeqId")) {
                dto.setOrderShipGroupShipGroupSeqId(state.getOrderShipGroupShipGroupSeqId());
            }
            if (returnedFieldsContains("CreatedBy")) {
                dto.setCreatedBy(state.getCreatedBy());
            }
            if (returnedFieldsContains("CreatedAt")) {
                dto.setCreatedAt(state.getCreatedAt());
            }
            if (returnedFieldsContains("UpdatedBy")) {
                dto.setUpdatedBy(state.getUpdatedBy());
            }
            if (returnedFieldsContains("UpdatedAt")) {
                dto.setUpdatedAt(state.getUpdatedAt());
            }
            return dto;
        }

    }
}

