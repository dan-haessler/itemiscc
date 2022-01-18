package itemiscc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Parser for the shopping basket and its items.
 */
public final class BasketParser {
  // Expected pattern of the basket/receipt entry.
  private static final Pattern PATTERN = Pattern.compile("(?:>\\s)?(\\d+)\\s((?:\\w*\\s*)*)\\s?(at|:)\\s(\\d+.\\d+)");
  private static final MathContext PRECISION = MathContext.DECIMAL128;
  private static final BigDecimal TAX_PRECISION = new BigDecimal("20");

  private BasketParser() {
  }

  /**
   * Parses a shopping basket string into a basket. If ":" is used it is gross
   * price, otherwise net price.
   *
   * @param basketString A string describing the shopping basket in the assumpted
   *                     pattern.
   * @return {@link Basket} containing pattern matched entries of the string.
   */
  public static Basket parse(String basketString) throws NumberFormatException {
    return new Basket(
        Arrays.stream(basketString.split("\n"))
            .map(PATTERN::matcher)
            .filter(Matcher::matches)
            .map(BasketParser::toBasketEntry)
            .collect(Collectors.toList()));
  }

  private static BasketEntry toBasketEntry(final Matcher matcher) throws NumberFormatException {
    int amount = Integer.valueOf(matcher.group(1));
    String description = matcher.group(2).strip();
    String newDescription = Arrays.stream(description.split(" "))
        .filter(word -> !word.contains("import"))
        .collect(Collectors.joining(" "));
    boolean imported = !description.equals(newDescription);
    boolean isNetPrice = matcher.group(3).trim().equals("at");
    BigDecimal taxRate = TaxTable.getTaxRate(newDescription, imported);
    BigDecimal netPrice;
    BigDecimal grossPrice;
    BigDecimal taxAmount;

    if (isNetPrice) {
      netPrice = new BigDecimal(matcher.group(4));
      taxAmount = netPrice.multiply(taxRate)
          .multiply(TAX_PRECISION).setScale(0, RoundingMode.UP)
          .divide(TAX_PRECISION, PRECISION);
      grossPrice = netPrice.add(taxAmount);
    } else {
      grossPrice = new BigDecimal(matcher.group(4));
      netPrice = grossPrice.divide((new BigDecimal("1.0")).add(taxRate), PRECISION);
      taxAmount = grossPrice.subtract(netPrice)
          .multiply(TAX_PRECISION).setScale(0, RoundingMode.UP)
          .divide(TAX_PRECISION, PRECISION);
      netPrice = grossPrice.subtract(taxAmount);
    }
    return new BasketEntry(amount, imported, newDescription, netPrice, grossPrice);
  }
}
