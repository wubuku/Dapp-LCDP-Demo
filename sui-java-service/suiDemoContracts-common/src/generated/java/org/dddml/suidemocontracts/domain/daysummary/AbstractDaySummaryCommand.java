package org.dddml.suidemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractDaySummaryCommand extends AbstractCommand implements DaySummaryCommand
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

    private Long version;

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }


    public static abstract class AbstractCreateOrMergePatchDaySummary extends AbstractDaySummaryCommand implements CreateOrMergePatchDaySummary
    {

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

        private Boolean active;

        public Boolean getActive()
        {
            return this.active;
        }

        public void setActive(Boolean active)
        {
            this.active = active;
        }

        private String[] arrayData;

        public String[] getArrayData() {
            return this.arrayData;
        }

        public void setArrayData(String[] arrayData) {
            this.arrayData = arrayData;
        }

    }

    public static abstract class AbstractCreateDaySummary extends AbstractCreateOrMergePatchDaySummary implements CreateDaySummary
    {
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }

    }

    public static abstract class AbstractMergePatchDaySummary extends AbstractCreateOrMergePatchDaySummary implements MergePatchDaySummary
    {
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
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


    }

    public static class SimpleCreateDaySummary extends AbstractCreateDaySummary
    {
    }

    
    public static class SimpleMergePatchDaySummary extends AbstractMergePatchDaySummary
    {
    }

    
	public static class SimpleDeleteDaySummary extends AbstractDaySummaryCommand implements DeleteDaySummary
	{
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_DELETE;
        }
	}

    

}

