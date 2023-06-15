package org.dddml.roochdemocontracts.rooch.contract.tests;

import org.dddml.roochdemocontracts.RoochDemoContractsApplication;
import org.dddml.roochdemocontracts.domain.Day;
import org.dddml.roochdemocontracts.domain.order.OrderItemShipGroupAssocSubitemId;
import org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemShipGroupAssocSubitemTableItemAdded;
import org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemShipGroupAssociationTableItemAdded;
import org.dddml.roochdemocontracts.rooch.contract.repository.OrderItemShipGroupAssocSubitemTableItemAddedRepository;
import org.dddml.roochdemocontracts.rooch.contract.repository.OrderItemShipGroupAssociationTableItemAddedRepository;
import org.dddml.roochdemocontracts.rooch.contract.service.OrderEventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    private OrderItemShipGroupAssociationTableItemAddedRepository orderItemShipGroupAssociationTableItemAddedRepository;

    @Autowired
    private OrderEventService orderEventService;

    @Test
    void testRepository_1() {
        List<OrderItemShipGroupAssocSubitemTableItemAdded> list =
                //orderItemShipGroupAssocSubitemTableItemAddedRepository.findAll();
                orderItemShipGroupAssocSubitemTableItemAddedRepository
                        .findByOrderByRoochEventId_EventSeqDesc(Pageable.ofSize(1));
        System.out.println(list);

        list = orderItemShipGroupAssocSubitemTableItemAddedRepository
                .findByRoochEventId_EventHandleIdOrderByRoochEventId_EventSeqDesc("", Pageable.ofSize(1));
        System.out.println(list);

        list = orderItemShipGroupAssocSubitemTableItemAddedRepository
                .findByOrderItemShipGroupAssocSubitemId_OrderIdAndOrderItemShipGroupAssocSubitemId_OrderShipGroupShipGroupSeqIdAndOrderItemShipGroupAssocSubitemId_OrderItemShipGroupAssociationProductObjId(
                        "", 1, "");
        System.out.println(list);

        Optional<OrderItemShipGroupAssocSubitemTableItemAdded> itemAdded = orderItemShipGroupAssocSubitemTableItemAddedRepository.findById(new OrderItemShipGroupAssocSubitemId(
                "", 1, "", new Day()));
        System.out.println(itemAdded);

        List<OrderItemShipGroupAssociationTableItemAdded> assocList = orderItemShipGroupAssociationTableItemAddedRepository
                .findByOrderItemShipGroupAssociationId_OrderIdAndOrderItemShipGroupAssociationId_OrderShipGroupShipGroupSeqId(
                        "", 1
                );
        System.out.println(assocList);
    }

    @Test
    void testPullOrderItemShipGroupAssociationTableItemAddedEvents() {
        orderEventService.pullOrderItemShipGroupAssociationTableItemAddedEvents();
    }

}
