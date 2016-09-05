package com.devopsbuddy.backend.persistence.domain.backend;

import com.devopsbuddy.enums.RolesEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by larris on 05/09/16.
 */
@Entity
public class Role implements Serializable {

    /** The Serial Verision UID for serializable Classes */
    private static final long serialVersionUID = 1L;

    public Role() {
    }

    public Role(RolesEnum rolesEnum){
        this.id = rolesEnum.getId();
        this.name = rolesEnum.getRoleName();
    }

    @Id
    private int id;

    private String name;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return id == role.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
