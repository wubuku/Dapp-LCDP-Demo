// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.service;

import com.github.wubuku.rooch.utils.RoochJsonRpcClient;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.domain.article.*;
import org.dddml.roochdemocontracts.rooch.contract.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.*;
import java.util.*;
import java.math.*;

@Service
public class RoochArticleService {

    @Autowired
    private ArticleStateRepository articleStateRepository;

    @Autowired
    private ReferenceTableItemAddedRepository referenceTableItemAddedRepository;
    @Autowired
    private ArticleEventService articleEventService;

    private RoochArticleStateRetriever roochArticleStateRetriever;

    @Autowired
    public RoochArticleService(RoochJsonRpcClient roochJsonRpcClient) {
        this.roochArticleStateRetriever = new RoochArticleStateRetriever(roochJsonRpcClient,
                id -> {
                    ArticleState.MutableArticleState s = new AbstractArticleState.SimpleArticleState();
                    s.setId(id);
                    return s;
                },
                (articleState, referenceNumber) -> (ReferenceState.MutableReferenceState)
                        ((EntityStateCollection.ModifiableEntityStateCollection<BigInteger, ReferenceState>) articleState.getReferences()).getOrAdd(referenceNumber),
                articleId -> {
                    articleEventService.pullReferenceTableItemAddedEvents();
                    return referenceTableItemAddedRepository.findByArticleReferenceId_ArticleId(articleId).stream()
                            .map(i -> i.getArticleReferenceId().getReferenceNumber()).collect(Collectors.toList());
                }
        );
    }

    @Transactional
    public void updateArticleState(String objectId) {
        ArticleState articleState = roochArticleStateRetriever.retrieveArticleState(objectId);
        if (articleState == null) {
            return;
        }
        articleStateRepository.merge(articleState);
    }

}

