package org.dddml.suidemocontracts.restful.resource;

import java.util.*;
import java.util.stream.*;
import javax.servlet.http.*;
import javax.validation.constraints.*;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.dddml.support.criterion.*;
import org.dddml.suidemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.daysummary.*;
import static org.dddml.suidemocontracts.domain.meta.M.*;

import com.alibaba.fastjson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.dddml.support.criterion.TypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping(path = "DaySummaries", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class DaySummaryResource {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private DaySummaryApplicationService daySummaryApplicationService;


    /**
     * 查询.
     * 查询 DaySummaries
     */
    @GetMapping
    public DaySummaryStateDto[] getAll( HttpServletRequest request,
                    @RequestParam(value = "sort", required = false) String sort,
                    @RequestParam(value = "fields", required = false) String fields,
                    @RequestParam(value = "firstResult", defaultValue = "0") Integer firstResult,
                    @RequestParam(value = "maxResults", defaultValue = "2147483647") Integer maxResults,
                    @RequestParam(value = "filter", required = false) String filter) {
        try {
        if (firstResult < 0) { firstResult = 0; }
        if (maxResults == null || maxResults < 1) { maxResults = Integer.MAX_VALUE; }

            Iterable<DaySummaryState> states = null; 
            CriterionDto criterion = null;
            if (!StringHelper.isNullOrEmpty(filter)) {
                criterion = JSON.parseObject(filter, CriterionDto.class);
            } else {
                criterion = QueryParamUtils.getQueryCriterionDto(request.getParameterMap().entrySet().stream()
                    .filter(kv -> DaySummaryResourceUtils.getFilterPropertyName(kv.getKey()) != null)
                    .collect(Collectors.toMap(kv -> kv.getKey(), kv -> kv.getValue())));
            }
            Criterion c = CriterionDto.toSubclass(criterion, getCriterionTypeConverter(), getPropertyTypeResolver(), 
                n -> (DaySummaryMetadata.aliasMap.containsKey(n) ? DaySummaryMetadata.aliasMap.get(n) : n));
            states = daySummaryApplicationService.get(
                c,
                DaySummaryResourceUtils.getQuerySorts(request.getParameterMap()),
                firstResult, maxResults);

            DaySummaryStateDto.DtoConverter dtoConverter = new DaySummaryStateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            return dtoConverter.toDaySummaryStateDtoArray(states);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 查询.
     * 分页查询 DaySummaries
     */
    @GetMapping("_page")
    public Page<DaySummaryStateDto> getPage( HttpServletRequest request,
                    @RequestParam(value = "fields", required = false) String fields,
                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                    @RequestParam(value = "size", defaultValue = "20") Integer size,
                    @RequestParam(value = "filter", required = false) String filter) {
        try {
            Integer firstResult = (page == null ? 0 : page) * (size == null ? 20 : size);
            Integer maxResults = (size == null ? 20 : size);
            Iterable<DaySummaryState> states = null; 
            CriterionDto criterion = null;
            if (!StringHelper.isNullOrEmpty(filter)) {
                criterion = JSON.parseObject(filter, CriterionDto.class);
            } else {
                criterion = QueryParamUtils.getQueryCriterionDto(request.getParameterMap().entrySet().stream()
                    .filter(kv -> DaySummaryResourceUtils.getFilterPropertyName(kv.getKey()) != null)
                    .collect(Collectors.toMap(kv -> kv.getKey(), kv -> kv.getValue())));
            }
            Criterion c = CriterionDto.toSubclass(criterion, getCriterionTypeConverter(), getPropertyTypeResolver(), 
                n -> (DaySummaryMetadata.aliasMap.containsKey(n) ? DaySummaryMetadata.aliasMap.get(n) : n));
            states = daySummaryApplicationService.get(
                c,
                DaySummaryResourceUtils.getQuerySorts(request.getParameterMap()),
                firstResult, maxResults);
            long count = daySummaryApplicationService.getCount(c);

            DaySummaryStateDto.DtoConverter dtoConverter = new DaySummaryStateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            Page.PageImpl<DaySummaryStateDto> statePage =  new Page.PageImpl<>(dtoConverter.toDaySummaryStateDtoList(states), count);
            statePage.setSize(size);
            statePage.setNumber(page);
            return statePage;

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    /**
     * 查看.
     * 通过 Id 获取单个 DaySummary
     */
    @GetMapping("{day}")
    public DaySummaryStateDto get(@PathVariable("day") String day, @RequestParam(value = "fields", required = false) String fields) {
        try {
            Day idObj = DaySummaryResourceUtils.parseIdString(day);
            DaySummaryState state = daySummaryApplicationService.get(idObj);
            if (state == null) { return null; }

            DaySummaryStateDto.DtoConverter dtoConverter = new DaySummaryStateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            return dtoConverter.toDaySummaryStateDto(state);

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
                n -> (DaySummaryMetadata.aliasMap.containsKey(n) ? DaySummaryMetadata.aliasMap.get(n) : n));
            count = daySummaryApplicationService.getCount(c);
            return count;

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }


    @PutMapping("{day}/_commands/Create")
    public void create(@PathVariable("day") String day, @RequestBody DaySummaryCommands.Create content) {
        try {

            DaySummaryCommands.Create cmd = content;//.toCreate();
            Day idObj = DaySummaryResourceUtils.parseIdString(day);
            if (cmd.getDay() == null) {
                cmd.setDay(idObj);
            } else if (!cmd.getDay().equals(idObj)) {
                throw DomainError.named("inconsistentId", "Argument Id %1$s NOT equals body Id %2$s", day, cmd.getDay());
            }
            cmd.setRequesterId(SecurityContextUtil.getRequesterId());
            daySummaryApplicationService.when(cmd);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    @GetMapping("_metadata/filteringFields")
    public List<PropertyMetadataDto> getMetadataFilteringFields() {
        try {

            List<PropertyMetadataDto> filtering = new ArrayList<>();
            DaySummaryMetadata.propertyTypeMap.forEach((key, value) -> {
                filtering.add(new PropertyMetadataDto(key, value, true));
            });
            return filtering;

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    @GetMapping("{day}/_events/{version}")
    public DaySummaryEvent getEvent(@PathVariable("day") String day, @PathVariable("version") long version) {
        try {

            Day idObj = DaySummaryResourceUtils.parseIdString(day);
            //DaySummaryStateEventDtoConverter dtoConverter = getDaySummaryStateEventDtoConverter();
            return daySummaryApplicationService.getEvent(idObj, version);

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }

    @GetMapping("{day}/_historyStates/{version}")
    public DaySummaryStateDto getHistoryState(@PathVariable("day") String day, @PathVariable("version") long version, @RequestParam(value = "fields", required = false) String fields) {
        try {

            Day idObj = DaySummaryResourceUtils.parseIdString(day);
            DaySummaryStateDto.DtoConverter dtoConverter = new DaySummaryStateDto.DtoConverter();
            if (StringHelper.isNullOrEmpty(fields)) {
                dtoConverter.setAllFieldsReturned(true);
            } else {
                dtoConverter.setReturnedFieldsString(fields);
            }
            return dtoConverter.toDaySummaryStateDto(daySummaryApplicationService.getHistoryState(idObj, version));

        } catch (Exception ex) { logger.info(ex.getMessage(), ex); throw DomainErrorUtils.convertException(ex); }
    }



    //protected  DaySummaryStateEventDtoConverter getDaySummaryStateEventDtoConverter() {
    //    return new DaySummaryStateEventDtoConverter();
    //}

    protected TypeConverter getCriterionTypeConverter() {
        return new DefaultTypeConverter();
    }

    protected PropertyTypeResolver getPropertyTypeResolver() {
        return new PropertyTypeResolver() {
            @Override
            public Class resolveTypeByPropertyName(String propertyName) {
                return DaySummaryResourceUtils.getFilterPropertyType(propertyName);
            }
        };
    }

    // ////////////////////////////////
 
    public static class DaySummaryResourceUtils {

        public static Day parseIdString(String idString) {
            TextFormatter<Day> formatter = DaySummaryMetadata.URL_ID_TEXT_FORMATTER;
            return formatter.parse(idString);
        }

        public static void setNullIdOrThrowOnInconsistentIds(String day, DaySummaryCommand value) {
            Day idObj = parseIdString(day);
            if (value.getDay() == null) {
                value.setDay(idObj);
            } else if (!value.getDay().equals(idObj)) {
                throw DomainError.named("inconsistentId", "Argument Id %1$s NOT equals body Id %2$s", day, value.getDay());
            }
        }
    
        public static List<String> getQueryOrders(String str, String separator) {
            return QueryParamUtils.getQueryOrders(str, separator, DaySummaryMetadata.aliasMap);
        }

        public static List<String> getQuerySorts(Map<String, String[]> queryNameValuePairs) {
            String[] values = queryNameValuePairs.get("sort");
            return QueryParamUtils.getQuerySorts(values, DaySummaryMetadata.aliasMap);
        }

        public static String getFilterPropertyName(String fieldName) {
            if ("sort".equalsIgnoreCase(fieldName)
                    || "firstResult".equalsIgnoreCase(fieldName)
                    || "maxResults".equalsIgnoreCase(fieldName)
                    || "fields".equalsIgnoreCase(fieldName)) {
                return null;
            }
            if (DaySummaryMetadata.aliasMap.containsKey(fieldName)) {
                return DaySummaryMetadata.aliasMap.get(fieldName);
            }
            return null;
        }

        public static Class getFilterPropertyType(String propertyName) {
            if (DaySummaryMetadata.propertyTypeMap.containsKey(propertyName)) {
                String propertyType = DaySummaryMetadata.propertyTypeMap.get(propertyName);
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

        public static DaySummaryStateDto[] toDaySummaryStateDtoArray(Iterable<Day> ids) {
            List<DaySummaryStateDto> states = new ArrayList<>();
            ids.forEach(i -> {
                DaySummaryStateDto dto = new DaySummaryStateDto();
                dto.setDay(i);
                states.add(dto);
            });
            return states.toArray(new DaySummaryStateDto[0]);
        }

    }

}

