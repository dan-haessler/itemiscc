package itemiscc;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * Unit test for given sales taxes examples.
 */
public class SalesTaxesTest {
  /**
   * Test shopping basket parsing with net prices.
   */
  @Test
  public void testNetBasketParsing() {
    Basket input = BasketParser.parse(String.join("\n",
        "> 1 book at 10.0",
        "> 1 music CD at 5.0",
        "> 1 imported box of chocolates at 20.00",
        "> 1 box of imported chocolates at 20.00"));

    BasketEntry book = new BasketEntry(1, false, "book", new BigDecimal("10.00"), new BigDecimal("10.0"));
    BasketEntry musicCd = new BasketEntry(1, false, "music CD", new BigDecimal("5.00"), new BigDecimal("5.5"));
    BasketEntry choc = new BasketEntry(1, true, "box of chocolates", new BigDecimal("20.0"), new BigDecimal("21.0"));
    Basket expected = new Basket(new ArrayList<>(Arrays.asList(book, musicCd, choc, choc)));

    assertEquals(input, expected);
  }

  /**
   * Test shopping basket parsing with gross prices.
   */
  @Test
  public void testGrossBasketParsing() {
    Basket input = BasketParser.parse(String.join("\n",
        "> 1 book: 10.0",
        "> 1 music CD: 5.50",
        "> 1 imported box of chocolates: 21.00",
        "> 1 box of imported chocolates: 21.00"));

    BasketEntry book = new BasketEntry(1, false, "book", new BigDecimal("10.00"), new BigDecimal("10.0"));
    BasketEntry musicCd = new BasketEntry(1, false, "music CD", new BigDecimal("5.00"), new BigDecimal("5.5"));
    BasketEntry choc = new BasketEntry(1, true, "box of chocolates", new BigDecimal("20.0"), new BigDecimal("21.0"));
    Basket expected = new Basket(new ArrayList<>(Arrays.asList(book, musicCd, choc, choc)));

    assertEquals(input, expected);
  }

  /**
   * Test tax lookup based on description, import bool.
   */
  @Test
  public void testTaxTable() {
    BigDecimal noTax = new BigDecimal("0.00");
    BigDecimal importedTax = new BigDecimal("0.05");
    BigDecimal basicTax = new BigDecimal("0.10");
    BigDecimal fullTax = new BigDecimal("0.15");

    // Net and gross prices do not matter here.
    assert(TaxTable.getTaxRate("book", false).compareTo(noTax) == 0);
    assert(TaxTable.getTaxRate("chocolate bar", false).compareTo(noTax) == 0);
    assert(TaxTable.getTaxRate("packet of headache pills", false).compareTo(noTax) == 0);

    assert(TaxTable.getTaxRate("book", true).compareTo(importedTax) == 0);
    assert(TaxTable.getTaxRate("music CD", false).compareTo(basicTax) == 0);
    assert(TaxTable.getTaxRate("music CD", true).compareTo(fullTax) == 0);
  }

  /**
   * Test first simple example. No imports, just some basic sales taxes.
   */
  @Test
  public void testFirstInput() throws NumberFormatException {
    String inputString = String.join("\n",
        "> 1 book at 12.49",
        "> 1 music CD at 14.99",
        "> 1 chocolate bar at 0.85");
    String expectedString = String.join("\n",
        "> 1 book: 12.49",
        "> 1 music CD: 16.49",
        "> 1 chocolate bar: 0.85",
        "> Sales Taxes: 1.50",
        "> Total: 29.83");
    Basket input = BasketParser.parse(inputString);
    Basket expected = BasketParser.parse(expectedString);

    assertEquals(input, expected);
    assertEquals(input.getReceipt(), expected.getReceipt());
  }

  /**
   * Test second example. Import taxes and basic sales taxes.
   */
  @Test
  public void testSecondInput() throws NumberFormatException {
    String inputString = String.join("\n",
        "> 1 imported box of chocolates at 10.00",
        "> 1 imported bottle of perfume at 47.50");
    String expectedString = String.join("\n",
        "> 1 imported box of chocolates: 10.50",
        "> 1 imported bottle of perfume: 54.65",
        "> Sales Taxes: 7.65",
        "> Total: 65.15");

    Basket input = BasketParser.parse(inputString);
    Basket expected = BasketParser.parse(expectedString);

    assertEquals(input, expected);
    assertEquals(input.getReceipt(), expected.getReceipt());
  }

  /**
   * Test third example. Import taxes and basic sales taxes.
   */
  @Test
  public void testThirdInput() throws NumberFormatException {
    String inputString = String.join("\n",
        "> 1 imported bottle of perfume at 27.99",
        "> 1 bottle of perfume at 18.99",
        "> 1 packet of headache pills at 9.75",
        "> 1 box of imported chocolates at 11.25");
    String expectedString = String.join("\n",
        "> 1 imported bottle of perfume: 32.19",
        "> 1 bottle of perfume: 20.89",
        "> 1 packet of headache pills: 9.75",
        "> 1 imported box of chocolates: 11.85",
        "> Sales Taxes: 6.70",
        "> Total: 74.68");

    Basket input = BasketParser.parse(inputString);
    Basket expected = BasketParser.parse(expectedString);

    assertEquals(input, expected);
    assertEquals(input.getReceipt(), expected.getReceipt());
  }
}
