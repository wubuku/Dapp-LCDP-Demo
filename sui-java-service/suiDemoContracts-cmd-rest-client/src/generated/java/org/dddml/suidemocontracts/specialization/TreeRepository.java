package org.dddml.suidemocontracts.specialization;

import org.dddml.support.criterion.Criterion;

public interface TreeRepository<TNode, TId> {
    Iterable<TNode> getRoots(Criterion filter, java.util.List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<TNode> getChildren(TId parentId, Criterion filter, java.util.List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<TId> getRootIds(Criterion filter, java.util.List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<TId> getChildIds(TId parentId, Criterion filter, java.util.List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<TNode> getRoots(Iterable<java.util.Map.Entry<String, Object>> filter, java.util.List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<TNode> getChildren(TId parentId, Iterable<java.util.Map.Entry<String, Object>> filter, java.util.List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<TId> getRootIds(Iterable<java.util.Map.Entry<String, Object>> filter, java.util.List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<TId> getChildIds(TId parentId, Iterable<java.util.Map.Entry<String, Object>> filter, java.util.List<String> orders, Integer firstResult, Integer maxResults);

}