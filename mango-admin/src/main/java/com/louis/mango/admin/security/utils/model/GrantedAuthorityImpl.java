package com.louis.mango.admin.security.utils.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * 权限封装
 */
public class GrantedAuthorityImpl implements GrantedAuthority {

    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
