package com.cloud.bookshop.web.future;

import java.text.NumberFormat;

import static com.cloud.bookshop.web.future.Shop.delay;

public class DiscountDemo {
    public static String applyDiscount(Quote quote) {
        return quote.getShop() + " price is " + apply(quote.getPrice(), quote.getDiscount());
    }

    private static String apply(double price, Discount discount) {
        delay();
        return NumberFormat.getInstance().format(price * (100 - discount.getPercent()) / 100);
    }
}
