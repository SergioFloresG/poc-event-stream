package com.mrgenis.poc.eventstream.sse.mapper;

import com.mrgenis.poc.eventstream.sse.controller.response.StreamResponse;
import java.util.Map;
import java.util.function.Function;
import org.json.JSONObject;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;

/**
 * The ToServerSentEvent class implements the Function interface to convert a StreamResponse object
 * into a ServerSentEvent. This transformation is appropriate for handling server-sent events (SSE)
 * within an event-streaming context.
 */
@Service
public class ToServerSentEvent implements
    Function<StreamResponse<JSONObject>, ServerSentEvent<StreamResponse<Map<String, Object>>>> {

  @Override
  public ServerSentEvent<StreamResponse<Map<String, Object>>> apply(
      StreamResponse<JSONObject> message) {
    JSONObject data = message.getData();
    Map<String, Object> dataMap = data.toMap();

    StreamResponse<Map<String, Object>> response
        = new StreamResponse<>(dataMap, message.getChunk());

    return ServerSentEvent.<StreamResponse<Map<String, Object>>>builder()
        .id(String.valueOf(message.getChunk().getId()))
        .event(message.getChunk().getEvent())
        .data(response)
        .build();
  }

  public ServerSentEvent<StreamResponse<Map<String, Object>>> lastMessage(Long id) {
    return ServerSentEvent.<StreamResponse<Map<String, Object>>>builder()
        .id(String.valueOf(id))
        .event("END")
        .build();
  }

}
