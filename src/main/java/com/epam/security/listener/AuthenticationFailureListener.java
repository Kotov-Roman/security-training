package com.epam.security.listener;

import com.epam.security.repo.AppUserRepository;
import com.epam.security.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class AuthenticationFailureListener implements
        ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private LoginAttemptService loginAttemptService;
    private AppUserRepository userRepository;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        log.error("AuthenticationFailureBadCredentialsEvent is thrown");
        String username = (String) e.getAuthentication().getPrincipal();

        if (userRepository.existsByUsername(username)) {
            loginAttemptService.loginFailed(username);
        }
    }
}
