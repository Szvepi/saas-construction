package hu.fodortech.dto;

import java.util.List;

public class AiResponse {

    private String description;

    private List<QuoteItemDto> items;

    public AiResponse() {
    }

    public AiResponse(String description, List<QuoteItemDto> items) {
        this.description = description;
        this.items = items;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuoteItemDto> getItems() {
        return items;
    }

    public void setItems(List<QuoteItemDto> items) {
        this.items = items;
    }
}
