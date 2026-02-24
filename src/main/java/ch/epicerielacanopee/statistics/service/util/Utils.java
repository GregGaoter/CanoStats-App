package ch.epicerielacanopee.statistics.service.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

  /**
   * Returns all elements that appear most frequently in the given list.
   * <p>
   * The method computes the frequency of each distinct string in the input list,
   * determines the highest occurrence count, and returns all strings whose
   * frequency matches this maximum. If several elements share the same highest
   * frequency, they are all included in the returned list (ex æquo).
   * <p>
   * The order of elements in the returned list corresponds to the iteration
   * order of the underlying frequency map, which is not guaranteed to be sorted.
   *
   * @param items the list of strings to analyze; must not be {@code null}
   * @return a list containing all strings with the highest frequency;
   *         never {@code null}, but may be empty if {@code items} is empty
   * @throws NullPointerException if {@code items} is {@code null}
   */
  public static List<String> commonestAll(List<String> items) {
    Map<String, Long> freq = items.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting()));

    long max = Collections.max(freq.values());

    return freq.entrySet().stream()
        .filter(e -> e.getValue() == max)
        .map(Map.Entry::getKey)
        .toList();
  }

  public static String getProductUnit(String vente) {
    if (vente == null)
      return "Inconnu";
    return vente.equals("Au poids") ? "kg" : "p";
  }

  public static String roundByUnit(double value, String unit) {
    return switch (unit) {
      case "kg" -> String.format("%.1f", value);
      case "p" -> String.format("%.0f", value);
      default -> String.format("%.1f", value);
    };
  }
}
