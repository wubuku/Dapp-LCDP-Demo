package org.dddml.suidemocontracts.tool;

import org.dddml.suidemocontracts.domain.meta.M.BoundedContextMetadata;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@SuppressWarnings({"unchecked", "rawtypes"})
public class XmlEntityDataTool {

    public final static String DEFAULT_XML_DATA_LOCATION_PATTERN = "file:../../data/*.xml";
    //"file:/C:/Users/yangjiefeng/Documents/GitHub/wms/data/*.xml";
    //"classpath*:/data/*.xml";

    private final static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private final static String BOUNDED_CONTEXT_DOMAIN_PACKAGE;
    private final static String XML_ROOT_NODE_NAME = "entity-engine-xml";
    private static DefaultConversionService defaultConversionService = new DefaultConversionService();

    static {
        // /////////////////////////
        BOUNDED_CONTEXT_DOMAIN_PACKAGE = getBoundedContextDomainPackage();

        // /////////////////////////
        defaultConversionService.addConverter(Long.class, Timestamp.class,
                new Converter() {

                    @SuppressWarnings("static-access")
                    @Override
                    public Object convert(Object source) {
                        if (source != null) {
                            return new Timestamp((Long) source);
                        }
                        return null;
                    }
                });

        defaultConversionService.addConverter(Long.class, Date.class,
                new Converter() {

                    @SuppressWarnings("static-access")
                    @Override
                    public Object convert(Object source) {
                        if (source != null) {
                            return new Date((Long) source);
                        }
                        return null;
                    }
                });

    }

    private static String getBoundedContextDomainPackage() {
        String[] thisClassNames = XmlEntityDataTool.class.getName().split("\\.");
        return Arrays.stream(thisClassNames).limit(thisClassNames.length - 2).reduce((x, y) -> x + "." + y)
                .get() + ".domain";
    }

    // only for test.
    public static void _main(String[] args) {

        List<Object> entityInstList = null;
        Map<String, List<Object>> entityInstGroupByEntityName = null;
        try {
            entityInstList = deserializeAll(DEFAULT_XML_DATA_LOCATION_PATTERN);
            entityInstGroupByEntityName = deserializeAllGroupByEntityName(DEFAULT_XML_DATA_LOCATION_PATTERN);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        System.out.println(entityInstList.size());
        for (Object obj : entityInstList) {
            //String jsonString = com.alibaba.fastjson.JSONObject.toJSONString(obj);
            //System.out.println(jsonString);
            System.out.println(obj);
        }
        int count = 0;
        for (Map.Entry<String, List<Object>> kv : entityInstGroupByEntityName.entrySet()) {
            System.out.println(kv.getKey());
            List<Object> objList = kv.getValue();
            count += objList.size();
            System.out.println(objList.size());
        }
        System.out.println(count);
        System.out.println(count == entityInstList.size());
        if (count != entityInstList.size()) {
            throw new RuntimeException("sum(entityInstGroupByEntityName.size()) != entityInstList.size()");
        }
        System.exit(0);
    }

    public static Map<String, List<Object>> deserializeAllGroupByEntityName(String xmlDataLocationPattern) {
        Map<String, List<Object>> entityObjListMap = new HashMap<>();
        deserializeAll(xmlDataLocationPattern, (e, d) -> {
            List<Object> objList = entityObjListMap.getOrDefault(e.getNodeName(), null);
            if (objList == null) {
                objList = new ArrayList<>();
                entityObjListMap.put(e.getNodeName(), objList);
            }
            objList.add(d);
        });
        return entityObjListMap;
    }

    public static List<Object> deserializeAll(String xmlDataLocationPattern) {
        List<Object> objList = new ArrayList<>();
        deserializeAll(xmlDataLocationPattern, (e, d) -> objList.add(d));
        return objList;
    }

    public static void deserializeAll(String xmlDataLocationPattern, BiConsumer<Node, Object> action) {
        Map<String, Map<String, PropertySetter>> propSetterMapCache = new HashMap<>();
        XmlEntityDataTool deserializer = new XmlEntityDataTool();
        List<Object> objList = new ArrayList<>();
        try {
            for (final Resource resource : resourcePatternResolver.getResources(xmlDataLocationPattern)) {
                deserializer.deserialize(propSetterMapCache, resource.getInputStream(), action);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Document parseXmlDocument(InputStream xmlInputStream) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(xmlInputStream);
    }

    private static void setProperty(Object obj, Map<String, PropertySetter> setterMap, String attrName, Object attrVal) throws InvocationTargetException, IllegalAccessException {
        Object attribute = null;
        PropertySetter propertySetter = setterMap.get(attrName);
        Set<String> ignorableNames = Arrays.stream(new String[]{
                "createdDate", "createdBy", "createdAt"
        }).collect(Collectors.toSet());
        if (propertySetter == null) {
            if (ignorableNames.contains(attrName)) {
                return;
            }
            throw new NullPointerException(String.format(
                    "Property setter NOT found. Attribute name: '%1$s', object type: '%2$s' ",
                    attrName, obj.getClass().getName()));
        }
        Class propertyType = propertySetter.getPropertyType();
        propertySetter.invoke(obj, convertAttributeValue(attrVal, propertyType));
    }

    private static Object convertAttributeValue(Object attributeVal, Class<?> type) {
        return defaultConversionService.convert(attributeVal, type);
    }

    private static String getCreatedAtPropertyName(String entityName) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> metadataClazz = getAggregateMetadataClass(entityName);
        Object fieldVal = metadataClazz.getField("PROPERTY_NAME_CREATED_AT").get(null);
        return (String) fieldVal;
    }

    private static String getVersionPropertyName(String entityName) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> metadataClazz = getAggregateMetadataClass(entityName);
        Object fieldVal = metadataClazz.getField("PROPERTY_NAME_VERSION").get(null);
        return (String) fieldVal;
    }

    private static Class<?> getAggregateMetadataClass(String entityName) throws ClassNotFoundException {
        String aggregateName = BoundedContextMetadata.TYPE_NAME_TO_AGGREGATE_NAME_MAP
                .get(entityName);
        String className = String.format("%1$s.meta.M$%2$sMetadata", BOUNDED_CONTEXT_DOMAIN_PACKAGE,
                aggregateName);
        return Class.forName(className);
    }

    private static Map<String, PropertySetter> buildPropertySetterMap(String entityName, Class beanClass) throws IntrospectionException {
        Map<String, PropertySetter> setterMap = new HashMap<>();
        BeanInfo info = Introspector.getBeanInfo(beanClass);
        PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
        for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            setterMap.put(propertyDescriptor.getName(), new PropertySetter() {
                @Override
                public void invoke(Object b, Object pVal) throws InvocationTargetException, IllegalAccessException {
                    propertyDescriptor.getWriteMethod().invoke(b, pVal);
                }

                @Override
                public Class getPropertyType() {
                    return propertyDescriptor.getPropertyType();
                }
            });
            String superEntityName = BoundedContextMetadata.TYPE_NAME_TO_AGGREGATE_NAME_MAP.get(entityName);
            if (propertyDescriptor.getName().equalsIgnoreCase(superEntityName + "Id")) {
                addPropertyPropertySetter(setterMap, propertyDescriptor);
            } else if (propertyDescriptor.getName().equalsIgnoreCase(superEntityName + "EventId")) {
                addPropertyPropertySetter(setterMap, propertyDescriptor);
            }
        }
        return setterMap;
    }

    private static void addPropertyPropertySetter(Map<String, PropertySetter> setterMap, PropertyDescriptor propertyDescriptor) throws IntrospectionException {
        Class propertyType = propertyDescriptor.getPropertyType();
        BeanInfo propertyTypeInfo = Introspector.getBeanInfo(propertyType);
        for (final PropertyDescriptor ppDescriptor : propertyTypeInfo.getPropertyDescriptors()) {
            if (!setterMap.containsKey(ppDescriptor.getName())) {
                setterMap.put(ppDescriptor.getName(), new PropertySetter() {
                    @Override
                    public void invoke(Object b, Object pVal) throws InvocationTargetException, IllegalAccessException {
                        Object pref = propertyDescriptor.getReadMethod().invoke(b);
                        if (pref == null) {
                            //throw new RuntimeException(String.format("The parent proeprty '%1$s' is null.", propertyDescriptor.getName()));
                            try {
                                pref = propertyType.newInstance();
                                propertyDescriptor.getWriteMethod().invoke(b, pref);
                            } catch (InstantiationException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (ppDescriptor.getWriteMethod() == null) {
                            throw new RuntimeException(String.format("CANNOT get WriteMethod for proeprty '%1$s'.", ppDescriptor.getName()));
                        }
                        ppDescriptor.getWriteMethod().invoke(pref, pVal);
                    }

                    @Override
                    public Class getPropertyType() {
                        return ppDescriptor.getPropertyType();
                    }
                });
            }
        }
    }

    public void deserialize(InputStream xmlInputStream, BiConsumer<Node, Object> action) {
        Map<String, Map<String, PropertySetter>> propSetterMapCache = new HashMap<>();
        deserialize(propSetterMapCache, xmlInputStream, action);
    }

    public void deserialize(Map<String, Map<String, PropertySetter>> propSetterMapCache, InputStream xmlInputStream, BiConsumer<Node, Object> action) {
        try {
            Document doc = parseXmlDocument(xmlInputStream);
            Element docElement = doc.getDocumentElement();
            if (!XML_ROOT_NODE_NAME.equals(docElement.getNodeName())) {
                return;
            }
            NodeList childNodes = docElement.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node entityDataNode = childNodes.item(i);
                if (!entityDataNode.hasAttributes()) {
                    continue;
                }
                Map<String, Object> attrMap = new HashMap<>();

                String entityName = entityDataNode.getNodeName();
                NamedNodeMap entityDataAttributes = entityDataNode.getAttributes();
                for (int j = 0; j < entityDataAttributes.getLength(); j++) {
                    Node item = entityDataAttributes.item(j);
                    String attributeName = item.getNodeName();
                    String nodeValue = item.getNodeValue();
                    attrMap.put(attributeName, nodeValue);
                }
                action.accept(entityDataNode, convertEntityData(propSetterMapCache, entityName, attrMap));
            }
        } catch (ParserConfigurationException | IOException | SAXException |
                 IllegalAccessException | IntrospectionException | InstantiationException | NoSuchFieldException |
                 InvocationTargetException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected Object convertEntityData(Map<String, Map<String, PropertySetter>> propSetterMapCache,
                                       String entityName, Map<String, Object> attrMap) throws
            ClassNotFoundException, IntrospectionException, IllegalAccessException,
            InstantiationException, NoSuchFieldException, InvocationTargetException {
        Class<?> beanClass = getEntityClass(entityName);
        //BeanInfo info = Introspector.getBeanInfo(beanClass);

        String createdAtPropName = getCreatedAtPropertyName(entityName);
        if (!attrMap.containsKey(createdAtPropName)) {
            attrMap.put(createdAtPropName, Long.valueOf(System.currentTimeMillis()));
        }

        if (autoSetVersionProperty()) {
            String versionPropName = getVersionPropertyName(entityName);
            if (!attrMap.containsKey(versionPropName)) {
                attrMap.put(versionPropName, -1L);
            }
        }

        Object beanInst = beanClass.newInstance();

        Map<String, PropertySetter> setterMap = propSetterMapCache.getOrDefault(entityName, null);
        if (setterMap == null) {
            setterMap = buildPropertySetterMap(entityName, beanClass);
            propSetterMapCache.put(entityName, setterMap);
        }
        for (Map.Entry<String, Object> kv : attrMap.entrySet()) {
            setProperty(beanInst, setterMap, kv.getKey(), kv.getValue());
        }

        return beanInst;
    }

    protected boolean autoSetVersionProperty() {
        return true;
    }

    protected Class<?> getEntityClass(String entityName) throws ClassNotFoundException {
        String[] names = getEntityClassNames(entityName);
        Class<?> entityClass = null;
        for (String n : names) {
            try {
                entityClass = Class.forName(n);
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
            }
            if (entityClass != null) {
                break;
            }
        }
        return entityClass;
    }

    protected String[] getEntityClassNames(String entityName) {
        String packageClassPath = BoundedContextMetadata.TYPE_NAME_TO_AGGREGATE_NAME_MAP
                .get(entityName).toLowerCase();
        ArrayList<String> names = new ArrayList<>();
        names.add(String.format(
                "%1$s.%2$s.Abstract%3$sStateEvent$Simple%4$sStateCreated",
                BOUNDED_CONTEXT_DOMAIN_PACKAGE, packageClassPath, entityName, entityName)
        );
        names.add(String.format(
                "%1$s.%2$s.CreateOrMergePatch%3$sDto$Create%4$sDto",
                BOUNDED_CONTEXT_DOMAIN_PACKAGE, packageClassPath, entityName, entityName)
        );
        return names.toArray(new String[0]);
    }

    interface PropertySetter {
        void invoke(Object beanObj, Object propertyVal) throws InvocationTargetException, IllegalAccessException;

        Class getPropertyType();
    }

    //	private static String toCamelCase(String s) {
    //		if (Character.isLowerCase(s.charAt(0)))
    //			return s;
    //		else
    //			return (new StringBuilder())
    //					.append(Character.toLowerCase(s.charAt(0)))
    //					.append(s.substring(1)).toString();
    //	}

}