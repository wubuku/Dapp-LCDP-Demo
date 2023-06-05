// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain;

import java.io.Serializable;
import java.math.BigInteger;
import org.dddml.roochdemocontracts.domain.*;

public class ReferenceVO implements Serializable {
    private BigInteger referenceNumber;

    public BigInteger getReferenceNumber()
    {
        return this.referenceNumber;
    }

    public void setReferenceNumber(BigInteger referenceNumber)
    {
        this.referenceNumber = referenceNumber;
    }

    private String title;

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    private String url;

    public String getUrl()
    {
        return this.url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public ReferenceVO()
    {
    }

    public ReferenceVO(BigInteger referenceNumber, String title, String url)
    {
        this.referenceNumber = referenceNumber;
        this.title = title;
        this.url = url;
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

        ReferenceVO other = (ReferenceVO)obj;
        return true 
            && (referenceNumber == other.referenceNumber || (referenceNumber != null && referenceNumber.equals(other.referenceNumber)))
            && (title == other.title || (title != null && title.equals(other.title)))
            && (url == other.url || (url != null && url.equals(other.url)))
            ;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        if (this.referenceNumber != null) {
            hash += 13 * this.referenceNumber.hashCode();
        }
        if (this.title != null) {
            hash += 13 * this.title.hashCode();
        }
        if (this.url != null) {
            hash += 13 * this.url.hashCode();
        }
        return hash;
    }



}

