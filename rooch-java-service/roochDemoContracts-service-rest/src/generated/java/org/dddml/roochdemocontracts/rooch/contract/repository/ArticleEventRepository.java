// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.repository;

import org.dddml.roochdemocontracts.domain.article.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ArticleEventRepository extends JpaRepository<AbstractArticleEvent, ArticleEventId> {

    List<AbstractArticleEvent> findByStatusIsNull();

    AbstractArticleEvent.ArticleCreated findFirstArticleCreatedByOrderByRoochEventId_EventSeqDesc();

    AbstractArticleEvent.ReferenceAdded findFirstReferenceAddedByOrderByRoochEventId_EventSeqDesc();

    AbstractArticleEvent.ReferenceUpdated findFirstReferenceUpdatedByOrderByRoochEventId_EventSeqDesc();

    AbstractArticleEvent.ReferenceRemoved findFirstReferenceRemovedByOrderByRoochEventId_EventSeqDesc();

}
