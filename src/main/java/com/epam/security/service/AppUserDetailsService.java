package com.epam.security.service;

import com.epam.security.common.exception.UserNotFoundException;
import com.epam.security.domain.AppUser;
import com.epam.security.repo.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.epam.security.Constants.USER_IS_BLOCKED_MSG;

@AllArgsConstructor
@Service
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository userRepository;
    private LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = userRepository.findByUsername(username);
        AppUser appUser = user.orElseThrow(UserNotFoundException::new);
        if (loginAttemptService.isBlocked(username)) {
            throw new LockedException(USER_IS_BLOCKED_MSG);
        }
        return appUser;
    }
}
