// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract;

import java.util.*;
import java.math.*;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wubuku.sui.bean.*;

public class Day extends MoveStruct<Day.DayFields> {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DayFields {
        private Month month;

        private Integer number;

        private String timeZone;


        public Month getMonth() {
            return month;
        }

        public void setMonth(Month month) {
            this.month = month;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        @Override
        public String toString() {
            return "DayFields{" +
                    "month=" + month +
                    ", number=" + number +
                    ", timeZone=" + '\'' + timeZone + '\'' +
                    '}';
        }
    }
}

