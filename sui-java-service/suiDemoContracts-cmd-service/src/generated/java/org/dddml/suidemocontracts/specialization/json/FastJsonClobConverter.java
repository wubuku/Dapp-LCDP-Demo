//package org.dddml.suidemocontracts.specialization.json;
//
//import org.dddml.suidemocontracts.specialization.ClobConverter;
//
//import java.util.Map;
//
///**
// * Created by yangjiefeng on 2018/5/14.
// */
//public class FastJsonClobConverter implements ClobConverter {
//
//    @Override
//    public String toString(Map<String, Object> lobProperties) {
//        return com.alibaba.fastjson.JSON.toJSONString(lobProperties);
//    }
//
//    @Override
//    public Map<String, Object> parseLobProperties(String text) {
//        Map<String, Object> ps = com.alibaba.fastjson.JSON.parseObject(text, Map.class);
//        return ps;
//    }
//}
