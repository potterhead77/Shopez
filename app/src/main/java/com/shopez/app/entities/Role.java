package com.shopez.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Role {   //this is not a bean it is a data class ie (JPA managed not Spring managed)
    @Id
    private String roleName;
    private String roleDescription;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
