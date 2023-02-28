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
import org.dddml.suidemocontracts.domain.domainname.*;
import static org.dddml.suidemocontracts.domain.meta.M.*;

import com.alibaba.fastjson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.dddml.support.criterion.TypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping(path = "DomainNames", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class DomainNameResource {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private DomainNameApplicationService domainNameApplicationService;


    /**
     * 查询.
     * 查询 DomainNames
     */
    @GetMapping
    public DomainNameStateDto[] getAll( HttpServletRequest request,
                    @RequestParam(value = "sort", required = false) String sort,
                    @RequestParam(value = "fields", required = false) String fields,
                    @RequestParam(value = "firstResult", defaultValue = "0") Integer firstResult,
                    @RequestParam(value = "maxResults", defaultValue = "2147483647") Integer maxResults,
                    @RequestParam(value = "filter", required = false) String filter) {
        try {
        if (firstResult < 0) { firstResult = 0; }
        if (maxResults == null || maxResults < 1) { maxResults = Integer.MAX_VALUE; }

            Iterable<DomainNameState> states = null; 
            CriterionDto criterion = null;
            if (!StringHelper.isNullOrEmpty(filter)) {
                criterion = JSON.parseObject(filter, CriterionDto.class);
            } else {
                criterion = QueryParamUtils.getQueryCriterionDto(request.getParameterMap().entrySet().stream()
                    .filter(kv -> DomainNameResourceUtils.getFilterPropertyName(kv.getKey()) != null)
                    .collect(Collectors.toMap(kv -> kv.getKey(), kv -> kv.getValue())));
            }
            Criterion c = CriterionDto.toSubclass(criterion, getCriterionTypeConverter(), getPropertyTypeResolver(), 
                n -> (DomainNameMetadata.aliasMap.containsKey(n) ? DomainNameMetadata.aliasMap.get(n) : n));
            states = domainNameApplicationService.get(
                c,
                DomainNameResourceUtils.getQuerySorts(request.getParameterMap()),
                firstResult, maxResults);

            DomainNameStateDto.DtoConverter dtoConverter = new DomainNameStateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            return dtoConverter.toDomainNameStateDtoArray(states);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 查询.
     * 分页查询 DomainNames
     */
    @GetMapping("_page")
    public Page<DomainNameStateDto> getPage( HttpServletRequest request,
                    @RequestParam(value = "fields", required = false) String fields,
                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                    @RequestParam(value = "size", defaultValue = "20") Integer size,
                    @RequestParam(value = "filter", required = false) String filter) {
        try {
            Integer firstResult = (page == null ? 0 : page) * (size == null ? 20 : size);
            Integer maxResults = (size == null ? 20 : size);
            Iterable<DomainNameState> states = null; 
            CriterionDto criterion = null;
            if (!StringHelper.isNullOrEmpty(filter)) {
                criterion = JSON.parseObject(filter, CriterionDto.class);
            } else {
                criterion = QueryParamUtils.getQueryCriterionDto(request.getParameterMap().entrySet().stream()
                    .filter(kv -> DomainNameResourceUtils.getFilterPropertyName(kv.getKey()) != null)
                    .collect(Collectors.toMap(kv -> kv.getKey(), kv -> kv.getValue())));
            }
            Criterion c = CriterionDto.toSubclass(criterion, getCriterionTypeConverter(), getPropertyTypeResolver(), 
                n -> (DomainNameMetadata.aliasMap.containsKey(n) ? DomainNameMetadata.aliasMap.get(n) : n));
            states = domainNameApplicationService.get(
                c,
                DomainNameResourceUtils.getQuerySorts(request.getParameterMap()),
                firstResult, maxResults);
            long count = domainNameApplicationService.getCount(c);

            DomainNameStateDto.DtoConverter dtoConverter = new DomainNameStateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            Page.PageImpl<DomainNameStateDto> statePage =  new Page.PageImpl<>(dtoConverter.toDomainNameStateDtoList(states), count);
            statePage.setSize(size);
            statePage.setNumber(page);
            return statePage;

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 查看.
     * 通过 Id 获取单个 DomainName
     */
    @GetMapping("{domainNameId}")
    public DomainNameStateDto get(@PathVariable("domainNameId") String domainNameId, @RequestParam(value = "fields", required = false) String fields) {
        try {
            DomainNameId idObj = DomainNameResourceUtils.parseIdString(domainNameId);
            DomainNameState state = domainNameApplicationService.get(idObj);
            if (state == null) { return null; }

            DomainNameStateDto.DtoConverter dtoConverter = new DomainNameStateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            return dtoConverter.toDomainNameStateDto(state);

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
                n -> (DomainNameMetadata.aliasMap.containsKey(n) ? DomainNameMetadata.aliasMap.get(n) : n));
            count = domainNameApplicationService.getCount(c);
            return count;

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    /**
     * 新建.
     * 新建 DomainName
     */
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public DomainNameId post(@RequestBody CreateOrMergePatchDomainNameDto.CreateDomainNameDto value,  HttpServletResponse response) {
        try {
            DomainNameCommand.CreateDomainName cmd = value;//.toCreateDomainName();
            if (cmd.getDomainNameId() == null) {
                throw DomainError.named("nullId", "Aggregate Id in cmd is null, aggregate name: %1$s.", "DomainName");
            }
            DomainNameId idObj = cmd.getDomainNameId();
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            domainNameApplicationService.when(cmd);

            return idObj;
        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    /**
     * 创建 or 修改.
     * 创建 or 修改 DomainName
     */
    @PutMapping("{domainNameId}")
    public void put(@PathVariable("domainNameId") String domainNameId, @RequestBody CreateOrMergePatchDomainNameDto value) {
        try {
            if (value.getVersion() != null) {
                value.setCommandType(Command.COMMAND_TYPE_MERGE_PATCH);
                DomainNameCommand.MergePatchDomainName cmd = (DomainNameCommand.MergePatchDomainName) value.toSubclass();
                DomainNameResourceUtils.setNullIdOrThrowOnInconsistentIds(domainNameId, cmd);
                cmd.setRequesterId(SecurityContextUtil.getRequesterId());
                domainNameApplicationService.when(cmd);
                return;
            }

            value.setCommandType(Command.COMMAND_TYPE_CREATE);
            DomainNameCommand.CreateDomainName cmd = (DomainNameCommand.CreateDomainName) value.toSubclass();
            DomainNameResourceUtils.setNullIdOrThrowOnInconsistentIds(domainNameId, cmd);
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            domainNameApplicationService.when(cmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    /**
     * 修改.
     * 修改 DomainName
     */
    @PatchMapping("{domainNameId}")
    public void patch(@PathVariable("domainNameId") String domainNameId, @RequestBody CreateOrMergePatchDomainNameDto.MergePatchDomainNameDto value) {
        try {

            DomainNameCommand.MergePatchDomainName cmd = value;//.toMergePatchDomainName();
            DomainNameResourceUtils.setNullIdOrThrowOnInconsistentIds(domainNameId, cmd);
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            domainNameApplicationService.when(cmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 删除.
     * 删除 DomainName
     */
    @DeleteMapping("{domainNameId}")
    public void delete(@PathVariable("domainNameId") String domainNameId,
                       @NotNull @RequestParam(value = "commandId", required = false) String commandId,
                       @NotNull @RequestParam(value = "version", required = false) @Min(value = -1) Long version,
                       @RequestParam(value = "requesterId", required = false) String requesterId) {
        try {

            DomainNameCommand.DeleteDomainName deleteCmd = new DeleteDomainNameDto();

            deleteCmd.setCommandId(commandId);
            deleteCmd.setRequesterId(requesterId);
            deleteCmd.setVersion(version);
            DomainNameResourceUtils.setNullIdOrThrowOnInconsistentIds(domainNameId, deleteCmd);
            deleteCmd.setRequesterId(SecurityContextUtil.getRequesterId());
            domainNameApplicationService.when(deleteCmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    @PutMapping("{domainNameId}/_commands/Register")
    public void register(@PathVariable("domainNameId") String domainNameId, @RequestBody DomainNameCommands.Register content) {
        try {

            DomainNameCommands.Register cmd = content;//.toRegister();
            DomainNameId idObj = DomainNameResourceUtils.parseIdString(domainNameId);
            if (cmd.getDomainNameId() == null) {
                cmd.setDomainNameId(idObj);
            } else if (!cmd.getDomainNameId().equals(idObj)) {
                throw DomainError.named("inconsistentId", "Argument Id %1$s NOT equals body Id %2$s", domainNameId, cmd.getDomainNameId());
            }
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            domainNameApplicationService.when(cmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    @PutMapping("{domainNameId}/_commands/Renew")
    public void renew(@PathVariable("domainNameId") String domainNameId, @RequestBody DomainNameCommands.Renew content) {
        try {

            DomainNameCommands.Renew cmd = content;//.toRenew();
            DomainNameId idObj = DomainNameResourceUtils.parseIdString(domainNameId);
            if (cmd.getDomainNameId() == null) {
                cmd.setDomainNameId(idObj);
            } else if (!cmd.getDomainNameId().equals(idObj)) {
                throw DomainError.named("inconsistentId", "Argument Id %1$s NOT equals body Id %2$s", domainNameId, cmd.getDomainNameId());
            }
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            domainNameApplicationService.when(cmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    @GetMapping("_metadata/filteringFields")
    public List<PropertyMetadataDto> getMetadataFilteringFields() {
        try {

            List<PropertyMetadataDto> filtering = new ArrayList<>();
            DomainNameMetadata.propertyTypeMap.forEach((key, value) -> {
                filtering.add(new PropertyMetadataDto(key, value, true));
            });
            return filtering;

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    @GetMapping("{domainNameId}/_events/{version}")
    public DomainNameEvent getEvent(@PathVariable("domainNameId") String domainNameId, @PathVariable("version") long version) {
        try {

            DomainNameId idObj = DomainNameResourceUtils.parseIdString(domainNameId);
            //DomainNameStateEventDtoConverter dtoConverter = getDomainNameStateEventDtoConverter();
            return domainNameApplicationService.getEvent(idObj, version);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    @GetMapping("{domainNameId}/_historyStates/{version}")
    public DomainNameStateDto getHistoryState(@PathVariable("domainNameId") String domainNameId, @PathVariable("version") long version, @RequestParam(value = "fields", required = false) String fields) {
        try {

            DomainNameId idObj = DomainNameResourceUtils.parseIdString(domainNameId);
            DomainNameStateDto.DtoConverter dtoConverter = new DomainNameStateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            return dtoConverter.toDomainNameStateDto(domainNameApplicationService.getHistoryState(idObj, version));

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }



    //protected  DomainNameStateEventDtoConverter getDomainNameStateEventDtoConverter() {
    //    return new DomainNameStateEventDtoConverter();
    //}

    protected TypeConverter getCriterionTypeConverter() {
        return new DefaultTypeConverter();
    }

    protected PropertyTypeResolver getPropertyTypeResolver() {
        return new PropertyTypeResolver() {
            @Override
            public Class resolveTypeByPropertyName(String propertyName) {
                return DomainNameResourceUtils.getFilterPropertyType(propertyName);
            }
        };
    }

    // ////////////////////////////////
 
    public static class DomainNameResourceUtils {

        public static DomainNameId parseIdString(String idString) {
            TextFormatter<DomainNameId> formatter = DomainNameMetadata.URL_ID_TEXT_FORMATTER;
            return formatter.parse(idString);
        }

        public static void setNullIdOrThrowOnInconsistentIds(String domainNameId, DomainNameCommand value) {
            DomainNameId idObj = parseIdString(domainNameId);
            if (value.getDomainNameId() == null) {
                value.setDomainNameId(idObj);
            } else if (!value.getDomainNameId().equals(idObj)) {
                throw DomainError.named("inconsistentId", "Argument Id %1$s NOT equals body Id %2$s", domainNameId, value.getDomainNameId());
            }
        }
    
        public static List<String> getQueryOrders(String str, String separator) {
            return QueryParamUtils.getQueryOrders(str, separator, DomainNameMetadata.aliasMap);
        }

        public static List<String> getQuerySorts(Map<String, String[]> queryNameValuePairs) {
            String[] values = queryNameValuePairs.get("sort");
            return QueryParamUtils.getQuerySorts(values, DomainNameMetadata.aliasMap);
        }

        public static String getFilterPropertyName(String fieldName) {
            if ("sort".equalsIgnoreCase(fieldName)
                    || "firstResult".equalsIgnoreCase(fieldName)
                    || "maxResults".equalsIgnoreCase(fieldName)
                    || "fields".equalsIgnoreCase(fieldName)) {
                return null;
            }
            if (DomainNameMetadata.aliasMap.containsKey(fieldName)) {
                return DomainNameMetadata.aliasMap.get(fieldName);
            }
            return null;
        }

        public static Class getFilterPropertyType(String propertyName) {
            if (DomainNameMetadata.propertyTypeMap.containsKey(propertyName)) {
                String propertyType = DomainNameMetadata.propertyTypeMap.get(propertyName);
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

        public static DomainNameStateDto[] toDomainNameStateDtoArray(Iterable<DomainNameId> ids) {
            List<DomainNameStateDto> states = new ArrayList<>();
            ids.forEach(i -> {
                DomainNameStateDto dto = new DomainNameStateDto();
                dto.setDomainNameId(i);
                states.add(dto);
            });
            return states.toArray(new DomainNameStateDto[0]);
        }

    }

}

