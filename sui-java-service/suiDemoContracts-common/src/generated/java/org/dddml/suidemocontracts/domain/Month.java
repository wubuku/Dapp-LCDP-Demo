// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain;

import java.io.Serializable;
import org.dddml.suidemocontracts.domain.*;

public class Month implements Serializable {
    private Year year = new Year();

    public Year getYear()
    {
        return this.year;
    }

    public void setYear(Year year)
    {
        this.year = year;
    }

    private Integer number;

    public Integer getNumber()
    {
        return this.number;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }

    private Boolean isLeap;

    public Boolean getIsLeap()
    {
        return this.isLeap;
    }

    public void setIsLeap(Boolean isLeap)
    {
        this.isLeap = isLeap;
    }

    protected Integer getYearNumber()
    {
        return getYear().getNumber();
    }

    protected void setYearNumber(Integer yearNumber)
    {
        getYear().setNumber(yearNumber);
    }

    protected String getYearCalendar()
    {
        return getYear().getCalendar();
    }

    protected void setYearCalendar(String yearCalendar)
    {
        getYear().setCalendar(yearCalendar);
    }

    public Month()
    {
    }

    public Month(Year year, Integer number, Boolean isLeap)
    {
        this.year = year;
        this.number = number;
        this.isLeap = isLeap;
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

        Month other = (Month)obj;
        return true 
            && (year == other.year || (year != null && year.equals(other.year)))
            && (number == other.number || (number != null && number.equals(other.number)))
            && (isLeap == other.isLeap || (isLeap != null && isLeap.equals(other.isLeap)))
            ;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        if (this.year != null) {
            hash += 13 * this.year.hashCode();
        }
        if (this.number != null) {
            hash += 13 * this.number.hashCode();
        }
        if (this.isLeap != null) {
            hash += 13 * this.isLeap.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        return "Month{" +
                "year=" + year +
                ", number=" + number +
                ", isLeap=" + isLeap +
                '}';
    }


}

