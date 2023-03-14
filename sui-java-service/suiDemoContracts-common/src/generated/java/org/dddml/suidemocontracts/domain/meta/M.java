package org.dddml.suidemocontracts.domain.meta;

import java.util.*;

import org.dddml.suidemocontracts.specialization.*;


public class M {

  // /////////////////////////////////////////
  public static class BoundedContextMetadata {

    public static final String NAME_REQUESTER_ID				= "requesterId";
    public static final String DISCRIMINATOR_COMMAND_TYPE		= "commandType";
    public static final String DISCRIMINATOR_EVENT_TYPE	= "eventType";
    public static final String DISCRIMINATOR_COMMAND_ID			= "commandId";

    public static final String HTTP_SERVICE_ORDERS_QUERY_NAME			= "sort";
    public static final String HTTP_SERVICE_FIRST_RESULT_QUERY_NAME		= "firstResult";
    public static final String HTTP_SERVICE_MAX_RESULTS_QUERY_NAME		= "maxResults";
    public static final String HTTP_SERVICE_RETURNED_FIELDS_QUERY_NAME	= "fields";
    public static final String HTTP_SERVICE_FILTER_QUERY_NAME			= "filter";

    public static final Map<String, String> TYPE_NAME_TO_AGGREGATE_NAME_MAP;

    public static final Map<String, Class<?>> CLASS_MAP;

    static {
        Map<String, String> typeToAggMap = new HashMap<>();

        typeToAggMap.put("DomainName", "DomainName");
        typeToAggMap.put("Order", "Order");
        typeToAggMap.put("OrderItem", "Order");
        typeToAggMap.put("Product", "Product");
        typeToAggMap.put("OrderV2", "OrderV2");
        typeToAggMap.put("OrderV2Item", "OrderV2");
        typeToAggMap.put("OrderShipGroup", "OrderV2");
        typeToAggMap.put("OrderItemShipGroupAssociation", "OrderV2");
        typeToAggMap.put("DaySummary", "DaySummary");
        TYPE_NAME_TO_AGGREGATE_NAME_MAP = typeToAggMap;

        Map<String, Class<?>> clsMap = new HashMap<>();
        clsMap.put("bool", Boolean.class);
        clsMap.put("Boolean", Boolean.class);
        clsMap.put("bool?", Boolean.class);
        clsMap.put("DateTime", java.util.Date.class);
        clsMap.put("Date", java.util.Date.class);
        clsMap.put("java.util.Date", java.util.Date.class);
        clsMap.put("DateTime?", java.util.Date.class);
        clsMap.put("decimal", java.math.BigDecimal.class);
        clsMap.put("BigDecimal", java.math.BigDecimal.class);
        clsMap.put("java.math.BigDecimal", java.math.BigDecimal.class);
        clsMap.put("decimal?", java.math.BigDecimal.class);
        clsMap.put("int", Integer.class);
        clsMap.put("Integer", Integer.class);
        clsMap.put("int?", Integer.class);
        clsMap.put("long", Long.class);
        clsMap.put("Long", Long.class);
        clsMap.put("long?", Long.class);
        clsMap.put("string", String.class);
        clsMap.put("String", String.class);
        clsMap.put("U8", Integer.class);
        clsMap.put("U16", Integer.class);
        clsMap.put("U32", Long.class);
        clsMap.put("U64", java.math.BigInteger.class);
        clsMap.put("BigInteger", java.math.BigInteger.class);
        clsMap.put("java.math.BigInteger", java.math.BigInteger.class);
        clsMap.put("U128", java.math.BigInteger.class);
        clsMap.put("vector<u8>", int[].class);
        clsMap.put("int[]", int[].class);
        clsMap.put("u8", Integer.class);
        clsMap.put("u16", Integer.class);
        clsMap.put("u32", Long.class);
        clsMap.put("u64", java.math.BigInteger.class);
        clsMap.put("u128", java.math.BigInteger.class);
        clsMap.put("blob", java.sql.Blob.class);
        clsMap.put("java.sql.Blob", java.sql.Blob.class);
        clsMap.put("byte-array", byte[].class);
        clsMap.put("byte[]", byte[].class);
        clsMap.put("object", Object.class);
        clsMap.put("Object", Object.class);
        clsMap.put("date-time", java.sql.Timestamp.class);
        clsMap.put("java.sql.Timestamp", java.sql.Timestamp.class);
        clsMap.put("date", java.sql.Date.class);
        clsMap.put("java.sql.Date", java.sql.Date.class);
        clsMap.put("time", java.sql.Time.class);
        clsMap.put("java.sql.Time", java.sql.Time.class);
        clsMap.put("currency-amount", java.math.BigDecimal.class);
        clsMap.put("currency-precise", java.math.BigDecimal.class);
        clsMap.put("fixed-point", java.math.BigDecimal.class);
        clsMap.put("floating-point", Double.class);
        clsMap.put("Double", Double.class);
        clsMap.put("numeric", Long.class);
        clsMap.put("id", String.class);
        clsMap.put("id-long", String.class);
        clsMap.put("id-vlong", String.class);
        clsMap.put("indicator", String.class);
        clsMap.put("very-short", String.class);
        clsMap.put("short-varchar", String.class);
        clsMap.put("long-varchar", String.class);
        clsMap.put("very-long", String.class);
        clsMap.put("comment", String.class);
        clsMap.put("description", String.class);
        clsMap.put("name", String.class);
        clsMap.put("value", String.class);
        clsMap.put("credit-card-number", String.class);
        clsMap.put("credit-card-date", String.class);
        clsMap.put("email", String.class);
        clsMap.put("url", String.class);
        clsMap.put("id-ne", String.class);
        clsMap.put("id-long-ne", String.class);
        clsMap.put("id-vlong-ne", String.class);
        clsMap.put("tel-number", String.class);
        clsMap.put("UID", String.class);
        clsMap.put("address", String.class);
        CLASS_MAP = clsMap;
    }

    private BoundedContextMetadata() {
    }
  }


  // /////////////////////////////////////////////////////////  
  public static class DomainNameMetadata {

    private DomainNameMetadata() {
    }

    public static final String PROPERTY_NAME_VERSION      = "offChainVersion";
    public static final String PROPERTY_NAME_ACTIVE       = "active";
    public static final String PROPERTY_NAME_DELETED      = "deleted";
    public static final String PROPERTY_NAME_CREATED_BY   = "createdBy";
    public static final String PROPERTY_NAME_CREATED_AT   = "createdAt";
    public static final String PROPERTY_NAME_UPDATED_BY   = "updatedBy";
    public static final String PROPERTY_NAME_UPDATED_AT   = "updatedAt";

    public static final String URL_ID_FIELD_SEPARATOR = ",";

    public static final TextFormatter<org.dddml.suidemocontracts.domain.domainname.DomainNameId> URL_ID_TEXT_FORMATTER =
                    new AbstractValueObjectTextFormatter<org.dddml.suidemocontracts.domain.domainname.DomainNameId>(org.dddml.suidemocontracts.domain.domainname.DomainNameId.class, URL_ID_FIELD_SEPARATOR) {
                        @Override
                        protected Class<?> getClassByTypeName(String type) {
                            return BoundedContextMetadata.CLASS_MAP.get(type);
                        }
                    };

    public static final Class ID_CLASS = org.dddml.suidemocontracts.domain.domainname.DomainNameId.class;

    public static final String[] propertyNames = new String[] {
            "expirationDate",
            "version",
            "offChainVersion",
            "createdBy",
            "createdAt",
            "updatedBy",
            "updatedAt",
            "active",
            "deleted",
            "domainNameId.topLevelDomain",
            "domainNameId.secondLevelDomain",
    };

    public static final String[] propertyTypes = new String[] {
            "BigInteger",
            "BigInteger",
            "Long",
            "String",
            "Date",
            "String",
            "Date",
            "Boolean",
            "Boolean",
            "String",
            "String",
    };

    public static final Map<String, String> propertyTypeMap;

    public static final Map<String, String> aliasMap;

    static {
        propertyTypeMap = new HashMap<String, String>();
        initPropertyTypeMap();
        aliasMap = new HashMap<String, String>();
        initAliasMap();
    }

    private static  void initAliasMap() {
        aliasMap.put("expirationDate", "expirationDate");
        aliasMap.put("ExpirationDate", "expirationDate");
        aliasMap.put("version", "version");
        aliasMap.put("Version", "version");
        aliasMap.put("offChainVersion", "offChainVersion");
        aliasMap.put("OffChainVersion", "offChainVersion");
        aliasMap.put("createdBy", "createdBy");
        aliasMap.put("CreatedBy", "createdBy");
        aliasMap.put("createdAt", "createdAt");
        aliasMap.put("CreatedAt", "createdAt");
        aliasMap.put("updatedBy", "updatedBy");
        aliasMap.put("UpdatedBy", "updatedBy");
        aliasMap.put("updatedAt", "updatedAt");
        aliasMap.put("UpdatedAt", "updatedAt");
        aliasMap.put("active", "active");
        aliasMap.put("Active", "active");
        aliasMap.put("deleted", "deleted");
        aliasMap.put("Deleted", "deleted");
        aliasMap.put("domainNameId.topLevelDomain", "domainNameId.topLevelDomain");
        aliasMap.put("DomainNameId.TopLevelDomain", "domainNameId.topLevelDomain");
        aliasMap.put("domainNameId.secondLevelDomain", "domainNameId.secondLevelDomain");
        aliasMap.put("DomainNameId.SecondLevelDomain", "domainNameId.secondLevelDomain");
    }

    private static void initPropertyTypeMap() {
        for (int i = 0; i < propertyNames.length; i++ ) {
            propertyTypeMap.put(propertyNames[i], propertyTypes[i]);
        }
    }

  }


  // /////////////////////////////////////////////////////////  
  public static class OrderMetadata {

    private OrderMetadata() {
    }

    public static final String PROPERTY_NAME_VERSION      = "offChainVersion";
    public static final String PROPERTY_NAME_ACTIVE       = "active";
    public static final String PROPERTY_NAME_DELETED      = "deleted";
    public static final String PROPERTY_NAME_CREATED_BY   = "createdBy";
    public static final String PROPERTY_NAME_CREATED_AT   = "createdAt";
    public static final String PROPERTY_NAME_UPDATED_BY   = "updatedBy";
    public static final String PROPERTY_NAME_UPDATED_AT   = "updatedAt";

    public static final Class ID_CLASS = String.class;

    public static final String[] propertyNames = new String[] {
            "id",
            "totalAmount",
            "version",
            "offChainVersion",
            "createdBy",
            "createdAt",
            "updatedBy",
            "updatedAt",
            "active",
            "deleted",
    };

    public static final String[] propertyTypes = new String[] {
            "String",
            "BigInteger",
            "BigInteger",
            "Long",
            "String",
            "Date",
            "String",
            "Date",
            "Boolean",
            "Boolean",
    };

    public static final Map<String, String> propertyTypeMap;

    public static final Map<String, String> aliasMap;

    static {
        propertyTypeMap = new HashMap<String, String>();
        initPropertyTypeMap();
        aliasMap = new HashMap<String, String>();
        initAliasMap();
    }

    private static  void initAliasMap() {
        aliasMap.put("id", "id");
        aliasMap.put("Id", "id");
        aliasMap.put("totalAmount", "totalAmount");
        aliasMap.put("TotalAmount", "totalAmount");
        aliasMap.put("version", "version");
        aliasMap.put("Version", "version");
        aliasMap.put("offChainVersion", "offChainVersion");
        aliasMap.put("OffChainVersion", "offChainVersion");
        aliasMap.put("createdBy", "createdBy");
        aliasMap.put("CreatedBy", "createdBy");
        aliasMap.put("createdAt", "createdAt");
        aliasMap.put("CreatedAt", "createdAt");
        aliasMap.put("updatedBy", "updatedBy");
        aliasMap.put("UpdatedBy", "updatedBy");
        aliasMap.put("updatedAt", "updatedAt");
        aliasMap.put("UpdatedAt", "updatedAt");
        aliasMap.put("active", "active");
        aliasMap.put("Active", "active");
        aliasMap.put("deleted", "deleted");
        aliasMap.put("Deleted", "deleted");
    }

    private static void initPropertyTypeMap() {
        for (int i = 0; i < propertyNames.length; i++ ) {
            propertyTypeMap.put(propertyNames[i], propertyTypes[i]);
        }
    }

  }


  // /////////////////////////////////////////////////////////  
  public static class OrderItemMetadata {

    private OrderItemMetadata() {
    }

    public static final String PROPERTY_NAME_VERSION      = "offChainVersion";
    public static final String PROPERTY_NAME_ACTIVE       = "active";
    public static final String PROPERTY_NAME_DELETED      = "deleted";
    public static final String PROPERTY_NAME_CREATED_BY   = "createdBy";
    public static final String PROPERTY_NAME_CREATED_AT   = "createdAt";
    public static final String PROPERTY_NAME_UPDATED_BY   = "updatedBy";
    public static final String PROPERTY_NAME_UPDATED_AT   = "updatedAt";

    public static final String[] propertyNames = new String[] {
            "productId",
            "quantity",
            "itemAmount",
            "offChainVersion",
            "createdBy",
            "createdAt",
            "updatedBy",
            "updatedAt",
            "active",
            "deleted",
            "orderId",
            "orderItemId.orderId",
            "orderItemId.productId",
    };

    public static final String[] propertyTypes = new String[] {
            "String",
            "BigInteger",
            "BigInteger",
            "Long",
            "String",
            "Date",
            "String",
            "Date",
            "Boolean",
            "Boolean",
            "String",
            "String",
            "String",
    };

    public static final Map<String, String> propertyTypeMap;

    public static final Map<String, String> aliasMap;

    static {
        propertyTypeMap = new HashMap<String, String>();
        initPropertyTypeMap();
        aliasMap = new HashMap<String, String>();
        initAliasMap();
    }

    private static  void initAliasMap() {
        aliasMap.put("productId", "orderItemId.productId");
        aliasMap.put("ProductId", "orderItemId.productId");
        aliasMap.put("quantity", "quantity");
        aliasMap.put("Quantity", "quantity");
        aliasMap.put("itemAmount", "itemAmount");
        aliasMap.put("ItemAmount", "itemAmount");
        aliasMap.put("offChainVersion", "offChainVersion");
        aliasMap.put("OffChainVersion", "offChainVersion");
        aliasMap.put("createdBy", "createdBy");
        aliasMap.put("CreatedBy", "createdBy");
        aliasMap.put("createdAt", "createdAt");
        aliasMap.put("CreatedAt", "createdAt");
        aliasMap.put("updatedBy", "updatedBy");
        aliasMap.put("UpdatedBy", "updatedBy");
        aliasMap.put("updatedAt", "updatedAt");
        aliasMap.put("UpdatedAt", "updatedAt");
        aliasMap.put("active", "active");
        aliasMap.put("Active", "active");
        aliasMap.put("deleted", "deleted");
        aliasMap.put("Deleted", "deleted");
        aliasMap.put("orderId", "orderItemId.orderId");
        aliasMap.put("OrderId", "orderItemId.orderId");
        aliasMap.put("orderItemId.orderId", "orderItemId.orderId");
        aliasMap.put("OrderItemId.OrderId", "orderItemId.orderId");
        aliasMap.put("orderItemId.productId", "orderItemId.productId");
        aliasMap.put("OrderItemId.ProductId", "orderItemId.productId");
    }

    private static void initPropertyTypeMap() {
        for (int i = 0; i < propertyNames.length; i++ ) {
            propertyTypeMap.put(propertyNames[i], propertyTypes[i]);
        }
    }

  }


  // /////////////////////////////////////////////////////////  
  public static class ProductMetadata {

    private ProductMetadata() {
    }

    public static final String PROPERTY_NAME_VERSION      = "offChainVersion";
    public static final String PROPERTY_NAME_ACTIVE       = "active";
    public static final String PROPERTY_NAME_DELETED      = "deleted";
    public static final String PROPERTY_NAME_CREATED_BY   = "createdBy";
    public static final String PROPERTY_NAME_CREATED_AT   = "createdAt";
    public static final String PROPERTY_NAME_UPDATED_BY   = "updatedBy";
    public static final String PROPERTY_NAME_UPDATED_AT   = "updatedAt";

    public static final Class ID_CLASS = String.class;

    public static final String[] propertyNames = new String[] {
            "productId",
            "name",
            "unitPrice",
            "version",
            "offChainVersion",
            "createdBy",
            "createdAt",
            "updatedBy",
            "updatedAt",
            "active",
            "deleted",
    };

    public static final String[] propertyTypes = new String[] {
            "String",
            "String",
            "BigInteger",
            "BigInteger",
            "Long",
            "String",
            "Date",
            "String",
            "Date",
            "Boolean",
            "Boolean",
    };

    public static final Map<String, String> propertyTypeMap;

    public static final Map<String, String> aliasMap;

    static {
        propertyTypeMap = new HashMap<String, String>();
        initPropertyTypeMap();
        aliasMap = new HashMap<String, String>();
        initAliasMap();
    }

    private static  void initAliasMap() {
        aliasMap.put("productId", "productId");
        aliasMap.put("ProductId", "productId");
        aliasMap.put("name", "name");
        aliasMap.put("Name", "name");
        aliasMap.put("unitPrice", "unitPrice");
        aliasMap.put("UnitPrice", "unitPrice");
        aliasMap.put("version", "version");
        aliasMap.put("Version", "version");
        aliasMap.put("offChainVersion", "offChainVersion");
        aliasMap.put("OffChainVersion", "offChainVersion");
        aliasMap.put("createdBy", "createdBy");
        aliasMap.put("CreatedBy", "createdBy");
        aliasMap.put("createdAt", "createdAt");
        aliasMap.put("CreatedAt", "createdAt");
        aliasMap.put("updatedBy", "updatedBy");
        aliasMap.put("UpdatedBy", "updatedBy");
        aliasMap.put("updatedAt", "updatedAt");
        aliasMap.put("UpdatedAt", "updatedAt");
        aliasMap.put("active", "active");
        aliasMap.put("Active", "active");
        aliasMap.put("deleted", "deleted");
        aliasMap.put("Deleted", "deleted");
    }

    private static void initPropertyTypeMap() {
        for (int i = 0; i < propertyNames.length; i++ ) {
            propertyTypeMap.put(propertyNames[i], propertyTypes[i]);
        }
    }

  }


  // /////////////////////////////////////////////////////////  
  public static class OrderV2Metadata {

    private OrderV2Metadata() {
    }

    public static final String PROPERTY_NAME_VERSION      = "offChainVersion";
    public static final String PROPERTY_NAME_ACTIVE       = "active";
    public static final String PROPERTY_NAME_DELETED      = "deleted";
    public static final String PROPERTY_NAME_CREATED_BY   = "createdBy";
    public static final String PROPERTY_NAME_CREATED_AT   = "createdAt";
    public static final String PROPERTY_NAME_UPDATED_BY   = "updatedBy";
    public static final String PROPERTY_NAME_UPDATED_AT   = "updatedAt";

    public static final Class ID_CLASS = String.class;

    public static final String[] propertyNames = new String[] {
            "orderId",
            "totalAmount",
            "version",
            "offChainVersion",
            "createdBy",
            "createdAt",
            "updatedBy",
            "updatedAt",
            "active",
            "deleted",
            "estimatedShipDate.monthYearNumber",
            "estimatedShipDate.monthYearCalendar",
            "estimatedShipDate.monthNumber",
            "estimatedShipDate.monthIsLeap",
            "estimatedShipDate.number",
            "estimatedShipDate.timeZone",
    };

    public static final String[] propertyTypes = new String[] {
            "String",
            "BigInteger",
            "BigInteger",
            "Long",
            "String",
            "Date",
            "String",
            "Date",
            "Boolean",
            "Boolean",
            "Integer",
            "String",
            "Integer",
            "Boolean",
            "Integer",
            "String",
    };

    public static final Map<String, String> propertyTypeMap;

    public static final Map<String, String> aliasMap;

    static {
        propertyTypeMap = new HashMap<String, String>();
        initPropertyTypeMap();
        aliasMap = new HashMap<String, String>();
        initAliasMap();
    }

    private static  void initAliasMap() {
        aliasMap.put("orderId", "orderId");
        aliasMap.put("OrderId", "orderId");
        aliasMap.put("totalAmount", "totalAmount");
        aliasMap.put("TotalAmount", "totalAmount");
        aliasMap.put("version", "version");
        aliasMap.put("Version", "version");
        aliasMap.put("offChainVersion", "offChainVersion");
        aliasMap.put("OffChainVersion", "offChainVersion");
        aliasMap.put("createdBy", "createdBy");
        aliasMap.put("CreatedBy", "createdBy");
        aliasMap.put("createdAt", "createdAt");
        aliasMap.put("CreatedAt", "createdAt");
        aliasMap.put("updatedBy", "updatedBy");
        aliasMap.put("UpdatedBy", "updatedBy");
        aliasMap.put("updatedAt", "updatedAt");
        aliasMap.put("UpdatedAt", "updatedAt");
        aliasMap.put("active", "active");
        aliasMap.put("Active", "active");
        aliasMap.put("deleted", "deleted");
        aliasMap.put("Deleted", "deleted");
        aliasMap.put("estimatedShipDate.monthYearNumber", "estimatedShipDate.monthYearNumber");
        aliasMap.put("EstimatedShipDate.MonthYearNumber", "estimatedShipDate.monthYearNumber");
        aliasMap.put("estimatedShipDate.month.year.number", "estimatedShipDate.monthYearNumber");
        aliasMap.put("EstimatedShipDate.Month.Year.Number", "estimatedShipDate.monthYearNumber");
        aliasMap.put("estimatedShipDate.monthYearCalendar", "estimatedShipDate.monthYearCalendar");
        aliasMap.put("EstimatedShipDate.MonthYearCalendar", "estimatedShipDate.monthYearCalendar");
        aliasMap.put("estimatedShipDate.month.year.calendar", "estimatedShipDate.monthYearCalendar");
        aliasMap.put("EstimatedShipDate.Month.Year.Calendar", "estimatedShipDate.monthYearCalendar");
        aliasMap.put("estimatedShipDate.monthNumber", "estimatedShipDate.monthNumber");
        aliasMap.put("EstimatedShipDate.MonthNumber", "estimatedShipDate.monthNumber");
        aliasMap.put("estimatedShipDate.month.number", "estimatedShipDate.monthNumber");
        aliasMap.put("EstimatedShipDate.Month.Number", "estimatedShipDate.monthNumber");
        aliasMap.put("estimatedShipDate.monthIsLeap", "estimatedShipDate.monthIsLeap");
        aliasMap.put("EstimatedShipDate.MonthIsLeap", "estimatedShipDate.monthIsLeap");
        aliasMap.put("estimatedShipDate.month.isLeap", "estimatedShipDate.monthIsLeap");
        aliasMap.put("EstimatedShipDate.Month.IsLeap", "estimatedShipDate.monthIsLeap");
        aliasMap.put("estimatedShipDate.number", "estimatedShipDate.number");
        aliasMap.put("EstimatedShipDate.Number", "estimatedShipDate.number");
        aliasMap.put("estimatedShipDate.timeZone", "estimatedShipDate.timeZone");
        aliasMap.put("EstimatedShipDate.TimeZone", "estimatedShipDate.timeZone");
    }

    private static void initPropertyTypeMap() {
        for (int i = 0; i < propertyNames.length; i++ ) {
            propertyTypeMap.put(propertyNames[i], propertyTypes[i]);
        }
    }

  }


  // /////////////////////////////////////////////////////////  
  public static class OrderV2ItemMetadata {

    private OrderV2ItemMetadata() {
    }

    public static final String PROPERTY_NAME_VERSION      = "offChainVersion";
    public static final String PROPERTY_NAME_ACTIVE       = "active";
    public static final String PROPERTY_NAME_DELETED      = "deleted";
    public static final String PROPERTY_NAME_CREATED_BY   = "createdBy";
    public static final String PROPERTY_NAME_CREATED_AT   = "createdAt";
    public static final String PROPERTY_NAME_UPDATED_BY   = "updatedBy";
    public static final String PROPERTY_NAME_UPDATED_AT   = "updatedAt";

    public static final String[] propertyNames = new String[] {
            "productId",
            "quantity",
            "itemAmount",
            "offChainVersion",
            "createdBy",
            "createdAt",
            "updatedBy",
            "updatedAt",
            "active",
            "deleted",
            "orderV2OrderId",
            "orderV2ItemId.orderV2OrderId",
            "orderV2ItemId.productId",
    };

    public static final String[] propertyTypes = new String[] {
            "String",
            "BigInteger",
            "BigInteger",
            "Long",
            "String",
            "Date",
            "String",
            "Date",
            "Boolean",
            "Boolean",
            "String",
            "String",
            "String",
    };

    public static final Map<String, String> propertyTypeMap;

    public static final Map<String, String> aliasMap;

    static {
        propertyTypeMap = new HashMap<String, String>();
        initPropertyTypeMap();
        aliasMap = new HashMap<String, String>();
        initAliasMap();
    }

    private static  void initAliasMap() {
        aliasMap.put("productId", "orderV2ItemId.productId");
        aliasMap.put("ProductId", "orderV2ItemId.productId");
        aliasMap.put("quantity", "quantity");
        aliasMap.put("Quantity", "quantity");
        aliasMap.put("itemAmount", "itemAmount");
        aliasMap.put("ItemAmount", "itemAmount");
        aliasMap.put("offChainVersion", "offChainVersion");
        aliasMap.put("OffChainVersion", "offChainVersion");
        aliasMap.put("createdBy", "createdBy");
        aliasMap.put("CreatedBy", "createdBy");
        aliasMap.put("createdAt", "createdAt");
        aliasMap.put("CreatedAt", "createdAt");
        aliasMap.put("updatedBy", "updatedBy");
        aliasMap.put("UpdatedBy", "updatedBy");
        aliasMap.put("updatedAt", "updatedAt");
        aliasMap.put("UpdatedAt", "updatedAt");
        aliasMap.put("active", "active");
        aliasMap.put("Active", "active");
        aliasMap.put("deleted", "deleted");
        aliasMap.put("Deleted", "deleted");
        aliasMap.put("orderV2OrderId", "orderV2ItemId.orderV2OrderId");
        aliasMap.put("OrderV2OrderId", "orderV2ItemId.orderV2OrderId");
        aliasMap.put("orderV2ItemId.orderV2OrderId", "orderV2ItemId.orderV2OrderId");
        aliasMap.put("OrderV2ItemId.OrderV2OrderId", "orderV2ItemId.orderV2OrderId");
        aliasMap.put("orderV2ItemId.productId", "orderV2ItemId.productId");
        aliasMap.put("OrderV2ItemId.ProductId", "orderV2ItemId.productId");
    }

    private static void initPropertyTypeMap() {
        for (int i = 0; i < propertyNames.length; i++ ) {
            propertyTypeMap.put(propertyNames[i], propertyTypes[i]);
        }
    }

  }


  // /////////////////////////////////////////////////////////  
  public static class OrderShipGroupMetadata {

    private OrderShipGroupMetadata() {
    }

    public static final String PROPERTY_NAME_VERSION      = "offChainVersion";
    public static final String PROPERTY_NAME_ACTIVE       = "active";
    public static final String PROPERTY_NAME_DELETED      = "deleted";
    public static final String PROPERTY_NAME_CREATED_BY   = "createdBy";
    public static final String PROPERTY_NAME_CREATED_AT   = "createdAt";
    public static final String PROPERTY_NAME_UPDATED_BY   = "updatedBy";
    public static final String PROPERTY_NAME_UPDATED_AT   = "updatedAt";

    public static final String[] propertyNames = new String[] {
            "shipGroupSeqId",
            "shipmentMethod",
            "offChainVersion",
            "createdBy",
            "createdAt",
            "updatedBy",
            "updatedAt",
            "active",
            "deleted",
            "orderV2OrderId",
            "orderV2OrderShipGroupId.orderV2OrderId",
            "orderV2OrderShipGroupId.shipGroupSeqId",
    };

    public static final String[] propertyTypes = new String[] {
            "Integer",
            "String",
            "Long",
            "String",
            "Date",
            "String",
            "Date",
            "Boolean",
            "Boolean",
            "String",
            "String",
            "Integer",
    };

    public static final Map<String, String> propertyTypeMap;

    public static final Map<String, String> aliasMap;

    static {
        propertyTypeMap = new HashMap<String, String>();
        initPropertyTypeMap();
        aliasMap = new HashMap<String, String>();
        initAliasMap();
    }

    private static  void initAliasMap() {
        aliasMap.put("shipGroupSeqId", "orderV2OrderShipGroupId.shipGroupSeqId");
        aliasMap.put("ShipGroupSeqId", "orderV2OrderShipGroupId.shipGroupSeqId");
        aliasMap.put("shipmentMethod", "shipmentMethod");
        aliasMap.put("ShipmentMethod", "shipmentMethod");
        aliasMap.put("offChainVersion", "offChainVersion");
        aliasMap.put("OffChainVersion", "offChainVersion");
        aliasMap.put("createdBy", "createdBy");
        aliasMap.put("CreatedBy", "createdBy");
        aliasMap.put("createdAt", "createdAt");
        aliasMap.put("CreatedAt", "createdAt");
        aliasMap.put("updatedBy", "updatedBy");
        aliasMap.put("UpdatedBy", "updatedBy");
        aliasMap.put("updatedAt", "updatedAt");
        aliasMap.put("UpdatedAt", "updatedAt");
        aliasMap.put("active", "active");
        aliasMap.put("Active", "active");
        aliasMap.put("deleted", "deleted");
        aliasMap.put("Deleted", "deleted");
        aliasMap.put("orderV2OrderId", "orderV2OrderShipGroupId.orderV2OrderId");
        aliasMap.put("OrderV2OrderId", "orderV2OrderShipGroupId.orderV2OrderId");
        aliasMap.put("orderV2OrderShipGroupId.orderV2OrderId", "orderV2OrderShipGroupId.orderV2OrderId");
        aliasMap.put("OrderV2OrderShipGroupId.OrderV2OrderId", "orderV2OrderShipGroupId.orderV2OrderId");
        aliasMap.put("orderV2OrderShipGroupId.shipGroupSeqId", "orderV2OrderShipGroupId.shipGroupSeqId");
        aliasMap.put("OrderV2OrderShipGroupId.ShipGroupSeqId", "orderV2OrderShipGroupId.shipGroupSeqId");
    }

    private static void initPropertyTypeMap() {
        for (int i = 0; i < propertyNames.length; i++ ) {
            propertyTypeMap.put(propertyNames[i], propertyTypes[i]);
        }
    }

  }


  // /////////////////////////////////////////////////////////  
  public static class OrderItemShipGroupAssociationMetadata {

    private OrderItemShipGroupAssociationMetadata() {
    }

    public static final String PROPERTY_NAME_VERSION      = "offChainVersion";
    public static final String PROPERTY_NAME_ACTIVE       = "active";
    public static final String PROPERTY_NAME_DELETED      = "deleted";
    public static final String PROPERTY_NAME_CREATED_BY   = "createdBy";
    public static final String PROPERTY_NAME_CREATED_AT   = "createdAt";
    public static final String PROPERTY_NAME_UPDATED_BY   = "updatedBy";
    public static final String PROPERTY_NAME_UPDATED_AT   = "updatedAt";

    public static final String[] propertyNames = new String[] {
            "productId",
            "quantity",
            "cancelQuantity",
            "offChainVersion",
            "createdBy",
            "createdAt",
            "updatedBy",
            "updatedAt",
            "active",
            "deleted",
            "orderV2OrderId",
            "orderShipGroupShipGroupSeqId",
            "orderV2OrderItemShipGroupAssociationId.orderV2OrderId",
            "orderV2OrderItemShipGroupAssociationId.orderShipGroupShipGroupSeqId",
            "orderV2OrderItemShipGroupAssociationId.productId",
    };

    public static final String[] propertyTypes = new String[] {
            "String",
            "BigInteger",
            "BigInteger",
            "Long",
            "String",
            "Date",
            "String",
            "Date",
            "Boolean",
            "Boolean",
            "String",
            "Integer",
            "String",
            "Integer",
            "String",
    };

    public static final Map<String, String> propertyTypeMap;

    public static final Map<String, String> aliasMap;

    static {
        propertyTypeMap = new HashMap<String, String>();
        initPropertyTypeMap();
        aliasMap = new HashMap<String, String>();
        initAliasMap();
    }

    private static  void initAliasMap() {
        aliasMap.put("productId", "orderV2OrderItemShipGroupAssociationId.productId");
        aliasMap.put("ProductId", "orderV2OrderItemShipGroupAssociationId.productId");
        aliasMap.put("quantity", "quantity");
        aliasMap.put("Quantity", "quantity");
        aliasMap.put("cancelQuantity", "cancelQuantity");
        aliasMap.put("CancelQuantity", "cancelQuantity");
        aliasMap.put("offChainVersion", "offChainVersion");
        aliasMap.put("OffChainVersion", "offChainVersion");
        aliasMap.put("createdBy", "createdBy");
        aliasMap.put("CreatedBy", "createdBy");
        aliasMap.put("createdAt", "createdAt");
        aliasMap.put("CreatedAt", "createdAt");
        aliasMap.put("updatedBy", "updatedBy");
        aliasMap.put("UpdatedBy", "updatedBy");
        aliasMap.put("updatedAt", "updatedAt");
        aliasMap.put("UpdatedAt", "updatedAt");
        aliasMap.put("active", "active");
        aliasMap.put("Active", "active");
        aliasMap.put("deleted", "deleted");
        aliasMap.put("Deleted", "deleted");
        aliasMap.put("orderV2OrderId", "orderV2OrderItemShipGroupAssociationId.orderV2OrderId");
        aliasMap.put("OrderV2OrderId", "orderV2OrderItemShipGroupAssociationId.orderV2OrderId");
        aliasMap.put("orderShipGroupShipGroupSeqId", "orderV2OrderItemShipGroupAssociationId.orderShipGroupShipGroupSeqId");
        aliasMap.put("OrderShipGroupShipGroupSeqId", "orderV2OrderItemShipGroupAssociationId.orderShipGroupShipGroupSeqId");
        aliasMap.put("orderV2OrderItemShipGroupAssociationId.orderV2OrderId", "orderV2OrderItemShipGroupAssociationId.orderV2OrderId");
        aliasMap.put("OrderV2OrderItemShipGroupAssociationId.OrderV2OrderId", "orderV2OrderItemShipGroupAssociationId.orderV2OrderId");
        aliasMap.put("orderV2OrderItemShipGroupAssociationId.orderShipGroupShipGroupSeqId", "orderV2OrderItemShipGroupAssociationId.orderShipGroupShipGroupSeqId");
        aliasMap.put("OrderV2OrderItemShipGroupAssociationId.OrderShipGroupShipGroupSeqId", "orderV2OrderItemShipGroupAssociationId.orderShipGroupShipGroupSeqId");
        aliasMap.put("orderV2OrderItemShipGroupAssociationId.productId", "orderV2OrderItemShipGroupAssociationId.productId");
        aliasMap.put("OrderV2OrderItemShipGroupAssociationId.ProductId", "orderV2OrderItemShipGroupAssociationId.productId");
    }

    private static void initPropertyTypeMap() {
        for (int i = 0; i < propertyNames.length; i++ ) {
            propertyTypeMap.put(propertyNames[i], propertyTypes[i]);
        }
    }

  }


  // /////////////////////////////////////////////////////////  
  public static class DaySummaryMetadata {

    private DaySummaryMetadata() {
    }

    public static final String PROPERTY_NAME_VERSION      = "offChainVersion";
    public static final String PROPERTY_NAME_ACTIVE       = "active";
    public static final String PROPERTY_NAME_DELETED      = "deleted";
    public static final String PROPERTY_NAME_CREATED_BY   = "createdBy";
    public static final String PROPERTY_NAME_CREATED_AT   = "createdAt";
    public static final String PROPERTY_NAME_UPDATED_BY   = "updatedBy";
    public static final String PROPERTY_NAME_UPDATED_AT   = "updatedAt";

    public static final String URL_ID_FIELD_SEPARATOR = ",";

    public static final TextFormatter<org.dddml.suidemocontracts.domain.Day> URL_ID_TEXT_FORMATTER =
                    new AbstractValueObjectTextFormatter<org.dddml.suidemocontracts.domain.Day>(org.dddml.suidemocontracts.domain.Day.class, URL_ID_FIELD_SEPARATOR) {
                        @Override
                        protected Class<?> getClassByTypeName(String type) {
                            return BoundedContextMetadata.CLASS_MAP.get(type);
                        }
                    };

    public static final Class ID_CLASS = org.dddml.suidemocontracts.domain.Day.class;

    public static final String[] propertyNames = new String[] {
            "description",
            "metadata",
            "optionalData",
            "version",
            "offChainVersion",
            "createdBy",
            "createdAt",
            "updatedBy",
            "updatedAt",
            "active",
            "deleted",
            "day.monthYearNumber",
            "day.monthYearCalendar",
            "day.monthNumber",
            "day.monthIsLeap",
            "day.number",
            "day.timeZone",
    };

    public static final String[] propertyTypes = new String[] {
            "String",
            "int[]",
            "int[]",
            "BigInteger",
            "Long",
            "String",
            "Date",
            "String",
            "Date",
            "Boolean",
            "Boolean",
            "Integer",
            "String",
            "Integer",
            "Boolean",
            "Integer",
            "String",
    };

    public static final Map<String, String> propertyTypeMap;

    public static final Map<String, String> aliasMap;

    static {
        propertyTypeMap = new HashMap<String, String>();
        initPropertyTypeMap();
        aliasMap = new HashMap<String, String>();
        initAliasMap();
    }

    private static  void initAliasMap() {
        aliasMap.put("description", "description");
        aliasMap.put("Description", "description");
        aliasMap.put("metadata", "metadata");
        aliasMap.put("Metadata", "metadata");
        aliasMap.put("optionalData", "optionalData");
        aliasMap.put("OptionalData", "optionalData");
        aliasMap.put("version", "version");
        aliasMap.put("Version", "version");
        aliasMap.put("offChainVersion", "offChainVersion");
        aliasMap.put("OffChainVersion", "offChainVersion");
        aliasMap.put("createdBy", "createdBy");
        aliasMap.put("CreatedBy", "createdBy");
        aliasMap.put("createdAt", "createdAt");
        aliasMap.put("CreatedAt", "createdAt");
        aliasMap.put("updatedBy", "updatedBy");
        aliasMap.put("UpdatedBy", "updatedBy");
        aliasMap.put("updatedAt", "updatedAt");
        aliasMap.put("UpdatedAt", "updatedAt");
        aliasMap.put("active", "active");
        aliasMap.put("Active", "active");
        aliasMap.put("deleted", "deleted");
        aliasMap.put("Deleted", "deleted");
        aliasMap.put("day.monthYearNumber", "day.monthYearNumber");
        aliasMap.put("Day.MonthYearNumber", "day.monthYearNumber");
        aliasMap.put("day.month.year.number", "day.monthYearNumber");
        aliasMap.put("Day.Month.Year.Number", "day.monthYearNumber");
        aliasMap.put("day.monthYearCalendar", "day.monthYearCalendar");
        aliasMap.put("Day.MonthYearCalendar", "day.monthYearCalendar");
        aliasMap.put("day.month.year.calendar", "day.monthYearCalendar");
        aliasMap.put("Day.Month.Year.Calendar", "day.monthYearCalendar");
        aliasMap.put("day.monthNumber", "day.monthNumber");
        aliasMap.put("Day.MonthNumber", "day.monthNumber");
        aliasMap.put("day.month.number", "day.monthNumber");
        aliasMap.put("Day.Month.Number", "day.monthNumber");
        aliasMap.put("day.monthIsLeap", "day.monthIsLeap");
        aliasMap.put("Day.MonthIsLeap", "day.monthIsLeap");
        aliasMap.put("day.month.isLeap", "day.monthIsLeap");
        aliasMap.put("Day.Month.IsLeap", "day.monthIsLeap");
        aliasMap.put("day.number", "day.number");
        aliasMap.put("Day.Number", "day.number");
        aliasMap.put("day.timeZone", "day.timeZone");
        aliasMap.put("Day.TimeZone", "day.timeZone");
    }

    private static void initPropertyTypeMap() {
        for (int i = 0; i < propertyNames.length; i++ ) {
            propertyTypeMap.put(propertyNames[i], propertyTypes[i]);
        }
    }

  }

}


