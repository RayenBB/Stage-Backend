package com.stage.security.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.stage.security.user.Permission.ADMIN_CREATE;
import static com.stage.security.user.Permission.ADMIN_DELETE;
import static com.stage.security.user.Permission.ADMIN_READ;
import static com.stage.security.user.Permission.ADMIN_UPDATE;
import static com.stage.security.user.Permission.ENTREPRENEUR_CREATE;
import static com.stage.security.user.Permission.ENTREPRENEUR_DELETE;
import static com.stage.security.user.Permission.ENTREPRENEUR_READ;
import static com.stage.security.user.Permission.ENTREPRENEUR_UPDATE;

@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    ENTREPRENEUR_READ,
                    ENTREPRENEUR_UPDATE,
                    ENTREPRENEUR_DELETE,
                    ENTREPRENEUR_CREATE
            )
    ),
    ENTREPRENEUR(
            Set.of(
                    ENTREPRENEUR_READ,
                    ENTREPRENEUR_UPDATE,
                    ENTREPRENEUR_DELETE,
                    ENTREPRENEUR_CREATE
            )
    )

    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}