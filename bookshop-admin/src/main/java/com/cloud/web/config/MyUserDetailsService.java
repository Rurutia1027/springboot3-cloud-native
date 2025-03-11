package com.cloud.web.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.equals(username, "mae")) {
            return new User("mae", new BCryptPasswordEncoder().encode("admin"),
                    // here we grant admin this authority to the user
                    // so that the user has the role of admin
                    // then the user is allowed to get access to the target resource url endpoint
                    // after he/she is authenticated by username/password successfully.
                    AuthorityUtils.createAuthorityList("admin", "xxx", "my_tester")
            );
        }
        return null;
    }
}
