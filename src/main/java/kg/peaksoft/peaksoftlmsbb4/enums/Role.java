package kg.peaksoft.peaksoftlmsbb4.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    TEACHER;
    @Override
    public String getAuthority() {
        return this.name();
    }
}