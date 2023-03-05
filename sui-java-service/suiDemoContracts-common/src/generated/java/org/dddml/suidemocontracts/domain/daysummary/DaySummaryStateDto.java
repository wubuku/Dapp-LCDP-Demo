package org.dddml.suidemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;


public class DaySummaryStateDto
{

    private Day day;

    public Day getDay()
    {
        return this.day;
    }

    public void setDay(Day day)
    {
        this.day = day;
    }

    private String id_;

    public String getId_()
    {
        return this.id_;
    }

    public void setId_(String id)
    {
        this.id_ = id;
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

    private int[] metadata;

    public int[] getMetadata()
    {
        return this.metadata;
    }

    public void setMetadata(int[] metadata)
    {
        this.metadata = metadata;
    }

    private int[] optionalData;

    public int[] getOptionalData()
    {
        return this.optionalData;
    }

    public void setOptionalData(int[] optionalData)
    {
        this.optionalData = optionalData;
    }

    private BigInteger version;

    public BigInteger getVersion()
    {
        return this.version;
    }

    public void setVersion(BigInteger version)
    {
        this.version = version;
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

    private String[] arrayData;

    public String[] getArrayData() {
        return this.arrayData;
    }

    public void setArrayData(String[] arrayData) {
        this.arrayData = arrayData;
    }


    public static class DtoConverter extends AbstractStateDtoConverter
    {
        public static Collection<String> collectionFieldNames = Arrays.asList(new String[]{});

        @Override
        protected boolean isCollectionField(String fieldName) {
            return CollectionUtils.collectionContainsIgnoringCase(collectionFieldNames, fieldName);
        }

        public DaySummaryStateDto[] toDaySummaryStateDtoArray(Iterable<DaySummaryState> states) {
            return toDaySummaryStateDtoList(states).toArray(new DaySummaryStateDto[0]);
        }

        public List<DaySummaryStateDto> toDaySummaryStateDtoList(Iterable<DaySummaryState> states) {
            ArrayList<DaySummaryStateDto> stateDtos = new ArrayList();
            for (DaySummaryState s : states) {
                DaySummaryStateDto dto = toDaySummaryStateDto(s);
                stateDtos.add(dto);
            }
            return stateDtos;
        }

        public DaySummaryStateDto toDaySummaryStateDto(DaySummaryState state)
        {
            if(state == null) {
                return null;
            }
            DaySummaryStateDto dto = new DaySummaryStateDto();
            if (returnedFieldsContains("Day")) {
                dto.setDay(state.getDay());
            }
            if (returnedFieldsContains("Id_")) {
                dto.setId_(state.getId_());
            }
            if (returnedFieldsContains("Description")) {
                dto.setDescription(state.getDescription());
            }
            if (returnedFieldsContains("Metadata")) {
                dto.setMetadata(state.getMetadata());
            }
            if (returnedFieldsContains("OptionalData")) {
                dto.setOptionalData(state.getOptionalData());
            }
            if (returnedFieldsContains("Version")) {
                dto.setVersion(state.getVersion());
            }
            if (returnedFieldsContains("Active")) {
                dto.setActive(state.getActive());
            }
            if (returnedFieldsContains("OffChainVersion")) {
                dto.setOffChainVersion(state.getOffChainVersion());
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
            if (returnedFieldsContains("ArrayData")) {
                ArrayList<String> arrayList = new ArrayList();
                if (state.getArrayData() != null) {
                    for (String s : state.getArrayData()) {
                        arrayList.add(s);
                    }
                }
                dto.setArrayData(arrayList.toArray(new String[0]));
            }
            return dto;
        }

    }
}

