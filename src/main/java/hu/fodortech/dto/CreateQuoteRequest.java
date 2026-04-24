package hu.fodortech.dto;

import java.math.BigDecimal;

public class CreateQuoteRequest {

    private String title;

    private BigDecimal totalPrice;

    private BigDecimal estimatedCost;

    private Long clientId;

    public CreateQuoteRequest() {
    }

    public CreateQuoteRequest(String title, BigDecimal totalPrice, BigDecimal estimatedCost, Long clientId) {
        this.title = title;
        this.totalPrice = totalPrice;
        this.estimatedCost = estimatedCost;
        this.clientId = clientId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(BigDecimal estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

}
