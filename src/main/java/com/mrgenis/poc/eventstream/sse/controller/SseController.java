package com.mrgenis.poc.eventstream.sse.controller;

import com.mrgenis.poc.eventstream.sse.controller.response.StreamResponse;
import com.mrgenis.poc.eventstream.sse.mapper.StringToDynamicJsonObject;
import com.mrgenis.poc.eventstream.sse.mapper.ToServerSentEvent;
import com.mrgenis.poc.eventstream.sse.mapper.ToStreamResponseMapper;
import com.mrgenis.poc.eventstream.sse.usecase.LoremUseCase;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController()
@RequiredArgsConstructor
public class SseController {

  private final LoremUseCase loremUseCase;
  private final ToServerSentEvent<String> toStreamMapper;
  private final StringToDynamicJsonObject stringToDynamicJsonObject;

  @CrossOrigin(origins = "*")
  @PostMapping(path = "/stream-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ServerSentEvent<StreamResponse<String>>> streamEvent(@RequestBody String body) {

    int randomNum = (int) (Math.random() * 2) + 5;
    JSONObject jsonObject = stringToDynamicJsonObject.apply(body);

    /*List<String> messages =  loremUseCase.getLoremChunks();*/
    List<Object> messages = Collections.nCopies(randomNum, jsonObject);

    var mapper = new ToStreamResponseMapper<Object>();
    var toSSE = mapper.andThen(toStreamMapper);

    Supplier<ServerSentEvent<StreamResponse<String>>> lastMessage = () -> {
      Long id = mapper.getSequence().getAndIncrement();
      return ServerSentEvent.<StreamResponse<String>>builder()
          .event("END")
          .id(String.valueOf(id))
          .build();
    };

    var processMessages = Flux.fromIterable(messages)
        .delayElements(Duration.ofMillis(500))
        .map(toSSE);

    return processMessages
        .concatWith(Mono.fromSupplier(lastMessage));
  }
}
