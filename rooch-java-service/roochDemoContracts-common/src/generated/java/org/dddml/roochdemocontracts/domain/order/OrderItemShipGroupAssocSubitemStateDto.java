// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.order;

import java.util.*;
import java.math.*;
import org.dddml.roochdemocontracts.domain.*;
import java.util.Date;
import org.dddml.roochdemocontracts.specialization.*;


public class OrderItemShipGroupAssocSubitemStateDto {

    private Day orderItemShipGroupAssocSubitemDay;

    public Day getOrderItemShipGroupAssocSubitemDay()
    {
        return this.orderItemShipGroupAssocSubitemDay;
    }

    public void setOrderItemShipGroupAssocSubitemDay(Day orderItemShipGroupAssocSubitemDay)
    {
        this.orderItemShipGroupAssocSubitemDay = orderItemShipGroupAssocSubitemDay;
    }

    private String description;

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    private String orderId;

    public String getOrderId()
    {
        return this.orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
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

    private String orderItemShipGroupAssociationProductObjId;

    public String getOrderItemShipGroupAssociationProductObjId()
    {
        return this.orderItemShipGroupAssociationProductObjId;
    }

    public void setOrderItemShipGroupAssociationProductObjId(String orderItemShipGroupAssociationProductObjId)
    {
        this.orderItemShipGroupAssociationProductObjId = orderItemShipGroupAssociationProductObjId;
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

        public OrderItemShipGroupAssocSubitemStateDto[] toOrderItemShipGroupAssocSubitemStateDtoArray(Iterable<OrderItemShipGroupAssocSubitemState> states) {
            return toOrderItemShipGroupAssocSubitemStateDtoList(states).toArray(new OrderItemShipGroupAssocSubitemStateDto[0]);
        }

        public List<OrderItemShipGroupAssocSubitemStateDto> toOrderItemShipGroupAssocSubitemStateDtoList(Iterable<OrderItemShipGroupAssocSubitemState> states) {
            ArrayList<OrderItemShipGroupAssocSubitemStateDto> stateDtos = new ArrayList();
            for (OrderItemShipGroupAssocSubitemState s : states) {
                OrderItemShipGroupAssocSubitemStateDto dto = toOrderItemShipGroupAssocSubitemStateDto(s);
                stateDtos.add(dto);
            }
            return stateDtos;
        }

        public OrderItemShipGroupAssocSubitemStateDto toOrderItemShipGroupAssocSubitemStateDto(OrderItemShipGroupAssocSubitemState state)
        {
            if(state == null) {
                return null;
            }
            OrderItemShipGroupAssocSubitemStateDto dto = new OrderItemShipGroupAssocSubitemStateDto();
            if (returnedFieldsContains("OrderItemShipGroupAssocSubitemDay")) {
                dto.setOrderItemShipGroupAssocSubitemDay(state.getOrderItemShipGroupAssocSubitemDay());
            }
            if (returnedFieldsContains("Description")) {
                dto.setDescription(state.getDescription());
            }
            if (returnedFieldsContains("Active")) {
                dto.setActive(state.getActive());
            }
            if (returnedFieldsContains("OffChainVersion")) {
                dto.setOffChainVersion(state.getOffChainVersion());
            }
            if (returnedFieldsContains("OrderId")) {
                dto.setOrderId(state.getOrderId());
            }
            if (returnedFieldsContains("OrderShipGroupShipGroupSeqId")) {
                dto.setOrderShipGroupShipGroupSeqId(state.getOrderShipGroupShipGroupSeqId());
            }
            if (returnedFieldsContains("OrderItemShipGroupAssociationProductObjId")) {
                dto.setOrderItemShipGroupAssociationProductObjId(state.getOrderItemShipGroupAssociationProductObjId());
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

