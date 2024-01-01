// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.article;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.specialization.*;
import org.dddml.roochdemocontracts.domain.AbstractEvent;

public abstract class AbstractArticleEvent extends AbstractEvent implements ArticleEvent.SqlArticleEvent, RoochEvent.MutableRoochEvent, HasStatus.MutableHasStatus {
    private ArticleEventId articleEventId = new ArticleEventId();

    public ArticleEventId getArticleEventId() {
        return this.articleEventId;
    }

    public void setArticleEventId(ArticleEventId eventId) {
        this.articleEventId = eventId;
    }
    
    public String getId() {
        return getArticleEventId().getId();
    }

    public void setId(String id) {
        getArticleEventId().setId(id);
    }

    private boolean eventReadOnly;

    public boolean getEventReadOnly() { return this.eventReadOnly; }

    public void setEventReadOnly(boolean readOnly) { this.eventReadOnly = readOnly; }

    public BigInteger getVersion() {
        return getArticleEventId().getVersion();
    }
    
    public void setVersion(BigInteger version) {
        getArticleEventId().setVersion(version);
    }

    private RoochEventId roochEventId;

    public RoochEventId getRoochEventId() {
        return this.roochEventId;
    }
    
    public void setRoochEventId(RoochEventId roochEventId) {
        this.roochEventId = roochEventId;
    }

    private String roochSender;

    public String getRoochSender() {
        return this.roochSender;
    }
    
    public void setRoochSender(String roochSender) {
        this.roochSender = roochSender;
    }

    private String roochTxHash;

    public String getRoochTxHash() {
        return this.roochTxHash;
    }
    
    public void setRoochTxHash(String roochTxHash) {
        this.roochTxHash = roochTxHash;
    }

    private String roochTypeTag;

    public String getRoochTypeTag() {
        return this.roochTypeTag;
    }
    
    public void setRoochTypeTag(String roochTypeTag) {
        this.roochTypeTag = roochTypeTag;
    }

    private Long roochTimestampMs;

    public Long getRoochTimestampMs() {
        return this.roochTimestampMs;
    }
    
    public void setRoochTimestampMs(Long roochTimestampMs) {
        this.roochTimestampMs = roochTimestampMs;
    }

    private BigInteger roochBlockHeight;

    public BigInteger getRoochBlockHeight() {
        return this.roochBlockHeight;
    }
    
    public void setRoochBlockHeight(BigInteger roochBlockHeight) {
        this.roochBlockHeight = roochBlockHeight;
    }

    private Long roochEventIndex;

    public Long getRoochEventIndex() {
        return this.roochEventIndex;
    }
    
    public void setRoochEventIndex(Long roochEventIndex) {
        this.roochEventIndex = roochEventIndex;
    }

    private String status;

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    private String createdBy;

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    private Date createdAt;

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    private String commandId;

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    private String commandType;

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    protected AbstractArticleEvent() {
    }

    protected AbstractArticleEvent(ArticleEventId eventId) {
        this.articleEventId = eventId;
    }

    protected ReferenceEventDao getReferenceEventDao() {
        return (ReferenceEventDao)ApplicationContext.current.get("referenceEventDao");
    }

    protected ReferenceEventId newReferenceEventId(BigInteger referenceNumber)
    {
        ReferenceEventId eventId = new ReferenceEventId(this.getArticleEventId().getId(), 
            referenceNumber, 
            this.getArticleEventId().getVersion());
        return eventId;
    }

    protected void throwOnInconsistentEventIds(ReferenceEvent.SqlReferenceEvent e)
    {
        throwOnInconsistentEventIds(this, e);
    }

    public static void throwOnInconsistentEventIds(ArticleEvent.SqlArticleEvent oe, ReferenceEvent.SqlReferenceEvent e)
    {
        if (!oe.getArticleEventId().getId().equals(e.getReferenceEventId().getArticleId()))
        { 
            throw DomainError.named("inconsistentEventIds", "Outer Id Id %1$s but inner id ArticleId %2$s", 
                oe.getArticleEventId().getId(), e.getReferenceEventId().getArticleId());
        }
    }


    public abstract String getEventType();

    public static class ArticleClobEvent extends AbstractArticleEvent {

        protected Map<String, Object> getDynamicProperties() {
            return dynamicProperties;
        }

        protected void setDynamicProperties(Map<String, Object> dynamicProperties) {
            if (dynamicProperties == null) {
                throw new IllegalArgumentException("dynamicProperties is null.");
            }
            this.dynamicProperties = dynamicProperties;
        }

        private Map<String, Object> dynamicProperties = new HashMap<>();

        protected String getDynamicPropertiesLob() {
            return ApplicationContext.current.getClobConverter().toString(getDynamicProperties());
        }

        protected void setDynamicPropertiesLob(String text) {
            getDynamicProperties().clear();
            Map<String, Object> ps = ApplicationContext.current.getClobConverter().parseLobProperties(text);
            if (ps != null) {
                for (Map.Entry<String, Object> kv : ps.entrySet()) {
                    getDynamicProperties().put(kv.getKey(), kv.getValue());
                }
            }
        }

        @Override
        public String getEventType() {
            return "ArticleClobEvent";
        }

    }

    public static class ArticleCreated extends ArticleClobEvent implements ArticleEvent.ArticleCreated {

        @Override
        public String getEventType() {
            return "ArticleCreated";
        }

        public String getTitle() {
            Object val = getDynamicProperties().get("title");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setTitle(String value) {
            getDynamicProperties().put("title", value);
        }

        public String getAuthor() {
            Object val = getDynamicProperties().get("author");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setAuthor(String value) {
            getDynamicProperties().put("author", value);
        }

        public String getContent() {
            Object val = getDynamicProperties().get("content");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setContent(String value) {
            getDynamicProperties().put("content", value);
        }

        public ReferenceVO[] getReferences() {
            Object val = getDynamicProperties().get("references");
            if (val instanceof ReferenceVO[]) {
                return (ReferenceVO[]) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, ReferenceVO[].class);
        }

        public void setReferences(ReferenceVO[] value) {
            getDynamicProperties().put("references", value);
        }

        public String[] getTags() {
            Object val = getDynamicProperties().get("tags");
            if (val instanceof String[]) {
                return (String[]) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String[].class);
        }

        public void setTags(String[] value) {
            getDynamicProperties().put("tags", value);
        }

        public String getOwner() {
            Object val = getDynamicProperties().get("owner");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setOwner(String value) {
            getDynamicProperties().put("owner", value);
        }

    }

    public static class ReferenceAdded extends ArticleClobEvent implements ArticleEvent.ReferenceAdded {

        @Override
        public String getEventType() {
            return "ReferenceAdded";
        }

        public BigInteger getReferenceNumber() {
            Object val = getDynamicProperties().get("referenceNumber");
            if (val instanceof BigInteger) {
                return (BigInteger) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger.class);
        }

        public void setReferenceNumber(BigInteger value) {
            getDynamicProperties().put("referenceNumber", value);
        }

        public String getTitle() {
            Object val = getDynamicProperties().get("title");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setTitle(String value) {
            getDynamicProperties().put("title", value);
        }

        public String getUrl() {
            Object val = getDynamicProperties().get("url");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setUrl(String value) {
            getDynamicProperties().put("url", value);
        }

    }

    public static class ReferenceUpdated extends ArticleClobEvent implements ArticleEvent.ReferenceUpdated {

        @Override
        public String getEventType() {
            return "ReferenceUpdated";
        }

        public BigInteger getReferenceNumber() {
            Object val = getDynamicProperties().get("referenceNumber");
            if (val instanceof BigInteger) {
                return (BigInteger) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger.class);
        }

        public void setReferenceNumber(BigInteger value) {
            getDynamicProperties().put("referenceNumber", value);
        }

        public String getTitle() {
            Object val = getDynamicProperties().get("title");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setTitle(String value) {
            getDynamicProperties().put("title", value);
        }

        public String getUrl() {
            Object val = getDynamicProperties().get("url");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setUrl(String value) {
            getDynamicProperties().put("url", value);
        }

        public String getAuthor() {
            Object val = getDynamicProperties().get("author");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setAuthor(String value) {
            getDynamicProperties().put("author", value);
        }

    }

    public static class ReferenceRemoved extends ArticleClobEvent implements ArticleEvent.ReferenceRemoved {

        @Override
        public String getEventType() {
            return "ReferenceRemoved";
        }

        public BigInteger getReferenceNumber() {
            Object val = getDynamicProperties().get("referenceNumber");
            if (val instanceof BigInteger) {
                return (BigInteger) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger.class);
        }

        public void setReferenceNumber(BigInteger value) {
            getDynamicProperties().put("referenceNumber", value);
        }

    }


}

