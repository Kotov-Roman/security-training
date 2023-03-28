package com.epam.security.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Permissions implements GrantedAuthority {
    VIEW_INFO,
    VIEW_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
