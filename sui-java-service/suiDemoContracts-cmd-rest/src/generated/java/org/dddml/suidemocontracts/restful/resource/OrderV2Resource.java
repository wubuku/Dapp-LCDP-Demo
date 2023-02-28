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
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.orderv2.*;
import static org.dddml.suidemocontracts.domain.meta.M.*;

import com.alibaba.fastjson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.dddml.support.criterion.TypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping(path = "OrderV2s", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class OrderV2Resource {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private OrderV2ApplicationService orderV2ApplicationService;


    /**
     * 查询.
     * 查询 OrderV2s
     */
    @GetMapping
    public OrderV2StateDto[] getAll( HttpServletRequest request,
                    @RequestParam(value = "sort", required = false) String sort,
                    @RequestParam(value = "fields", required = false) String fields,
                    @RequestParam(value = "firstResult", defaultValue = "0") Integer firstResult,
                    @RequestParam(value = "maxResults", defaultValue = "2147483647") Integer maxResults,
                    @RequestParam(value = "filter", required = false) String filter) {
        try {
        if (firstResult < 0) { firstResult = 0; }
        if (maxResults == null || maxResults < 1) { maxResults = Integer.MAX_VALUE; }

            Iterable<OrderV2State> states = null; 
            CriterionDto criterion = null;
            if (!StringHelper.isNullOrEmpty(filter)) {
                criterion = JSON.parseObject(filter, CriterionDto.class);
            } else {
                criterion = QueryParamUtils.getQueryCriterionDto(request.getParameterMap().entrySet().stream()
                    .filter(kv -> OrderV2ResourceUtils.getFilterPropertyName(kv.getKey()) != null)
                    .collect(Collectors.toMap(kv -> kv.getKey(), kv -> kv.getValue())));
            }
            Criterion c = CriterionDto.toSubclass(criterion, getCriterionTypeConverter(), getPropertyTypeResolver(), 
                n -> (OrderV2Metadata.aliasMap.containsKey(n) ? OrderV2Metadata.aliasMap.get(n) : n));
            states = orderV2ApplicationService.get(
                c,
                OrderV2ResourceUtils.getQuerySorts(request.getParameterMap()),
                firstResult, maxResults);

            OrderV2StateDto.DtoConverter dtoConverter = new OrderV2StateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            return dtoConverter.toOrderV2StateDtoArray(states);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 查询.
     * 分页查询 OrderV2s
     */
    @GetMapping("_page")
    public Page<OrderV2StateDto> getPage( HttpServletRequest request,
                    @RequestParam(value = "fields", required = false) String fields,
                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                    @RequestParam(value = "size", defaultValue = "20") Integer size,
                    @RequestParam(value = "filter", required = false) String filter) {
        try {
            Integer firstResult = (page == null ? 0 : page) * (size == null ? 20 : size);
            Integer maxResults = (size == null ? 20 : size);
            Iterable<OrderV2State> states = null; 
            CriterionDto criterion = null;
            if (!StringHelper.isNullOrEmpty(filter)) {
                criterion = JSON.parseObject(filter, CriterionDto.class);
            } else {
                criterion = QueryParamUtils.getQueryCriterionDto(request.getParameterMap().entrySet().stream()
                    .filter(kv -> OrderV2ResourceUtils.getFilterPropertyName(kv.getKey()) != null)
                    .collect(Collectors.toMap(kv -> kv.getKey(), kv -> kv.getValue())));
            }
            Criterion c = CriterionDto.toSubclass(criterion, getCriterionTypeConverter(), getPropertyTypeResolver(), 
                n -> (OrderV2Metadata.aliasMap.containsKey(n) ? OrderV2Metadata.aliasMap.get(n) : n));
            states = orderV2ApplicationService.get(
                c,
                OrderV2ResourceUtils.getQuerySorts(request.getParameterMap()),
                firstResult, maxResults);
            long count = orderV2ApplicationService.getCount(c);

            OrderV2StateDto.DtoConverter dtoConverter = new OrderV2StateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            Page.PageImpl<OrderV2StateDto> statePage =  new Page.PageImpl<>(dtoConverter.toOrderV2StateDtoList(states), count);
            statePage.setSize(size);
            statePage.setNumber(page);
            return statePage;

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 查看.
     * 通过 Id 获取单个 OrderV2
     */
    @GetMapping("{orderId}")
    public OrderV2StateDto get(@PathVariable("orderId") String orderId, @RequestParam(value = "fields", required = false) String fields) {
        try {
            String idObj = orderId;
            OrderV2State state = orderV2ApplicationService.get(idObj);
            if (state == null) { return null; }

            OrderV2StateDto.DtoConverter dtoConverter = new OrderV2StateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            return dtoConverter.toOrderV2StateDto(state);

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
                n -> (OrderV2Metadata.aliasMap.containsKey(n) ? OrderV2Metadata.aliasMap.get(n) : n));
            count = orderV2ApplicationService.getCount(c);
            return count;

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    /**
     * 新建.
     * 新建 OrderV2
     */
    @PostMapping @ResponseBody @ResponseStatus(HttpStatus.CREATED)
    public String post(@RequestBody CreateOrMergePatchOrderV2Dto.CreateOrderV2Dto value,  HttpServletResponse response) {
        try {
            OrderV2Command.CreateOrderV2 cmd = value;//.toCreateOrderV2();
            if (cmd.getOrderId() == null) {
                throw DomainError.named("nullId", "Aggregate Id in cmd is null, aggregate name: %1$s.", "OrderV2");
            }
            String idObj = cmd.getOrderId();
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            orderV2ApplicationService.when(cmd);

            return idObj;
        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    /**
     * 创建 or 修改.
     * 创建 or 修改 OrderV2
     */
    @PutMapping("{orderId}")
    public void put(@PathVariable("orderId") String orderId, @RequestBody CreateOrMergePatchOrderV2Dto value) {
        try {
            if (value.getVersion() != null) {
                value.setCommandType(Command.COMMAND_TYPE_MERGE_PATCH);
                OrderV2Command.MergePatchOrderV2 cmd = (OrderV2Command.MergePatchOrderV2) value.toSubclass();
                OrderV2ResourceUtils.setNullIdOrThrowOnInconsistentIds(orderId, cmd);
                cmd.setRequesterId(SecurityContextUtil.getRequesterId());
                orderV2ApplicationService.when(cmd);
                return;
            }

            value.setCommandType(Command.COMMAND_TYPE_CREATE);
            OrderV2Command.CreateOrderV2 cmd = (OrderV2Command.CreateOrderV2) value.toSubclass();
            OrderV2ResourceUtils.setNullIdOrThrowOnInconsistentIds(orderId, cmd);
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            orderV2ApplicationService.when(cmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    /**
     * 修改.
     * 修改 OrderV2
     */
    @PatchMapping("{orderId}")
    public void patch(@PathVariable("orderId") String orderId, @RequestBody CreateOrMergePatchOrderV2Dto.MergePatchOrderV2Dto value) {
        try {

            OrderV2Command.MergePatchOrderV2 cmd = value;//.toMergePatchOrderV2();
            OrderV2ResourceUtils.setNullIdOrThrowOnInconsistentIds(orderId, cmd);
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            orderV2ApplicationService.when(cmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 删除.
     * 删除 OrderV2
     */
    @DeleteMapping("{orderId}")
    public void delete(@PathVariable("orderId") String orderId,
                       @NotNull @RequestParam(value = "commandId", required = false) String commandId,
                       @NotNull @RequestParam(value = "version", required = false) @Min(value = -1) Long version,
                       @RequestParam(value = "requesterId", required = false) String requesterId) {
        try {

            OrderV2Command.DeleteOrderV2 deleteCmd = new DeleteOrderV2Dto();

            deleteCmd.setCommandId(commandId);
            deleteCmd.setRequesterId(requesterId);
            deleteCmd.setVersion(version);
            OrderV2ResourceUtils.setNullIdOrThrowOnInconsistentIds(orderId, deleteCmd);
            deleteCmd.setRequesterId(SecurityContextUtil.getRequesterId());
            orderV2ApplicationService.when(deleteCmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    @PutMapping("{orderId}/_commands/RemoveItem")
    public void removeItem(@PathVariable("orderId") String orderId, @RequestBody OrderV2Commands.RemoveItem content) {
        try {

            OrderV2Commands.RemoveItem cmd = content;//.toRemoveItem();
            String idObj = orderId;
            if (cmd.getOrderId() == null) {
                cmd.setOrderId(idObj);
            } else if (!cmd.getOrderId().equals(idObj)) {
                throw DomainError.named("inconsistentId", "Argument Id %1$s NOT equals body Id %2$s", orderId, cmd.getOrderId());
            }
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            orderV2ApplicationService.when(cmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    @PutMapping("{orderId}/_commands/UpdateItemQuantity")
    public void updateItemQuantity(@PathVariable("orderId") String orderId, @RequestBody OrderV2Commands.UpdateItemQuantity content) {
        try {

            OrderV2Commands.UpdateItemQuantity cmd = content;//.toUpdateItemQuantity();
            String idObj = orderId;
            if (cmd.getOrderId() == null) {
                cmd.setOrderId(idObj);
            } else if (!cmd.getOrderId().equals(idObj)) {
                throw DomainError.named("inconsistentId", "Argument Id %1$s NOT equals body Id %2$s", orderId, cmd.getOrderId());
            }
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            orderV2ApplicationService.when(cmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    @PutMapping("{orderId}/_commands/UpdateEstimatedShipDate")
    public void updateEstimatedShipDate(@PathVariable("orderId") String orderId, @RequestBody OrderV2Commands.UpdateEstimatedShipDate content) {
        try {

            OrderV2Commands.UpdateEstimatedShipDate cmd = content;//.toUpdateEstimatedShipDate();
            String idObj = orderId;
            if (cmd.getOrderId() == null) {
                cmd.setOrderId(idObj);
            } else if (!cmd.getOrderId().equals(idObj)) {
                throw DomainError.named("inconsistentId", "Argument Id %1$s NOT equals body Id %2$s", orderId, cmd.getOrderId());
            }
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            orderV2ApplicationService.when(cmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    @GetMapping("_metadata/filteringFields")
    public List<PropertyMetadataDto> getMetadataFilteringFields() {
        try {

            List<PropertyMetadataDto> filtering = new ArrayList<>();
            OrderV2Metadata.propertyTypeMap.forEach((key, value) -> {
                filtering.add(new PropertyMetadataDto(key, value, true));
            });
            return filtering;

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    @GetMapping("{orderId}/_events/{version}")
    public OrderV2Event getEvent(@PathVariable("orderId") String orderId, @PathVariable("version") long version) {
        try {

            String idObj = orderId;
            //OrderV2StateEventDtoConverter dtoConverter = getOrderV2StateEventDtoConverter();
            return orderV2ApplicationService.getEvent(idObj, version);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    @GetMapping("{orderId}/_historyStates/{version}")
    public OrderV2StateDto getHistoryState(@PathVariable("orderId") String orderId, @PathVariable("version") long version, @RequestParam(value = "fields", required = false) String fields) {
        try {

            String idObj = orderId;
            OrderV2StateDto.DtoConverter dtoConverter = new OrderV2StateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            return dtoConverter.toOrderV2StateDto(orderV2ApplicationService.getHistoryState(idObj, version));

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 查看.
     * 获取指定 ProductId 的 OrderV2Item
     */
    @GetMapping("{orderId}/OrderV2Items/{productId}")
    public OrderV2ItemStateDto getOrderV2Item(@PathVariable("orderId") String orderId, @PathVariable("productId") String productId) {
        try {

            OrderV2ItemState state = orderV2ApplicationService.getOrderV2Item(orderId, productId);
            if (state == null) { return null; }
            OrderV2ItemStateDto.DtoConverter dtoConverter = new OrderV2ItemStateDto.DtoConverter();
            OrderV2ItemStateDto stateDto = dtoConverter.toOrderV2ItemStateDto(state);
            dtoConverter.setAllFieldsReturned(true);
            return stateDto;

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 创建 or 修改.
     * 创建 or 修改 OrderV2Item
     */
    @PutMapping(path = "{orderId}/OrderV2Items/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putOrderV2Item(@PathVariable("orderId") String orderId, @PathVariable("productId") String productId,
                       @RequestParam(value = "commandId", required = false) String commandId,
                       @RequestParam(value = "version", required = false) Long version,
                       @RequestParam(value = "requesterId", required = false) String requesterId,
                       @RequestBody CreateOrMergePatchOrderV2ItemDto.MergePatchOrderV2ItemDto body) {
        try {
            OrderV2Command.MergePatchOrderV2 mergePatchOrderV2 = new CreateOrMergePatchOrderV2Dto.MergePatchOrderV2Dto();
            mergePatchOrderV2.setOrderId(orderId);
            mergePatchOrderV2.setCommandId(commandId != null && !commandId.isEmpty() ? commandId : body.getCommandId());
            if (version != null) { mergePatchOrderV2.setVersion(version); }
            mergePatchOrderV2.setRequesterId(requesterId != null && !requesterId.isEmpty() ? requesterId : body.getRequesterId());
            OrderV2ItemCommand.MergePatchOrderV2Item mergePatchOrderV2Item = body;//.toMergePatchOrderV2Item();
            mergePatchOrderV2Item.setProductId(productId);
            mergePatchOrderV2.getOrderV2ItemCommands().add(mergePatchOrderV2Item);
            mergePatchOrderV2.setRequesterId(SecurityContextUtil.getRequesterId());
            orderV2ApplicationService.when(mergePatchOrderV2);
        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 移除.
     * 移除 OrderV2Item
     */
    @DeleteMapping("{orderId}/OrderV2Items/{productId}")
    public void deleteOrderV2Item(@PathVariable("orderId") String orderId, @PathVariable("productId") String productId,
                       @RequestParam(value = "commandId", required = false) String commandId,
                       @RequestParam(value = "version", required = false) Long version,
                       @RequestParam(value = "requesterId", required = false) String requesterId) {
        try {
            OrderV2Command.MergePatchOrderV2 mergePatchOrderV2 = new CreateOrMergePatchOrderV2Dto.MergePatchOrderV2Dto();
            mergePatchOrderV2.setOrderId(orderId);
            mergePatchOrderV2.setCommandId(commandId);// != null && !commandId.isEmpty() ? commandId : body.getCommandId());
            if (version != null) { 
                mergePatchOrderV2.setVersion(version); 
            } else {
                mergePatchOrderV2.setVersion(orderV2ApplicationService.get(orderId).getVersion());
            }
            mergePatchOrderV2.setRequesterId(requesterId);// != null && !requesterId.isEmpty() ? requesterId : body.getRequesterId());
            OrderV2ItemCommand.RemoveOrderV2Item removeOrderV2Item = new RemoveOrderV2ItemDto();
            removeOrderV2Item.setProductId(productId);
            mergePatchOrderV2.getOrderV2ItemCommands().add(removeOrderV2Item);
            mergePatchOrderV2.setRequesterId(SecurityContextUtil.getRequesterId());
            orderV2ApplicationService.when(mergePatchOrderV2);
        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * OrderV2Item List
     */
    @GetMapping("{orderId}/OrderV2Items")
    public OrderV2ItemStateDto[] getOrderV2Items(@PathVariable("orderId") String orderId,
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
                    .filter(kv -> OrderV2ResourceUtils.getOrderV2ItemFilterPropertyName(kv.getKey()) != null)
                    .collect(Collectors.toMap(kv -> kv.getKey(), kv -> kv.getValue())));
            }
            Criterion c = CriterionDto.toSubclass(criterion, getCriterionTypeConverter(), getPropertyTypeResolver(), 
                n -> (OrderV2ItemMetadata.aliasMap.containsKey(n) ? OrderV2ItemMetadata.aliasMap.get(n) : n));
            Iterable<OrderV2ItemState> states = orderV2ApplicationService.getOrderV2Items(orderId, c,
                    OrderV2ResourceUtils.getOrderV2ItemQuerySorts(request.getParameterMap()));
            if (states == null) { return null; }
            OrderV2ItemStateDto.DtoConverter dtoConverter = new OrderV2ItemStateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            return dtoConverter.toOrderV2ItemStateDtoArray(states);
        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 新建.
     * 新建 OrderV2Item
     */
    @PostMapping(path = "{orderId}/OrderV2Items", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postOrderV2Items(@PathVariable("orderId") String orderId,
                       @RequestParam(value = "commandId", required = false) String commandId,
                       @RequestParam(value = "version", required = false) Long version,
                       @RequestParam(value = "requesterId", required = false) String requesterId,
                       @RequestBody CreateOrMergePatchOrderV2ItemDto.CreateOrderV2ItemDto body) {
        try {
            OrderV2Command.MergePatchOrderV2 mergePatchOrderV2 = new AbstractOrderV2Command.SimpleMergePatchOrderV2();
            mergePatchOrderV2.setOrderId(orderId);
            mergePatchOrderV2.setCommandId(commandId != null && !commandId.isEmpty() ? commandId : body.getCommandId());
            if (version != null) { mergePatchOrderV2.setVersion(version); }
            mergePatchOrderV2.setRequesterId(requesterId != null && !requesterId.isEmpty() ? requesterId : body.getRequesterId());
            OrderV2ItemCommand.CreateOrderV2Item createOrderV2Item = body.toCreateOrderV2Item();
            mergePatchOrderV2.getOrderV2ItemCommands().add(createOrderV2Item);
            mergePatchOrderV2.setRequesterId(SecurityContextUtil.getRequesterId());
            orderV2ApplicationService.when(mergePatchOrderV2);
        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }



    //protected  OrderV2StateEventDtoConverter getOrderV2StateEventDtoConverter() {
    //    return new OrderV2StateEventDtoConverter();
    //}

    protected TypeConverter getCriterionTypeConverter() {
        return new DefaultTypeConverter();
    }

    protected PropertyTypeResolver getPropertyTypeResolver() {
        return new PropertyTypeResolver() {
            @Override
            public Class resolveTypeByPropertyName(String propertyName) {
                return OrderV2ResourceUtils.getFilterPropertyType(propertyName);
            }
        };
    }

    protected PropertyTypeResolver getOrderV2ItemPropertyTypeResolver() {
        return new PropertyTypeResolver() {
            @Override
            public Class resolveTypeByPropertyName(String propertyName) {
                return OrderV2ResourceUtils.getOrderV2ItemFilterPropertyType(propertyName);
            }
        };
    }

    // ////////////////////////////////
 
    public static class OrderV2ResourceUtils {

        public static void setNullIdOrThrowOnInconsistentIds(String orderId, OrderV2Command value) {
            String idObj = orderId;
            if (value.getOrderId() == null) {
                value.setOrderId(idObj);
            } else if (!value.getOrderId().equals(idObj)) {
                throw DomainError.named("inconsistentId", "Argument Id %1$s NOT equals body Id %2$s", orderId, value.getOrderId());
            }
        }
    
        public static List<String> getQueryOrders(String str, String separator) {
            return QueryParamUtils.getQueryOrders(str, separator, OrderV2Metadata.aliasMap);
        }

        public static List<String> getQuerySorts(Map<String, String[]> queryNameValuePairs) {
            String[] values = queryNameValuePairs.get("sort");
            return QueryParamUtils.getQuerySorts(values, OrderV2Metadata.aliasMap);
        }

        public static String getFilterPropertyName(String fieldName) {
            if ("sort".equalsIgnoreCase(fieldName)
                    || "firstResult".equalsIgnoreCase(fieldName)
                    || "maxResults".equalsIgnoreCase(fieldName)
                    || "fields".equalsIgnoreCase(fieldName)) {
                return null;
            }
            if (OrderV2Metadata.aliasMap.containsKey(fieldName)) {
                return OrderV2Metadata.aliasMap.get(fieldName);
            }
            return null;
        }

        public static Class getFilterPropertyType(String propertyName) {
            if (OrderV2Metadata.propertyTypeMap.containsKey(propertyName)) {
                String propertyType = OrderV2Metadata.propertyTypeMap.get(propertyName);
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

        public static List<String> getOrderV2ItemQueryOrders(String str, String separator) {
            return QueryParamUtils.getQueryOrders(str, separator, OrderV2ItemMetadata.aliasMap);
        }

        public static List<String> getOrderV2ItemQuerySorts(Map<String, String[]> queryNameValuePairs) {
            String[] values = queryNameValuePairs.get("sort");
            return QueryParamUtils.getQuerySorts(values, OrderV2ItemMetadata.aliasMap);
        }

        public static String getOrderV2ItemFilterPropertyName(String fieldName) {
            if ("sort".equalsIgnoreCase(fieldName)
                    || "firstResult".equalsIgnoreCase(fieldName)
                    || "maxResults".equalsIgnoreCase(fieldName)
                    || "fields".equalsIgnoreCase(fieldName)) {
                return null;
            }
            if (OrderV2ItemMetadata.aliasMap.containsKey(fieldName)) {
                return OrderV2ItemMetadata.aliasMap.get(fieldName);
            }
            return null;
        }

        public static Class getOrderV2ItemFilterPropertyType(String propertyName) {
            if (OrderV2ItemMetadata.propertyTypeMap.containsKey(propertyName)) {
                String propertyType = OrderV2ItemMetadata.propertyTypeMap.get(propertyName);
                if (!StringHelper.isNullOrEmpty(propertyType)) {
                    if (BoundedContextMetadata.CLASS_MAP.containsKey(propertyType)) {
                        return BoundedContextMetadata.CLASS_MAP.get(propertyType);
                    }
                }
            }
            return String.class;
        }

        public static Iterable<Map.Entry<String, Object>> getOrderV2ItemQueryFilterMap(Map<String, String[]> queryNameValuePairs) {
            Map<String, Object> filter = new HashMap<>();
            queryNameValuePairs.forEach((key, values) -> {
                if (values.length > 0) {
                    String pName = getOrderV2ItemFilterPropertyName(key);
                    if (!StringHelper.isNullOrEmpty(pName)) {
                        Class pClass = getOrderV2ItemFilterPropertyType(pName);
                        filter.put(pName, ApplicationContext.current.getTypeConverter().convertFromString(pClass, values[0]));
                    }
                }
            });
            return filter.entrySet();
        }

        public static OrderV2StateDto[] toOrderV2StateDtoArray(Iterable<String> ids) {
            List<OrderV2StateDto> states = new ArrayList<>();
            ids.forEach(i -> {
                OrderV2StateDto dto = new OrderV2StateDto();
                dto.setOrderId(i);
                states.add(dto);
            });
            return states.toArray(new OrderV2StateDto[0]);
        }

    }

}

