// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.article;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.dddml.roochdemocontracts.rooch.contract.*;

import java.math.*;
import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReferenceRemoved {
    private String id;

    private BigInteger version;

    private BigInteger referenceNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigInteger getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(BigInteger referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @Override
    public String toString() {
        return "ReferenceRemoved{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", referenceNumber=" + referenceNumber +
                '}';
    }

}