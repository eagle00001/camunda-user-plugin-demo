package org.camunda.bpm.identity.plugins.vo;

import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;

/**
 * @author eagle_daiqiang
 */
public class MyUserGroupEntity extends GroupEntity {
    //    表id
    private String tableIdentity;

    public String getTableIdentity() {
        return tableIdentity;
    }

    public void setTableIdentity(String tableIdentity) {
        this.tableIdentity = tableIdentity;
    }
}
