package itemiscc;

/**
 * Main class of the application.
 */
public class SalesTaxes {
  /**
   * Prints out receipt details for the example shopping baskets.
   */
  public static void main(String[] args) {
    String firstInput = String.join("\n",
        "> 1 book at 12.49",
        "> 1 music CD at 14.99",
        "> 1 chocolate bar at 0.85");
    String secondInput = String.join("\n",
        "> 1 imported box of chocolates: 10.50",
        "> 1 imported bottle of perfume: 54.65",
        "> Sales Taxes: 7.65",
        "> Total: 65.15");
    String thirdInput = String.join("\n",
        "> 1 imported bottle of perfume at 27.99",
        "> 1 bottle of perfume at 18.99",
        "> 1 packet of headache pills at 9.75",
        "> 1 box of imported chocolates at 11.25");

    Basket firstBasket = BasketParser.parse(firstInput);
    Basket secondBasket = BasketParser.parse(secondInput);
    Basket thirdBasket = BasketParser.parse(thirdInput);

    System.out.println("Output 1:\n\n" + firstBasket.getReceipt() + "\n");
    System.out.println("Output 2:\n\n" + secondBasket.getReceipt() + "\n");
    System.out.println("Output 3:\n\n" + thirdBasket.getReceipt() + "\n");
  }
}
