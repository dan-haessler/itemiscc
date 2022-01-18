package itemiscc;

import java.math.BigDecimal;
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
    this.entries.stream().map(BasketEntry::toString).forEach(string -> builder.append(string).append("\n"));
    builder.append("Sales Taxes: ").append(this.getTotalTaxes()).append("\n");
    builder.append("Total: ").append(this.getTotal());
    return builder.toString();
  }
  
  private BigDecimal getTotalTaxes() {
    return this.entries.stream().map(BasketEntry::getTaxes).reduce(new BigDecimal("0.0"), BigDecimal::add);
  }

  private BigDecimal getTotal() {
    return this.entries.stream().map(BasketEntry::getGrossPrice).reduce(new BigDecimal("0.0"), BigDecimal::add);
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
