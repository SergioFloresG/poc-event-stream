package com.mrgenis.poc.eventstream.common.filer;

import com.mrgenis.poc.eventstream.common.config.InfoAppProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppVersionFilter extends HttpFilter {

  public static final String X_APP_VERSION = "X-App-Version";
  private final InfoAppProperties properties;

  @Override
  protected void doFilter(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    var version = String.format("%s/%s", properties.getName(), properties.getVersion());
    response.addHeader(X_APP_VERSION, version);

    chain.doFilter(request, response);
  }
}
