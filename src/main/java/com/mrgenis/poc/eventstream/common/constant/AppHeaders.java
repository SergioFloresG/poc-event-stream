package com.mrgenis.poc.eventstream.common.constant;

import jakarta.ws.rs.core.HttpHeaders;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AppHeaders {

  public static final String LANGUAGE = HttpHeaders.ACCEPT_LANGUAGE;
  public static final String FAKER_SEED = "X-Seed";

}
