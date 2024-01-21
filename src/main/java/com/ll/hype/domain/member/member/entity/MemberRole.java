package com.ll.hype.domain.member.member.entity;

import lombok.Getter;

@Getter
public enum MemberRole {
    MEMBER("MEMBER"),
    ADMIN("ADMIN"),
    SUPER_VISOR("SUPER_VISOR"),
    STORE_MANAGER("STORE_MANAGER");

    private final String value;
    MemberRole(String value) {
        this.value = value;
    }
}
