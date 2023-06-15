// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.repository;

import org.dddml.roochdemocontracts.domain.article.ArticleReferenceId;
import org.dddml.roochdemocontracts.rooch.contract.persistence.ReferenceTableItemAdded;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface ReferenceTableItemAddedRepository extends JpaRepository<ReferenceTableItemAdded, ArticleReferenceId> {

    @Transactional(readOnly = true)
    List<ReferenceTableItemAdded> findByOrderByRoochEventId_EventSeqDesc(Pageable pageable);

    @Transactional(readOnly = true)
    List<ReferenceTableItemAdded> findByRoochEventId_EventHandleIdOrderByRoochEventId_EventSeqDesc(String eventHandleId, Pageable pageable);

    @Transactional(readOnly = true)
    ReferenceTableItemAdded findFirstByOrderByRoochEventId_EventSeqDesc();

    @Transactional(readOnly = true)
    List<ReferenceTableItemAdded> findByArticleReferenceId_ArticleId(String articleId);

}
