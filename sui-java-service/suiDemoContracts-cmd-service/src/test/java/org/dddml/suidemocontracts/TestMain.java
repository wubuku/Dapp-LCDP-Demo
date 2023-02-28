package org.dddml.suidemocontracts;

//import org.dddml.suidemocontracts.domain.car.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.specialization.spring.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class TestMain {

    private static org.springframework.context.ApplicationContext springFrameworkApplicationContext;

    static {
        springFrameworkApplicationContext = new ClassPathXmlApplicationContext(
                //"config/AggregatesHibernateConfig.xml",
                //"config/TreesHibernateConfig.xml",
                //"config/TreesConfig.xml",
                //"config/CustomIdGeneratorsConfig.xml",
                "config/SpringConfigs.xml",
                "config/TestDatasourceConfig.xml"
        );
    }

    public static void main(final String[] args) throws Exception {
        ApplicationContext.current = new SpringApplicationContext(springFrameworkApplicationContext);
        testCarApplicationService();
        System.exit(0);
    }

    /*
    private static void testInitEntityData() {
        try {
            Map<String, List<Object>> allData = XmlEntityDataTool.deserializeAllGroupByEntityName(
                    XmlEntityDataTool.DEFAULT_XML_DATA_LOCATION_PATTERN);
            for (Map.Entry<String, List<Object>> kv : allData.entrySet()) {
                for (Object e : kv.getValue()) {
                    ApplicationServiceReflectUtils.invokeApplicationServiceInitializeMethod(kv.getKey(), e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    private static void testCarApplicationService() {
        /*
        CarApplicationService carApplicationService = (CarApplicationService) ApplicationContext.current.get("carApplicationService");
        CarCommand.CreateCar cc = new AbstractCarCommand.SimpleCreateCar();
        cc.setId(UUID.randomUUID().toString());
        cc.setCommandId(UUID.randomUUID().toString());
        cc.setRequesterId("1");
        carApplicationService.when(cc);

        Iterable<CarState> cs = carApplicationService.getAll(0, Integer.MAX_VALUE);
        for(CarState c : cs) {
            System.out.println(c.getId() + "\t" + c.getCreatedBy());
        }
        */
    }
}
