package com.mrgenis.poc.eventstream.common.config;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.mrgenis.poc.eventstream.common.constant.AppHeaders;
import com.mrgenis.poc.eventstream.common.faker.FakerBuilder;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class ApplicationConfig {

  @Bean
  @RequestScope
  public Faker faker(
      HttpServletRequest request
  ) {
    return new FakerBuilder()
        .locale(obtainLocale(request))
        .random(obtainRandom(request))
        .build();
  }

  @Bean
  @RequestScope
  public FakeValuesService fakeValuesService(HttpServletRequest request) {

    return new FakerBuilder()
        .locale(obtainLocale(request))
        .random(obtainRandom(request))
        .buildService();
  }

  private Random obtainRandom(HttpServletRequest request) {
    int seed = request.getIntHeader(AppHeaders.FAKER_SEED);
    Random random = new Random();
    if (seed > -1) {
      random.setSeed(seed);
    }

    return random;
  }

  private Locale obtainLocale(HttpServletRequest request) {
    String language = request.getHeader(AppHeaders.LANGUAGE);
    Locale locale = Locale.ENGLISH;
    if (Objects.nonNull(language)) {
      locale = Locale.forLanguageTag(language);
    }

    return locale;
  }

}
