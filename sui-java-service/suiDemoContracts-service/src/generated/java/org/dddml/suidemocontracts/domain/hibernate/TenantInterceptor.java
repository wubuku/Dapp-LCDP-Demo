package org.dddml.suidemocontracts.domain.hibernate;

import org.dddml.suidemocontracts.domain.TenantSupport;
import org.dddml.suidemocontracts.specialization.*;
import org.hibernate.*;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Iterator;

public class TenantInterceptor extends EmptyInterceptor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof TenantSupport) {
            log.debug("[save] Updating the entity " + id + " with tenant information: " + TenantContext.getCurrentTenant());
            return setTenantId(entity, id, state, propertyNames, types);
        }
        return false;
    }

    private static boolean setTenantId(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        String currentTenantId = TenantContext.getCurrentTenant();
        if (TenantSupport.SUPER_TENANT_ID.equals(currentTenantId)) {
            if (null == ((TenantSupport) entity).getTenantId()) {
                return setTenantId((TenantSupport) entity, state, propertyNames, currentTenantId);
            } // else {} //You know what you are doing...
        } else {
            if (null == currentTenantId) {
                throw new CallbackException("Current tenantId is null.");
            }
            String stringEntityId = (id instanceof String) ? (String) id : null;
            if (stringEntityId != null) {
                if (!(stringEntityId.startsWith(currentTenantId) || stringEntityId.endsWith(currentTenantId))) {
                    throw new CallbackException("Entity Id MUST starts with or ends with current tenantId.");
                }
            }
            return setTenantId((TenantSupport) entity, state, propertyNames, currentTenantId);
        }
        return false;
    }

    private static boolean setTenantId(TenantSupport entity, Object[] state, String[] propertyNames, String currentTenantId) {
        for (int i = 0; i < propertyNames.length; i++) {
            if ("tenantId".equals(propertyNames[i])) {
                if (!currentTenantId.equals(state[i])) {
                    state[i] = currentTenantId;
                    entity.setTenantId(currentTenantId);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof TenantSupport) {
            log.debug("[delete] Updating the entity " + id + " with tenant information: " + TenantContext.getCurrentTenant());
            setTenantId(entity, id, state, propertyNames, types);
        }
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof TenantSupport) {
            log.debug("[flush-dirty] Updating the entity " + id + " with tenant information: " + TenantContext.getCurrentTenant());
            return setTenantId(entity, id, currentState, propertyNames, types);
        }
        return false;
    }

}

