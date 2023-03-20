package org.dddml.suidemocontracts.specialization;

import org.dddml.support.criterion.CriterionDto;
import org.dddml.support.criterion.StringHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by yangjiefeng on 2018/7/31.
 */
public class QueryParamUtils {

    public static CriterionDto getQueryCriterionDto(Map<String, String[]> queryNameValuePairs) {
        List<CriterionDto> conjunction = new ArrayList<>();
        for (Map.Entry<String, String[]> kv : queryNameValuePairs.entrySet()) {
            String key = kv.getKey().trim();
            String[] values = kv.getValue();
            List<String> eqList = new ArrayList<>();
            Arrays.stream(values).forEach(s -> {
                String v = s.trim();
                if (!v.endsWith(")")) {
                    eqList.add(v);
                } else {
                    List<String> kws = Arrays.asList("eq", "gt", "lt", "ge", "le", "like", "notEq", "ne");
                    String op = null;
                    int idx = v.indexOf("(");
                    if (idx > 0) {
                        op = v.substring(0, idx);
                        String eval = v.substring(idx + 1, v.length() - 1);
                        if (op.equalsIgnoreCase("eq")) {
                            eqList.add(eval);
                        } else if (op.equalsIgnoreCase("notEq")
                                || op.equalsIgnoreCase("ne")) {
                            CriterionDto c = new CriterionDto();
                            c.setType("eq");
                            c.setProperty(key);
                            c.setValue(eval);
                            CriterionDto notCr = new CriterionDto();
                            notCr.setType("not");
                            notCr.setCriterion(c);
                            conjunction.add(notCr);
                        } else if (kws.contains(op)) {
                            CriterionDto c = new CriterionDto();
                            c.setType(op);
                            c.setProperty(key);
                            c.setValue(eval);
                            conjunction.add(c);
                        }
                    } else {
                        eqList.add(v);
                    }
                }
            });
            if (eqList.size() > 0) {
                if (eqList.size() == 1) {
                    CriterionDto c = new CriterionDto();
                    c.setType("eq");
                    c.setProperty(key);
                    c.setValue(eqList.get(0));
                    conjunction.add(c);
                } else {
                    CriterionDto propertyIn = new CriterionDto();
                    propertyIn.setType("in");
                    propertyIn.setProperty(key);
                    propertyIn.setValues(eqList.toArray(new String[0]));
                    conjunction.add(propertyIn);
                }
            }
        }

        CriterionDto criterion = new CriterionDto();
        criterion.setType("conjunction");
        criterion.setCriteria(conjunction.toArray(new CriterionDto[0]));
        return criterion;
    }

    public static List<String> getQueryOrders(String ordersStr, String separator, Map<String, String> nameMap) {
        List<String> orders = new ArrayList<>();
        if (StringHelper.isNullOrEmpty(ordersStr)) {
            return orders;
        }
        String[] splits = ordersStr.split(separator);
        for (String item : splits) {
            if (!StringHelper.isNullOrEmpty(item)) {
                String prefix = "";
                String f = item.trim();
                if (f.startsWith("-")) {
                    f = item.substring(1).trim();
                    prefix = "-";
                }
                if (nameMap != null && nameMap.containsKey(f)) {
                    f = nameMap.get(f);
                }
                orders.add(prefix + f);
            }
        }
        return orders;
    }

    public static List<String> getQuerySorts(Map<String, String[]> queryNameValuePairs, Map<String, String> nameMap) {
        String[] values = queryNameValuePairs.get("sort");
        return getQuerySorts(values, nameMap);
    }

    public static List<String> getQuerySorts(String[] values, Map<String, String> nameMap) {
        List<String> sorts = new ArrayList<>();
        if (values == null) {
            return null;
        }
        if (values.length == 1
                && !values[0].toLowerCase().endsWith(",asc")
                && !values[0].toLowerCase().endsWith(",desc")) {
            return getQueryOrders(values[0], ",", nameMap);
        }
        Arrays.stream(values).forEach(s -> {
            String prefix = "";
            String f = s.trim();
            if (f.toLowerCase().endsWith(",asc")) {
                f = s.substring(0, s.length() - 4).trim();
            } else if (f.toLowerCase().endsWith(",desc")) {
                f = s.substring(0, s.length() - 5).trim();
                prefix = "-";
            }
            if (!f.isEmpty()) {
                if (nameMap != null && nameMap.containsKey(f)) {
                    f = nameMap.get(f);
                }
                sorts.add(prefix + f);
            }
        });
        return sorts;
    }

}
