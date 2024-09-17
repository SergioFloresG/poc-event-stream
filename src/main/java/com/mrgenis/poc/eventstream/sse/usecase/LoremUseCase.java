package com.mrgenis.poc.eventstream.sse.usecase;

import com.mrgenis.poc.eventstream.common.lorem.function.LoremChunkParagraphFunction;
import com.mrgenis.poc.eventstream.common.lorem.supplier.LoremSupplierParagraphs;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LoremUseCase {

  LoremSupplierParagraphs paragraphs = new LoremSupplierParagraphs();
  LoremChunkParagraphFunction chunkParagraphFunction = new LoremChunkParagraphFunction();

  public List<String> getLoremChunks() {
    return List.of(chunkParagraphFunction.apply(paragraphs.get()));
  }


}
