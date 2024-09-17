package com.mrgenis.poc.eventstream.common.config;

import com.mrgenis.poc.eventstream.common.constant.RateLimitConst;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
    prefix = RateLimitConst.PROPERTY_PREFIX, name = RateLimitConst.PROPERTY_ENABLE,
    havingValue = "true", matchIfMissing = true)
public class RateLimitFilter implements Filter {

  private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
  private final RateLimitProperties rateLimitProperties;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String clientIp = getClientId(httpRequest);

    Bucket bucket = cache.computeIfAbsent(clientIp, this::newBucket);

    if (bucket.tryConsume(1)) {
      chain.doFilter(request, response);
    } else {
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      httpResponse.setStatus(429);
    }

  }

  /**
   * Creates a new rate limiting bucket for the provided client IP address.
   *
   * @param clientIp the IP address of the client for which the new bucket is being created
   * @return a Bucket configured with the rate limit and refill policy as defined in
   * rateLimitProperties
   */
  private Bucket newBucket(String clientIp) {
    Bandwidth limitBandwidth = Bandwidth.builder()
        .capacity(rateLimitProperties.getLimit())
        .refillGreedy(rateLimitProperties.getLimit(), rateLimitProperties.getDuration())
        .build();

    return Bucket.builder().addLimit(limitBandwidth).build();
  }

  /**
   * Retrieves the client ID from the given HTTP servlet request. It first checks for the
   * "X-Forwarded-For" header to see if the request was forwarded from a proxy or a load balancer.
   * If the header exists, it extracts the first IP address from the list. If the header is not
   * present, it falls back to the remote address of the request.
   *
   * @param request the HttpServletRequest from which the client ID is extracted
   * @return the client ID, which is either the first IP address from the "X-Forwarded-For" header
   * or the remote address of the request if the header is not present
   */
  private String getClientId(HttpServletRequest request) {
    String xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader == null) {
      return request.getRemoteAddr();
    }
    return xfHeader.split(",")[0];
  }
}
