package au.com.anz;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// import java.util.stream.*;

class DepTarget {
  Integer rank;
  String key;
  String vaue;

  public DepTarget(Integer rank, String key, String vaue) {
    this.rank = rank;
    this.key = key;
    this.vaue = vaue;
  }
}

class Result {

  /*
   * Complete the 'findDirty' function below.
   *
   * The function is expected to return a STRING_ARRAY.
   * The function accepts following parameters:
   *  1. 2D_STRING_ARRAY data
   *  2. STRING_ARRAY files
   */

  public static List<String> findDirty(List<List<String>> data, List<String> files) {

    List<DepTarget> depTargets =
        IntStream.range(0, data.size())
            .mapToObj(
                entryIdx -> {
                  List<String> k = data.get(entryIdx);
                  return new DepTarget(entryIdx, k.get(1), k.get(0));
                })
            .sorted(Comparator.comparing((DepTarget depTarget) -> depTarget.rank))
            .collect(Collectors.toList());

    Deque<String> deque = new ArrayDeque<>(files);
    List<String> results = new ArrayList<>();

    while (!deque.isEmpty()) {
      String head = deque.pollFirst();
      List<DepTarget> dependencies =
          depTargets
              .stream()
              .filter(depTarget -> Objects.equals(depTarget.key, head))
              .collect(Collectors.toList());

      if (!dependencies.isEmpty()) {
        dependencies.forEach(
            dependency -> {
              results.add(dependency.vaue);
              deque.addFirst(dependency.vaue);
            });
      }
    }

    return results;
  }
}

public class Solution {
  public static void main(String[] args) {

    List<List<String>> data =
        List.of(
            List.of("prog1", "a.o"),
            List.of("prog1", "b.o"),
            List.of("a.o", "a.c"),
            List.of("b.o", "b.c"),
            List.of("prog2", "b.o"),
            List.of("prog2", "c.o"),
            List.of("c.o", "c.c"));

    List<String> files = List.of("a.o", "b.c");

    List<String> result = Result.findDirty(data, files);

    System.out.println(result);
  }
}
