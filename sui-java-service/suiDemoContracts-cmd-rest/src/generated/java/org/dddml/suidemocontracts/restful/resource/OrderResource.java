package org.dddml.suidemocontracts.restful.resource;

import java.util.*;
import java.util.stream.*;
import javax.servlet.http.*;
import javax.validation.constraints.*;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.dddml.support.criterion.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.order.*;
import static org.dddml.suidemocontracts.domain.meta.M.*;

import com.alibaba.fastjson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.dddml.support.criterion.TypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping(path = "Orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class OrderResource {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private OrderApplicationService orderApplicationService;


    /**
     * 查询.
     * 查询 Orders
     */
    @GetMapping
    public OrderStateDto[] getAll( HttpServletRequest request,
                    @RequestParam(value = "sort", required = false) String sort,
                    @RequestParam(value = "fields", required = false) String fields,
                    @RequestParam(value = "firstResult", defaultValue = "0") Integer firstResult,
                    @RequestParam(value = "maxResults", defaultValue = "2147483647") Integer maxResults,
                    @RequestParam(value = "filter", required = false) String filter) {
        try {
        if (firstResult < 0) { firstResult = 0; }
        if (maxResults == null || maxResults < 1) { maxResults = Integer.MAX_VALUE; }

            Iterable<OrderState> states = null; 
            CriterionDto criterion = null;
            if (!StringHelper.isNullOrEmpty(filter)) {
                criterion = JSON.parseObject(filter, CriterionDto.class);
            } else {
                criterion = QueryParamUtils.getQueryCriterionDto(request.getParameterMap().entrySet().stream()
                    .filter(kv -> OrderResourceUtils.getFilterPropertyName(kv.getKey()) != null)
                    .collect(Collectors.toMap(kv -> kv.getKey(), kv -> kv.getValue())));
            }
            Criterion c = CriterionDto.toSubclass(criterion, getCriterionTypeConverter(), getPropertyTypeResolver(), 
                n -> (OrderMetadata.aliasMap.containsKey(n) ? OrderMetadata.aliasMap.get(n) : n));
            states = orderApplicationService.get(
                c,
                OrderResourceUtils.getQuerySorts(request.getParameterMap()),
                firstResult, maxResults);

            OrderStateDto.DtoConverter dtoConverter = new OrderStateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            return dtoConverter.toOrderStateDtoArray(states);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 查询.
     * 分页查询 Orders
     */
    @GetMapping("_page")
    public Page<OrderStateDto> getPage( HttpServletRequest request,
                    @RequestParam(value = "fields", required = false) String fields,
                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                    @RequestParam(value = "size", defaultValue = "20") Integer size,
                    @RequestParam(value = "filter", required = false) String filter) {
        try {
            Integer firstResult = (page == null ? 0 : page) * (size == null ? 20 : size);
            Integer maxResults = (size == null ? 20 : size);
            Iterable<OrderState> states = null; 
            CriterionDto criterion = null;
            if (!StringHelper.isNullOrEmpty(filter)) {
                criterion = JSON.parseObject(filter, CriterionDto.class);
            } else {
                criterion = QueryParamUtils.getQueryCriterionDto(request.getParameterMap().entrySet().stream()
                    .filter(kv -> OrderResourceUtils.getFilterPropertyName(kv.getKey()) != null)
                    .collect(Collectors.toMap(kv -> kv.getKey(), kv -> kv.getValue())));
            }
            Criterion c = CriterionDto.toSubclass(criterion, getCriterionTypeConverter(), getPropertyTypeResolver(), 
                n -> (OrderMetadata.aliasMap.containsKey(n) ? OrderMetadata.aliasMap.get(n) : n));
            states = orderApplicationService.get(
                c,
                OrderResourceUtils.getQuerySorts(request.getParameterMap()),
                firstResult, maxResults);
            long count = orderApplicationService.getCount(c);

            OrderStateDto.DtoConverter dtoConverter = new OrderStateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            Page.PageImpl<OrderStateDto> statePage =  new Page.PageImpl<>(dtoConverter.toOrderStateDtoList(states), count);
            statePage.setSize(size);
            statePage.setNumber(page);
            return statePage;

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 查看.
     * 通过 Id 获取单个 Order
     */
    @GetMapping("{id}")
    public OrderStateDto get(@PathVariable("id") String id, @RequestParam(value = "fields", required = false) String fields) {
        try {
            String idObj = id;
            OrderState state = orderApplicationService.get(idObj);
            if (state == null) { return null; }

            OrderStateDto.DtoConverter dtoConverter = new OrderStateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            return dtoConverter.toOrderStateDto(state);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    @GetMapping("_count")
    public long getCount( HttpServletRequest request,
                         @RequestParam(value = "filter", required = false) String filter) {
        try {
            long count = 0;
            CriterionDto criterion = null;
            if (!StringHelper.isNullOrEmpty(filter)) {
                criterion = JSONObject.parseObject(filter, CriterionDto.class);
            } else {
                criterion = QueryParamUtils.getQueryCriterionDto(request.getParameterMap());
            }
            Criterion c = CriterionDto.toSubclass(criterion,
                getCriterionTypeConverter(), 
                getPropertyTypeResolver(), 
                n -> (OrderMetadata.aliasMap.containsKey(n) ? OrderMetadata.aliasMap.get(n) : n));
            count = orderApplicationService.getCount(c);
            return count;

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    @PutMapping("{id}/_commands/Create")
    public void create(@PathVariable("id") String id, @RequestBody OrderCommands.Create content) {
        try {

            OrderCommands.Create cmd = content;//.toCreate();
            String idObj = id;
            if (cmd.getId() == null) {
                cmd.setId(idObj);
            } else if (!cmd.getId().equals(idObj)) {
                throw DomainError.named("inconsistentId", "Argument Id %1$s NOT equals body Id %2$s", id, cmd.getId());
            }
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            orderApplicationService.when(cmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    @PutMapping("{id}/_commands/RemoveItem")
    public void removeItem(@PathVariable("id") String id, @RequestBody OrderCommands.RemoveItem content) {
        try {

            OrderCommands.RemoveItem cmd = content;//.toRemoveItem();
            String idObj = id;
            if (cmd.getId() == null) {
                cmd.setId(idObj);
            } else if (!cmd.getId().equals(idObj)) {
                throw DomainError.named("inconsistentId", "Argument Id %1$s NOT equals body Id %2$s", id, cmd.getId());
            }
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            orderApplicationService.when(cmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    @PutMapping("{id}/_commands/UpdateItemQuantity")
    public void updateItemQuantity(@PathVariable("id") String id, @RequestBody OrderCommands.UpdateItemQuantity content) {
        try {

            OrderCommands.UpdateItemQuantity cmd = content;//.toUpdateItemQuantity();
            String idObj = id;
            if (cmd.getId() == null) {
                cmd.setId(idObj);
            } else if (!cmd.getId().equals(idObj)) {
                throw DomainError.named("inconsistentId", "Argument Id %1$s NOT equals body Id %2$s", id, cmd.getId());
            }
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            orderApplicationService.when(cmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    @GetMapping("_metadata/filteringFields")
    public List<PropertyMetadataDto> getMetadataFilteringFields() {
        try {

            List<PropertyMetadataDto> filtering = new ArrayList<>();
            OrderMetadata.propertyTypeMap.forEach((key, value) -> {
                filtering.add(new PropertyMetadataDto(key, value, true));
            });
            return filtering;

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    @GetMapping("{id}/_events/{version}")
    public OrderEvent getEvent(@PathVariable("id") String id, @PathVariable("version") long version) {
        try {

            String idObj = id;
            //OrderStateEventDtoConverter dtoConverter = getOrderStateEventDtoConverter();
            return orderApplicationService.getEvent(idObj, version);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    @GetMapping("{id}/_historyStates/{version}")
    public OrderStateDto getHistoryState(@PathVariable("id") String id, @PathVariable("version") long version, @RequestParam(value = "fields", required = false) String fields) {
        try {

            String idObj = id;
            OrderStateDto.DtoConverter dtoConverter = new OrderStateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            return dtoConverter.toOrderStateDto(orderApplicationService.getHistoryState(idObj, version));

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 查看.
     * 获取指定 ProductId 的 OrderItem
     */
    @GetMapping("{id}/OrderItems/{productId}")
    public OrderItemStateDto getOrderItem(@PathVariable("id") String id, @PathVariable("productId") String productId) {
        try {

            OrderItemState state = orderApplicationService.getOrderItem(id, productId);
            if (state == null) { return null; }
            OrderItemStateDto.DtoConverter dtoConverter = new OrderItemStateDto.DtoConverter();
            OrderItemStateDto stateDto = dtoConverter.toOrderItemStateDto(state);
            dtoConverter.setAllFieldsReturned(true);
            return stateDto;

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * OrderItem List
     */
    @GetMapping("{id}/OrderItems")
    public OrderItemStateDto[] getOrderItems(@PathVariable("id") String id,
                    @RequestParam(value = "sort", required = false) String sort,
                    @RequestParam(value = "fields", required = false) String fields,
                    @RequestParam(value = "filter", required = false) String filter,
                     HttpServletRequest request) {
        try {
            CriterionDto criterion = null;
            if (!StringHelper.isNullOrEmpty(filter)) {
                criterion = JSON.parseObject(filter, CriterionDto.class);
            } else {
                criterion = QueryParamUtils.getQueryCriterionDto(request.getParameterMap().entrySet().stream()
                    .filter(kv -> OrderResourceUtils.getOrderItemFilterPropertyName(kv.getKey()) != null)
                    .collect(Collectors.toMap(kv -> kv.getKey(), kv -> kv.getValue())));
            }
            Criterion c = CriterionDto.toSubclass(criterion, getCriterionTypeConverter(), getPropertyTypeResolver(), 
                n -> (OrderItemMetadata.aliasMap.containsKey(n) ? OrderItemMetadata.aliasMap.get(n) : n));
            Iterable<OrderItemState> states = orderApplicationService.getOrderItems(id, c,
                    OrderResourceUtils.getOrderItemQuerySorts(request.getParameterMap()));
            if (states == null) { return null; }
            OrderItemStateDto.DtoConverter dtoConverter = new OrderItemStateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            return dtoConverter.toOrderItemStateDtoArray(states);
        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }



    //protected  OrderStateEventDtoConverter getOrderStateEventDtoConverter() {
    //    return new OrderStateEventDtoConverter();
    //}

    protected TypeConverter getCriterionTypeConverter() {
        return new DefaultTypeConverter();
    }

    protected PropertyTypeResolver getPropertyTypeResolver() {
        return new PropertyTypeResolver() {
            @Override
            public Class resolveTypeByPropertyName(String propertyName) {
                return OrderResourceUtils.getFilterPropertyType(propertyName);
            }
        };
    }

    protected PropertyTypeResolver getOrderItemPropertyTypeResolver() {
        return new PropertyTypeResolver() {
            @Override
            public Class resolveTypeByPropertyName(String propertyName) {
                return OrderResourceUtils.getOrderItemFilterPropertyType(propertyName);
            }
        };
    }

    // ////////////////////////////////
 
    public static class OrderResourceUtils {

        public static void setNullIdOrThrowOnInconsistentIds(String id, OrderCommand value) {
            String idObj = id;
            if (value.getId() == null) {
                value.setId(idObj);
            } else if (!value.getId().equals(idObj)) {
                throw DomainError.named("inconsistentId", "Argument Id %1$s NOT equals body Id %2$s", id, value.getId());
            }
        }
    
        public static List<String> getQueryOrders(String str, String separator) {
            return QueryParamUtils.getQueryOrders(str, separator, OrderMetadata.aliasMap);
        }

        public static List<String> getQuerySorts(Map<String, String[]> queryNameValuePairs) {
            String[] values = queryNameValuePairs.get("sort");
            return QueryParamUtils.getQuerySorts(values, OrderMetadata.aliasMap);
        }

        public static String getFilterPropertyName(String fieldName) {
            if ("sort".equalsIgnoreCase(fieldName)
                    || "firstResult".equalsIgnoreCase(fieldName)
                    || "maxResults".equalsIgnoreCase(fieldName)
                    || "fields".equalsIgnoreCase(fieldName)) {
                return null;
            }
            if (OrderMetadata.aliasMap.containsKey(fieldName)) {
                return OrderMetadata.aliasMap.get(fieldName);
            }
            return null;
        }

        public static Class getFilterPropertyType(String propertyName) {
            if (OrderMetadata.propertyTypeMap.containsKey(propertyName)) {
                String propertyType = OrderMetadata.propertyTypeMap.get(propertyName);
                if (!StringHelper.isNullOrEmpty(propertyType)) {
                    if (BoundedContextMetadata.CLASS_MAP.containsKey(propertyType)) {
                        return BoundedContextMetadata.CLASS_MAP.get(propertyType);
                    }
                }
            }
            return String.class;
        }

        public static Iterable<Map.Entry<String, Object>> getQueryFilterMap(Map<String, String[]> queryNameValuePairs) {
            Map<String, Object> filter = new HashMap<>();
            queryNameValuePairs.forEach((key, values) -> {
                if (values.length > 0) {
                    String pName = getFilterPropertyName(key);
                    if (!StringHelper.isNullOrEmpty(pName)) {
                        Class pClass = getFilterPropertyType(pName);
                        filter.put(pName, ApplicationContext.current.getTypeConverter().convertFromString(pClass, values[0]));
                    }
                }
            });
            return filter.entrySet();
        }

        public static List<String> getOrderItemQueryOrders(String str, String separator) {
            return QueryParamUtils.getQueryOrders(str, separator, OrderItemMetadata.aliasMap);
        }

        public static List<String> getOrderItemQuerySorts(Map<String, String[]> queryNameValuePairs) {
            String[] values = queryNameValuePairs.get("sort");
            return QueryParamUtils.getQuerySorts(values, OrderItemMetadata.aliasMap);
        }

        public static String getOrderItemFilterPropertyName(String fieldName) {
            if ("sort".equalsIgnoreCase(fieldName)
                    || "firstResult".equalsIgnoreCase(fieldName)
                    || "maxResults".equalsIgnoreCase(fieldName)
                    || "fields".equalsIgnoreCase(fieldName)) {
                return null;
            }
            if (OrderItemMetadata.aliasMap.containsKey(fieldName)) {
                return OrderItemMetadata.aliasMap.get(fieldName);
            }
            return null;
        }

        public static Class getOrderItemFilterPropertyType(String propertyName) {
            if (OrderItemMetadata.propertyTypeMap.containsKey(propertyName)) {
                String propertyType = OrderItemMetadata.propertyTypeMap.get(propertyName);
                if (!StringHelper.isNullOrEmpty(propertyType)) {
                    if (BoundedContextMetadata.CLASS_MAP.containsKey(propertyType)) {
                        return BoundedContextMetadata.CLASS_MAP.get(propertyType);
                    }
                }
            }
            return String.class;
        }

        public static Iterable<Map.Entry<String, Object>> getOrderItemQueryFilterMap(Map<String, String[]> queryNameValuePairs) {
            Map<String, Object> filter = new HashMap<>();
            queryNameValuePairs.forEach((key, values) -> {
                if (values.length > 0) {
                    String pName = getOrderItemFilterPropertyName(key);
                    if (!StringHelper.isNullOrEmpty(pName)) {
                        Class pClass = getOrderItemFilterPropertyType(pName);
                        filter.put(pName, ApplicationContext.current.getTypeConverter().convertFromString(pClass, values[0]));
                    }
                }
            });
            return filter.entrySet();
        }

        public static OrderStateDto[] toOrderStateDtoArray(Iterable<String> ids) {
            List<OrderStateDto> states = new ArrayList<>();
            ids.forEach(i -> {
                OrderStateDto dto = new OrderStateDto();
                dto.setId(i);
                states.add(dto);
            });
            return states.toArray(new OrderStateDto[0]);
        }

    }

}

