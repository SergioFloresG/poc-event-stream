package com.mrgenis.poc.eventstream.sse.usecase;

import com.mrgenis.poc.eventstream.common.faker.function.LoremChunkParagraphFunction;
import com.mrgenis.poc.eventstream.common.faker.supplier.LoremSupplierParagraphs;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoremUseCase {

  private final LoremSupplierParagraphs paragraphs;
  LoremChunkParagraphFunction chunkParagraphFunction = new LoremChunkParagraphFunction();

  public List<String> getLoremChunks() {
    return List.of(chunkParagraphFunction.apply(paragraphs.get()));
  }


}
