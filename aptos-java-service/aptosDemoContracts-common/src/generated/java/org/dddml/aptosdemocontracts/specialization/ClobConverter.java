package org.dddml.aptosdemocontracts.specialization;

import java.util.Map;

/**
 * Created by yangjiefeng on 2018/5/14.
 */
public interface ClobConverter {

    String toString(Map<String, Object> lobProperties);

    Map<String, Object> parseLobProperties(String text);

}
