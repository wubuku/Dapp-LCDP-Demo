// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wubuku.sui.bean.*;

public class Month extends MoveObject<Month.MonthFields> {

    @JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
    public static class MonthFields {
        private Year year;

        private Integer number;

        private Boolean isLeap;


        public Year getYear() {
            return year;
        }

        public void setYear(Year year) {
            this.year = year;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public Boolean getIsLeap() {
            return isLeap;
        }

        public void setIsLeap(Boolean isLeap) {
            this.isLeap = isLeap;
        }

        @Override
        public String toString() {
            return "MonthFields{" +
                    "year=" + year +
                    ", number=" + number +
                    ", isLeap=" + isLeap +
                    '}';
        }
    }
}
