package com.mrgenis.poc.eventstream.common.config;


import com.mrgenis.poc.eventstream.common.constant.AppHeaders;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import net.datafaker.Faker;
import net.datafaker.service.RandomService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class ApplicationConfig {

  private final Random random = new Random();

  @Bean
  @RequestScope
  public Faker faker(
      HttpServletRequest request
  ) {
    return new Faker(obtainLocale(request), obtainRandom(request));
  }

  private RandomService obtainRandom(HttpServletRequest request) {
    int seed = request.getIntHeader(AppHeaders.FAKER_SEED);
    if (seed > -1) {
      random.setSeed(seed);
    }

    return new RandomService(random);
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
