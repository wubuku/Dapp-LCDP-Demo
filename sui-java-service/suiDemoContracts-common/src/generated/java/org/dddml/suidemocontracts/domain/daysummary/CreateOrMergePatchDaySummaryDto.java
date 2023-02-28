package org.dddml.suidemocontracts.domain.daysummary;

import org.dddml.suidemocontracts.domain.*;
import java.util.Date;

public class CreateOrMergePatchDaySummaryDto extends AbstractDaySummaryCommandDto implements DaySummaryCommand.CreateOrMergePatchDaySummary
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

    public void copyTo(CreateOrMergePatchDaySummary command)
    {
        ((AbstractDaySummaryCommandDto) this).copyTo(command);
        command.setDescription(this.getDescription());
        command.setMetadata(this.getMetadata());
        command.setOptionalData(this.getOptionalData());
        command.setActive(this.getActive());
        command.setArrayData(this.getArrayData());
    }

    public DaySummaryCommand toCommand()
    {
        if (getCommandType() == null) {
            setCommandType(COMMAND_TYPE_MERGE_PATCH);
        }
        if (COMMAND_TYPE_CREATE.equals(getCommandType())) {
            AbstractDaySummaryCommand.SimpleCreateDaySummary command = new AbstractDaySummaryCommand.SimpleCreateDaySummary();
            copyTo((AbstractDaySummaryCommand.AbstractCreateDaySummary) command);
            return command;
        } else if (COMMAND_TYPE_MERGE_PATCH.equals(getCommandType())) {
            AbstractDaySummaryCommand.SimpleMergePatchDaySummary command = new AbstractDaySummaryCommand.SimpleMergePatchDaySummary();
            copyTo((AbstractDaySummaryCommand.SimpleMergePatchDaySummary) command);
            return command;
        } 
        throw new UnsupportedOperationException("Unknown command type:" + getCommandType());
    }


    public DaySummaryCommand toSubclass() {
        if (getCommandType() == null) {
            setCommandType(COMMAND_TYPE_MERGE_PATCH);
        }
        if (COMMAND_TYPE_CREATE.equals(getCommandType()) || null == getCommandType()) {
            CreateDaySummaryDto command = new CreateDaySummaryDto();
            copyTo((CreateDaySummary) command);
            return command;
        } else if (COMMAND_TYPE_MERGE_PATCH.equals(getCommandType())) {
            MergePatchDaySummaryDto command = new MergePatchDaySummaryDto();
            copyTo((MergePatchDaySummary) command);
            return command;
        } 
        throw new UnsupportedOperationException("Unknown command type:" + getCommandType());
    }

    public void copyTo(CreateDaySummary command)
    {
        copyTo((CreateOrMergePatchDaySummary) command);
    }

    public void copyTo(MergePatchDaySummary command)
    {
        copyTo((CreateOrMergePatchDaySummary) command);
        command.setIsPropertyDescriptionRemoved(this.getIsPropertyDescriptionRemoved());
        command.setIsPropertyMetadataRemoved(this.getIsPropertyMetadataRemoved());
        command.setIsPropertyArrayDataRemoved(this.getIsPropertyArrayDataRemoved());
        command.setIsPropertyOptionalDataRemoved(this.getIsPropertyOptionalDataRemoved());
        command.setIsPropertyActiveRemoved(this.getIsPropertyActiveRemoved());
    }

    public static class CreateDaySummaryDto extends CreateOrMergePatchDaySummaryDto implements DaySummaryCommand.CreateDaySummary
    {
        public CreateDaySummaryDto() {
            this.commandType = COMMAND_TYPE_CREATE;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }
        public DaySummaryCommand.CreateDaySummary toCreateDaySummary()
        {
            return (DaySummaryCommand.CreateDaySummary) toCommand();
        }

    }

    public static class MergePatchDaySummaryDto extends CreateOrMergePatchDaySummaryDto implements DaySummaryCommand.MergePatchDaySummary
    {
        public MergePatchDaySummaryDto() {
            this.commandType = COMMAND_TYPE_MERGE_PATCH;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
        }
        public DaySummaryCommand.MergePatchDaySummary toMergePatchDaySummary()
        {
            return (DaySummaryCommand.MergePatchDaySummary) toCommand();
        }

    }

}

