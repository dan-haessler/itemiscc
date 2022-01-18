package itemiscc;

import java.util.ArrayList;

/**
 * Parser for the shopping basket and its items.
 */
public final class BasketParser {
  private BasketParser() {
  }

  /**
   * Parses a shopping basket string into a basket.
   *
   * @param basketString A string describing the shopping basket in the assumpted
   *                     pattern.
   * @return {@link Basket} containing pattern matched entries of the string.
   */
  public static Basket parse(String basketString) {
    return new Basket(new ArrayList<>());
  }
}
