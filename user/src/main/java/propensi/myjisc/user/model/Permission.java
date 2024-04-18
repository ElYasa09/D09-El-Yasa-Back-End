package propensi.myjisc.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    STAFF_READ("staff:read"),
    STAFF_UPDATE("staff:update"),
    STAFF_CREATE("staff:create"),
    STAFF_DELETE("staff:delete"),
    GURU_READ("guru:read"),
    GURU_UPDATE("guru:update"),
    GURU_CREATE("guru:create"),
    GURU_DELETE("guru:delete"),
    SISWA_READ("siswa:read"),
    SISWA_UPDATE("siswa:update"),
    SISWA_CREATE("siswa:create"),
    SISWA_DELETE("siswa:delete"),
    GUEST_READ("guest:read");

    @Getter
    private final String permission;
}