// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;

public class DaySummaryCommands {
    private DaySummaryCommands() {
    }

    public static class Create extends AbstractDaySummaryCommand implements DaySummaryCommand {

        public String getCommandType() {
            return "Create";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Day
         */
        private Day day;

        public Day getDay() {
            return this.day;
        }

        public void setDay(Day day) {
            this.day = day;
        }

        /**
         * Description
         */
        private String description;

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Meta Data
         */
        private int[] metaData;

        public int[] getMetaData() {
            return this.metaData;
        }

        public void setMetaData(int[] metaData) {
            this.metaData = metaData;
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

        /**
         * Optional Data
         */
        private int[] optionalData;

        public int[] getOptionalData() {
            return this.optionalData;
        }

        public void setOptionalData(int[] optionalData) {
            this.optionalData = optionalData;
        }

        /**
         * Off Chain Version
         */
        private Long offChainVersion;

        public Long getOffChainVersion() {
            return this.offChainVersion;
        }

        public void setOffChainVersion(Long offChainVersion) {
            this.offChainVersion = offChainVersion;
        }

    }

    public static class Delete extends AbstractDaySummaryCommand implements DaySummaryCommand {

        public String getCommandType() {
            return "Delete";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Day
         */
        private Day day;

        public Day getDay() {
            return this.day;
        }

        public void setDay(Day day) {
            this.day = day;
        }

        /**
         * Off Chain Version
         */
        private Long offChainVersion;

        public Long getOffChainVersion() {
            return this.offChainVersion;
        }

        public void setOffChainVersion(Long offChainVersion) {
            this.offChainVersion = offChainVersion;
        }

    }

}

