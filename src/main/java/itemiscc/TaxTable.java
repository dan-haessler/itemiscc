package itemiscc;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Tax lookup class for the basket items.
 */
public class TaxTable {
  private static HashMap<String, Category> CATEGORY_TABLE = new HashMap<String, Category>();

  static {
    CATEGORY_TABLE.put("book", Category.BOOK);
    CATEGORY_TABLE.put("music CD", Category.OTHER);
    CATEGORY_TABLE.put("chocolate bar", Category.FOOD);
    CATEGORY_TABLE.put("box of chocolates", Category.FOOD);
    CATEGORY_TABLE.put("bottle of perfume", Category.OTHER);
    CATEGORY_TABLE.put("packet of headache pills", Category.MEDICAL);
  }

  private static final HashMap<Category, BigDecimal> BASIC_TAX_TABLE = new HashMap<Category, BigDecimal>();

  static {
    BASIC_TAX_TABLE.put(Category.BOOK, new BigDecimal("0.0"));
    BASIC_TAX_TABLE.put(Category.FOOD, new BigDecimal("0.0"));
    BASIC_TAX_TABLE.put(Category.MEDICAL, new BigDecimal("0.0"));
    BASIC_TAX_TABLE.put(Category.OTHER, new BigDecimal("0.1"));
  }

  private static final BigDecimal IMPORT_TAX = new BigDecimal("0.05");

  private TaxTable() {
  }

  /**
   * Determines the tax rate of an entry in the shopping basket based on
   * description and whether it is imported.
   */
  public static BigDecimal getTaxRate(String description, boolean imported) {
    Category category = CATEGORY_TABLE.getOrDefault(description, Category.OTHER);
    BigDecimal taxRate = BASIC_TAX_TABLE.get(category);
    if (imported) {
      taxRate = taxRate.add(IMPORT_TAX);
    }
    return taxRate;
  }
}
