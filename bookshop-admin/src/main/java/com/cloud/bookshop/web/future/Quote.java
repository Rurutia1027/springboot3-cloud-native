package com.cloud.bookshop.web.future;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
@AllArgsConstructor
public class Quote {
    private final String shop;
    private final double price;
    private final Discount discount;

    public static Quote parse(String content) {
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(content, ":");
        return new Quote(items[0], Double.parseDouble(items[1]), Discount.valueOf(items[2]));
    }
}
