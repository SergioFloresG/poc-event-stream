package com.mrgenis.poc.eventstream.common.faker.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * A function that chunks a given paragraph into an array of string chunks. Each chunk contains a
 * random number of words within a specified range.
 */
public class LoremChunkParagraphFunction implements Function<String, String[]> {

  private static final Random RANDOM = new Random();
  private static final int MIN_CHUNK_SIZE = 10;
  private static final int MAX_CHUNK_SIZE = 50;


  /**
   * Chunks a given string into an array of string chunks based on random chunk sizes.
   *
   * @param s the input string to be chunked
   * @return an array of string chunks
   */
  @Override
  public String[] apply(String s) {
    String[] words = s.split("\\s+");
    int chunkSize = RANDOM.nextInt(MAX_CHUNK_SIZE - MIN_CHUNK_SIZE + 1) + MIN_CHUNK_SIZE;
    List<Integer> chunkSizes = distributeWords(words.length, chunkSize);
    List<String> chunks = chunkParagraph(words, chunkSizes);

    return chunks.toArray(new String[0]);
  }

  /**
   * Splits a given array of words into chunks based on the specified sizes.
   *
   * @param words      an array of words to be chunked
   * @param chunkSizes a list of integers representing the desired sizes of each chunk
   * @return a list of strings where each string is a chunk formed by concatenating the given words
   * according to the specified chunk sizes
   */
  private List<String> chunkParagraph(String[] words, List<Integer> chunkSizes) {
    Queue<String> wordsQueue = new LinkedList<>(Arrays.asList(words));
    return chunkSizes.stream().map(size -> {
      StringBuilder chunk = new StringBuilder();

      IntStream.range(0, size).filter(i -> !wordsQueue.isEmpty()).forEach(i -> {
        if (i > 0) {
          chunk.append(' ');
        }
        chunk.append(wordsQueue.poll());
      });
      chunk.append(' ');
      return chunk.toString();
    }).toList();
  }


  /**
   * Distributes a total number of words into a given number of chunks randomly.
   *
   * @param totalWords the total number of words to be distributed
   * @param chunkSize  the number of chunks into which the words should be dispersed
   * @return a list of integers where each integer represents the number of words in each chunk
   */
  private List<Integer> distributeWords(int totalWords, int chunkSize) {
    List<Integer> chunkSizes = new ArrayList<>(Collections.nCopies(chunkSize, 1));
    int remainingWords = totalWords - chunkSize;

    while (remainingWords > 0) {
      int randomIndex = RANDOM.nextInt(chunkSize);
      chunkSizes.set(randomIndex, chunkSizes.get(randomIndex) + 1);
      remainingWords--;
    }

    return chunkSizes;
  }

}
