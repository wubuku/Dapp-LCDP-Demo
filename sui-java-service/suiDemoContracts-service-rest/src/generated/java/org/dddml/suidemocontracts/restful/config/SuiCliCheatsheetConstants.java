// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.restful.config;

import org.dddml.suidemocontracts.sui.contract.ContractConstants;

import java.util.*;

public class SuiCliCheatsheetConstants {
    public static final String PACKAGE_ID_ARGUMENT_HINT = "_PACKAGE_ID_";

    public static Map<String, String> ID_GENERATOR_OBJECT_NAME_TO_ARGUMENT_HINT_MAP;

    static {
        Map<String, String> map = new HashMap<>();
        map.put(ContractConstants.DOMAIN_NAME_MODULE_DOMAIN_NAME_ID_TABLE, "_DOMAIN_NAME_DOMAIN_NAME_ID_TABLE_OBJECT_ID_");
        map.put(ContractConstants.PRODUCT_MODULE_PRODUCT_ID_GENERATOR, "_PRODUCT_PRODUCT_ID_GENERATOR_OBJECT_ID_");
        map.put(ContractConstants.ORDER_V2_MODULE_ORDER_ID_TABLE, "_ORDER_V2_ORDER_ID_TABLE_OBJECT_ID_");
        map.put(ContractConstants.DAY_SUMMARY_MODULE_DAY_SUMMARY_ID_TABLE, "_DAY_SUMMARY_DAY_SUMMARY_ID_TABLE_OBJECT_ID_");
        ID_GENERATOR_OBJECT_NAME_TO_ARGUMENT_HINT_MAP = map;
    }

    private SuiCliCheatsheetConstants() {
    }
}
