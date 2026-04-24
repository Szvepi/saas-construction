package hu.fodortech.dto;

import hu.fodortech.entity.Quote;
import java.math.BigDecimal;
import java.util.UUID;

public class QuoteResponse {

    private UUID id;

    private String title;

    private String status;

    private BigDecimal totalPrice;

    private BigDecimal estimatedCost;

    private BigDecimal profit;

    public QuoteResponse() {
    }

    public QuoteResponse(UUID id, String title, String status, BigDecimal totalPrice, BigDecimal estimatedCost, BigDecimal profit) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.totalPrice = totalPrice;
        this.estimatedCost = estimatedCost;
        this.profit = profit;
    }

    public static QuoteResponse fromQuote(Quote quote) {
        BigDecimal profit = quote.getTotalPrice().subtract(quote.getEstimatedCost());

        return new QuoteResponse(
            quote.getId(),
            quote.getTitle(),
            quote.getStatus().toString(),
            quote.getTotalPrice(),
            quote.getEstimatedCost(),
            profit
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

}
