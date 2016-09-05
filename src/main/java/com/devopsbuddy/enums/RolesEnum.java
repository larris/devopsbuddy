package com.devopsbuddy.enums;

/**
 * Created by larris on 06/09/16.
 */
public enum RolesEnum {

    BASIC(1, "ROLE_MASIC"),
    PRO(2, "ROLE_PRO"),
    ADMIN(3, "ROLE_ADMIN");

    private  final int id;
    private  final String roleName;

    RolesEnum(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
}
