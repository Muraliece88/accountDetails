package com.assignment.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

import static com.assignment.constants.TransactionConstants.CORRELATION_ID;

@Component
public class CorelationFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String correlationId = ((HttpServletRequest) servletRequest).getHeader(CORRELATION_ID);
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        MDC.put(CORRELATION_ID, correlationId);

        try {
            filterChain.doFilter(httpServletRequest, servletResponse);
        } finally {
            MDC.remove(CORRELATION_ID);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
