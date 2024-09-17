package com.mrgenis.poc.eventstream.common.config;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "info.app")
public class InfoAppProperties {

  @NotEmpty
  private String name;
  @NotEmpty
  private String description;
  @NotEmpty
  private String version;

}
