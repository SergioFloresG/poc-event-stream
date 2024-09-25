package com.mrgenis.poc.eventstream.sse.mapper;

import static com.mrgenis.poc.eventstream.common.faker.service.FakerSupplierService.SupplierType;

import com.mrgenis.poc.eventstream.common.faker.service.FakerSupplierService;
import com.mrgenis.poc.eventstream.common.faker.service.FakerSupplierService.FakerValue;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StringToDynamicJsonObject implements Function<String, JSONObject> {

  private static final String REGEX_AT_SIGN = "^@(.+)@$";
  private static final String REGEX_HASH_SIGN = "^#(.+)#$";
  private final FakerSupplierService fakerSupplierService;

  @Override
  public JSONObject apply(String body) throws JSONException {
    return transformObject(new JSONObject(body));
  }

  private JSONObject transformObject(final JSONObject object) {

    object.keySet().forEach(key -> {
      Object value = object.get(key);
      if (value instanceof JSONArray array) {
        transformArray(array);
      } else if (value instanceof JSONObject objectValue) {
        transformObject(objectValue);
      } else if (value instanceof String stringValue) {

        overrideIfFaker(
            (valueToPut) -> object.put(key, valueToPut),
            stringValue
        );

      }
    });
    return object;
  }

  private void transformArray(JSONArray array) {
    for (int i = 0; i < array.length(); i++) {
      Object value = array.get(i);
      if (value instanceof JSONObject object) {
        transformObject(object);
      } else if (value instanceof JSONArray subarray) {
        transformArray(subarray);
      } else if (value instanceof String stringValue) {
        int finalI = i;
        overrideIfFaker(
            (valueToPut) -> array.put(finalI, valueToPut),
            stringValue
        );
      }

    }
  }

  private void overrideIfFaker(Consumer<Object> setter, String stringValue) {
    FakerValue fakerValue = null;

    if (stringValue.matches(REGEX_AT_SIGN)) {
      // Se trata de un valor que varía en cada ejecución.
      String fakerService = ((String) stringValue).replaceAll(REGEX_AT_SIGN, "$1");
      fakerValue = fakerSupplierService.apply(SupplierType.DYNAMIC, fakerService);
    } else if (stringValue.matches(REGEX_HASH_SIGN)) {
      // Se trata de un valor que es constante en cada ejecución.
      String fakerService = ((String) stringValue).replaceAll(REGEX_HASH_SIGN, "$1");
      fakerValue = fakerSupplierService.apply(SupplierType.ONETIME, fakerService);
    }

    if (fakerValue != null) {
      setter.accept(fakerValue);
    }
  }


}
