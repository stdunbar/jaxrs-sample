package com.hotjoe.services.model;


import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


/**
 * A simple "model" object to take and return.
 *
 */
public class Product implements Serializable {
    private static final long serialVersionUID = -4728364803694212664L;

    private Integer productId;
    private String description;
    private String createDate;


    public Product() {
        OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        this.createDate = formatter.format(now);
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
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
