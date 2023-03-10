package org.dddml.suidemocontracts.domain;

import java.io.Serializable;
import org.dddml.suidemocontracts.domain.*;

public class SuiEventId implements Serializable
{
    private String txDigest;

    public String getTxDigest()
    {
        return this.txDigest;
    }

    public void setTxDigest(String txDigest)
    {
        this.txDigest = txDigest;
    }

    private Long eventSeq;

    public Long getEventSeq()
    {
        return this.eventSeq;
    }

    public void setEventSeq(Long eventSeq)
    {
        this.eventSeq = eventSeq;
    }

    public SuiEventId()
    {
    }

    public SuiEventId(String txDigest, Long eventSeq)
    {
        this.txDigest = txDigest;
        this.eventSeq = eventSeq;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        SuiEventId other = (SuiEventId)obj;
        return true 
            && (txDigest == other.txDigest || (txDigest != null && txDigest.equals(other.txDigest)))
            && (eventSeq == other.eventSeq || (eventSeq != null && eventSeq.equals(other.eventSeq)))
            ;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        if (this.txDigest != null) {
            hash += 13 * this.txDigest.hashCode();
        }
        if (this.eventSeq != null) {
            hash += 13 * this.eventSeq.hashCode();
        }
        return hash;
    }



}
