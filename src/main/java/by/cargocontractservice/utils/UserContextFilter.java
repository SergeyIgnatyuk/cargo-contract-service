package by.cargocontractservice.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

import static by.cargocontractservice.utils.UserContext.CORRELATION_ID;

@Component
public class UserContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Optional.ofNullable(UserContextHolder.getContext())
                .ifPresent(userContext -> userContext.setCorrelationId(httpServletRequest.getHeader(CORRELATION_ID)));
        System.out.printf("UserContextFilter Correlation id: %s\n", Optional.ofNullable(UserContextHolder.getContext())
                .map(UserContext::getCorrelationId)
                .orElse("null"));
        filterChain.doFilter(httpServletRequest, servletResponse);
    }
}
