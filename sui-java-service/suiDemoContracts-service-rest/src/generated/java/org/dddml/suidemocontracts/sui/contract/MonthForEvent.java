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

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MonthForEvent {
    private YearForEvent year;

    private Integer number;

    private Boolean isLeap;

    public YearForEvent getYear() {
        return year;
    }

    public void setYear(YearForEvent year) {
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

