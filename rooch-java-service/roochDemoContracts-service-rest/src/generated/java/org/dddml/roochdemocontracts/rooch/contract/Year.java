// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wubuku.rooch.bean.*;

public class Year extends AnnotatedMoveStructView<Year.YearFields> {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class YearFields {
        private Integer number;

        private String calendar;


        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public String getCalendar() {
            return calendar;
        }

        public void setCalendar(String calendar) {
            this.calendar = calendar;
        }

        @Override
        public String toString() {
            return "YearFields{" +
                    "number=" + number +
                    ", calendar=" + '\'' + calendar + '\'' +
                    '}';
        }
    }
}

