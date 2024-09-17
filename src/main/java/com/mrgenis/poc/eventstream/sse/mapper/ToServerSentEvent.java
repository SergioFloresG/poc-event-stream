package com.mrgenis.poc.eventstream.sse.mapper;

import com.mrgenis.poc.eventstream.sse.controller.response.StreamResponse;
import java.util.function.Function;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;

/**
 * The ToServerSentEvent class implements the Function interface to convert a StreamResponse object
 * into a ServerSentEvent. This transformation is appropriate for handling server-sent events (SSE)
 * within an event-streaming context.
 */
@Service
public class ToServerSentEvent<T> implements
    Function<StreamResponse<T>, ServerSentEvent<StreamResponse<T>>> {

  @Override
  public ServerSentEvent<StreamResponse<T>> apply(StreamResponse<T> message) {
    return ServerSentEvent.<StreamResponse<T>>builder()
        .id(String.valueOf(message.getChunk().getId()))
        .event(message.getChunk().getEvent())
        .data(message)
        .build();
  }

}
