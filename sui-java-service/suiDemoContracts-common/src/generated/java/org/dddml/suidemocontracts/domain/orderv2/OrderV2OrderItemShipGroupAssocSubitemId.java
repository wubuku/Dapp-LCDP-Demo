package org.dddml.suidemocontracts.domain.orderv2;

import java.io.Serializable;
import org.dddml.suidemocontracts.domain.*;

public class OrderV2OrderItemShipGroupAssocSubitemId implements Serializable
{
    private String orderV2OrderId;

    public String getOrderV2OrderId()
    {
        return this.orderV2OrderId;
    }

    public void setOrderV2OrderId(String orderV2OrderId)
    {
        this.orderV2OrderId = orderV2OrderId;
    }

    private Integer orderShipGroupShipGroupSeqId;

    public Integer getOrderShipGroupShipGroupSeqId()
    {
        return this.orderShipGroupShipGroupSeqId;
    }

    public void setOrderShipGroupShipGroupSeqId(Integer orderShipGroupShipGroupSeqId)
    {
        this.orderShipGroupShipGroupSeqId = orderShipGroupShipGroupSeqId;
    }

    private String orderItemShipGroupAssociationProductId;

    public String getOrderItemShipGroupAssociationProductId()
    {
        return this.orderItemShipGroupAssociationProductId;
    }

    public void setOrderItemShipGroupAssociationProductId(String orderItemShipGroupAssociationProductId)
    {
        this.orderItemShipGroupAssociationProductId = orderItemShipGroupAssociationProductId;
    }

    private Integer orderItemShipGroupAssocSubitemSeqId;

    public Integer getOrderItemShipGroupAssocSubitemSeqId()
    {
        return this.orderItemShipGroupAssocSubitemSeqId;
    }

    public void setOrderItemShipGroupAssocSubitemSeqId(Integer orderItemShipGroupAssocSubitemSeqId)
    {
        this.orderItemShipGroupAssocSubitemSeqId = orderItemShipGroupAssocSubitemSeqId;
    }

    public OrderV2OrderItemShipGroupAssocSubitemId()
    {
    }

    public OrderV2OrderItemShipGroupAssocSubitemId(String orderV2OrderId, Integer orderShipGroupShipGroupSeqId, String orderItemShipGroupAssociationProductId, Integer orderItemShipGroupAssocSubitemSeqId)
    {
        this.orderV2OrderId = orderV2OrderId;
        this.orderShipGroupShipGroupSeqId = orderShipGroupShipGroupSeqId;
        this.orderItemShipGroupAssociationProductId = orderItemShipGroupAssociationProductId;
        this.orderItemShipGroupAssocSubitemSeqId = orderItemShipGroupAssocSubitemSeqId;
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

        OrderV2OrderItemShipGroupAssocSubitemId other = (OrderV2OrderItemShipGroupAssocSubitemId)obj;
        return true 
            && (orderV2OrderId == other.orderV2OrderId || (orderV2OrderId != null && orderV2OrderId.equals(other.orderV2OrderId)))
            && (orderShipGroupShipGroupSeqId == other.orderShipGroupShipGroupSeqId || (orderShipGroupShipGroupSeqId != null && orderShipGroupShipGroupSeqId.equals(other.orderShipGroupShipGroupSeqId)))
            && (orderItemShipGroupAssociationProductId == other.orderItemShipGroupAssociationProductId || (orderItemShipGroupAssociationProductId != null && orderItemShipGroupAssociationProductId.equals(other.orderItemShipGroupAssociationProductId)))
            && (orderItemShipGroupAssocSubitemSeqId == other.orderItemShipGroupAssocSubitemSeqId || (orderItemShipGroupAssocSubitemSeqId != null && orderItemShipGroupAssocSubitemSeqId.equals(other.orderItemShipGroupAssocSubitemSeqId)))
            ;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        if (this.orderV2OrderId != null) {
            hash += 13 * this.orderV2OrderId.hashCode();
        }
        if (this.orderShipGroupShipGroupSeqId != null) {
            hash += 13 * this.orderShipGroupShipGroupSeqId.hashCode();
        }
        if (this.orderItemShipGroupAssociationProductId != null) {
            hash += 13 * this.orderItemShipGroupAssociationProductId.hashCode();
        }
        if (this.orderItemShipGroupAssocSubitemSeqId != null) {
            hash += 13 * this.orderItemShipGroupAssocSubitemSeqId.hashCode();
        }
        return hash;
    }


    protected static final String[] FLATTENED_PROPERTY_NAMES = new String[]{
            "orderV2OrderId",
            "orderShipGroupShipGroupSeqId",
            "orderItemShipGroupAssociationProductId",
            "orderItemShipGroupAssocSubitemSeqId",
    };

    protected static final String[] FLATTENED_PROPERTY_TYPES = new String[]{
            "String",
            "Integer",
            "String",
            "Integer",
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

