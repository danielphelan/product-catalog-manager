package com.daniel.productcatalog.security;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

public class ApiKeyFilter extends OncePerRequestFilter {

  private final String apiKey;

  public ApiKeyFilter(String apiKey) {
    this.apiKey = apiKey;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    try {
      String requestApiKey = request.getHeader("x-api-key");
      String format = request.getParameter("format");

      String requestUri = request.getRequestURI();
      List<String> allowedUris = List.of("/api/export/advertisers");
      if (allowedUris.stream().anyMatch(requestUri::startsWith) && "CSV".equalsIgnoreCase(format)) {
          filterChain.doFilter(request, response);
          return;
      }

      if (apiKey.equals(requestApiKey)) {
        logger.info("Successful authentication attempt for API key");
          SecurityContextHolder.getContext().setAuthentication(
              new PreAuthenticatedAuthenticationToken("apiKeyUser", null, null)
          );
      } else {
          logger.warn("Failed authentication attempt with API key");
          response.setStatus(HttpServletResponse.SC_FORBIDDEN);
          return;
      }

      filterChain.doFilter(request, response);
    } finally {
      SecurityContextHolder.clearContext();
    }
  }
}
