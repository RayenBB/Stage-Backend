package com.stage.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    ENTREPRENEUR_READ("entrepreneur:read"),
    ENTREPRENEUR_UPDATE("entrepreneur:update"),
    ENTREPRENEUR_CREATE("entrepreneur:create"),
    ENTREPRENEUR_DELETE("entrepreneur:delete")

    ;

    @Getter
    private final String permission;
}
