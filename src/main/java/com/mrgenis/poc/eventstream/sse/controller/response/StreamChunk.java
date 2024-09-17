package com.mrgenis.poc.eventstream.sse.controller.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")

public class StreamChunk {

  private Long id;
  private String event;
  @Default
  private LocalDateTime timestamp = LocalDateTime.now();


}
