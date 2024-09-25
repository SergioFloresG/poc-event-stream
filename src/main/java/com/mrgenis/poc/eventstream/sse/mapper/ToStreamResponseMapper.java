package com.mrgenis.poc.eventstream.sse.mapper;

import com.mrgenis.poc.eventstream.sse.controller.response.StreamChunk;
import com.mrgenis.poc.eventstream.sse.controller.response.StreamResponse;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import lombok.Getter;

/**
 * The SequentialStringToStreamResponseMapper class is a specialized Function that transforms a
 * given String message into a StreamResponse, with a sequential ID using an AtomicLong for
 * generating unique IDs. This class helps in streaming data by wrapping each message with metadata
 * in the form of a StreamChunk.
 */
@Getter
public class ToStreamResponseMapper<T> implements Function<T, StreamResponse<String>> {

  private final AtomicLong sequence;

  /**
   * Constructs a SequentialStringToStreamResponseMapper with an initialValue for the sequence.
   *
   * @param initialValue the starting point for the sequence which will be used to generate unique
   *                     IDs
   */
  public ToStreamResponseMapper(long initialValue) {
    this.sequence = new AtomicLong(initialValue);
  }

  /**
   * Constructs a SequentialStringToStreamResponseMapper with the given AtomicLong value to manage
   * the sequence.
   *
   * @param value an AtomicLong which will be used to generate unique sequential IDs for each
   *              StreamChunk associated with the StreamResponse.
   */
  public ToStreamResponseMapper(AtomicLong value) {
    this.sequence = value;
  }

  /**
   * Default constructor for SequentialStringToStreamResponseMapper that initializes the sequence
   * with a default value of 1.
   */
  public ToStreamResponseMapper() {
    this(1L);
  }


  /**
   * Transforms a given message into a StreamResponse, wrapping it with a StreamChunk containing
   * metadata and a sequential ID.
   *
   * @param message the message to be transformed into a StreamResponse
   * @return a StreamResponse containing the original message and a StreamChunk with metadata
   */
  @Override
  public StreamResponse<String> apply(T message) {
    var chunk = StreamChunk.builder()
        .id(sequence.getAndIncrement())
        .event("MESSAGE")
        .build();

    String data = message.toString();
    return StreamResponse.<String>builder()
        .data(data)
        .chunk(chunk)
        .build();
  }


}
