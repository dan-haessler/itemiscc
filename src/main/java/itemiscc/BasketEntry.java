package itemiscc;

import java.math.BigDecimal;

/**
 * A simple shopping basket entry.
 */
public class BasketEntry {
  private final Integer amount;
  private final Boolean imported;
  private final String description;
  private final BigDecimal netPrice;
  private final BigDecimal grossPrice;

  /**
   * Constructs a new basket entry.
   *
   * @param amount      Amount of items as Integer.
   * @param imported    bool whether it is imported.
   * @param description string description of item.
   * @param netPrice    net price/shelf price of item.
   * @param grossPrice  gross price of item.
   */
  public BasketEntry(Integer amount, Boolean imported, String description, BigDecimal netPrice, BigDecimal grossPrice) {
    this.amount = amount;
    this.imported = imported;
    this.description = description;
    this.netPrice = netPrice;
    this.grossPrice = grossPrice;
  }

  public int getAmount() {
    return this.amount.intValue();
  }

  public boolean isImported() {
    return this.imported.booleanValue();
  }

  public String getDescription() {
    return this.description;
  }

  public BigDecimal getTaxes() {
    return this.grossPrice.subtract(this.netPrice);
  }

  public BigDecimal getGrossPrice() {
    return this.grossPrice;
  }

  /**
   * Comparison for two basket entries. The money value comparison uses
   * BigDecimals compareTo() to consider different scales used by
   * the BigDecimal class.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    final BasketEntry other = (BasketEntry) obj;
    return other.amount == this.amount
        && other.imported == this.imported
        && other.description.equals(this.description)
        && other.netPrice.compareTo(this.netPrice) == 0
        && other.grossPrice.compareTo(this.grossPrice) == 0;
  }

  /**
   * Returns a string with the assumpted gross price pattern.
   */
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(this.amount).append(" ");
    if (this.imported) {
      builder.append("imported ");
    }
    builder.append(this.description).append(": ").append(this.grossPrice);
    return builder.toString();
  }
}
