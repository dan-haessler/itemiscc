# itemis coding challenge solution 1: Sales Taxes
This is my solution written in Java with the build tool Maven. 

# Assumptions
On the string patterns of the shopping basket:
* Every entry begins with a new line.
* May or may not start with "> " followed by n digits for the amount.
* The following string is the description which may contains "import" as a keyword for an imported article.
* ":" for gross prices
* "at" for shelf prices

# How to
Install: 
```
mvn install
```

Get checkstyle report and javadoc:
```
mvn site
```

Run:
```
java -jar target/SalesTaxes.jar
```