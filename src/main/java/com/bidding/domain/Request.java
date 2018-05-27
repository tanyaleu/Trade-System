package com.bidding.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Request.
 */
@Entity
@Table(name = "request")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;
    public static int SELL = 0;

    public static int BUY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "product", nullable = false)
    private String product;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull
    @Column(name = "items_count", nullable = false)
    private Integer itemsCount;

    @NotNull
    @Column(name = "ts_type", nullable = false)
    private Integer type;

    @NotNull
    @Column(name = "quality", nullable = false)
    private Integer quality;

    @ManyToOne
    private User requester;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public Request product(String product) {
        this.product = product;
        return this;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getPrice() {
        return price;
    }

    public Request price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getItemsCount() {
        return itemsCount;
    }

    public Request itemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
        return this;
    }

    public void setItemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
    }

    public Integer getType() {
        return type;
    }

    public Request type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getQuality() {
        return quality;
    }

    public Request quality(Integer quality) {
        this.quality = quality;
        return this;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public User getRequester() {
        return requester;
    }

    public Request requester(User user) {
        this.requester = user;
        return this;
    }

    public void setRequester(User user) {
        this.requester = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Request request = (Request) o;
        if (request.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), request.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", product='" + getProduct() + "'" +
            ", price=" + getPrice() +
            ", itemsCount=" + getItemsCount() +
            ", type=" + getType() +
            ", quality=" + getQuality() +
            "}";
    }
    public void takeResult() {
        System.out.println("Follow request completed for login:" + requester.getLogin());
        System.out.println("product:" + getProduct());
        System.out.println("count:" + getItemsCount());
        System.out.println("type:" +getType());
        System.out.println("price:" + getPrice());
    }
}
