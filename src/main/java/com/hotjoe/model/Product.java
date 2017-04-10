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

    private Integer productId;
    private String description;
    private OffsetDateTime createDate;


    public Product() {
        this.createDate = OffsetDateTime.now(ZoneId.of("UTC"));
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateDate() {
        return DateTimeFormatter.ISO_INSTANT.format(this.createDate);
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }
}
