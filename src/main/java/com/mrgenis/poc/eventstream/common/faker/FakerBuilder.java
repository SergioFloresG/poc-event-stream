package com.mrgenis.poc.eventstream.common.faker;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import java.util.Locale;
import java.util.Random;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * FakerBuilder is a builder class for creating instances of the Faker class. It allows the user to
 * configure the locale and random service used by the Faker instance. The default locale is set to
 * {@link Locale#ENGLISH} if not specified.
 */
@Setter
@Accessors(fluent = true)
public class FakerBuilder {

  private Locale locale = Locale.ENGLISH;
  private Random random;

  /**
   * Builds and returns a new instance of the Faker class with the configured locale and random
   * service.
   *
   * @return a new instance of Faker
   */
  public Faker build() {
    return new Faker(locale, random);
  }

  public FakeValuesService buildService() {
    return new FakeValuesService(locale, new RandomService(random));
  }

}
