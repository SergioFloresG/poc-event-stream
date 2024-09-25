package com.mrgenis.poc.eventstream.common.faker.service;

import com.github.javafaker.Faker;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FakerSupplierService implements
    BiFunction<FakerSupplierService.SupplierType, String, Object> {

  private final Faker faker;


  public enum SupplierType {
    ONETIME,
    DYNAMIC
  }


  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Getter
  public static final class FakerValue {

    private final SupplierType type;
    private final Supplier<Object> supplier;

    @Override
    public String toString() {
      return supplier.get().toString();
    }
  }

  private Supplier<Object> onetimeSupplier(Supplier<Object> fakerSupplier) {
    final AtomicReference<Object> reference = new AtomicReference<>();
    reference.set(fakerSupplier.get());
    return reference::get;
  }

  private Supplier<Object> dynamicSupplier(Supplier<Object> fakerSupplier) {
    return fakerSupplier;
  }

  @Override
  public FakerValue apply(SupplierType supplierType, String service) {
    final String expression = String.format("#{%s}", service);
    Supplier<Object> fakerSupplier = () -> faker.expression(expression);

    return switch (supplierType) {
      case DYNAMIC -> new FakerValue(SupplierType.DYNAMIC, dynamicSupplier(fakerSupplier));
      case ONETIME -> new FakerValue(SupplierType.ONETIME, onetimeSupplier(fakerSupplier));
    };


  }

}
