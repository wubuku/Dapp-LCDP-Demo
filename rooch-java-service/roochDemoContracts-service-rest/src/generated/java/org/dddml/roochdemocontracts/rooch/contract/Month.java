// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wubuku.rooch.bean.*;

public class Month extends AnnotatedMoveStructView<Month.MonthValue> {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class MonthValue {
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
            return "MonthValue{" +
                    "year=" + year +
                    ", number=" + number +
                    ", isLeap=" + isLeap +
                    '}';
        }
    }
}

