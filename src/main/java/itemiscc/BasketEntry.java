package itemiscc;

import java.math.BigDecimal;

/**
 * A simple shopping basket entry.
 */
public class BasketEntry {
  private final int amount;
  private final boolean imported;
  private final String description;
  private final BigDecimal netPrice;
  private final BigDecimal grossPrice;

  /**
   * Constructs a new basket entry.
   *
   * @param amount      Amount of items as an integer.
   * @param imported    boolean whether it is imported.
   * @param description string description of item.
   * @param netPrice    net price/shelf price of item.
   * @param grossPrice  gross price of item.
   */
  public BasketEntry(int amount, boolean imported, String description, BigDecimal netPrice, BigDecimal grossPrice) {
    this.amount = amount;
    this.imported = imported;
    this.description = description;
    this.netPrice = netPrice;
    this.grossPrice = grossPrice;
  }

  /** 
   * Getter for the amount of products within this entry.
   *
   * @return amount of products.
   */
  public int getAmount() {
    return this.amount;
  }

  /** 
   * Getter whether this product of the entry was imported.
   *
   * @return boolean whether it is imported.
   */
  public boolean isImported() {
    return this.imported;
  }

  /** 
   * Description of this product.
   *
   * @return description as String
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Taxes of the product(s).
   *
   * @return BigDecimal of the total tax sum.
   */
  public BigDecimal getTaxes() {
    return this.grossPrice.subtract(this.netPrice);
  }

  /**
   * The gross price of the product(s).
   *
   * @return BigDecimal of the gross price.
   */
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
