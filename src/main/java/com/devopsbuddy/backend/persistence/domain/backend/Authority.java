package com.devopsbuddy.backend.persistence.domain.backend;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by larris on 06/09/16.
 */
public class Authority implements GrantedAuthority {

    private final  String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return null;
    }
}
