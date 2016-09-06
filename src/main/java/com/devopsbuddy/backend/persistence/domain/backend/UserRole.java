package com.devopsbuddy.backend.persistence.domain.backend;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by larris on 05/09/16.
 */
@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {
    /** The Serial Verision UID for serializable Classes */
    private static final long serialVersionUID = 1L;

    public UserRole() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public  UserRole(User user,Role role){
        this.user = user;
        this.role = role;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        return id == userRole.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
