package com.toilamdev.stepbystep.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public enum RoleName {
    SYSADMIN("Quản trị hệ thống cấp cao."),
    ADMIN("Quản trị viên, quản lý các hoạt động chung của hệ thống."),
    MANAGER("Điều phối viên, chịu trách nhiệm kiểm duyệt nội dung, giám sát hoạt động của người dùng"),
    USER("Người dùng thông thường");

    private final String description;
}
