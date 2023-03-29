// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.domainname;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.dddml.suidemocontracts.sui.contract.*;

import java.math.*;
import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Registered {
    private String id;

    private DomainNameId domainNameId;

    private BigInteger registrationPeriod;

    private String owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DomainNameId getDomainNameId() {
        return domainNameId;
    }

    public void setDomainNameId(DomainNameId domainNameId) {
        this.domainNameId = domainNameId;
    }

    public BigInteger getRegistrationPeriod() {
        return registrationPeriod;
    }

    public void setRegistrationPeriod(BigInteger registrationPeriod) {
        this.registrationPeriod = registrationPeriod;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Registered{" +
                "id='" + id + '\'' +
                ", domainNameId=" + domainNameId +
                ", registrationPeriod=" + registrationPeriod +
                ", owner=" + '\'' + owner + '\'' +
                '}';
    }

}