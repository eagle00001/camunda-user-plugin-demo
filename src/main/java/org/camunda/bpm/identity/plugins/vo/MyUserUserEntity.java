package org.camunda.bpm.identity.plugins.vo;

import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;

/**
 * @author eagle_daiqiang
 */
public class MyUserUserEntity extends UserEntity {
//    表id
    private String tableIdentity;

    public String getTableIdentity() {
        return tableIdentity;
    }

    public void setTableIdentity(String tableIdentity) {
        this.tableIdentity = tableIdentity;
    }
}
