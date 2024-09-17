package com.mrgenis.poc.eventstream.sse.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class StreamResponse<T> {

  private T data;

  private StreamChunk chunk;
}
