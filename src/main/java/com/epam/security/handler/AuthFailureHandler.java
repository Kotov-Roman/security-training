package com.epam.security.handler;

import com.epam.security.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.error(exception.getMessage());
        super.onAuthenticationFailure(request, response, exception);
        String errorMsg = Constants.BAD_CREDENTIALS_MSG;

        if (exception.getMessage().equals(Constants.USER_IS_BLOCKED_MSG)) {
            errorMsg = Constants.USER_IS_BLOCKED_MSG;
        }

        setDefaultFailureUrl("/login?error");
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMsg);
    }

}
