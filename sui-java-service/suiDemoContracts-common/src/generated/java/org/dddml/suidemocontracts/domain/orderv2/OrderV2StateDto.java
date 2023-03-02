package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;


public class OrderV2StateDto
{

    private String orderId;

    public String getOrderId()
    {
        return this.orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    private String surrogateId;

    public String getSurrogateId()
    {
        return this.surrogateId;
    }

    public void setSurrogateId(String id)
    {
        this.surrogateId = id;
    }

    private BigInteger totalAmount;

    public BigInteger getTotalAmount()
    {
        return this.totalAmount;
    }

    public void setTotalAmount(BigInteger totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    private Day estimatedShipDate;

    public Day getEstimatedShipDate()
    {
        return this.estimatedShipDate;
    }

    public void setEstimatedShipDate(Day estimatedShipDate)
    {
        this.estimatedShipDate = estimatedShipDate;
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

    private Long version;

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
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

    private OrderV2ItemStateDto[] items;

    public OrderV2ItemStateDto[] getItems()
    {
        return this.items;
    }	

    public void setItems(OrderV2ItemStateDto[] items)
    {
        this.items = items;
    }


    public static class DtoConverter extends AbstractStateDtoConverter
    {
        public static Collection<String> collectionFieldNames = Arrays.asList(new String[]{"Items"});

        @Override
        protected boolean isCollectionField(String fieldName) {
            return CollectionUtils.collectionContainsIgnoringCase(collectionFieldNames, fieldName);
        }

        public OrderV2StateDto[] toOrderV2StateDtoArray(Iterable<OrderV2State> states) {
            return toOrderV2StateDtoList(states).toArray(new OrderV2StateDto[0]);
        }

        public List<OrderV2StateDto> toOrderV2StateDtoList(Iterable<OrderV2State> states) {
            ArrayList<OrderV2StateDto> stateDtos = new ArrayList();
            for (OrderV2State s : states) {
                OrderV2StateDto dto = toOrderV2StateDto(s);
                stateDtos.add(dto);
            }
            return stateDtos;
        }

        public OrderV2StateDto toOrderV2StateDto(OrderV2State state)
        {
            if(state == null) {
                return null;
            }
            OrderV2StateDto dto = new OrderV2StateDto();
            if (returnedFieldsContains("OrderId")) {
                dto.setOrderId(state.getOrderId());
            }
            if (returnedFieldsContains("SurrogateId")) {
                dto.setSurrogateId(state.getSurrogateId());
            }
            if (returnedFieldsContains("TotalAmount")) {
                dto.setTotalAmount(state.getTotalAmount());
            }
            if (returnedFieldsContains("EstimatedShipDate")) {
                dto.setEstimatedShipDate(state.getEstimatedShipDate());
            }
            if (returnedFieldsContains("Active")) {
                dto.setActive(state.getActive());
            }
            if (returnedFieldsContains("Version")) {
                dto.setVersion(state.getVersion());
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
            if (returnedFieldsContains("Items")) {
                ArrayList<OrderV2ItemStateDto> arrayList = new ArrayList();
                if (state.getItems() != null) {
                    OrderV2ItemStateDto.DtoConverter conv = new OrderV2ItemStateDto.DtoConverter();
                    String returnFS = CollectionUtils.mapGetValueIgnoringCase(getReturnedFields(), "Items");
                    if(returnFS != null) { conv.setReturnedFieldsString(returnFS); } else { conv.setAllFieldsReturned(this.getAllFieldsReturned()); }
                    for (OrderV2ItemState s : state.getItems()) {
                        arrayList.add(conv.toOrderV2ItemStateDto(s));
                    }
                }
                dto.setItems(arrayList.toArray(new OrderV2ItemStateDto[0]));
            }
            return dto;
        }

    }
}
