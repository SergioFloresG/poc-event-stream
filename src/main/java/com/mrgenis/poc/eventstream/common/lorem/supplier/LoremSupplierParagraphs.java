package com.mrgenis.poc.eventstream.common.lorem.supplier;

import com.github.javafaker.Faker;
import java.util.function.Supplier;

/**
 * LoremSupplierParagraphs is a component that supplies a random paragraph of lorem ipsum text. It
 * implements the Supplier interface, providing a single method to generate the text.
 */
public class LoremSupplierParagraphs implements Supplier<String> {

  private static final Faker faker = new Faker();

  /**
   * Generates and returns a random paragraph of lorem ipsum text.
   *
   * @return a randomly generated paragraph of lorem ipsum text.
   */
  @Override
  public String get() {
    return faker.lorem().sentence(60);
  }
}
