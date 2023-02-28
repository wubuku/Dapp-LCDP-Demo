package org.dddml.suidemocontracts.domain;


public interface TenantSupport {

    public static final String SUPER_TENANT_ID  = "*";

    String getTenantId();

    void setTenantId(String tenantId);

}


