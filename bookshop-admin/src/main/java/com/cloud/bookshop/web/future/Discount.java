package com.cloud.bookshop.web.future;

import lombok.Data;

public enum Discount {
    NONE(0),
    SILVER(5),
    GOLDEN(10),
    PLATINUM(15),
    DIAMOND(20);
    private final int percent;

    Discount(int percent) {
        this.percent = percent;
    }

    public int getPercent() {
        return this.percent;
    }
}
