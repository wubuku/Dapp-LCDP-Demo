// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wubuku.sui.bean.*;

public class Year extends MoveObject<Year.YearFields> {

    @JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
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
