package org.dddml.suidemocontracts.domain.daysummary;

import java.io.Serializable;
import org.dddml.suidemocontracts.domain.Day;
import org.dddml.suidemocontracts.domain.*;

public class DaySummaryEventId implements Serializable
{
    private Day day = new Day();

    public Day getDay()
    {
        return this.day;
    }

    public void setDay(Day day)
    {
        this.day = day;
    }

    private Long version;

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }

    protected Integer getDayMonthYearNumber()
    {
        return getDay().getMonth().getYear().getNumber();
    }

    protected void setDayMonthYearNumber(Integer dayMonthYearNumber)
    {
        getDay().getMonth().getYear().setNumber(dayMonthYearNumber);
    }

    protected String getDayMonthYearCalendar()
    {
        return getDay().getMonth().getYear().getCalendar();
    }

    protected void setDayMonthYearCalendar(String dayMonthYearCalendar)
    {
        getDay().getMonth().getYear().setCalendar(dayMonthYearCalendar);
    }

    protected Integer getDayMonthNumber()
    {
        return getDay().getMonth().getNumber();
    }

    protected void setDayMonthNumber(Integer dayMonthNumber)
    {
        getDay().getMonth().setNumber(dayMonthNumber);
    }

    protected Boolean getDayMonthIsLeap()
    {
        return getDay().getMonth().getIsLeap();
    }

    protected void setDayMonthIsLeap(Boolean dayMonthIsLeap)
    {
        getDay().getMonth().setIsLeap(dayMonthIsLeap);
    }

    protected Integer getDayNumber()
    {
        return getDay().getNumber();
    }

    protected void setDayNumber(Integer dayNumber)
    {
        getDay().setNumber(dayNumber);
    }

    protected String getDayTimeZone()
    {
        return getDay().getTimeZone();
    }

    protected void setDayTimeZone(String dayTimeZone)
    {
        getDay().setTimeZone(dayTimeZone);
    }

    public DaySummaryEventId()
    {
    }

    public DaySummaryEventId(Day day, Long version)
    {
        this.day = day;
        this.version = version;
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

        DaySummaryEventId other = (DaySummaryEventId)obj;
        return true 
            && (day == other.day || (day != null && day.equals(other.day)))
            && (version == other.version || (version != null && version.equals(other.version)))
            ;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        if (this.day != null) {
            hash += 13 * this.day.hashCode();
        }
        if (this.version != null) {
            hash += 13 * this.version.hashCode();
        }
        return hash;
    }


    protected static final String[] FLATTENED_PROPERTY_NAMES = new String[]{
            "dayMonthYearNumber",
            "dayMonthYearCalendar",
            "dayMonthNumber",
            "dayMonthIsLeap",
            "dayNumber",
            "dayTimeZone",
            "version",
    };

    protected static final String[] FLATTENED_PROPERTY_TYPES = new String[]{
            "Integer",
            "String",
            "Integer",
            "Boolean",
            "Integer",
            "String",
            "Long",
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

