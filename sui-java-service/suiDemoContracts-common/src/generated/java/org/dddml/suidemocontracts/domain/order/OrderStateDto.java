package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;


public class OrderStateDto
{

    private String id;

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
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

    private OrderItemStateDto[] items;

    public OrderItemStateDto[] getItems()
    {
        return this.items;
    }	

    public void setItems(OrderItemStateDto[] items)
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

        public OrderStateDto[] toOrderStateDtoArray(Iterable<OrderState> states) {
            return toOrderStateDtoList(states).toArray(new OrderStateDto[0]);
        }

        public List<OrderStateDto> toOrderStateDtoList(Iterable<OrderState> states) {
            ArrayList<OrderStateDto> stateDtos = new ArrayList();
            for (OrderState s : states) {
                OrderStateDto dto = toOrderStateDto(s);
                stateDtos.add(dto);
            }
            return stateDtos;
        }

        public OrderStateDto toOrderStateDto(OrderState state)
        {
            if(state == null) {
                return null;
            }
            OrderStateDto dto = new OrderStateDto();
            if (returnedFieldsContains("Id")) {
                dto.setId(state.getId());
            }
            if (returnedFieldsContains("TotalAmount")) {
                dto.setTotalAmount(state.getTotalAmount());
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
                ArrayList<OrderItemStateDto> arrayList = new ArrayList();
                if (state.getItems() != null) {
                    OrderItemStateDto.DtoConverter conv = new OrderItemStateDto.DtoConverter();
                    String returnFS = CollectionUtils.mapGetValueIgnoringCase(getReturnedFields(), "Items");
                    if(returnFS != null) { conv.setReturnedFieldsString(returnFS); } else { conv.setAllFieldsReturned(this.getAllFieldsReturned()); }
                    for (OrderItemState s : state.getItems()) {
                        arrayList.add(conv.toOrderItemStateDto(s));
                    }
                }
                dto.setItems(arrayList.toArray(new OrderItemStateDto[0]));
            }
            return dto;
        }

    }
}

