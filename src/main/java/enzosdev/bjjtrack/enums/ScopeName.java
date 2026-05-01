package enzosdev.bjjtrack.enums;

import lombok.Getter;

@Getter
public enum ScopeName {
    PLATFORM_ADMIN("platform:admin"),
    ADMIN_ALL("admin:all"),
    ATTENDANCE_READ("attendance:read"),
    ATTENDANCE_WRITE("attendance:write"),
    STUDENT_READ("student:read"),
    PROFILE_WRITE("profile:write"),
    STUDENT_PROMOTE("student:promote");

    private final String value;

    ScopeName(String value) {
        this.value = value;
    }
}
