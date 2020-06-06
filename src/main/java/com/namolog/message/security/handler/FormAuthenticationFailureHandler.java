package com.namolog.message.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {
        // 인증 실패시 여러가지 작업을 수행

        String errorMessage = "Invalid username or password";
        if (exception instanceof UsernameNotFoundException)
            errorMessage = "Invalid username";
        else if (exception instanceof BadCredentialsException)
            errorMessage = "Invalid password";
        else if (exception instanceof DisabledException)
            errorMessage = "Locked";
        else if(exception instanceof CredentialsExpiredException)
            errorMessage = "Expired password";

        setDefaultFailureUrl("/login?error=true&exception=" + errorMessage);
        super.onAuthenticationFailure(request, response, exception);
    }
}