package com.mrgenis.poc.eventstream.sse.controller.response;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class StreamResponse<T> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private T data;

  private StreamChunk chunk;
}
