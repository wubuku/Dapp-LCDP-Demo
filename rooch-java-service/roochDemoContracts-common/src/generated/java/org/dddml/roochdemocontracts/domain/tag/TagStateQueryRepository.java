// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.tag;

import java.util.Map;
import java.util.List;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;

public interface TagStateQueryRepository {
    TagState get(String id);

    Iterable<TagState> getAll(Integer firstResult, Integer maxResults);
    
    Iterable<TagState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<TagState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults);

    TagState getFirst(Iterable<Map.Entry<String, Object>> filter, List<String> orders);

    TagState getFirst(Map.Entry<String, Object> keyValue, List<String> orders);

    Iterable<TagState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults);

    long getCount(Iterable<Map.Entry<String, Object>> filter);

    long getCount(Criterion filter);

}

