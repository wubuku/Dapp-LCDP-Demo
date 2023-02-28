package org.dddml.suidemocontracts.domain;

import java.io.Serializable;
import org.dddml.suidemocontracts.domain.*;

public class Day implements Serializable
{
    private Month month = new Month();

    public Month getMonth()
    {
        return this.month;
    }

    public void setMonth(Month month)
    {
        this.month = month;
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

    private String timeZone;

    public String getTimeZone()
    {
        return this.timeZone;
    }

    public void setTimeZone(String timeZone)
    {
        this.timeZone = timeZone;
    }

    protected Integer getMonthYearNumber()
    {
        return getMonth().getYear().getNumber();
    }

    protected void setMonthYearNumber(Integer monthYearNumber)
    {
        getMonth().getYear().setNumber(monthYearNumber);
    }

    protected String getMonthYearCalendar()
    {
        return getMonth().getYear().getCalendar();
    }

    protected void setMonthYearCalendar(String monthYearCalendar)
    {
        getMonth().getYear().setCalendar(monthYearCalendar);
    }

    protected Integer getMonthNumber()
    {
        return getMonth().getNumber();
    }

    protected void setMonthNumber(Integer monthNumber)
    {
        getMonth().setNumber(monthNumber);
    }

    protected Boolean getMonthIsLeap()
    {
        return getMonth().getIsLeap();
    }

    protected void setMonthIsLeap(Boolean monthIsLeap)
    {
        getMonth().setIsLeap(monthIsLeap);
    }

    public Day()
    {
    }

    public Day(Month month, Integer number, String timeZone)
    {
        this.month = month;
        this.number = number;
        this.timeZone = timeZone;
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

        Day other = (Day)obj;
        return true 
            && (month == other.month || (month != null && month.equals(other.month)))
            && (number == other.number || (number != null && number.equals(other.number)))
            && (timeZone == other.timeZone || (timeZone != null && timeZone.equals(other.timeZone)))
            ;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        if (this.month != null) {
            hash += 13 * this.month.hashCode();
        }
        if (this.number != null) {
            hash += 13 * this.number.hashCode();
        }
        if (this.timeZone != null) {
            hash += 13 * this.timeZone.hashCode();
        }
        return hash;
    }


    protected static final String[] FLATTENED_PROPERTY_NAMES = new String[]{
            "monthYearNumber",
            "monthYearCalendar",
            "monthNumber",
            "monthIsLeap",
            "number",
            "timeZone",
    };

    protected static final String[] FLATTENED_PROPERTY_TYPES = new String[]{
            "Integer",
            "String",
            "Integer",
            "Boolean",
            "Integer",
            "String",
    };

    protected static final java.util.Map<String, String> FLATTENED_PROPERTY_TYPE_MAP;

    static {
        java.util.Map<String, String> m = new java.util.HashMap<String, String>();
        for (int i = 0; i < FLATTENED_PROPERTY_NAMES.length; i++) {
            m.put(FLATTENED_PROPERTY_NAMES[i], FLATTENED_PROPERTY_TYPES[i]);
        }
        FLATTENED_PROPERTY_TYPE_MAP = m;
    }

    protected void forEachFlattenedProperty(java.util.function.BiConsumer<String, Object> consumer) {
        for (int i = 0; i < FLATTENED_PROPERTY_NAMES.length; i++) {
            String pn = FLATTENED_PROPERTY_NAMES[i];
            if (Character.isLowerCase(pn.charAt(0))) {
                pn = Character.toUpperCase(pn.charAt(0)) + pn.substring(1);
            }
            java.lang.reflect.Method m = null;
            try {
                m = this.getClass().getDeclaredMethod("get" + pn, new Class[0]);
            } catch (NoSuchMethodException e) {
                try {
                    m = this.getClass().getMethod("get" + pn, new Class[0]);
                } catch (NoSuchMethodException e1) {
                    throw new RuntimeException(e);
                }
            }
            Object pv = null;
            try {
                pv = m.invoke(this);
            } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            consumer.accept(pn, pv);
        }
    }

    protected void setFlattenedPropertyValues(Object... values) {
        for (int i = 0; i < FLATTENED_PROPERTY_NAMES.length; i++) {
            String pn = FLATTENED_PROPERTY_NAMES[i];
            if (Character.isLowerCase(pn.charAt(0))) {
                pn = Character.toUpperCase(pn.charAt(0)) + pn.substring(1);
            }
            Object v = values[i];
            Class propCls = v == null ? Object.class : v.getClass();
            java.lang.reflect.Method setterMethod = null;
            if (v == null) {
                setterMethod = getNullValueSetterMethod(pn);
            }
            if (setterMethod == null) {
                try {
                    setterMethod = this.getClass().getDeclaredMethod("set" + pn, new Class[]{propCls});
                } catch (NoSuchMethodException e) {
                    try {
                        setterMethod = this.getClass().getMethod("set" + pn, new Class[]{propCls});
                    } catch (NoSuchMethodException e1) {
                        throw new RuntimeException(e1);
                    }
                }
            }
            try {
                setterMethod.invoke(this, v);
            } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private java.lang.reflect.Method getNullValueSetterMethod(String pascalPropName) {
        java.lang.reflect.Method m;
        final String methodName = "set" + pascalPropName;
        m = java.util.Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(me -> me.getName().equals(methodName) && me.getParameterCount() == 1)
                .findFirst().orElse(null);
        if (m == null) {
            m = java.util.Arrays.stream(this.getClass().getMethods())
                    .filter(me -> me.getName().equals(methodName) && me.getParameterCount() == 1)
                    .findFirst().orElse(null);
        }
        return m;
    }

}

