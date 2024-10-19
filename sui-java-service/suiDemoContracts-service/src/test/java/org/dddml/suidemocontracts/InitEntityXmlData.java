package org.dddml.suidemocontracts;

import org.dddml.suidemocontracts.specialization.ApplicationContext;
import org.dddml.suidemocontracts.specialization.DomainError;
import org.dddml.suidemocontracts.specialization.spring.SpringApplicationContext;
import org.dddml.suidemocontracts.tool.ApplicationServiceReflectUtils;
import org.dddml.suidemocontracts.tool.XmlEntityDataTool;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * Created by yangjiefeng on 2018/1/30.
 */
public class InitEntityXmlData {


    public static void _main(final String[] args) throws Exception {
        //TODO: Create a simple CLI tool to support DDDML Builder?
        //static
        org.springframework.context.ApplicationContext springFrameworkApplicationContext;
        //static {
            springFrameworkApplicationContext = new ClassPathXmlApplicationContext(
                    "config/SpringConfigs.xml",
                    "config/TestDataSourceConfig.xml"
            );
        //}
        ApplicationContext.current = new SpringApplicationContext(springFrameworkApplicationContext);
        String xmlDataLocationPattern = null;
        if (args != null && args.length > 0) {
            xmlDataLocationPattern = args[0];
        }
        InitEntityXmlData.createEntitiesFromXmlData(xmlDataLocationPattern);
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Initialize data using XML files in the data directory
    // ///////////////////////////////////////////////////////////////////////////
    public static void createEntitiesFromXmlData(String xmlDataLocationPattern) {
        String pathPattern = XmlEntityDataTool.DEFAULT_XML_DATA_LOCATION_PATTERN;
        if (xmlDataLocationPattern != null) {
            pathPattern = xmlDataLocationPattern;
        }
        Map<String, List<Object>> allData = XmlEntityDataTool.deserializeAllGroupByEntityName(pathPattern);
        for (Map.Entry<String, List<Object>> kv : allData.entrySet()) {
            for (Object e : kv.getValue()) {
                try {
                    ApplicationServiceReflectUtils.invokeApplicationServiceInitializeMethod(kv.getKey(), e);
                } catch (Exception ex) {
                    if(isCausedByConstraintViolation(ex)) {
                        ex.printStackTrace();
                    } else {
                        ex.printStackTrace();
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }

    public static boolean isCausedByConstraintViolation(Exception ex) {
        boolean b = false;
        Throwable c = ex;
        while (c != null) {
            if (c.getClass().getName().endsWith("ConstraintViolationException")) {
                b = true;
                break;
            }
            if (c.getMessage() != null && c.getMessage().startsWith("Duplicate entry")) {
                b = true;
                break;
            }
            if(c.getMessage() != null && c.getMessage().startsWith("[rebirth]")) {
                b = true;
                break;
            }
            if (c instanceof DomainError) {
                DomainError domainError = (DomainError) c;
                if (domainError.getName().equalsIgnoreCase("rebirth")) {
                    b = true;
                    break;
                }
            }
            c = c.getCause();
        }
        return b;
    }
}
