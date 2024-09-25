package com.mrgenis.poc.eventstream.common.faker.supplier;

import com.github.javafaker.Faker;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * LoremSupplierParagraphs is a component that supplies a random paragraph of lorem ipsum text. It
 * implements the Supplier interface, providing a single method to generate the text.
 */
@Component
@RequiredArgsConstructor
public class LoremSupplierParagraphs implements Supplier<String> {

  private final Faker faker;

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
