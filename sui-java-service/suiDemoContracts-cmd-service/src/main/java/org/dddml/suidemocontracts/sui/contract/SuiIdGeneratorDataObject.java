package org.dddml.suidemocontracts.sui.contract;

import java.util.Date;
import java.util.Objects;

public class SuiIdGeneratorDataObject {
    private String objectName;

    private String suiObjectType;
    private String suiObjectId;

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getSuiObjectType() {
        return suiObjectType;
    }

    public void setSuiObjectType(String suiObjectType) {
        this.suiObjectType = suiObjectType;
    }

    public String getSuiObjectId() {
        return suiObjectId;
    }

    public void setSuiObjectId(String suiObjectId) {
        this.suiObjectId = suiObjectId;
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

    private String updatedBy;

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    private Date updatedAt;

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuiIdGeneratorDataObject that = (SuiIdGeneratorDataObject) o;
        return Objects.equals(objectName, that.objectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectName);
    }

    @Override
    public String toString() {
        return "SuiIdGeneratorDataObject{" +
                "objectName='" + objectName + '\'' +
                ", suiObjectType='" + suiObjectType + '\'' +
                ", suiObjectId='" + suiObjectId + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt=" + createdAt +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
