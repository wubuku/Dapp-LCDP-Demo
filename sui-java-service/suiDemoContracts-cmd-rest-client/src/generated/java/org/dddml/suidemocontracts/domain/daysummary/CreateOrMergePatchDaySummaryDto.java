package org.dddml.suidemocontracts.domain.daysummary;

import org.dddml.suidemocontracts.domain.*;
import java.util.Date;

public class CreateOrMergePatchDaySummaryDto extends AbstractDaySummaryCommandDto
{

    /**
     * Description
     */
    private String description;

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Metadata
     */
    private int[] metadata;

    public int[] getMetadata()
    {
        return this.metadata;
    }

    public void setMetadata(int[] metadata)
    {
        this.metadata = metadata;
    }

    /**
     * Optional Data
     */
    private int[] optionalData;

    public int[] getOptionalData()
    {
        return this.optionalData;
    }

    public void setOptionalData(int[] optionalData)
    {
        this.optionalData = optionalData;
    }

    /**
     * Active
     */
    private Boolean active;

    public Boolean getActive()
    {
        return this.active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    /**
     * Array Data
     */
    private String[] arrayData;

    public String[] getArrayData() {
        return this.arrayData;
    }

    public void setArrayData(String[] arrayData) {
        this.arrayData = arrayData;
    }


    private Boolean isPropertyDescriptionRemoved;

    public Boolean getIsPropertyDescriptionRemoved()
    {
        return this.isPropertyDescriptionRemoved;
    }

    public void setIsPropertyDescriptionRemoved(Boolean removed)
    {
        this.isPropertyDescriptionRemoved = removed;
    }

    private Boolean isPropertyMetadataRemoved;

    public Boolean getIsPropertyMetadataRemoved()
    {
        return this.isPropertyMetadataRemoved;
    }

    public void setIsPropertyMetadataRemoved(Boolean removed)
    {
        this.isPropertyMetadataRemoved = removed;
    }

    private Boolean isPropertyArrayDataRemoved;

    public Boolean getIsPropertyArrayDataRemoved()
    {
        return this.isPropertyArrayDataRemoved;
    }

    public void setIsPropertyArrayDataRemoved(Boolean removed)
    {
        this.isPropertyArrayDataRemoved = removed;
    }

    private Boolean isPropertyOptionalDataRemoved;

    public Boolean getIsPropertyOptionalDataRemoved()
    {
        return this.isPropertyOptionalDataRemoved;
    }

    public void setIsPropertyOptionalDataRemoved(Boolean removed)
    {
        this.isPropertyOptionalDataRemoved = removed;
    }

    private Boolean isPropertyActiveRemoved;

    public Boolean getIsPropertyActiveRemoved()
    {
        return this.isPropertyActiveRemoved;
    }

    public void setIsPropertyActiveRemoved(Boolean removed)
    {
        this.isPropertyActiveRemoved = removed;
    }

    public static class CreateDaySummaryDto extends CreateOrMergePatchDaySummaryDto
    {
        public CreateDaySummaryDto() {
            this.commandType = COMMAND_TYPE_CREATE;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }

    }

    public static class MergePatchDaySummaryDto extends CreateOrMergePatchDaySummaryDto
    {
        public MergePatchDaySummaryDto() {
            this.commandType = COMMAND_TYPE_MERGE_PATCH;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
        }

    }

}

