package org.dddml.suidemocontracts.sui.contract.repository;

import org.dddml.suidemocontracts.domain.daysummary.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DaySummaryEventRepository extends JpaRepository<AbstractDaySummaryEvent, DaySummaryEventId> {
    AbstractDaySummaryEvent.DaySummaryCreated findFirstDaySummaryCreatedByOrderBySuiTimestampDesc();
}
