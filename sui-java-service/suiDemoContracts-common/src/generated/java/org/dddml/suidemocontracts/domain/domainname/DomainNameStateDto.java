package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;


public class DomainNameStateDto
{

    private DomainNameId domainNameId;

    public DomainNameId getDomainNameId()
    {
        return this.domainNameId;
    }

    public void setDomainNameId(DomainNameId domainNameId)
    {
        this.domainNameId = domainNameId;
    }

    private String id;

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    private BigInteger expirationDate;

    public BigInteger getExpirationDate()
    {
        return this.expirationDate;
    }

    public void setExpirationDate(BigInteger expirationDate)
    {
        this.expirationDate = expirationDate;
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


    public static class DtoConverter extends AbstractStateDtoConverter
    {
        public static Collection<String> collectionFieldNames = Arrays.asList(new String[]{});

        @Override
        protected boolean isCollectionField(String fieldName) {
            return CollectionUtils.collectionContainsIgnoringCase(collectionFieldNames, fieldName);
        }

        public DomainNameStateDto[] toDomainNameStateDtoArray(Iterable<DomainNameState> states) {
            return toDomainNameStateDtoList(states).toArray(new DomainNameStateDto[0]);
        }

        public List<DomainNameStateDto> toDomainNameStateDtoList(Iterable<DomainNameState> states) {
            ArrayList<DomainNameStateDto> stateDtos = new ArrayList();
            for (DomainNameState s : states) {
                DomainNameStateDto dto = toDomainNameStateDto(s);
                stateDtos.add(dto);
            }
            return stateDtos;
        }

        public DomainNameStateDto toDomainNameStateDto(DomainNameState state)
        {
            if(state == null) {
                return null;
            }
            DomainNameStateDto dto = new DomainNameStateDto();
            if (returnedFieldsContains("DomainNameId")) {
                dto.setDomainNameId(state.getDomainNameId());
            }
            if (returnedFieldsContains("Id")) {
                dto.setId(state.getId());
            }
            if (returnedFieldsContains("ExpirationDate")) {
                dto.setExpirationDate(state.getExpirationDate());
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
            return dto;
        }

    }
}

