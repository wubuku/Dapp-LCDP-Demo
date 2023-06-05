// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.roochdemocontracts.domain.*;
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
        private String optionalData;

        public String getOptionalData() {
            return this.optionalData;
        }

        public void setOptionalData(String optionalData) {
            this.optionalData = optionalData;
        }

        /**
         * U16 Array Data
         */
        private Integer[] u16ArrayData;

        public Integer[] getU16ArrayData() {
            return this.u16ArrayData;
        }

        public void setU16ArrayData(Integer[] u16ArrayData) {
            this.u16ArrayData = u16ArrayData;
        }

        /**
         * U32 Array Data
         */
        private Long[] u32ArrayData;

        public Long[] getU32ArrayData() {
            return this.u32ArrayData;
        }

        public void setU32ArrayData(Long[] u32ArrayData) {
            this.u32ArrayData = u32ArrayData;
        }

        /**
         * U64 Array Data
         */
        private BigInteger[] u64ArrayData;

        public BigInteger[] getU64ArrayData() {
            return this.u64ArrayData;
        }

        public void setU64ArrayData(BigInteger[] u64ArrayData) {
            this.u64ArrayData = u64ArrayData;
        }

        /**
         * U128 Array Data
         */
        private BigInteger[] u128ArrayData;

        public BigInteger[] getU128ArrayData() {
            return this.u128ArrayData;
        }

        public void setU128ArrayData(BigInteger[] u128ArrayData) {
            this.u128ArrayData = u128ArrayData;
        }

        /**
         * U256 Array Data
         */
        private BigInteger[] u256ArrayData;

        public BigInteger[] getU256ArrayData() {
            return this.u256ArrayData;
        }

        public void setU256ArrayData(BigInteger[] u256ArrayData) {
            this.u256ArrayData = u256ArrayData;
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
