package org.dddml.suidemocontracts.specialization;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStateDtoConverter {

    private String returnedFieldsString;

    private Map<String, String> returnedFields;

    protected Map<String, String> getReturnedFields() {
        if (returnedFields == null) {
            return new HashMap<String, String>();
        }
        return returnedFields;
    }

    private boolean allFieldsReturned;

    public boolean getAllFieldsReturned() {
        return allFieldsReturned;
    }

    public void setAllFieldsReturned(boolean value) {
        allFieldsReturned = value;
    }

    private Map<String, String> returnedFieldsExcludingCollection;

    protected Map<String, String> getReturnedFieldsExcludingCollection() {
        if (returnedFieldsExcludingCollection != null) {
            return returnedFieldsExcludingCollection;
        }
        HashMap<String, String> fsMap = new HashMap<String, String>();
        for (Map.Entry<String, String> p : getReturnedFields().entrySet()) {
            if (!isCollectionField(p.getKey())) {
                fsMap.put(p.getKey(), p.getValue());
            }
        }
        returnedFieldsExcludingCollection = fsMap;
        return returnedFieldsExcludingCollection;
    }

    protected String getReturnedFieldsString() {
        return returnedFieldsString;
    }

    public void setReturnedFieldsString(String value) {
        returnedFieldsString = value;
        HashMap<String, String> fsMap = new HashMap<String, String>();
        if (returnedFieldsString != null) {
            String[] fs = returnedFieldsString.split(getReturnedFieldsStringSeparator());
            for (String f : fs) {
                if (f != null && !f.trim().isEmpty()) {
                    String f1 = f.trim();
                    int i = f.indexOf('.');
                    String k = f1;
                    String v = null;
                    if (i >= 0) {
                        k = f1.substring(0, i);
                        v = f1.substring(i + 1);
                    }
                    if (fsMap.containsKey(k)) {
                        String oldVal = fsMap.get(k);
                        if (oldVal != null && !oldVal.trim().isEmpty()) {
                            fsMap.put(k, oldVal + getReturnedFieldsStringSeparator() + v);
                        } else {
                            fsMap.put(k, v);
                        }
                    } else {
                        fsMap.put(k, v);
                    }
                }
            }
        }
        returnedFields = fsMap;
        returnedFieldsExcludingCollection = null;
    }

    public boolean returnedFieldsContains(String fieldName) {
        if (getAllFieldsReturned()) {
            return true;
        }
        //对于集合类型的属性，需要显式地要求返回
        if (isCollectionField(fieldName)) {
            return CollectionUtils.mapContainsKeyIgnoringCase(getReturnedFields(), fieldName);
        }
        //对于集合类型之外的属性，缺省就是都返回的
        Map<String, String> returnedFieldsExcludingCollection = getReturnedFieldsExcludingCollection();
        if (returnedFieldsExcludingCollection.size() <= 0) {
            return true;
        }
        return CollectionUtils.mapContainsKeyIgnoringCase(returnedFieldsExcludingCollection, fieldName);
    }

    protected String getReturnedFieldsStringSeparator() {
        return ",";
    }

    protected abstract boolean isCollectionField(String fieldName);

}
