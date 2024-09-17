package com.mrgenis.poc.eventstream.common.config;

import com.mrgenis.poc.eventstream.common.constant.RateLimitConst;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = RateLimitConst.PROPERTY_PREFIX)
public class RateLimitProperties {

  @Min(1)
  private int limit = 60;
  @NotNull
  private Duration duration = Duration.ofMinutes(1);

}
