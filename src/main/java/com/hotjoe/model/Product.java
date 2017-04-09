package com.hotjoe.model;


import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


/**
 * A simple "model" object to take and return.
 *
 */
public class Product implements Serializable {
    private static final long serialVersionUID = -4728364803694212664L;

    private int productId;
    private String identifier;
    private OffsetDateTime createDate;


    public Product() {
        this.createDate = OffsetDateTime.now(ZoneId.of("UTC"));
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCreateDate() {
        return DateTimeFormatter.ISO_INSTANT.format(this.createDate);
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }
}
