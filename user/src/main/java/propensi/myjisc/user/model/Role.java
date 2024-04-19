package propensi.myjisc.user.model;

import org.springframework.security.core.GrantedAuthority;


    public enum Role implements GrantedAuthority {
        ADMIN,
        GUEST,
        MURID,
        GURU,
        STAFF;

        @Override
        public String getAuthority() {
            return name();
        }
    }
