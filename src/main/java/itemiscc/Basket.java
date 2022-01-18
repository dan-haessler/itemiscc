package itemiscc;

import java.util.List;

/**
 * Represents the shopping basket.
 */
public class Basket {
  private final List<BasketEntry> entries;

  /**
   * Creates a basket.
   *
   * @param entries basket items as list.
   */
  public Basket(final List<BasketEntry> entries) {
    this.entries = entries;
  }

  /**
   * Builds a receipt string containing the basket string representation, sales
   * taxes and the total sum.
   *
   * @return String with basket items using gross price, total taxes and total
   *         amount.
   */
  public String getReceipt() {
    StringBuilder builder = new StringBuilder();
    return builder.toString();
  }

  /**
   * A basket is equal when the basket entries are equal.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    return this.entries.equals(((Basket) obj).entries);
  }
}
