// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain;

import java.io.Serializable;
import org.dddml.aptosdemocontracts.domain.*;

public class Year implements Serializable {
    private Integer number;

    public Integer getNumber()
    {
        return this.number;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }

    private String calendar;

    public String getCalendar()
    {
        return this.calendar;
    }

    public void setCalendar(String calendar)
    {
        this.calendar = calendar;
    }

    public Year()
    {
    }

    public Year(Integer number, String calendar)
    {
        this.number = number;
        this.calendar = calendar;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Year other = (Year)obj;
        return true 
            && (number == other.number || (number != null && number.equals(other.number)))
            && (calendar == other.calendar || (calendar != null && calendar.equals(other.calendar)))
            ;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        if (this.number != null) {
            hash += 13 * this.number.hashCode();
        }
        if (this.calendar != null) {
            hash += 13 * this.calendar.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        return "Year{" +
                "number=" + number +
                ", calendar=" + '\'' + calendar + '\'' +
                '}';
    }


}

