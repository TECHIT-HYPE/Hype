package com.ll.hype.domain.member.member.entity;

public enum MemberRole {
    MEMBER("MEMBER"),
    ADMIN("ADMIN"),
    SUPER_VISOR("SUPER_VISOR"),
    STORE_MANAGER("STORE_MANAGER");
    private String value;
    MemberRole(String value) {

    }
}
