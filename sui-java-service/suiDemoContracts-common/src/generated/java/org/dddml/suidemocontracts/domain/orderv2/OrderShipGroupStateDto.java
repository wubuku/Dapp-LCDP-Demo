package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;


public class OrderShipGroupStateDto
{

    private Integer shipGroupSeqId;

    public Integer getShipGroupSeqId()
    {
        return this.shipGroupSeqId;
    }

    public void setShipGroupSeqId(Integer shipGroupSeqId)
    {
        this.shipGroupSeqId = shipGroupSeqId;
    }

    private String shipmentMethod;

    public String getShipmentMethod()
    {
        return this.shipmentMethod;
    }

    public void setShipmentMethod(String shipmentMethod)
    {
        this.shipmentMethod = shipmentMethod;
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

    private OrderItemShipGroupAssociationStateDto[] orderItemShipGroupAssociations;

    public OrderItemShipGroupAssociationStateDto[] getOrderItemShipGroupAssociations()
    {
        return this.orderItemShipGroupAssociations;
    }	

    public void setOrderItemShipGroupAssociations(OrderItemShipGroupAssociationStateDto[] orderItemShipGroupAssociations)
    {
        this.orderItemShipGroupAssociations = orderItemShipGroupAssociations;
    }


    public static class DtoConverter extends AbstractStateDtoConverter
    {
        public static Collection<String> collectionFieldNames = Arrays.asList(new String[]{"OrderItemShipGroupAssociations"});

        @Override
        protected boolean isCollectionField(String fieldName) {
            return CollectionUtils.collectionContainsIgnoringCase(collectionFieldNames, fieldName);
        }

        public OrderShipGroupStateDto[] toOrderShipGroupStateDtoArray(Iterable<OrderShipGroupState> states) {
            return toOrderShipGroupStateDtoList(states).toArray(new OrderShipGroupStateDto[0]);
        }

        public List<OrderShipGroupStateDto> toOrderShipGroupStateDtoList(Iterable<OrderShipGroupState> states) {
            ArrayList<OrderShipGroupStateDto> stateDtos = new ArrayList();
            for (OrderShipGroupState s : states) {
                OrderShipGroupStateDto dto = toOrderShipGroupStateDto(s);
                stateDtos.add(dto);
            }
            return stateDtos;
        }

        public OrderShipGroupStateDto toOrderShipGroupStateDto(OrderShipGroupState state)
        {
            if(state == null) {
                return null;
            }
            OrderShipGroupStateDto dto = new OrderShipGroupStateDto();
            if (returnedFieldsContains("ShipGroupSeqId")) {
                dto.setShipGroupSeqId(state.getShipGroupSeqId());
            }
            if (returnedFieldsContains("ShipmentMethod")) {
                dto.setShipmentMethod(state.getShipmentMethod());
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
            if (returnedFieldsContains("OrderItemShipGroupAssociations")) {
                ArrayList<OrderItemShipGroupAssociationStateDto> arrayList = new ArrayList();
                if (state.getOrderItemShipGroupAssociations() != null) {
                    OrderItemShipGroupAssociationStateDto.DtoConverter conv = new OrderItemShipGroupAssociationStateDto.DtoConverter();
                    String returnFS = CollectionUtils.mapGetValueIgnoringCase(getReturnedFields(), "OrderItemShipGroupAssociations");
                    if(returnFS != null) { conv.setReturnedFieldsString(returnFS); } else { conv.setAllFieldsReturned(this.getAllFieldsReturned()); }
                    for (OrderItemShipGroupAssociationState s : state.getOrderItemShipGroupAssociations()) {
                        arrayList.add(conv.toOrderItemShipGroupAssociationStateDto(s));
                    }
                }
                dto.setOrderItemShipGroupAssociations(arrayList.toArray(new OrderItemShipGroupAssociationStateDto[0]));
            }
            return dto;
        }

    }
}

