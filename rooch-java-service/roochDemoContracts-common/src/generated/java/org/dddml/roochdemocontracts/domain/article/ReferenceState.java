// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.article;

import java.util.*;
import java.math.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.specialization.Event;

public interface ReferenceState
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    BigInteger getReferenceNumber();

    String getTitle();

    String getAuthor();

    BigInteger getPublicationYear();

    String getPublisher();

    String getDoi();

    String getUrl();

    BigInteger getPageNumber();

    Long getOffChainVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    String getArticleId();

    interface MutableReferenceState extends ReferenceState {
        void setReferenceNumber(BigInteger referenceNumber);

        void setTitle(String title);

        void setAuthor(String author);

        void setPublicationYear(BigInteger publicationYear);

        void setPublisher(String publisher);

        void setDoi(String doi);

        void setUrl(String url);

        void setPageNumber(BigInteger pageNumber);

        void setOffChainVersion(Long offChainVersion);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);

        void setArticleId(String articleId);


        void mutate(Event e);

        //void when(ReferenceEvent.ReferenceStateCreated e);

        //void when(ReferenceEvent.ReferenceStateMergePatched e);

        //void when(ReferenceEvent.ReferenceStateRemoved e);
    }

    interface SqlReferenceState extends MutableReferenceState {
        ArticleReferenceId getArticleReferenceId();

        void setArticleReferenceId(ArticleReferenceId articleReferenceId);


        boolean isStateUnsaved();

        boolean getForReapplying();
    }
}

