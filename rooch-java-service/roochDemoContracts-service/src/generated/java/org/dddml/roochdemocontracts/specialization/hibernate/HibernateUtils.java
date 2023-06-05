package org.dddml.roochdemocontracts.specialization.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import java.util.List;
import java.util.Map;

public class HibernateUtils {

    private HibernateUtils() {
    }

    public static void criteriaAddFilterAndOrdersAndSetFirstResultAndMaxResults(Criteria criteria, Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults) {
        if (filter != null) {
            criteriaAddFilter(criteria, filter);
        }
        criteriaAddOrdersAndSetFirstResultAndMaxResults(criteria, orders, firstResult, maxResults);
    }

    public static void criteriaAddFilter(Criteria criteria, Iterable<Map.Entry<String, Object>> filter) {
        for (Map.Entry<String, Object> p : filter) {
            criteriaAddFilterPair(criteria, p);
        }
    }

    public static void criteriaAddFilterPair(Criteria criteria, Map.Entry<String, Object> filterPair) {
        if (filterPair.getValue() == null) {
            criteria.add(org.hibernate.criterion.Restrictions.isNull(filterPair.getKey()));
        } else {
            criteria.add(org.hibernate.criterion.Restrictions.eq(filterPair.getKey(), filterPair.getValue()));
        }
    }

    public static void criteriaAddOrders(Criteria criteria, List<String> orders) {
        for (String order : orders) {
            boolean isDesc = order.startsWith("-");
            String pName = isDesc ? order.substring(1) : order;
            criteria.addOrder(isDesc ? Order.desc(pName) : Order.asc(pName));
        }
    }

    public static void disjunctionAddCriterion(org.hibernate.criterion.Disjunction disjunction, String propertyName, Object propertyValue) {
        if (propertyValue == null) {
            disjunction.add(org.hibernate.criterion.Restrictions.isNull(propertyName));
        } else {
            disjunction.add(org.hibernate.criterion.Restrictions.eq(propertyName, propertyValue));
        }
    }

    public static void criteriaAddCriterion(Criteria criteria, String propertyName, Object propertyValue) {
        if (propertyValue == null) {
            criteria.add(org.hibernate.criterion.Restrictions.isNull(propertyName));
        } else {
            criteria.add(org.hibernate.criterion.Restrictions.eq(propertyName, propertyValue));
        }
    }

    public static void criteriaAddFilterAndOrdersAndSetFirstResultAndMaxResults(Criteria criteria, org.dddml.support.criterion.Criterion filter, List<String> orders, Integer firstResult, Integer maxResults) {
        criteriaAddFilterAndSetFirstResultAndMaxResults(criteria, filter, firstResult, maxResults);
        if (orders != null) {
            criteriaAddOrders(criteria, orders);
        }
    }

    public static void criteriaAddOrdersAndSetFirstResultAndMaxResults(Criteria criteria, List<String> orders, Integer firstResult, Integer maxResults) {
        if (orders != null) {
            criteriaAddOrders(criteria, orders);
        }

        if (firstResult != null) {
            criteria.setFirstResult(firstResult);
        }
        if (maxResults != null) {
            criteria.setMaxResults(maxResults);
        }
    }

    public static void criteriaAddFilterAndSetFirstResultAndMaxResults(Criteria criteria, org.dddml.support.criterion.Criterion filter, Integer firstResult, Integer maxResults) {
        if (filter != null) {
            org.hibernate.criterion.Criterion hc = CriterionUtils.toHibernateCriterion(filter);
            criteria.add(hc);
        }
        if (firstResult != null) {
            criteria.setFirstResult(firstResult);
        }
        if (maxResults != null) {
            criteria.setMaxResults(maxResults);
        }
    }

}
