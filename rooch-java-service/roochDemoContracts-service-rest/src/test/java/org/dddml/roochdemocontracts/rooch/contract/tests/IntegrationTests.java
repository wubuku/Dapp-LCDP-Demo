package org.dddml.roochdemocontracts.rooch.contract.tests;

import org.dddml.roochdemocontracts.RoochDemoContractsApplication;
import org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemShipGroupAssocSubitemTableItemAdded;
import org.dddml.roochdemocontracts.rooch.contract.repository.OrderItemShipGroupAssocSubitemTableItemAddedRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

//@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = RoochDemoContractsApplication.class
)
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class IntegrationTests {

    @Autowired
    private OrderItemShipGroupAssocSubitemTableItemAddedRepository orderItemShipGroupAssocSubitemTableItemAddedRepository;

    @Test
    void testRepository_1() {
        List<OrderItemShipGroupAssocSubitemTableItemAdded> list = orderItemShipGroupAssocSubitemTableItemAddedRepository.findAll();
        System.out.println(list);
    }


}
